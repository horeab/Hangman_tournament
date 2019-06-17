package libgdx.ui.screens.tournament;

import java.util.List;

import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.services.tournament.CreateTournamentService;
import libgdx.ui.util.TournamentStage;

public class NewTournamentOnlineScreen extends AbstractTournamentScreen {

    public NewTournamentOnlineScreen(List<BaseUserInfo> usersToChooseForTournament, GameConfig gameConfig) {
        super(new TournamentContext(TournamentStage.STAGE_0, new CreateTournamentService(usersToChooseForTournament).createNewTournamentUsers()), gameConfig);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        modifyStats();
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gamePay(getGameConfig());
    }

    private void modifyStats() {
        gameStatsDbApiService.incrementGameStatsTournamentsStarted(getCurrentUser().getId(), getGameConfig().getUniqueId());
    }


}
