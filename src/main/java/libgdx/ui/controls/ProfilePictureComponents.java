package libgdx.ui.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import libgdx.utils.model.FontColor;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.Date;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.UserProfilePictureUrlService;
import libgdx.ui.services.game.userexperience.UserExperienceService;
import libgdx.utils.DateUtils;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.model.RGBColor;

public class ProfilePictureComponents {

    private Image profilePic;
    private boolean withLastActiveNotification;
    private int experience;
    private Table profilePictureContainer;
    private BaseUserInfo baseUserInfo;
    private Dimen profilePicSideDimen;

    public ProfilePictureComponents(BaseUserInfo baseUserInfo, Dimen profilePicSideDimen) {
        this.baseUserInfo = baseUserInfo;
        this.profilePicSideDimen = profilePicSideDimen;
        this.experience = new UserInventoryDbApiService().getExperience(baseUserInfo.getId());
    }

    public ProfilePictureComponents withLastActiveNotification() {
        withLastActiveNotification = true;
        return this;
    }

    public ProfilePictureComponents create() {
        if (profilePic == null) {
            profilePic = create(baseUserInfo);
        }
        profilePictureContainer = overlapLevelImg(profilePicSideDimen, baseUserInfo, profilePic);
        return this;
    }

    public int getExperience() {
        return experience;
    }

    public Image getProfilePictureImage() {
        return profilePic;
    }

    public Table getProfilePictureContainer() {
        return profilePictureContainer;
    }

    private Table overlapLevelImg(Dimen sideDimen, final BaseUserInfo baseUserInfo, Image profilePic) {
        Stack profilePicStack = new Stack();
        profilePicStack.add(profilePic);
        float dimen = sideDimen.getDimen();
        if (TournamentGame.getInstance().getLoginService().isUserLoggedIn()) {
            final MyWrappedLabel levelLabel = new MyWrappedLabel("-");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    levelLabel.setText(String.valueOf(
                            new UserExperienceService().getUserExperienceLevel(experience)));
                }
            }).start();
            levelLabel.setTextColor(FontColor.GREEN);
            float levelFontScale = sideDimen.getRawDimen() > 6 ? 0.9f : 0.6f;
            levelLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(levelFontScale));
            levelLabel.setAlignment(Align.center);
            Stack levelContainerStack = new Stack();
            levelContainerStack.add(GraphicUtils.getImage(Resource.level_container));
            levelContainerStack.add(levelLabel);

            Table levelContainerTable = new Table();
            if (withLastActiveNotification) {
                levelContainerTable.add(createLastActiveNotification(sideDimen, baseUserInfo)).padBottom(dimen / 3).padRight(dimen / 3).uniform();
            } else {
                levelContainerTable.add().uniform();
            }
            float levelContainerDim = dimen / 1.8f;
            levelContainerTable.add(levelContainerStack).padBottom(dimen / 20).padLeft(dimen / 20).width(levelContainerDim).height(levelContainerDim).uniform().row();
            levelContainerTable.add().uniform();
            levelContainerTable.add().uniform();

            profilePicStack.add(levelContainerTable);
        }

        Table table = new Table();
        table.add(profilePicStack).width(dimen).height(dimen);
        return table;
    }

    private Image createLastActiveNotification(Dimen sideDimen, final BaseUserInfo baseUserInfo) {
        final Image image = new Image();
        final RGBColor color = RGBColor.LIGHT_MAUVE2;
        final int dim = Math.round(sideDimen.getDimen() / 5);
        final Pixmap pixmap = new Pixmap(dim, dim, Pixmap.Format.RGBA8888);
        pixmap.setColor(color.r, color.g, color.b, color.a);
        pixmap.fillRectangle(0, 0, dim, dim);
        image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        new Thread(new Runnable() {
            @Override
            public void run() {
                Date lastTimeActive = new UsersDbApiService().getLastTimeActiveDateForUser(baseUserInfo.getId());
                if (DateUtils.minusSeconds(20).before(lastTimeActive)) {
                    color.setValue(RGBColor.LIGHT_GREEN);
                } else if (DateUtils.minusMinutes(3).before(lastTimeActive)) {
                    color.setValue(RGBColor.YELLOW);
                } else {
                    color.setValue(RGBColor.RED);
                }
                pixmap.setColor(color.r, color.g, color.b, color.a);
                pixmap.fillRectangle(0, 0, dim, dim);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        image.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
                        pixmap.dispose();
                    }
                });
            }
        }).start();
        return image;
    }

    private Image create(BaseUserInfo baseUserInfo) {
        Image profilePic = GraphicUtils.getImage(Resource.unknown_user);
        ImageAsync imageAsync = new ImageAsync(new MutablePair<Image, String>(profilePic, new UserProfilePictureUrlService(baseUserInfo).getProfilePictureUrl()));
        imageAsync.setDefaultImage(Resource.unknown_user);
        imageAsync.create();
        return profilePic;
    }
}
