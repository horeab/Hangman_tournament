package libgdx.ui.implementations.games.centenar.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.ui.game.TournamentGame;
import libgdx.resources.SpecificResource;

public enum CentenarSpecificResource implements SpecificResource {

    // @formatter:off
    specific_labels("labels/labels", I18NBundle.class),

    title_clouds_background("title_clouds_background.png", Texture.class),

    campaign_level_0_0("campaign/l_0/level_0_0.png", Texture.class),
    campaign_level_0_1("campaign/l_0/level_0_1.png", Texture.class),
    campaign_level_0_2("campaign/l_0/level_0_2.png", Texture.class),
    campaign_level_0_3("campaign/l_0/level_0_3.png", Texture.class),
    campaign_level_0_4("campaign/l_0/level_0_4.png", Texture.class),

    img_cat4_999("questions/images/cat4/999.png", Texture.class),;

    // @formatter:on

    private String path;
    private Class<?> classType;

    CentenarSpecificResource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String getPath() {
        return TournamentGame.getInstance().getAppInfoService().getImplementationGameResourcesFolder() + path;
    }

}
