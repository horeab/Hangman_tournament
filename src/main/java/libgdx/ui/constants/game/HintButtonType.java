package libgdx.ui.constants.game;

import libgdx.ui.controls.button.ButtonSkin;

public enum HintButtonType {

    HINT_PRESS_RANDOM_ANSWER(ButtonSkin.HINT),
    HINT_DISABLE_TWO_ANSWERS(ButtonSkin.HINT),;

    private ButtonSkin buttonSkin;

    HintButtonType(ButtonSkin buttonSkin) {
        this.buttonSkin = buttonSkin;
    }

    public ButtonSkin getButtonSkin() {
        return buttonSkin;
    }
}
