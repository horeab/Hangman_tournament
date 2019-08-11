package libgdx.ui.services.gametypes.types.hangmangame.controls;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.resources.ResourcesManager;

public class HangmanLabel extends Table {

    private MyWrappedLabel label;

    public HangmanLabel(String text) {
        super();
        label=new MyWrappedLabel(text);
        add(label);
    }

    public void setFontScale(float fontScale) {
        label.setFontScale(fontScale);
    }

    public void setRedColor() {
        label.setTextColor(Color.RED);
    }

}
