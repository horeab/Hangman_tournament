package libgdx.ui.services.game.livegame;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import libgdx.controls.ScreenRunnable;
import libgdx.screen.AbstractScreen;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;

public class LiveGameAnswerUpdateService {

    private int liveGameId;
    private int currentGameInfoVersion;
    private boolean isUser1LoggedIn;
    private AbstractGameScreenCreator screenCreator;
    private LiveGameService liveGameService = new LiveGameService();

    public LiveGameAnswerUpdateService(int liveGameId, AbstractGameScreenCreator screenCreator) {
        this.liveGameId = liveGameId;
        this.screenCreator = screenCreator;
        this.isUser1LoggedIn = liveGameService.isUser1LoggedIn(liveGameId);
        this.currentGameInfoVersion = liveGameService.getCurrentUserHasCurrentUserGameInfoVersionValue(liveGameId) + 1;
    }

    public void opponentPressedAnswer(final GameUser opponent, final String jsonGameInfo, final int gameInfoVersion) {
        liveGameService.updateCurrentUserHasOpponentGameInfoVersion(liveGameId, gameInfoVersion);
        for (GameAnswerInfo gameAnswerInfo : getGameAnswerInfo(jsonGameInfo)) {
            if (opponent.getGameQuestionInfo() != null) {
                GameService gameService = GameServiceContainer.getGameService(opponent.getBaseUserInfo().getId(), opponent.getGameQuestionInfo());
                gameService.addAnswerToGameInfo(opponent, new GameAnswerInfo(gameAnswerInfo.getAnswer(), gameAnswerInfo.getMillisAnswered()));
                opponentGameInfoChangedHeaderAnimation();
            }
        }
    }

    protected List<GameAnswerInfo> getGameAnswerInfo(String jsonGameInfo) {
        return Collections.singletonList(new Gson().fromJson(jsonGameInfo, GameAnswerInfo.class));
    }

    /**
     * Execute update to database only if the current user and the opponent have the correct gameInfoVersion
     * The currentUser presses and answer. The version of the currentUser gameInfo is changed because of this.
     * This triggers the opponent listener to update the GameInfo.
     * In some cases eg. Internet is down, the update misses a version number.
     * This Listener executes the new GameInfo update for the current user only after the opponent has the latest gameInfo from the currentUser.
     */
    public void updateCurrentUserPressedAnswerToDb(final GameAnswerInfo lastAnswerPressed, AbstractScreen screen) {

        final LiveGameService liveGameService = new LiveGameService();
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public synchronized void run() {
                int shouldHavePrecedentVersion = currentGameInfoVersion - 1;
                int[] versions = liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId);
                if (versions[0] == shouldHavePrecedentVersion && versions[1] == shouldHavePrecedentVersion) {
                    String jsonLastAnswerPressed = new Gson().toJson(lastAnswerPressed);
                    if (isUser1LoggedIn) {
                        liveGameService.updateUser1GameInfo(liveGameId, jsonLastAnswerPressed, currentGameInfoVersion);
                    } else {
                        liveGameService.updateUser2GameInfo(liveGameId, jsonLastAnswerPressed, currentGameInfoVersion);
                    }
                    currentGameInfoVersion++;
                    executorService.shutdown();
                }
            }
        }, 0, 300, TimeUnit.MILLISECONDS);
    }

    public void updateCurrentUserPressedAnswerFromDb(final GameUser currentUser, final String jsonGameInfo, final int gameInfoVersion) {
        liveGameService.updateCurrentUserHasCurrentUserGameInfoVersion(liveGameId, gameInfoVersion);
        for (GameAnswerInfo gameAnswerInfo : getGameAnswerInfo(jsonGameInfo)) {
            GameQuestionInfo gameQuestionInfo = currentUser.getGameQuestionInfo();
            if (gameQuestionInfo != null) {
                GameServiceContainer.getGameService(currentUser.getBaseUserInfo().getId(), gameQuestionInfo).addAnswerToGameInfo(currentUser, gameAnswerInfo);
                processCurrentUserGameInfo(gameQuestionInfo);
            }
            enableTouchableAllControls();
        }
    }

    private void opponentGameInfoChangedHeaderAnimation() {
        if (screenCreator != null && screenCreator.getOpponentHeaderAnimationService() != null) {
            screenCreator.getOpponentHeaderAnimationService().gameInfoChanged();
        }
    }

    private void processCurrentUserGameInfo(final GameQuestionInfo gameQuestionInfo) {
        Gdx.app.postRunnable(new ScreenRunnable(TournamentGame.getInstance().getAbstractScreen()) {
            @Override
            public void executeOperations() {
                screenCreator.getGameScreenQuestionContainerCreator().processGameInfo(gameQuestionInfo);
            }
        });
    }

    private void enableTouchableAllControls() {
        screenCreator.getGameScreenQuestionContainerCreator().getGameControlsService().enableTouchableAllControls();
    }

    public int getCurrentGameInfoVersion() {
        return currentGameInfoVersion;
    }

    public boolean isUser1LoggedIn() {
        return isUser1LoggedIn;
    }
}
