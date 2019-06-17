package libgdx.ui.screens.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.ImageButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.controls.button.LeaderboardButtonBuilder;
import libgdx.ui.controls.button.ProVersionButtonCreator;
import libgdx.ui.controls.button.RoundButtonBuilder;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.controls.userstatusbar.UserStatusBarBuilder;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.mainmenu.popup.RewardsPopup;
import libgdx.ui.screens.mainmenu.popup.SettingsPopup;
import libgdx.ui.screens.mainmenu.popup.StartGameBeforeLoginPopupCampaign;
import libgdx.ui.screens.mainmenu.popup.StartGameBeforeLoginPopupChallenge;
import libgdx.ui.screens.mainmenu.popup.StartGameBeforeLoginPopupPractice;
import libgdx.ui.screens.mainmenu.popup.StartGameBeforeLoginPopupTournament;
import libgdx.ui.services.game.constants.ConstantsService;
import libgdx.utils.ScreenDimensionsManager;

public class MainMenuScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        super.buildStage();
        addScreenBackground();
        new ConstantsService().processApiVersionValue(getScreen());
        addUserStatusBar();
        createGameControls();
        executeOnFirstTimeMainMenuDisplayed();
    }

    private void addUserStatusBar() {
        new UserStatusBar(this) {
            @Override
            protected UserStatusBarBuilder createUserStatusBarBuilder() {
                UserStatusBarBuilder userStatusBarBuilder = super.createUserStatusBarBuilder();
                if (TournamentGame.getInstance().getAppInfoService().googleFacebookLoginEnabled()) {
                    userStatusBarBuilder.withLoginButton();
                }
                return userStatusBarBuilder;
            }
        }.addToScreen();
    }

    private void createGameControls() {
        Table table = new Table();
        table.add().padTop(MainDimen.vertical_general_margin.getDimen() * 2).row();
        addTitle(table);
        addStartGameButtons(table);
        addExtraButtons(table);
        table.setWidth(ScreenDimensionsManager.getScreenWidth());
        table.setHeight(ScreenDimensionsManager.getScreenHeight());
        addActor(table);
    }

    private void addTitle(Table table) {
        Image titleRaysImage = GraphicUtils.getImage(Resource.title_rays);
        new ActorAnimation(titleRaysImage, getScreen()).animateFastFadeInFadeOut();
        float titleWidth = ScreenDimensionsManager.getScreenWidth();
        float titleHeight = ScreenDimensionsManager.getNewHeightForNewWidth(titleWidth, titleRaysImage.getWidth(), titleRaysImage.getHeight());
        titleRaysImage.setWidth(titleWidth);
        titleRaysImage.setHeight(titleHeight);
        titleRaysImage.setY(ScreenDimensionsManager.getScreenHeightValue(49));
        addActor(titleRaysImage);
        Stack titleLabel = createTitleLabel();
        table.add(titleLabel)
                .width(titleWidth)
                .height(titleHeight)
                .padBottom(MainDimen.vertical_general_margin.getDimen() * 1)
                .row();
    }

    private void addScreenBackground() {
        Image mainMenuScreenBackground = GraphicUtils.getImage(Resource.main_menu_screen_background);
        mainMenuScreenBackground.setFillParent(true);
        mainMenuScreenBackground.toBack();
        addActor(mainMenuScreenBackground);
    }

    private void addExtraButtons(Table table) {
        Table extraBtnTable = new Table();
        addExtraButton(extraBtnTable, new ProVersionButtonCreator().createMainMenuButton());
        int padding = 6;
        extraBtnTable.add().padLeft(MainDimen.horizontal_general_margin.getDimen() * padding);
        addExtraButton(extraBtnTable, createFreeCoinsButton());
        extraBtnTable.add().padRight(MainDimen.horizontal_general_margin.getDimen() * padding);
        addExtraButton(extraBtnTable, createSettingsButton());
        table.setWidth(ScreenDimensionsManager.getScreenWidth());
        table.add(extraBtnTable);
    }

    private void addStartGameButtons(Table table) {
        Table firstRowStartGame = new Table();
        addStartGameButton(firstRowStartGame, createCampaignButton());
        addStartGameButton(firstRowStartGame, createPlayButton());
        table.add(firstRowStartGame).row();
        Table secondRowStartGame = new Table();
        addStartGameButton(secondRowStartGame, createChallengePlayerButton());
        addStartGameButton(secondRowStartGame, createPracticeButton());
        table.add(secondRowStartGame).padBottom(MainDimen.vertical_general_margin.getDimen() * 3).row();
    }

    private void addExtraButton(Table extraBtnTable, MyButton btn) {
        extraBtnTable.add(btn).height(btn.getHeight()).width(btn.getWidth());
    }

    private void addStartGameButton(Table table, MyButton button) {
        table.add(button).height(button.getHeight()).width(button.getWidth()).padRight(MainDimen.horizontal_general_margin.getDimen() * 2);
    }

    private Stack createTitleLabel() {
        String appName = TournamentGame.getInstance().getAppInfoService().getAppName();
        MyWrappedLabel titleLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(appName).build());
        titleLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(appName.length() > 14 ? 1.3f : 1.7f));
        titleLabel.setAlignment(Align.center);
        return createTitleStack(titleLabel);
    }

    protected Stack createTitleStack(MyWrappedLabel titleLabel) {
        Stack stack = new Stack();
        stack.addActor(GraphicUtils.getImage(Resource.title_clouds_background));
        stack.addActor(titleLabel);
        return stack;
    }

    private MyButton createPracticeButton() {
        final AbstractScreen screen = getScreen();
        MyButton button = new RoundButtonBuilder(getScreen()).setPracticeButton().setFontDimen(FontManager.getSmallFontDim()).setFixedButtonSize(ButtonSize.SMALL_MENU_ROUND_IMAGE).build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new StartGameBeforeLoginPopupPractice(getScreen()).addToPopupManager();
            }
        });
        return button;
    }

    private MyButton createChallengePlayerButton() {
        MyButton button = new RoundButtonBuilder(getScreen()).setChallengeButton().setFontDimen(FontManager.getSmallFontDim()).setFixedButtonSize(ButtonSize.SMALL_MENU_ROUND_IMAGE).build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new StartGameBeforeLoginPopupChallenge(getScreen()).addToPopupManager();
            }
        });
        return button;
    }

    private MyButton createCampaignButton() {
        MyButton button = new RoundButtonBuilder(getScreen()).setCampaignButton().setFontDimen(FontManager.calculateMultiplierStandardFontSize(1.2f)).build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new StartGameBeforeLoginPopupCampaign(getScreen()).addToPopupManager();
            }
        });
        return button;
    }

    private MyButton createPlayButton() {
        MyButton button = new RoundButtonBuilder(getScreen()).setTournamentButton().build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new StartGameBeforeLoginPopupTournament(getScreen()).addToPopupManager();
            }
        });
        return button;
    }

    private MyButton createFreeCoinsButton() {
        MyButton button = new ImageButtonBuilder(ButtonSkin.COINS, getScreen())
                .setText(TournamentGameLabel.resources_free_offer.getText())
                .animateZoomInZoomOut()
                .build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new RewardsPopup(getScreen()).addToPopupManager();
            }
        });
        button.setVisible(TournamentGame.getInstance().getLoginService().isUserLoggedIn());
        return button;
    }

    private MyButton createSettingsButton() {
        MyButton button = new ImageButtonBuilder(ButtonSkin.SETTINGS_MENU, getScreen())
                .setFixedButtonSize(ButtonSize.BIG_IMAGE)
                .build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new SettingsPopup(getScreen()).addToPopupManager();
            }
        });
        button.setVisible(TournamentGame.getInstance().getLoginService().isUserLoggedIn());
        return button;
    }

    private void executeOnFirstTimeMainMenuDisplayed() {
        TournamentGame instance = TournamentGame.getInstance();
        if (instance.isFirstTimeMainMenuDisplayed()) {
            instance.getMainDependencyManager().createRatingService(this).appLaunched();
            instance.setFirstTimeMainMenuDisplayed(false);
        }
    }

    private MainMenuScreen getScreen() {
        return this;
    }

    @Override
    public void onBackKeyPress() {
        Gdx.app.exit();
    }
}
