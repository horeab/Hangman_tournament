package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.popup.MyPopup;
import libgdx.game.Game;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.utils.InternetUtils;

public class ForceUpdatePopup extends MyPopup {

    public ForceUpdatePopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    public void addButtons() {
        MyButton updateAppBtn = new ButtonBuilder(TournamentGameLabel.update_btn.getText()).setDefaultButton().build();
        updateAppBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                InternetUtils.openAppUrl(Game.getInstance().getAppInfoService().getStoreAppId(), false);
                hide();
            }
        });
        addButton(updateAppBtn);
    }

    @Override
    protected String getLabelText() {
        return TournamentGameLabel.update_info.getText();
    }

    @Override
    public void hide() {
        Gdx.app.exit();
    }
}
