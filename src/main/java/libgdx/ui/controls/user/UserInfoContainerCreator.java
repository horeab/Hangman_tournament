package libgdx.ui.controls.user;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.List;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.resources.ResourcesManager;
import libgdx.ui.services.dbapi.UserGamesDbApiService;
import libgdx.utils.ActorPositionManager;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class UserInfoContainerCreator {

    private static final String CURRENT_USER_CONTAINER_NAME = "CURRENT_USER_CONTAINER_NAME";
    private static final String OPPONENT_CONTAINER_NAME = "OPPONENT_CONTAINER_NAME";

    public Table createUserInfoContainer(List<UserInfoContainerConfig> userInfoContainerConfigs) {
        Table userInfoTable = new Table();
        float containerSide = Dimen.side_userinfo_container.getDimen();
        for (int i = 0; i < userInfoContainerConfigs.size(); i++) {
            if (i != 0) {
                userInfoTable.add().width(MainDimen.horizontal_general_margin.getDimen());
            }
            BaseUserInfo opponent = null;
            if (userInfoContainerConfigs.size() == 2) {
                opponent = i == 0 ? userInfoContainerConfigs.get(1).getGameUser().getBaseUserInfo() : userInfoContainerConfigs.get(0).getGameUser().getBaseUserInfo();
            }
            Table userTable = createUserTables(userInfoContainerConfigs.get(i), opponent);
            String userTableName = null;
            if (i == 0) {
                userTableName = getCurrentUserContainerName();
            } else if (i == 1) {
                userTableName = getOpponentContainerName();
            }
            userTable.setName(userTableName);
            userInfoTable.add(userTable).width(containerSide).height(containerSide);
        }
        return userInfoTable;
    }

    public static String getCurrentUserContainerName() {
        return CURRENT_USER_CONTAINER_NAME + UserInfoContainerCreator.class.toString();
    }

    public static String getOpponentContainerName() {
        return OPPONENT_CONTAINER_NAME + UserInfoContainerCreator.class.toString();
    }

    private Table createUserTables(UserInfoContainerConfig userInfoContainerConfig, BaseUserInfo opponent) {
        Table table = createTable(GraphicUtils.getNinePatch(userInfoContainerConfig.getContainerColor().getBackground()));
        BaseUserInfo baseUserInfo = userInfoContainerConfig.getGameUser().getBaseUserInfo();
        UserInfo userInfo = new UserInfo(baseUserInfo, Dimen.side_userinfo_container_profile_picture);
        float width = Dimen.side_userinfo_container.getDimen() - ScreenDimensionsManager.getScreenWidthValue(5);
        userInfo.getUserNameLabel().setEllipsis(width);
        userInfo.getUserNameLabel().setFontScale(FontManager.getSmallFontDim());
        table.add(userInfo.getUserNameLabel()).width(width).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
        table.add(userInfo.getProfilePictureContainer()).row();
        if (userInfoContainerConfig.getGameUser().userHasMultipleQuestions()) {
            MyWrappedLabel questionsAnswered = new MyWrappedLabel(String.valueOf(userInfoContainerConfig.getGameUser().getWonQuestions() + "/" + userInfoContainerConfig.getGameUser().getTotalNrOfQuestions()));
            questionsAnswered.setStyleDependingOnContrast(Color.WHITE, Color.GREEN);
            questionsAnswered.setFontScale(FontManager.calculateMultiplierStandardFontSize(1.6f));
            table.add(questionsAnswered).pad(MainDimen.vertical_general_margin.getDimen() / 2).row();
        }
        if (userInfoContainerConfig.isWithScoreLabel()) {
            MyWrappedLabel scoreLabel = new MyWrappedLabel(String.valueOf(new UserGamesDbApiService().selectTotalGamesUser1AgainstUser2(baseUserInfo.getId(), opponent.getId())));
            scoreLabel.setTextColor(Color.RED);
            scoreLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(1.6f));
            table.add(scoreLabel).pad(MainDimen.vertical_general_margin.getDimen() / 2);
        }
        return table;
    }

    private Table createTable(Drawable background) {
        Table table = new Table();
        table.setBackground(background);
        ActorPositionManager.setActorCenterVerticalOnScreen(table);
        return table;
    }
}
