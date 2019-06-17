package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.preferences.SettingsService;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.controls.button.ProVersionButtonCreator;
import libgdx.ui.controls.button.builders.FacebookShareButtonBuilder;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.builders.ButtonWithIconBuilder;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.popup.MyPopup;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.mainmenu.popup.rewards.FacebookShareRewardConfig;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;
import libgdx.ui.services.ScreenManager;

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
