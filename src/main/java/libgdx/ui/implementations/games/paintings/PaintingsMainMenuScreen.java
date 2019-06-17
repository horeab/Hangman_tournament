package libgdx.ui.implementations.games.paintings;

import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.ui.screens.mainmenu.MainMenuScreen;

class PaintingsMainMenuScreen extends MainMenuScreen {
    @Override
    protected Stack createTitleStack(MyWrappedLabel titleLabel) {
        titleLabel.setVisible(false);
        return super.createTitleStack(titleLabel);
    }
}
