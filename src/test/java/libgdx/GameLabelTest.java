package libgdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.I18NBundleLoader;
import com.badlogic.gdx.utils.I18NBundle;

import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.game.Game;
import libgdx.resources.Res;
import libgdx.resources.gamelabel.GameLabel;
import libgdx.resources.gamelabel.GameLabelUtils;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.util.TestDataCreator;

public class GameLabelTest extends TournamentGameTestDbApiService {

    @Test
    public void testAllLanguages() {
        for (String lang : TestDataCreator.ALL_LANG) {
            Res mainLabelRes = GameLabelUtils.getMainLabelRes(lang);
            Res labelRes = GameLabelUtils.getLabelRes(lang);
            for (TournamentGameLabel gameLabel : TournamentGameLabel.values()) {
                assertLabel(lang, labelRes, gameLabel);
            }
            for (MainGameLabel gameLabel : MainGameLabel.values()) {
                assertLabel(lang, mainLabelRes, gameLabel);
            }
        }
    }

    private void assertLabel(String lang, Res labelRes, GameLabel gameLabel) {
        try {
            getLabel(gameLabel, labelRes);
        } catch (Exception e) {
            System.out.print(gameLabel.getKey() + " - " + lang + "  ");
            throw new RuntimeException();
        }
    }

    private static String getLabel(GameLabel label, Res labelsRes) {
        I18NBundleLoader.I18NBundleParameter param = new I18NBundleLoader.I18NBundleParameter(null, "UTF-8");
        Game.getInstance().getAssetManager().load(labelsRes.getPath(), I18NBundle.class, param);
        I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal(labelsRes.getPath()), "UTF-8");
        if (bundle.get(label.getKey()) != null) {
            return bundle.format(label.getKey());
        }
        return label.getKey();
    }

}
