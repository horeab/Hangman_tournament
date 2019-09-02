package libgdx.ui.util;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.utils.InternetUtils;

public class DataCreator {

    public static void createUsers() {
        if (InternetUtils.hasInternet() && new UsersDbApiService().selectUsersForTournament().size() < 8) {
            for (BaseUserInfo user : TestDataCreator.createUsers()) {
                new UsersDbApiService().createUser(user.getExternalId(), user.getFullName(), user.getAccountCreationSource());
            }
        }
    }

    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.toString();
        }
    }
}
