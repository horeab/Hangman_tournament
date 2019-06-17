package libgdx.ui.screens.actionoptions.gameconfig;

import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.util.TournamentStage;

public class TournamentContext {

    private TournamentStage tournamentStage;
    private List<TournamentUser> tournamentUsers;
    private int availableHints;

    public TournamentContext(TournamentStage tournamentStage, List<TournamentUser> tournamentUsers) {
        this.tournamentStage = tournamentStage;
        this.tournamentUsers = tournamentUsers;
        this.availableHints = TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getTournamentAmountAvailableHints();
    }

    public TournamentStage getTournamentStage() {
        return tournamentStage;
    }

    public List<TournamentUser> getTournamentUsers() {
        return tournamentUsers;
    }

    public void setTournamentStage(TournamentStage tournamentStage) {
        this.tournamentStage = tournamentStage;
    }

    public void setAvailableHints(int availableHints) {
        this.availableHints = availableHints;
    }

    public int getAvailableHints() {
        return availableHints;
    }
}
