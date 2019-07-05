package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.MyTextField;
import libgdx.game.Game;
import libgdx.game.external.AppInfoService;
import libgdx.preferences.SettingsService;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.controls.button.ProVersionButtonCreator;
import libgdx.ui.game.TournamentAppInfoService;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.builders.ButtonWithIconBuilder;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.popup.MyPopup;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.util.Language;
import libgdx.ui.util.TestDataCreator;

public class SettingsPopup extends MyPopup<AbstractScreen, ScreenManager> {

    private SettingsService settingsService;

    public SettingsPopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
        this.settingsService = new SettingsService();
    }

    @Override
    public void addButtons() {
        addButton(createAchievementButton());
        MyButton proVersButton = new ProVersionButtonCreator().createSettingButton();
        if (proVersButton != null) {
            addButton(proVersButton);
        }
        addButton(createToggleSoundButton());
        addButton(createPrivacyPolicyButton());
        addAvailableLanguagesButtons();
    }

    private void addAvailableLanguagesButtons() {
        if (Game.getInstance().getAppInfoService().screenShotMode()) {
            final MyTextField myTextField = new MyTextField();
            getButtonTable().add(myTextField).row();
            MyButton changeLangBtn = new ButtonBuilder().setWrappedText("Change Lang to", Dimen.width_btn_menu_big.getDimen())
                    .setDefaultButton()
                    .build();
            changeLangBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    final TournamentAppInfoService currentAppInfoService = TournamentGame.getInstance().getAppInfoService();
                    Game.getInstance().setNewContext(
                            new TournamentAppInfoService() {
                                @Override
                                public String getAppName() {
                                    return currentAppInfoService.getAppName();
                                }

                                @Override
                                public String getStoreAppId() {
                                    return currentAppInfoService.getStoreAppId();
                                }

                                @Override
                                public boolean screenShotMode() {
                                    return currentAppInfoService.screenShotMode();
                                }

                                @Override
                                public void showRewardedVideoAd() {
                                    currentAppInfoService.screenShotMode();
                                }

                                @Override
                                public String getGameIdPrefix() {
                                    return currentAppInfoService.getGameIdPrefix();
                                }

                                @Override
                                public String proVersionStoreAppId() {
                                    return currentAppInfoService.proVersionStoreAppId();
                                }

                                @Override
                                public void showPopupAd() {
                                    currentAppInfoService.showPopupAd();
                                }

                                @Override
                                public String getImplementationGameResourcesFolder() {
                                    return currentAppInfoService.getImplementationGameResourcesFolder();
                                }

                                @Override
                                public String getLanguage() {
                                    return myTextField.getTextField().getText();
                                }

                                @Override
                                public String getMainResourcesFolder() {
                                    return currentAppInfoService.getMainResourcesFolder();
                                }

                                @Override
                                public String getResourcesFolder() {
                                    return currentAppInfoService.getResourcesFolder();
                                }

                                @Override
                                public boolean isPortraitMode() {
                                    return currentAppInfoService.isPortraitMode();
                                }

                                @Override
                                public boolean isProVersion() {
                                    return currentAppInfoService.isProVersion();
                                }

                                @Override
                                public boolean googleFacebookLoginEnabled() {
                                    return currentAppInfoService.googleFacebookLoginEnabled();
                                }

                                @Override
                                public float gameScreenTopMargin() {
                                    return currentAppInfoService.gameScreenTopMargin();
                                }
                            });
                }
            });
            addButton(changeLangBtn);
        }
    }

    private MyButton createPrivacyPolicyButton() {
        MyButton button = new ButtonBuilder().setWrappedText(MainGameLabel.privacy_policy.getText(), Dimen.width_btn_menu_big.getDimen())
                .setDefaultButton()
                .build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://pastebin.com/raw/n7LwL8Bx");
            }
        });
        return button;
    }

    private MyButton createAchievementButton() {
        MyButton button = new ButtonWithIconBuilder(TournamentGameLabel.achievement_label.getText(), Resource.achievement)
                .setDefaultButton()
                .build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.showAchievementsScreen();
            }
        });
        return button;
    }

    private MyButton createToggleSoundButton() {
        String text = settingsService.isSoundOn() ? MainGameLabel.sound_off.getText() : MainGameLabel.sound_on.getText();
        Resource resource = settingsService.isSoundOn() ? Resource.sound_off : Resource.sound_on;
        final MyButton button = new ButtonWithIconBuilder(text, resource)
                .setDefaultButton()
                .build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsService.toggleSound();
                hide();
            }
        });
        button.setVisible(TournamentGame.getInstance().getLoginService().isUserLoggedIn());
        return button;
    }

    @Override
    protected String getLabelText() {
        return "";
    }
}
