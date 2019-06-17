package libgdx.ui.screens.mainmenu.popup;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.controls.button.builders.LoginButtonBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.ScreenManager;

public abstract class StartGameBeforeLoginPopup extends MyPopup<AbstractScreen, ScreenManager> {

    StartGameBeforeLoginPopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
        if (TournamentGame.getInstance().getLoginService().isUserLoggedIn() || !TournamentGame.getInstance().hasInternet()) {
            redirectAfterLogin().run();
        }
    }

    @Override
    public void addButtons() {
        addButton(new LoginButtonBuilder(redirectAfterLogin()).setFacebookConnectButton().build());
        addButton(new LoginButtonBuilder(redirectAfterLogin()).setGoogleConnectButton().build());
        addButton(new LoginButtonBuilder(redirectAfterLogin()).setGuestConnectButton().build());
    }

    abstract Runnable redirectAfterLogin();

}
