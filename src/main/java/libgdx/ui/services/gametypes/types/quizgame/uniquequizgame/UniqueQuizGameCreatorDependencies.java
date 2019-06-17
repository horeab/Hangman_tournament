package libgdx.ui.services.gametypes.types.quizgame.uniquequizgame;

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
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.screencreator.UniqueQuizQuestionContainerCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.screencreator.UniqueQuizScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.service.UniqueAnswersQuizGameService;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.service.UniqueQuizRefreshQuestionDisplayService;

public class UniqueQuizGameCreatorDependencies extends CreatorDependencies {

    @Override
    public Class<? extends GameService> getGameServiceClass() {
        return UniqueAnswersQuizGameService.class;
    }

    @Override
    public RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons) {
        return new UniqueQuizRefreshQuestionDisplayService(screen, allAnswerButtons);
    }

    @Override
    public QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen) {
        return new UniqueQuizQuestionContainerCreatorService(gameUser, screen);
    }

    @Override
    public AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser) {
        return new UniqueQuizScreenBackgroundCreator(screen, gameUser);
    }

    @Override
    public HintButtonType getHintButtonType() {
        return HintButtonType.HINT_DISABLE_TWO_ANSWERS;
    }

}
