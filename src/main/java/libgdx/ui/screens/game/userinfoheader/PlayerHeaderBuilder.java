package libgdx.ui.screens.game.userinfoheader;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.BackButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.services.dbapi.UserGamesDbApiService;
import libgdx.utils.ScreenDimensionsManager;

public class PlayerHeaderBuilder {

    public static final String STAR_IMAGE_TABLE = "STAR_IMAGE_TABLE";
    private static final String ACTOR_NAME_ACTION_TABLE = "playerActionTableTable";
    private static final String ACTOR_NAME_FILLCORRECTANSWERS_TABLE = "playerFillCorrectAnswersTable";
    private static final String ACTOR_NAME_FILLALLANSWERS_TABLE = "playerAllAnswersTable";
    private static final String ACTOR_NAME_SCORE_LABEL = "scoreLabel";

    private MyWrappedLabel scoreLabel;
    private BaseUserInfo baseUserInfo;
    private boolean withAnswerGoal;
    private int gameTotalNrOfQuestions;
    private Resource fillCorrectAnswersTableColorRes;
    private boolean smallDisplay = false;

    PlayerHeaderBuilder(BaseUserInfo baseUserInfo, int gameTotalNrOfQuestions) {
        this.baseUserInfo = baseUserInfo;
        this.gameTotalNrOfQuestions = gameTotalNrOfQuestions;
        this.fillCorrectAnswersTableColorRes = Resource.game_user_header_background_green;
    }

    public PlayerHeaderBuilder withScoreLabel(BaseUserInfo opponentAgainstToShowScore) {
        if (opponentAgainstToShowScore != null) {
            scoreLabel = new MyWrappedLabel(String.valueOf(new UserGamesDbApiService().selectTotalGamesUser1AgainstUser2(baseUserInfo.getId(), opponentAgainstToShowScore.getId())));
            scoreLabel.setAlignment(Align.center);
            scoreLabel.setName(getScoreLabelName(baseUserInfo));
        }
        return this;
    }

    public PlayerHeaderBuilder setSmallDisplay(boolean smallDisplay) {
        this.smallDisplay = smallDisplay;
        return this;
    }

    public PlayerHeaderBuilder withAnswerGoal(boolean withAnswerGoal) {
        this.withAnswerGoal = withAnswerGoal;
        return this;
    }

    public PlayerHeaderBuilder withFillCorrectAnswersTableColorRes(Resource fillCorrectAnswersTableColorRes) {
        this.fillCorrectAnswersTableColorRes = fillCorrectAnswersTableColorRes;
        return this;
    }

    public PlayerHeader build() {
        PlayerHeader playerHeader = createPlayerHeaderInstance();
        float width = ScreenDimensionsManager.getScreenWidthValue(95);
        Stack stack = new Stack();
        stack.setWidth(width);
        stack.add(createBackgroundTable());
        stack.add(createActionTable());
        stack.add(createUserInfoTable(width));
        playerHeader.add(stack);
        return playerHeader;
    }

    private PlayerHeader createPlayerHeaderInstance() {
        return gameTotalNrOfQuestions > 1 ? new MultipleQuestionPlayerHeader(baseUserInfo, gameTotalNrOfQuestions) : new SingleQuestionPlayerHeader(baseUserInfo);
    }

    public static String getStarImageName(int index, int baseUserInfoId) {
        return STAR_IMAGE_TABLE + index + baseUserInfoId;
    }

    private Table createBackgroundTable() {
        Table table = new Table();
        table.setBackground(GraphicUtils.getNinePatch(Resource.game_user_header_background_default));
        return table;
    }

