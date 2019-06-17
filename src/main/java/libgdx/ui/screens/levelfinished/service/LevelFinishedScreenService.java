package libgdx.ui.screens.levelfinished.service;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.screens.levelfinished.campaign.LevelFinishedCampaignCurrentUserLoseScreen;
import libgdx.ui.screens.levelfinished.campaign.LevelFinishedCampaignCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.campaign.LevelFinishedCampaignScreen;
import libgdx.ui.screens.levelfinished.practice.LevelFinishedPracticeCurrentUserLoseScreen;
import libgdx.ui.screens.levelfinished.practice.LevelFinishedPracticeCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.practice.LevelFinishedPracticeScreen;
import libgdx.ui.screens.levelfinished.tournament.AbstractLevelFinishedTournamentScreen;
import libgdx.ui.screens.levelfinished.tournament.LevelFinishedTournamentCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.tournament.LevelFinishedTournamentOpponentWinScreen;

public class LevelFinishedScreenService {

    public LevelFinishedCampaignScreen getLevelFinishedCampaignScreen(GameContext gameContext, CampaignLevel campaignLevelEnum) {
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService()
                .createUsersWithGameFinished(gameContext);
        if (TournamentGame.getInstance().getCurrentUser().equals(usersWithLevelFinished.getUserThatWon())) {
            return new LevelFinishedCampaignCurrentUserWinScreen(usersWithLevelFinished, gameContext, campaignLevelEnum);
        } else {
            return new LevelFinishedCampaignCurrentUserLoseScreen(usersWithLevelFinished, gameContext, campaignLevelEnum);
        }
    }

    public LevelFinishedPracticeScreen getLevelFinishedPracticeScreen(GameContext gameContext) {
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService()
                .createUsersWithGameFinished(gameContext);
        if (TournamentGame.getInstance().getCurrentUser().equals(usersWithLevelFinished.getUserThatWon())) {
            return new LevelFinishedPracticeCurrentUserWinScreen(usersWithLevelFinished, gameContext);
        } else {
            return new LevelFinishedPracticeCurrentUserLoseScreen(usersWithLevelFinished, gameContext);
        }
    }

    public AbstractLevelFinishedTournamentScreen getLevelFinishedTournamentScreen(TournamentContext tournamentContext,
                                                                                  GameContext gameContext) {
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService()
                .createUsersWithGameFinished(gameContext);
        if (TournamentGame.getInstance().getCurrentUser().equals(usersWithLevelFinished.getUserThatWon())) {
            return new LevelFinishedTournamentCurrentUserWinScreen(tournamentContext, usersWithLevelFinished, gameContext);
        } else {
            return new LevelFinishedTournamentOpponentWinScreen(tournamentContext, usersWithLevelFinished, gameContext);
        }
    }

}
