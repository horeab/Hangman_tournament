package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service;


import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.quizgame.QuizGameService;

public class SubmitQuestionsRefreshQuestionDisplayService extends RefreshQuestionDisplayService<QuizGameService> {

    public SubmitQuestionsRefreshQuestionDisplayService(AbstractGameScreen abstractGameScreen, Map<String, MyButton> allAnswerButtons) {
        super(abstractGameScreen, allAnswerButtons);
    }

    @Override
    public void refreshQuestion(GameQuestionInfo gameQuestionInfo) {
    }

    @Override
    public void gameOverQuestion(GameQuestionInfo gameQuestionInfo) {
    }
}
