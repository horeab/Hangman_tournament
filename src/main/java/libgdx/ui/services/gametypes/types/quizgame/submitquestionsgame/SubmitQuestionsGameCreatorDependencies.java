package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame;

import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferSinglePlayerLevelFinishedService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.realopponent.RealOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.screens.CampaignGameScreen;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.screens.game.screens.PracticeGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.livegame.LiveGameAnswerUpdateService;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsContainerCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsRealOpponentQuestionContainerCreator;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsSimulatedOpponentQuestionContainerCreator;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsWithHeaderAndTimerContainerCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsLiveGameAnswerUpdateService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsRefreshQuestionDisplayService;

public class SubmitQuestionsGameCreatorDependencies extends CreatorDependencies {

    @Override
    public Class<? extends GameService> getGameServiceClass() {
        return SubmitQuestionsGameService.class;
    }

    @Override
    public RealOpponentQuestionContainerCreator getRealOpponentQuestionContainerCreator(ChallengeGameScreen abstractScreen, GameContext gameContext) {
        return new SubmitQuestionsRealOpponentQuestionContainerCreator(abstractScreen, gameContext);
    }

    @Override
    public SimulatedOpponentQuestionContainerCreator getSimulatedOpponentQuestionContainerCreator(AbstractGameScreen abstractScreen, GameContext gameContext) {
        return new SubmitQuestionsSimulatedOpponentQuestionContainerCreator(abstractScreen, gameContext);
    }

    @Override
    public LiveGameAnswerUpdateService getLiveGameAnswerUpdateService(int liveGameId, AbstractGameScreenCreator screenCreator) {
        return new SubmitQuestionsLiveGameAnswerUpdateService(liveGameId, screenCreator);
    }

    @Override
    public RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons) {
        return new SubmitQuestionsRefreshQuestionDisplayService(screen, allAnswerButtons);
    }

    @Override
    public QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen) {
        SubmitQuestionsContainerCreatorService containerCreatorService;
        if (gameUser.getTotalNrOfQuestions() == ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS) {
            containerCreatorService = new SubmitQuestionsWithHeaderAndTimerContainerCreatorService(gameUser, screen);
        } else {
            containerCreatorService = new SubmitQuestionsContainerCreatorService(gameUser, screen);
        }
        return containerCreatorService;
    }

    @Override
    public AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser) {
        return new SubmitQuestionsScreenBackgroundCreator(screen, gameUser);
    }

    @Override
    public HintButtonType getHintButtonType() {
        return HintButtonType.HINT_DISABLE_TWO_ANSWERS;
    }

}
