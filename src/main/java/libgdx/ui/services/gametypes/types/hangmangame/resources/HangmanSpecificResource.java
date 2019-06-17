package libgdx.ui.services.gametypes.types.hangmangame.resources;

import com.badlogic.gdx.graphics.Texture;

import libgdx.ui.game.TournamentGame;
import libgdx.resources.SpecificResource;

public enum HangmanSpecificResource implements SpecificResource {

    // @formatter:off
    h0("hangmangame/h0.png", Texture.class),
    h1("hangmangame/h1.png", Texture.class),
    h2("hangmangame/h2.png", Texture.class),
    h3("hangmangame/h3.png", Texture.class),
    h4("hangmangame/h4.png", Texture.class),
    h5("hangmangame/h5.png", Texture.class),
    h6("hangmangame/h6.png", Texture.class),
    skyb0("hangmangame/skyb0.png", Texture.class),
    skyb1("hangmangame/skyb1.png", Texture.class),
    skyb2("hangmangame/skyb2.png", Texture.class),
    skyb3("hangmangame/skyb3.png", Texture.class),
    skyb4("hangmangame/skyb4.png", Texture.class),
    skyb5("hangmangame/skyb5.png", Texture.class),
    skyb6("hangmangame/skyb6.png", Texture.class),
    smoke("hangmangame/smoke.png", Texture.class),
    forest_texture("hangmangame/forest_texture.png", Texture.class),
    grass_texture("hangmangame/grass_texture.png", Texture.class),;
    // @formatter:on

    private String path;
    private Class<?> classType;

    HangmanSpecificResource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }

    @Override
    public String getPath() {
        return TournamentGame.getInstance().getAppInfoService().getResourcesFolder() + path;
    }
}
