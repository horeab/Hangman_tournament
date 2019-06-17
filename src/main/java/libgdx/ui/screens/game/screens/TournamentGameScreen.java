package libgdx.ui.screens.game.screens;

import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentScreenCreator;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;
import libgdx.ui.implementations.config.dependecies.MultiPlayerLevelFinishedService;

public class TournamentGameScreen extends AbstractGameScreen<SimulatedOpponentScreenCreator> implements ScreenWithGameContext {

    private TournamentContext tournamentContext;
    private MultiPlayerLevelFinishedService multiPlayerLevelFinishedService;

    public TournamentGameScreen(GameContext gameContext, TournamentContext tournamentContext) {
        super(gameContext);
        this.tournamentContext = tournamentContext;
        this.multiPlayerLevelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
    }

    @Override
    protected SimulatedOpponentScreenCreator initScreenCreator(GameContext gameContext) {
        return new SimulatedOpponentScreenCreator(this, gameContext);
    }

    @Override
    public void executeLevelFinished() {
        screenManager.showLevelFinishedTournamentScreen(tournamentContext, getGameContext());
    }

    @Override
    public void goToNextQuestionScreen() {
        screenManager.startGameTournamentScreen(getGameContext(), tournamentContext);
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_tournament_info_msg).addToPopupManager();
    }

    @Override
    public LevelFinishedService getLevelFinishedService() {
        return multiPlayerLevelFinishedService;
    }
}
