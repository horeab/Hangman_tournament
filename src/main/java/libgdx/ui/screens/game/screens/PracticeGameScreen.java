package libgdx.ui.screens.game.screens;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentScreenCreator;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;

public class PracticeGameScreen extends AbstractGameScreen<SimulatedOpponentScreenCreator> implements ScreenWithGameContext {

    public PracticeGameScreen(GameContext gameContext) {
        super(gameContext);
    }

    @Override
    protected SimulatedOpponentScreenCreator initScreenCreator(GameContext gameContext) {
        return new SimulatedOpponentScreenCreator(this, gameContext);
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gamePay(getGameConfig());
    }

    @Override
    public void executeLevelFinished() {
        screenManager.showLevelFinishedPracticeScreen(getGameContext());
    }

    @Override
    public void goToNextQuestionScreen() {
        screenManager.startGamePracticeScreen(getGameContext());
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

    @Override
    public LevelFinishedService getLevelFinishedService() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService();
    }
}
