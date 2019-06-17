package libgdx.ui.services.gametypes.types.quizgame.dependentquizgame;

import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.screencreator.DependentQuizQuestionContainerCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.screencreator.DependentQuizScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.service.DependentAnswersQuizGameService;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.service.DependentQuizRefreshQuestionDisplayService;

public class DependentQuizGameCreatorDependencies extends CreatorDependencies {

    @Override
    public Class<? extends GameService> getGameServiceClass() {
        return DependentAnswersQuizGameService.class;
    }

    @Override
    public RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons) {
        return new DependentQuizRefreshQuestionDisplayService(screen, allAnswerButtons);
    }

    @Override
    public QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen) {
        return new DependentQuizQuestionContainerCreatorService(gameUser, screen);
    }

    @Override
    public AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser) {
        return new DependentQuizScreenBackgroundCreator(screen, gameUser);
    }

    @Override
    public HintButtonType getHintButtonType() {
        return HintButtonType.HINT_DISABLE_TWO_ANSWERS;
    }

}
