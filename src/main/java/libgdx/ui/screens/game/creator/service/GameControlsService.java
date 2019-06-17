package libgdx.ui.screens.game.creator.service;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import libgdx.ui.controls.button.HintButton;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyLabel;
import libgdx.resources.ResourcesManager;

public class GameControlsService {

    private Map<String, MyButton> allAnswerButtons;
    private List<HintButton> hintButtons;

    public GameControlsService(Map<String, MyButton> allAnswerButtons, List<HintButton> hintButtons) {
        this.allAnswerButtons = allAnswerButtons;
        this.hintButtons = hintButtons;
    }

    public void disableButton(MyButton btn) {
        if (!btn.isDisabled()) {
            btn.setDisabled(true);
        }
    }

    public void disableAllControls() {
        for (MyButton button : allAnswerButtons.values()) {
            disableButton(button);
        }
        disableAllHintButtons();
    }

    public void disableAllHintButtons() {
        for (HintButton hintButton : hintButtons) {
            //stop animation
            hintButton.getMyButton().setTransform(false);
            hintButton.getMyButton().setDisabled(true);
        }
    }

    public void disableTouchableAllControls() {
        processTouchableControls(ResourcesManager.getLabelGrey(), Touchable.disabled);
    }

    public void enableTouchableAllControls() {
        processTouchableControls(ResourcesManager.getLabelBlack(), Touchable.enabled);
    }

    private void processTouchableControls(String labelStyleName, Touchable touchable) {
        List<MyButton> buttonsToProcess = new ArrayList<>(allAnswerButtons.values());
        for (HintButton hintButton : hintButtons) {
            buttonsToProcess.add(hintButton.getMyButton());
        }
        for (MyButton button : buttonsToProcess) {
            List<MyLabel> centerRowLabels = button.getCenterRowLabels();
            for (MyLabel label : centerRowLabels) {
                if (!button.isDisabled()) {
                    label.setStyle(labelStyleName);
                }
            }
            button.setTouchable(touchable);
        }
    }
}
