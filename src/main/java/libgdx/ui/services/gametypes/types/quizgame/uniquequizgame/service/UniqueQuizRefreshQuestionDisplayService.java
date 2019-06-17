package libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.service;


import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.services.gametypes.types.quizgame.QuizGameService;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.screencreator.UniqueQuizQuestionContainerCreatorService;

public class UniqueQuizRefreshQuestionDisplayService extends RefreshQuestionDisplayService<QuizGameService> {

    public UniqueQuizRefreshQuestionDisplayService(AbstractGameScreen abstractGameScreen, Map<String, MyButton> allAnswerButtons) {
        super(abstractGameScreen, allAnswerButtons);
    }

    @Override
    public void refreshQuestion(GameQuestionInfo gameQuestionInfo) {
    }

    @Override
    public void gameOverQuestion(GameQuestionInfo gameQuestionInfo) {
        if (gameQuestionInfo != null) {
            for (String answer : gameService.getAnswers()) {
                allAnswerButtons.get(answer).setButtonSkin(UniqueQuizQuestionContainerCreatorService.getCorrectButtonSkin(gameService.getAllAnswerOptions()));
            }
        }
    }

}
