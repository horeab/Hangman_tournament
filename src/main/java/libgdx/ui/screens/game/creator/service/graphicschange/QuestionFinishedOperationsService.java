package libgdx.ui.screens.game.creator.service.graphicschange;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import java.util.Random;

import libgdx.controls.ScreenRunnable;
import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfig;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.controls.popup.notificationpopup.PositionNotificationPopup;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.screens.game.userinfoheader.ScreenPlayerHeaderContainer;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.SoundUtils;

public class QuestionFinishedOperationsService {

    private AbstractGameScreen gameScreen;
    private GameContext gameContext;

    public QuestionFinishedOperationsService(AbstractGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.gameContext = gameScreen.getGameContext();
    }


    public void executeGameFinishedDependingNumberOfQuestions() {
        QuestionFinishedOperationsService questionFinishedOperationsService = new QuestionFinishedOperationsService(gameScreen);
        if (gameContext.getCurrentUserGameUser().userHasMultipleQuestions()) {
            questionFinishedOperationsService.executeGameFinishedOperations();
        } else {
            questionFinishedOperationsService.executeFinishedQuestionOperations();
        }
    }

    public void executeFinishedQuestionOperations() {
        processLastFinishedQuestion();
        if (new Random().nextInt(8) == 2) {
            gameScreen.showPopupAd();
        }
    }

    private void processLastFinishedQuestion() {
        gameScreen.getScreenCreator().getGameScreenQuestionContainerCreator().getGameControlsService().disableTouchableAllControls();
        new Thread(new ScreenRunnable(gameScreen) {
            @Override
            public void executeOperations() {
                final boolean gameFinished = gameScreen.getLevelFinishedService().isGameFinished(gameContext);
                Gdx.app.postRunnable(new ScreenRunnable(gameScreen) {
                    @Override
                    public void executeOperations() {
                        if (gameFinished) {
                            executeGameFinishedOperations();
                        } else {
                            executeQuestionFinishedOperations();
                        }
                    }
                });
            }
        }).start();
    }

    private void executeQuestionFinishedOperations() {
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        if (currentUserGameUser.getFinishedQuestions() == currentUserGameUser.getTotalNrOfQuestions()) {
            waitingForOtherPlayer();
        } else {
            gameScreen.goToNextQuestionScreen();
        }
    }


    private void waitingForOtherPlayer() {
        MyNotificationPopupConfig myNotificationPopupConfig = new MyNotificationPopupConfigBuilder().setPopupWidth(Float.valueOf(ScreenDimensionsManager.getScreenWidth())).setText(TournamentGameLabel.live_game_waiting_player.getText(gameContext.getOpponentGameUser().getBaseUserInfo().getFullName())).build();
        PositionNotificationPopup positionNotificationPopup = new MyNotificationPopupCreator(myNotificationPopupConfig)
                .positionNotificationPopup(ScreenDimensionsManager.getScreenWidth() / 2 - myNotificationPopupConfig.getPopupWidth() / 4,
                        ScreenDimensionsManager.getScreenHeight() / 2);
        positionNotificationPopup.addToPopupManager();
        positionNotificationPopup.setTransform(true);
        new ActorAnimation(positionNotificationPopup, gameScreen).animateZoomInZoomOut(0.1f);
    }

    private void executeGameFinishedOperations() {
        new Thread(new ScreenRunnable(gameScreen) {
            @Override
            public void executeOperations() {
                final UsersWithLevelFinished usersWithLevelFinished = gameScreen.getLevelFinishedService().createUsersWithGameFinished(gameContext);
                Gdx.app.postRunnable(new ScreenRunnable(gameScreen) {
                    @Override
                    public void executeOperations() {
                        SoundUtils.playSound(gameScreen.getCurrentUser().equals(usersWithLevelFinished.getUserThatWon()) ? Resource.sound_success_game_over : Resource.sound_fail_game_over);
                        animateGameFinished(usersWithLevelFinished);
                    }
                });
            }
        }).start();
    }

    private void animateGameFinished(final UsersWithLevelFinished usersWithLevelFinished) {
        RunnableAction ra1 = new RunnableAction();
        ra1.setRunnable(new ScreenRunnable(gameScreen) {
            @Override
            public void executeOperations() {
                PlayerHeaderContainer playerHeaderContainer = ScreenPlayerHeaderContainer.getPlayerHeaderContainer(gameScreen);
                PlayerHeader playerHeaderThatWon = playerHeaderContainer.getPlayerHeader(usersWithLevelFinished.getUserThatWon());
                PlayerHeader playerHeaderThatLost = playerHeaderContainer.getPlayerHeader(usersWithLevelFinished.getUserThatLost());
                if (playerHeaderThatWon != null) {
                    playerHeaderThatWon.animateGameOverHeaderWin();
                }
                if (playerHeaderThatLost != null) {
                    playerHeaderThatLost.animateGameOverHeaderLose();
                }
            }
        });
        RunnableAction ra2 = new RunnableAction();
        ra2.setRunnable(new ScreenRunnable(gameScreen) {
            @Override
            public void executeOperations() {
                gameScreen.executeLevelFinished();
            }
        });
        gameScreen.addAction(Actions.sequence(ra1, Actions.delay(1f), ra2));
    }
}
