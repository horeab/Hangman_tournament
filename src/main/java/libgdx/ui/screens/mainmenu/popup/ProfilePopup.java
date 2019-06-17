package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.ButtonWithIconBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.preferences.SettingsService;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.ScreenManager;

public class ProfilePopup extends MyPopup<AbstractScreen, ScreenManager> {

    public ProfilePopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    public void addButtons() {
    }

    @Override
    protected String getLabelText() {
        return "";
    }
}
