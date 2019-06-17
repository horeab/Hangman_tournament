package libgdx.ui.screens.tournament;

import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator;
import libgdx.ui.util.TournamentStage;

public abstract class AbstractTournamentScreen extends AbstractScreen {

    private TournamentContext tournamentContext;
    private GameConfig gameConfig;

    AbstractTournamentScreen(TournamentContext tournamentContext, GameConfig gameConfig) {
        this.tournamentContext = tournamentContext;
        this.gameConfig = gameConfig;
    }

    public TournamentContext getTournamentContext() {
        return tournamentContext;
    }

    void setTournamentStage(TournamentStage tournamentStage) {
        this.tournamentContext.setTournamentStage(tournamentStage);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        new TournamentLoadingScreenUserProfilePictureCreator(this, tournamentContext, gameConfig).create();
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_tournament_info_msg).addToPopupManager();
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }
}
