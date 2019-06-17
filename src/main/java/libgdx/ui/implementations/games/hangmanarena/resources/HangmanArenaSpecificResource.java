package libgdx.ui.implementations.games.hangmanarena.resources;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.ui.game.TournamentGame;
import libgdx.resources.SpecificResource;

public enum HangmanArenaSpecificResource implements SpecificResource {

    // @formatter:off
    specific_labels("labels/labels", I18NBundle.class),

    campaign_level_0_0("campaign/l_0/level_0_0.png", Texture.class),
    campaign_level_0_1("campaign/l_0/level_0_1.png", Texture.class),
    campaign_level_0_2("campaign/l_0/level_0_2.png", Texture.class),
    campaign_level_0_3("campaign/l_0/level_0_3.png", Texture.class),
    campaign_level_0_4("campaign/l_0/level_0_4.png", Texture.class),
    campaign_level_1_0("campaign/l_1/level_1_0.png", Texture.class),
    campaign_level_1_1("campaign/l_1/level_1_1.png", Texture.class),
    campaign_level_1_2("campaign/l_1/level_1_2.png", Texture.class),
    campaign_level_1_3("campaign/l_1/level_1_3.png", Texture.class),
    campaign_level_1_4("campaign/l_1/level_1_4.png", Texture.class),
    campaign_level_2_0("campaign/l_2/level_2_0.png", Texture.class),
    campaign_level_2_1("campaign/l_2/level_2_1.png", Texture.class),
    campaign_level_2_2("campaign/l_2/level_2_2.png", Texture.class),
    campaign_level_2_3("campaign/l_2/level_2_3.png", Texture.class),
    campaign_level_2_4("campaign/l_2/level_2_4.png", Texture.class),
    campaign_level_3_0("campaign/l_3/level_3_0.png", Texture.class),
    campaign_level_3_1("campaign/l_3/level_3_1.png", Texture.class),
    campaign_level_3_2("campaign/l_3/level_3_2.png", Texture.class),
    campaign_level_3_3("campaign/l_3/level_3_3.png", Texture.class),
    campaign_level_3_4("campaign/l_3/level_3_4.png", Texture.class),
    campaign_level_4_0("campaign/l_4/level_4_0.png", Texture.class),
    campaign_level_4_1("campaign/l_4/level_4_1.png", Texture.class),
    campaign_level_4_2("campaign/l_4/level_4_2.png", Texture.class),
    campaign_level_4_3("campaign/l_4/level_4_3.png", Texture.class),
    campaign_level_4_4("campaign/l_4/level_4_4.png", Texture.class),
    campaign_level_5_0("campaign/l_5/level_5_0.png", Texture.class),
    campaign_level_5_1("campaign/l_5/level_5_1.png", Texture.class),
    campaign_level_5_2("campaign/l_5/level_5_2.png", Texture.class),
    campaign_level_5_3("campaign/l_5/level_5_3.png", Texture.class),
    campaign_level_5_4("campaign/l_5/level_5_4.png", Texture.class),;

    // @formatter:on

    private String path;
    private Class<?> classType;

    HangmanArenaSpecificResource(String path, Class<?> classType) {
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
