package libgdx.ui.services.login.guest;

import libgdx.constants.user.AccountCreationSource;
import libgdx.game.Game;
import libgdx.game.external.LoginService;

public class GuestUserLoginService implements LoginService {

    private GuestUserService guestUserService;

    public GuestUserLoginService() {
        guestUserService = new GuestUserService();
    }

    @Override
    public String getExternalId() {
        return guestUserService.getExternalId();
    }

    @Override
    public String getFullName() {
        return guestUserService.getFullName();
    }

    @Override
    public AccountCreationSource getAccountCreationSource() {
        return AccountCreationSource.INTERNAL;
    }

    @Override
    public boolean isUserLoggedIn() {
        return guestUserService.isLoggedIn();
    }

    @Override
    public void loginClick(AccountCreationSource accountCreationSource, final Runnable redirectAfterLogin) {
        if (isUserLoggedIn()) {
            guestUserService.logOutGuestUser();
            goToMainMenu();
        } else {
            guestUserService.createGuestUser();
            redirectAfterLogin.run();
        }
    }

    @Override
    public void goToMainMenu() {
        Game.getInstance().getScreenManager().showMainScreen();
    }
}
