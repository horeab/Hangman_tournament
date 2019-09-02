package libgdx.ui.services.game;

import java.util.Arrays;

import libgdx.resources.Res;
import libgdx.constants.user.AccountCreationSource;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.services.TournamentGameResourceService;

public class UserProfilePictureUrlService {


    private BaseUserInfo baseUserInfo;

    public UserProfilePictureUrlService(BaseUserInfo baseUserInfo) {
        this.baseUserInfo = baseUserInfo;
    }

    public String getProfilePictureUrl() {
        String profilePictureUrl = baseUserInfo.getProfilePictureUrl();
        if (baseUserInfo.getAccountCreationSource() == AccountCreationSource.FACEBOOK) {
            profilePictureUrl = getFacebookProfilePictureUrl(baseUserInfo.getExternalId());
        } else if (baseUserInfo.getId() > 0 && Arrays.asList(AccountCreationSource.INTERNAL, AccountCreationSource.GOOGLE).contains(baseUserInfo.getAccountCreationSource())) {
            profilePictureUrl = getLocalProfilePictureUrl(String.valueOf(baseUserInfo.getId()));
        }
        return profilePictureUrl;
    }

    private static String getLocalProfilePictureUrl(String userExternalId) {
        Res resource = new TournamentGameResourceService().getByName("guest" + userExternalId.substring(userExternalId.length() - 1));
        return resource != null ? resource.getPath() : null;
    }

    public static String getFacebookProfilePictureUrl(String userExternalId) {
        return "https://graph.facebook.com/" + userExternalId + "/picture?type=normal";
    }
}
