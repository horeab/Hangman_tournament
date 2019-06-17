package libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.service;


import java.util.Map;

import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;

public class DependentQuizRefreshQuestionDisplayService extends RefreshQuestionDisplayService<DependentAnswersQuizGameService> {

    public DependentQuizRefreshQuestionDisplayService(AbstractGameScreen abstractGameScreen, Map<String, MyButton> allAnswerButtons) {
        super(abstractGameScreen, allAnswerButtons);
    }

    @Override
    public void refreshQuestion(GameQuestionInfo gameQuestionInfo) {
    }

    @Override
    public void gameOverQuestion(GameQuestionInfo gameQuestionInfo) {
        if (gameQuestionInfo != null) {
            for (String answer : gameService.getAnswers()) {
                allAnswerButtons.get(answer).setButtonSkin(ButtonSkin.SQUARE_ANSWER_OPTION_CORRECT);
            }
        }
    }

}