    private Table createUserInfoTable(float width) {
        Dimen profilePictureSideDimen = smallDisplay ? Dimen.side_game_player_header_profile_picture_small : Dimen.side_game_player_header_profile_picture;
        UserInfo userInfo = new UserInfo(baseUserInfo, profilePictureSideDimen);
        Table table = new Table();
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            MyButton backBtn = new BackButtonBuilder().createScreenBackButton(TournamentGame.getInstance().getAbstractScreen());
            width = width - backBtn.getWidth();
            table.add(backBtn).height(backBtn.getWidth()).width(backBtn.getWidth());
        }
        if (scoreLabel != null) {
            float scoreWidth = width / 10;
            width = width - scoreWidth;
            table.add(scoreLabel).align(Align.center).width(scoreWidth);
        }
        if (!smallDisplay) {
            table.add(userInfo.getProfilePictureContainer()).padLeft(ScreenDimensionsManager.getScreenWidthValue(0.5f)).width(profilePictureSideDimen.getDimen());
        }
        float incrementTableWidth = width - profilePictureSideDimen.getDimen();
        Table usernameAndActionsTable = createUsernameAndActionsTable(userInfo.getUserNameLabel(), incrementTableWidth);
        Stack stack = new Stack();
        stack.addActor(usernameAndActionsTable);
        if (withAnswerGoal) {
            processGoalTable(userInfo, incrementTableWidth, stack);
        }
        table.add(stack).width(incrementTableWidth);
        userInfo.getProfilePictureContainer().toFront();
        return table;
    }

    private void processGoalTable(UserInfo userInfo, float incrementTableWidth, Stack stack) {
        Table goalTable = createGoalTable(incrementTableWidth);
        stack.addActor(goalTable);
        float widthUntilFirstGoal = ((incrementTableWidth * 0.8f) / goalTable.getChildren().size) * (getFirstChildToContainGoal(goalTable));
        float multiplier = 1f;
        while (userInfo.getUserNameLabel().getPrefWidth() > widthUntilFirstGoal) {
            multiplier = multiplier - 0.1f;
            userInfo.getUserNameLabel().setFontScale(FontManager.calculateMultiplierStandardFontSize(multiplier));
        }
    }

    private Table createGoalTable(float incrementTableWidth) {
        Table table = new Table();
        List<Integer> questionsToAddStar = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(gameTotalNrOfQuestions).getQuestionsToAddFinishIconAndStarIcon();
        float horizontalGeneralMarginDimen = MainDimen.horizontal_general_margin.getDimen();
        for (int i = 1; i <= gameTotalNrOfQuestions; i++) {
            //adds an empty table between goals
            table.add(new Table()).width(getEmptyTableBetweenGoalWidth(incrementTableWidth)).growY();
            Table iconContainer = new Table();
            for (int j = 0; j < questionsToAddStar.size(); j++) {
                if (i == questionsToAddStar.get(j)) {
                    iconContainer.add(createIconTable(j));
                }
            }
            int padMultiplier = gameTotalNrOfQuestions == 3 ? 6 : 4;
            iconContainer.padRight(horizontalGeneralMarginDimen * padMultiplier);
            table.add(iconContainer).width(getStarTableWidth()).growY();
        }
        return table;
    }

    private int getFirstChildToContainGoal(Table goalTable) {
        for (int i = 0; i < goalTable.getChildren().size; i++) {
            Actor actor = goalTable.getChildren().get(i);
            if (actor instanceof Table) {
                if (((Table) actor).findActor(getStarImageName(0, baseUserInfo.getId())) != null) {
                    return i;
                }
            }
        }
        return -1;
    }

    private float getEmptyTableBetweenGoalWidth(float incrementTableWidth) {
        return incrementTableWidth / Float.valueOf(gameTotalNrOfQuestions) - getStarTableWidth();
    }

    private float getStarTableWidth() {
        return MainDimen.horizontal_general_margin.getDimen() / 4;
    }

    private Table createIconTable(int index) {
        Table table = new Table();
        float sideDimen = MainDimen.vertical_general_margin.getDimen() * 1.4f;
        Image image = GraphicUtils.getImage(index == 0 ? Resource.finish_disabled : Resource.star_disabled);
        image.setName(getStarImageName(index, baseUserInfo.getId()));
        table.add(image).width(sideDimen).height(sideDimen);
        return table;
    }

    private Table createUsernameAndActionsTable(MyWrappedLabel userNameLabel, float incrementTableWidth) {
        Table table = new Table();
        userNameLabel.setFontScale(smallDisplay ? FontManager.calculateMultiplierStandardFontSize(0.6f) : FontManager.getNormalFontDim());
        userNameLabel.setAlignment(Align.left);
        table.add(createAllAnswersTable()).width(0).growY();
        table.add(createFillCorrectAnswersTable()).width(0).growY();
        table.add(userNameLabel).padLeft(MainDimen.horizontal_general_margin.getDimen()).left().height(getHeaderHeight());
        table.add().expandX().width(0);
        return table;
    }

    private float getHeaderHeight() {
        return smallDisplay ? Dimen.height_userinfo_row_small.getDimen() : Dimen.height_userinfo_row.getDimen();
    }

    private Table createAllAnswersTable() {
        Table table = new Table();
        table.setVisible(false);
        table.setName(getFillAllAnswersTableName(baseUserInfo));
        table.setBackground(GraphicUtils.getNinePatch(Resource.game_user_header_background_multiple_answers_wrong));
        return table;
    }

    private Table createFillCorrectAnswersTable() {
        Table fillCorrectLettersTable = new Table();
        fillCorrectLettersTable.setVisible(false);
        fillCorrectLettersTable.setName(getFillCorrectAnswersTableName(baseUserInfo));
        fillCorrectLettersTable.setBackground(GraphicUtils.getNinePatch(fillCorrectAnswersTableColorRes));
        return fillCorrectLettersTable;
    }

    private Table createActionTable() {
        Table actionTable = new Table();
        actionTable.setName(getActionTableName(baseUserInfo));
        return actionTable;
    }

    static String getFillAllAnswersTableName(BaseUserInfo currentUser) {
        return ACTOR_NAME_FILLALLANSWERS_TABLE + currentUser.getId() + currentUser.getFullName();
    }

    static String getFillCorrectAnswersTableName(BaseUserInfo currentUser) {
        return ACTOR_NAME_FILLCORRECTANSWERS_TABLE + currentUser.getId() + currentUser.getFullName();
    }

    static String getActionTableName(BaseUserInfo currentUser) {
        return ACTOR_NAME_ACTION_TABLE + currentUser.getId() + currentUser.getFullName();
    }

    static String getScoreLabelName(BaseUserInfo currentUser) {
        return ACTOR_NAME_SCORE_LABEL + currentUser.getId() + currentUser.getFullName();
    }
}
