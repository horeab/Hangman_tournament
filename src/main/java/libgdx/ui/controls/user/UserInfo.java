package libgdx.ui.controls.user;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import libgdx.ui.controls.ProfilePictureComponents;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.resources.FontManager;

public class UserInfo {


    private MyWrappedLabel userNameLabel;
    private Image profilePictureImage;
    private Table profilePictureContainer;
    private BaseUserInfo baseUserInfo;
    private int experience;

    public UserInfo(BaseUserInfo baseUserInfo, Dimen profilePictureContainerDimen) {
        this(baseUserInfo, new ProfilePictureComponents(baseUserInfo, profilePictureContainerDimen).create());
    }

    public UserInfo(BaseUserInfo baseUserInfo, ProfilePictureComponents profilePictureComponents) {
        userNameLabel = createUsernameLabel(baseUserInfo.getFullName());
        profilePictureImage = profilePictureComponents.getProfilePictureImage();
        profilePictureContainer = profilePictureComponents.getProfilePictureContainer();
        experience = profilePictureComponents.getExperience();
        this.baseUserInfo = baseUserInfo;
    }

    private MyWrappedLabel createUsernameLabel(String userName) {
        MyWrappedLabel label = new MyWrappedLabel(userName);
        label.setStyleDependingOnContrast();
        label.setAlignment(Align.center);
        label.setFontScale(FontManager.getSmallFontDim());
        return label;
    }

    public int getExperience() {
        return experience;
    }

    public Image getProfilePictureImage() {
        return profilePictureImage;
    }

    /**
     * Contains level and lastActiveStatus
     **/
    public Table getProfilePictureContainer() {
        return profilePictureContainer;
    }

    public MyWrappedLabel getUserNameLabel() {
        return userNameLabel;
    }

    public BaseUserInfo getBaseUserInfo() {
        return baseUserInfo;
    }
}
