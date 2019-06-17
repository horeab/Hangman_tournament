package libgdx.ui.services.gametypes.creatordependencies;

import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.realopponent.RealOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.services.game.livegame.LiveGameAnswerUpdateService;
import libgdx.ui.services.questions.QuestionCreator;

public abstract class CreatorDependencies {

    public GameService getGameService(int userId, Question question) {
        return GameServiceContainer.getGameService(getGameServiceClass(), userId, question);
    }

    public LiveGameAnswerUpdateService getLiveGameAnswerUpdateService(int liveGameId, AbstractGameScreenCreator screenCreator) {
        return new LiveGameAnswerUpdateService(liveGameId, screenCreator);
    }

    public RealOpponentQuestionContainerCreator getRealOpponentQuestionContainerCreator(ChallengeGameScreen abstractScreen, GameContext gameContext) {
        return new RealOpponentQuestionContainerCreator(abstractScreen, gameContext);
    }

    public SimulatedOpponentQuestionContainerCreator getSimulatedOpponentQuestionContainerCreator(AbstractGameScreen abstractScreen, GameContext gameContext) {
        return new SimulatedOpponentQuestionContainerCreator(abstractScreen, gameContext);
    }

    public QuestionCreator getQuestionCreator(QuestionDifficulty questionDifficulty, QuestionCategory questionCategory) {
        return new QuestionCreator(questionDifficulty, questionCategory);
    }

    public abstract Class<? extends GameService> getGameServiceClass();

    public abstract RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons);

    public abstract QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen);

    public abstract AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser);

    public abstract HintButtonType getHintButtonType();

}
