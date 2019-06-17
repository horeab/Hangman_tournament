package libgdx.ui.screens.mainmenu.popup;

import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;

public class StartGameBeforeLoginPopupChallenge extends StartGameBeforeLoginPopup {

    public StartGameBeforeLoginPopupChallenge(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    protected String getLabelText() {
        return TournamentGameLabel.login_challenge_popup_info_needlogin.getText();
    }

    @Override
    Runnable redirectAfterLogin() {
        return new Runnable() {
            @Override
            public void run() {
                screenManager.showSearchUserScreen();
            }
        };
    }
}
