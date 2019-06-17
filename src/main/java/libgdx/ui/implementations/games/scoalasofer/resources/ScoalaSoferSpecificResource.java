package libgdx.ui.implementations.games.scoalasofer.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.ui.game.TournamentGame;
import libgdx.resources.SpecificResource;

public enum ScoalaSoferSpecificResource implements SpecificResource {

    // @formatter:off
    specific_labels("labels/labels", I18NBundle.class),

    title_clouds_background("title_clouds_background.png", Texture.class),

    campaign_level_0_0("campaign/l_0/level_0_0.png", Texture.class),
    campaign_level_0_1("campaign/l_0/level_0_1.png", Texture.class),
    campaign_level_0_2("campaign/l_0/level_0_2.png", Texture.class),
    campaign_level_0_3("campaign/l_0/level_0_3.png", Texture.class),
    campaign_level_0_4("campaign/l_0/level_0_4.png", Texture.class),
    campaign_level_1_5("campaign/l_1/level_1_5.png", Texture.class),
    campaign_level_1_6("campaign/l_1/level_1_6.png", Texture.class),
    campaign_level_1_7("campaign/l_1/level_1_7.png", Texture.class),
    campaign_level_1_8("campaign/l_1/level_1_8.png", Texture.class),
    campaign_level_1_9("campaign/l_1/level_1_9.png", Texture.class),
    campaign_level_2_10("campaign/l_2/level_2_10.png", Texture.class),
    campaign_level_2_11("campaign/l_2/level_2_11.png", Texture.class),
    campaign_level_2_12("campaign/l_2/level_2_12.png", Texture.class),
    campaign_level_2_13("campaign/l_2/level_2_13.png", Texture.class),
    campaign_level_2_14("campaign/l_2/level_2_14.png", Texture.class),

    btn_practice("btn_practice.png", Texture.class),
    btn_settings_up("btn_settings_up.png", Texture.class),
    btn_settings_down("btn_settings_down.png", Texture.class),
;

    // @formatter:on

    private String path;
    private Class<?> classType;

    ScoalaSoferSpecificResource(String path, Class<?> classType) {
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
