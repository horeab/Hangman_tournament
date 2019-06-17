package libgdx.ui.implementations.games.countrygeo.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.resources.SpecificResource;
import libgdx.ui.game.TournamentGame;

public enum CountryGeoSpecificResource implements SpecificResource {

    // @formatter:off
    specific_labels("labels/labels", I18NBundle.class),

    img_cat0_999("questions/images/cat0/999.png", Texture.class),
    img_cat1_999("questions/images/cat1/999.png", Texture.class),
    img_cat2_999("questions/images/cat2/999.png", Texture.class),
    img_cat3_999("questions/images/cat3/999.png", Texture.class),
    img_cat4_999("questions/images/cat4/999.png", Texture.class),

    campaign_level_0_0("campaign/l_0/level_0_0.png", Texture.class),
    campaign_level_0_1("campaign/l_0/level_0_1.png", Texture.class),
    campaign_level_0_2("campaign/l_0/level_0_2.png", Texture.class),
    campaign_level_0_3("campaign/l_0/level_0_3.png", Texture.class),
    campaign_level_0_4("campaign/l_0/level_0_4.png", Texture.class),

    ;

    // @formatter:on

    private String path;
    private Class<?> classType;

    CountryGeoSpecificResource(String path, Class<?> classType) {
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
