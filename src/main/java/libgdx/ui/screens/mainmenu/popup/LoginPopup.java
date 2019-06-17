package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.ui.game.TournamentGame;
import libgdx.constants.user.AccountCreationSource;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.button.builders.LoginButtonBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.ScreenManager;

public class LoginPopup extends MyPopup<AbstractScreen, ScreenManager> {

    public LoginPopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    public void addButtons() {
        if (!TournamentGame.getInstance().getLoginService().isUserLoggedIn()) {
            addButton(new LoginButtonBuilder(redirectAfterLogin()).setFacebookConnectButton().build());
            addButton(new LoginButtonBuilder(redirectAfterLogin()).setGoogleConnectButton().build());
            addButton(new LoginButtonBuilder(redirectAfterLogin()).setGuestConnectButton().build());
        } else {
            addButton(new ButtonBuilder(TournamentGameLabel.login_stay_connected.getText()).setDefaultButton().addClickListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    hide();
                }
            }).build());
            addButton(new LoginButtonBuilder(redirectAfterLogin()).setDisconnectButton(getScreen().getCurrentUser().getAccountCreationSource()).build());
        }
    }

    @Override
    protected String getLabelText() {
        String text = "";
        if (!TournamentGame.getInstance().getLoginService().isUserLoggedIn()) {
            text = TournamentGameLabel.login_tournament_popup_info_needlogin.getText();
        } else if (getScreen().getCurrentUser().getAccountCreationSource() == AccountCreationSource.INTERNAL) {
            text = TournamentGameLabel.login_logout_guest_info.getText();
        }
        return text;
    }

    private Runnable redirectAfterLogin() {
        return new Runnable() {
            @Override
            public void run() {
                screenManager.showMainScreen();
            }
        };
    }
}
