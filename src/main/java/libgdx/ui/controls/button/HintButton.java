package libgdx.ui.controls.button;

import libgdx.controls.button.MyButton;
import libgdx.ui.constants.game.HintButtonType;

public class HintButton  {
    public HintButton(HintButtonType hintButtonType, MyButton myButton) {
        this.hintButtonType = hintButtonType;
        this.myButton = myButton;
    }

    private HintButtonType hintButtonType;
    private MyButton myButton;

    public HintButtonType getHintButtonType() {
        return hintButtonType;
    }

    public MyButton getMyButton() {
        return myButton;
    }
}
