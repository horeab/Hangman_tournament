package libgdx.ui.controls.button;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.MainButtonSize;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.ImageButtonBuilder;
import libgdx.ui.game.TournamentGame;

public class ProVersionButtonBuilder extends ImageButtonBuilder {


    public ProVersionButtonBuilder() {
        super(ButtonSkin.PRO, TournamentGame.getInstance().getAbstractScreen());
        setFixedButtonSize(MainButtonSize.STANDARD_IMAGE);
    }

    @Override
    public MyButton build() {
        MyButton button = super.build();
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TournamentGame.getInstance().getScreenManager().showLeaderboardScreen();
            }
        });
        return button;
    }
}
