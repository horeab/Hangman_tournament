package libgdx.ui.controls.popup;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.popup.MyPopup;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;

public class ExitGamePopup extends MyPopup {

    private TournamentGameLabel infoMsg;
    private Runnable executeOnExit;

    public ExitGamePopup(AbstractScreen abstractScreen,
                         TournamentGameLabel infoMsg) {
        this(abstractScreen, infoMsg, new Runnable() {
            @Override
            public void run() {
                TournamentGame.getInstance().getScreenManager().showMainScreen();
            }
        });
    }

    public ExitGamePopup(AbstractScreen abstractScreen,
                         TournamentGameLabel infoMsg,
                         Runnable executeOnExit) {
        super(abstractScreen);
        this.infoMsg = infoMsg;
        this.executeOnExit = executeOnExit;
    }

    @Override
    public void addButtons() {
        MyButton continueBtn = new ButtonBuilder(TournamentGameLabel.exit_game_continue_btn.getText()).setDefaultButton().build();
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        MyButton exitBtn = new ButtonBuilder(TournamentGameLabel.exit_game_exit_btn.getText()).setDefaultButton().build();
        exitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                executeOnExit.run();
            }
        });
        addButton(continueBtn);
        addButton(exitBtn);
    }

    @Override
    protected String getLabelText() {
        return infoMsg.getText();
    }

}
