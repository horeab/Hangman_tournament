package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.ButtonBuilder;

class InternetButtonBuilder extends ButtonBuilder {

    public void prepareInternetButton(ClickListener refreshScreenClickListener) {
        prepareInternetButton(this, refreshScreenClickListener);
    }

    public ButtonBuilder prepareInternetButton(ButtonBuilder buttonBuilder, ClickListener refreshScreenClickListener) {
        buttonBuilder.addClickListener(refreshScreenClickListener);
        buttonBuilder.setDisabled(!TournamentGame.getInstance().hasInternet());
        return buttonBuilder;
    }

}