package libgdx.ui.services.game.livegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.controls.popup.notificationpopup.ChallengeNotificationPopupCreator;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.ChallengeGameScreen;
import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.model.game.question.Question;

public class LiveGameActionsService {

    private ScreenManager screenManager = TournamentGame.getInstance().getScreenManager();

    public void displayLiveGameChallenge(final LiveGameInvite liveGameInvite, AbstractScreen screen) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                new ChallengeNotificationPopupCreator(liveGameInvite).build().addToPopupManager();
            }
        });
    }

    public void startChallengeWithCoins(final BaseUserInfo opponent, final Question[] questions, final int liveGameId, final GameConfig gameConfig, final AbstractScreen screen) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                new StartScreenWithCoinsService(screen.getCurrentUser(), gameConfig).startChallenge(opponent, questions, liveGameId);
            }
        });
    }

    public void displayRematchNotificationPopup(final Integer userStatus, final AbstractLevelFinishedChallengeScreen screen) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                new LiveGameRematchService().displayRematchNotificationPopup(
                        userStatus,
                        (Table) screen.getRoot().findActor(UserInfoContainerCreator.getOpponentContainerName()));
            }
        });
    }

    public void opponentPressedAnswer(final String jsonGameInfo, final int gameInfoVersion, ChallengeGameScreen gameScreen) {
        gameScreen.opponentPressedAnswer(jsonGameInfo, gameInfoVersion);
    }

    public void currentUserPressedAnswer(final String jsonGameInfo, int gameInfoVersion, ChallengeGameScreen gameScreen) {
        gameScreen.updateCurrentUserPressedAnswerFromDb(jsonGameInfo, gameInfoVersion);
    }

    public void updateCurrentUserPressedAnswerToDb(ChallengeGameScreen screen, GameAnswerInfo lastAnswerPressed) {
        screen.updateCurrentUserPressedAnswerToDb(lastAnswerPressed);
    }

    public void executeBeforeLevelFinished(final AbstractGameScreen screen) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                screen.getScreenCreator().getQuestionFinishedOperationsService().executeFinishedQuestionOperations();
            }
        });
    }


    public void showLevelFinishedChallengeScreen(final AbstractGameScreen screen, final GameContext gameContext, final int liveGameId) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                screenManager.showLevelFinishedChallengeScreen(gameContext, liveGameId);
            }
        });
    }

    public void usersAreLoaded(ChallengeGameScreen screen) {
        screen.usersAreLoaded();
    }

    public void showChallengePlayerVersusPlayerScreen(final GameContext gameContext, final int liveGameId, AbstractScreen screen) {
        executeOperation(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                screenManager.showChallengePlayerVersusPlayerScreen(gameContext, liveGameId);
            }
        });
    }

    private void executeOperation(Runnable runnable) {
        Gdx.app.postRunnable(runnable);
    }
}
