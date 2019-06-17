package libgdx.ui.services.gametypes.types.imageclickgame.service;


import java.util.Map;

import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;

public class ImageClickRefreshQuestionDisplayService extends RefreshQuestionDisplayService<ImageClickGameService> {

    public ImageClickRefreshQuestionDisplayService(AbstractGameScreen abstractGameScreen, Map<String, MyButton> allAnswerButtons) {
        super(abstractGameScreen, allAnswerButtons);
    }

    @Override
    public void refreshQuestion(GameQuestionInfo gameQuestionInfo) {
    }

    @Override
    public void gameOverQuestion(GameQuestionInfo gameQuestionInfo) {
        if (gameQuestionInfo != null) {
            for (String answer : gameService.getAnswers(gameQuestionInfo.getQuestion())) {
                allAnswerButtons.get(answer).setButtonSkin(ButtonSkin.ANSWER_IMAGE_CLICK_CORRECT);
            }
        }
    }

}
