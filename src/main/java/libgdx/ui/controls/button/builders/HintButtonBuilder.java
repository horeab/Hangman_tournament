package libgdx.ui.controls.button.builders;

import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.controls.button.HintButton;
import libgdx.controls.button.builders.ImageButtonBuilder;
import libgdx.ui.screens.game.screens.AbstractGameScreen;

public class HintButtonBuilder {

    private HintButtonType hintButtonType;
    private AbstractGameScreen gameScreen;

    public HintButtonBuilder(HintButtonType hintButtonType, AbstractGameScreen gameScreen) {
        this.hintButtonType = hintButtonType;
        this.gameScreen = gameScreen;
    }

    public HintButton build() {
        return new HintButton(hintButtonType, new ImageButtonBuilder(hintButtonType.getButtonSkin(), gameScreen).addFadeOutOnClick().animateZoomInZoomOut().build());
    }

}
