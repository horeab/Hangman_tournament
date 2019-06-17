package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.resources.Resource;

public class InternetButtonWithIconBuilder extends InternetButtonBuilder {

    public void prepareInternetButtonWithIcon(String text, Resource icon, ClickListener refreshScreenClickListener) {
        prepareInternetButtonWithIcon(this, text, icon, refreshScreenClickListener);
    }

    public ButtonBuilder prepareInternetButtonWithIcon(ButtonBuilder buttonBuilder, String text, Resource icon, ClickListener refreshScreenClickListener) {
        buttonBuilder.addCenterTextImageColumn(createTableLabelImage(text, icon));
        return prepareInternetButton(buttonBuilder, refreshScreenClickListener);
    }
}
