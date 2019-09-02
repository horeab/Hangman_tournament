package libgdx.ui.screens.livegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import libgdx.resources.dimen.MainDimen;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.constants.game.GameType;
import libgdx.controls.MyTextField;
import libgdx.ui.controls.ProfilePictureComponents;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.game.diamond.DiamondControlsService;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class SearchUserScreen extends AbstractScreen {

    private ScrollPane scrollPane;
    private MyWrappedLabel infoLabel;

    @Override
    public void buildStage() {
        super.buildStage();
        Gdx.input.setOnscreenKeyboardVisible(true);
        infoLabel = new MyWrappedLabel("");
        infoLabel.setStyleDependingOnContrast();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        new UserStatusBar(this).addToScreen();

        Table table = new Table();
        table.setFillParent(true);
        table.add(new DiamondControlsService(GameType.CHALLENGE, getCurrentUser().getId()).createDiamondLevelUpTable()).padTop(verticalGeneralMarginDimen * 5).row();
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPane = new ScrollPane(createSearchResultTable(new ArrayList<BaseUserInfo>()), scrollPaneStyle);
        scrollPane.setScrollingDisabled(true, false);
        table.add(new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setStyleDependingOnContrast().setText(TournamentGameLabel.live_game_search_friends.getText()).build())).padTop(verticalGeneralMarginDimen * 2).row();
        table.add(createSearchInput()).padTop(verticalGeneralMarginDimen).row();
        table.add(scrollPane).row();
        table.add(infoLabel).row();
        table.add().expand();
        addActor(table);
    }

    private MyTextField createSearchInput() {
        Gdx.input.setInputProcessor(getStage());
        final MyTextField myTextField = new MyTextField();
        addActor(myTextField);
        getStage().setKeyboardFocus(myTextField.getTextField());
        myTextField.getTextField().setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(final TextField textField, char key) {
                final List<BaseUserInfo> userList = new ArrayList<>();
                infoLabel.setVisible(userList.isEmpty());
                infoLabel.setText(MainGameLabel.loading.getText());
                final String text = textField.getText();
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        infoLabel.setText(MainGameLabel.loading.getText());
                        if (StringUtils.isNotBlank(text) && text.equals(textField.getText())) {
                            List<BaseUserInfo> users = usersDbApiService.selectUserNameContainsText(text);
                            if (users == null || users.isEmpty()) {
                                infoLabel.setText(TournamentGameLabel.no_results.getText());
                            }
                            userList.addAll(users);
                        }
                        scrollPane.setActor(createSearchResultTable(userList));
                        infoLabel.setVisible(userList.isEmpty() && StringUtils.isNotBlank(text));
                    }
                }, 1f);
            }
        });
        return myTextField;
    }

    private Table createSearchResultTable(List<BaseUserInfo> baseUserInfoList) {
        Table table = new Table();
        table.padTop(MainDimen.vertical_general_margin.getDimen());
        for (BaseUserInfo baseUserInfo : baseUserInfoList) {
            if (!baseUserInfo.getExternalId().equals(getCurrentUser().getExternalId())) {
                Table infoTable = new Table();
                UserInfo userInfo = new UserInfo(baseUserInfo, new ProfilePictureComponents(baseUserInfo, Dimen.side_leaderboard_userinfo_profile_picture).withLastActiveNotification().create());
                infoTable.add(userInfo.getProfilePictureContainer());
                infoTable.add(createNameAndChallengeButton(userInfo)).padLeft(MainDimen.horizontal_general_margin.getDimen() * 2);
                infoTable.setBackground(GraphicUtils.getNinePatch(Resource.leaderboard_otheruser_background));
                table.add(infoTable).width(ScreenDimensionsManager.getScreenWidthValue(95)).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
            }
        }
        return table;
    }

    private Table createNameAndChallengeButton(final UserInfo userInfo) {
        Table table = new Table();
        float width = ScreenDimensionsManager.getScreenWidthValue(40);
        userInfo.getUserNameLabel().setEllipsis(width);
        userInfo.getUserNameLabel().setAlignment(Align.left);
        userInfo.getUserNameLabel().setFontScale(FontManager.getNormalFontDim());
        table.add(userInfo.getUserNameLabel()).align(Align.left).width(width).row();
        MyButton challengeButton = new ButtonBuilder(TournamentGameLabel.live_game_challenge.getText()).setFixedButtonSize(ButtonSize.THIN_SIZE).setButtonSkin(ButtonSkin.GREEN).build();
        challengeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.showChallengeOnlineOptionsScreen(userInfo.getBaseUserInfo());
            }
        });
        table.add(challengeButton).height(challengeButton.getHeight()).width(challengeButton.getWidth());
        return table;
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }
}
