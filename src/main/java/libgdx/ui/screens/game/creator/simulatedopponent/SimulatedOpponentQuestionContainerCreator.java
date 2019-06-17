package libgdx.ui.screens.game.creator.simulatedopponent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import libgdx.controls.ScreenRunnable;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.service.graphicschange.QuestionFinishedOperationsService;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenQuestionContainerCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;

public class SimulatedOpponentQuestionContainerCreator<TGameService extends GameService> extends AbstractGameScreenQuestionContainerCreator<AbstractGameScreen, TGameService> {

    private long opponentAnswerCheckCountdownAmount = 999999999;

    public SimulatedOpponentQuestionContainerCreator(AbstractGameScreen abstractScreen, GameContext gameContext) {
        super(abstractScreen, gameContext);
    }

    @Override
    public void beforeStartGame() {
        if (getGameContext().getOpponentGameUsers() != null && !getGameContext().getOpponentGameUsers().isEmpty()) {
            refreshOpponentAnswerPressCounter();
        }
    }

    @Override
    public void buttonClick(final String answer) {
        GameUser currentUserGameUser = getGameContext().getCurrentUserGameUser();
        GameQuestionInfo gameQuestionInfo = currentUserGameUser.getGameQuestionInfo();
        getGameService().addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo(answer, getGameScreen().getMillisPassedSinceScreenDisplayed()));
        processGameInfo(gameQuestionInfo);
    }

    private void refreshOpponentAnswerPressCounter() {
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new ScreenRunnable(getGameScreen()) {
            @Override
            public void executeOperations() {
                try {
                    GameQuestionInfo gameQuestionInfo = getGameContext().getOpponentGameUser().getGameQuestionInfo();
                    GameAnswerInfo gameAnswerInfo = getOpponentGameService().simulatePressedAnswer(gameQuestionInfo);
                    processOpponentAnswerPressCounter(gameQuestionInfo, gameAnswerInfo);
                    if (opponentAnswerCheckCountdownAmount == 0) {
                        executorService.shutdown();
                    }
                    opponentAnswerCheckCountdownAmount--;
                } catch (Exception e) {
                    System.out.print("");
                }
            }

            @Override
            public void executeOperationsAfterScreenChanged() {
                executorService.shutdown();
            }
        }, 0, 10, TimeUnit.MILLISECONDS);
    }

    public void processOpponentAnswerPressCounter(GameQuestionInfo gameQuestionInfo, GameAnswerInfo gameAnswerInfo) {
        LevelFinishedService levelFinishedService = getGameScreen().getLevelFinishedService();
        GameUser opponentGameUser = getGameContext().getOpponentGameUser();
        if (processGameAnswerInfoDependingNumberOfQuestions()) {
            if (gameAnswerInfo != null &&
                    opponentGameUser.getTotalAnswerMillisForGameQuestionInfoList() + gameAnswerInfo.getMillisAnswered() < getGameContext().getMillisGameInProgress()) {
                updateGameQuestionInfo(gameAnswerInfo, opponentGameUser);
            }
            if (levelFinishedService.isGameFinished(getGameContext()) && !gameQuestionInfo.isQuestionOpen()) {
                executeGameFinishedOperations();
            }
        }
    }

    private void updateGameQuestionInfo(GameAnswerInfo gameAnswerInfo, GameUser gameUser) {
        getOpponentGameService().addAnswerToGameInfo(gameUser, gameAnswerInfo);
        getOpponentHeaderAnimationService().gameInfoChanged();
    }

    private void executeGameFinishedOperations() {
        getRefreshQuestionDisplayService().gameOverQuestion(getGameScreen().getGameContext().getCurrentUserGameUser().getGameQuestionInfo());
        new QuestionFinishedOperationsService(getGameScreen()).executeGameFinishedDependingNumberOfQuestions();
    }

    private boolean processGameAnswerInfoDependingNumberOfQuestions() {
        return getGameContext().getCurrentUserGameUser().userHasMultipleQuestions()
                || getGameContext().getOpponentGameUser().getGameQuestionInfo().isQuestionOpen();
    }

    protected TGameService getOpponentGameService() {
        GameUser opponentGameUser = getGameContext().getOpponentGameUser();
        GameQuestionInfo gameQuestionInfo = opponentGameUser.getGameQuestionInfo();
        return (TGameService) GameServiceContainer.getGameService(opponentGameUser.getBaseUserInfo().getId(), gameQuestionInfo);
    }

}
