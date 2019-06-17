package libgdx.ui.screens.game.screens;

import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

import libgdx.controls.ScreenRunnable;
import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.screens.game.creator.realopponent.RealOpponentScreenCreator;
import libgdx.ui.services.game.livegame.LiveGameAnswerUpdateService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;
import libgdx.ui.services.listeners.ListenerAll;
import libgdx.ui.services.listeners.livegame.beforegame.StartGameListener;
import libgdx.ui.services.listeners.livegame.ingame.GameFinishedListener;
import libgdx.ui.services.listeners.livegame.ingame.GameInfoChangeListener;
import libgdx.ui.services.listeners.livegame.ingame.OpponentAbandonGameListener;

public class ChallengeGameScreen extends AbstractGameScreen<RealOpponentScreenCreator> implements libgdx.ui.screens.game.ChallengeGameScreen, ScreenWithGameContext {

    private int liveGameId;
    private LiveGameService liveGameService = new LiveGameService();
    private LiveGameAnswerUpdateService liveGameAnswerUpdateService;
    private Timer.Task timerBeforeStartGameWaitOpponentAbandonGame;

    public ChallengeGameScreen(GameContext gameContext, int liveGameId) {
        super(gameContext);
        this.liveGameId = liveGameId;
    }

    @Override
    protected void initFields() {
        super.initFields();
        this.timerBeforeStartGameWaitOpponentAbandonGame = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                screenManager.showLevelFinishedChallengeScreen(getGameContext(), liveGameId);
            }
        }, 10);
        liveGameAnswerUpdateService = getGameContext().getCurrentUserCreatorDependencies().getLiveGameAnswerUpdateService(liveGameId, getScreenCreator());
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gamePay(getGameConfig());
    }

    @Override
    public void addListenersToScreen() {
        super.addListenersToScreen();
        addListenerToScreen(new StartGameListener<ChallengeGameScreen>(liveGameId, this));
    }

    @Override
    public void buildStage() {
        super.buildStage();
        getScreenCreator().getGameScreenQuestionContainerCreator().getGameControlsService().disableTouchableAllControls();
    }

    @Override
    public LevelFinishedService getLevelFinishedService() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
    }

    @Override
    protected RealOpponentScreenCreator initScreenCreator(GameContext gameContext) {
        return new RealOpponentScreenCreator(this, gameContext);
    }

    @Override
    public void afterBuildStage() {
        super.afterBuildStage();
        liveGameService.startGame(liveGameId);
    }

    @Override
    public void executeLevelFinished() {
        liveGameService.gameFinished(liveGameId);
        screenManager.showLevelFinishedChallengeScreen(getGameContext(), liveGameId);
    }

    @Override
    public void opponentPressedAnswer(final String jsonGameInfo, final int gameInfoVersion) {
        liveGameAnswerUpdateService.opponentPressedAnswer(getGameContext().getOpponentGameUser(), jsonGameInfo, gameInfoVersion);
    }

    @Override
    public void usersAreLoaded() {
        timerBeforeStartGameWaitOpponentAbandonGame.cancel();
        getScreenCreator().getGameScreenQuestionContainerCreator().getGameControlsService().enableTouchableAllControls();
        List<ListenerAll> screenListeners = new ArrayList<>();
        screenListeners.add(new GameFinishedListener<>(liveGameId, this));
        screenListeners.add(new GameInfoChangeListener<>(liveGameId, liveGameAnswerUpdateService.isUser1LoggedIn(), this));
        screenListeners.add(new OpponentAbandonGameListener<>(liveGameId, this));
        for (ListenerAll listener : screenListeners) {
            listener.start();
        }
    }

    @Override
    public void updateCurrentUserPressedAnswerFromDb(final String jsonGameInfo, final int gameInfoVersion) {
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(getGameContext().getCurrentUserGameUser(), jsonGameInfo, gameInfoVersion);
    }

    @Override
    public void updateCurrentUserPressedAnswerToDb(final GameAnswerInfo lastAnswerPressed) {
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerToDb(lastAnswerPressed, this);
    }

    @Override
    public void goToNextQuestionScreen() {
        screenManager.startGameChallengeScreen(getGameContext(), liveGameId);
    }

    public int getCurrentGameInfoVersion() {
        return liveGameAnswerUpdateService.getCurrentGameInfoVersion();
    }

    @Override
    public int getLiveGameId() {
        return liveGameId;
    }

    @Override
    public void showPopupAd() {
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_challenge_info_msg, new ScreenRunnable(this) {
            @Override
            public void executeOperations() {
                liveGameService.abandonMatch(liveGameId);
                screenManager.showMainScreen();
            }
        }).addToPopupManager();
    }
}
