package libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.screencreator;

import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.quizgame.QuizQuestionContainerCreatorService;

public class DependentQuizQuestionContainerCreatorService extends QuizQuestionContainerCreatorService {

    public DependentQuizQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        super(gameUser, abstractGameScreen);
    }

}
