package libgdx.ui.screens.mainmenu.popup;

import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;

public class StartGameBeforeLoginPopupCampaign extends StartGameBeforeLoginPopup {

    public StartGameBeforeLoginPopupCampaign(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    protected String getLabelText() {
        return TournamentGameLabel.login_practice_popup_info_needlogin.getText();
    }

    @Override
    Runnable redirectAfterLogin() {
        return new Runnable() {
            @Override
            public void run() {
                screenManager.showCampaignScreen();
            }
        };
    }
}
