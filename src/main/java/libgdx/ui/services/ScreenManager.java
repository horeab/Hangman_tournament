package libgdx.ui.services;

import java.util.List;

import libgdx.screen.AbstractScreenManager;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.ScreenTypeEnum;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;

public class ScreenManager extends AbstractScreenManager {

    @Override
    public void showMainScreen() {
        showScreen(ScreenTypeEnum.MAIN_MENU);
    }

    public void showLeaderboardScreen() {
        showScreen(ScreenTypeEnum.LEADERBOARD);
    }


    public void showCampaignScreen() {
        showScreen(ScreenTypeEnum.CAMPAIGN);
    }

    public void showAchievementsScreen() {
        showScreen(ScreenTypeEnum.ACHIEVEMENTS);
    }
    ////////TOURNAMENT///////////

    public void goToNextStageTournamentScreen(TournamentContext tournamentContext, GameConfig gameConfig) {
        showScreen(ScreenTypeEnum.TOURNAMENT_NEXT_STAGE, tournamentContext, gameConfig);
    }

    public void startNewTournamentOnlineScreen(List<BaseUserInfo> usersToChooseForTournament, GameConfig gameConfig) {
        showScreen(ScreenTypeEnum.TOURNAMENT_NEW, usersToChooseForTournament, gameConfig);
    }
    ////////CHOOSE GAME TYPE///////////

    public void showTournamentOnlineOptionsScreen() {
        showScreen(ScreenTypeEnum.TOURNAMENT_ONLINE_OPTIONS);
    }

    public void showPracticeOnlineOptionsScreen() {
        showScreen(ScreenTypeEnum.PRACTICE_ONLINE_OPTIONS);
    }

    ////////GAME///////////

    public void startGamePracticeScreen(GameContext gameContext) {
        showScreen(ScreenTypeEnum.GAME_PRACTICE, gameContext);
    }

    public void startGameCampaignScreen(GameContext gameContext, CampaignLevel campaignLevelEnum) {
        showScreen(ScreenTypeEnum.GAME_CAMPAIGN, gameContext, campaignLevelEnum);
    }

    public void startGameTournamentScreen(GameContext gameContext, TournamentContext tournamentContext) {
        showScreen(ScreenTypeEnum.GAME_TOURNAMENT, gameContext, tournamentContext);
    }
    ////////LEVEL FINISHED///////////

    public void showLevelFinishedPracticeScreen(GameContext gameContext) {
        showScreen(ScreenTypeEnum.LEVELFINISHED_PRACTICE, gameContext);
    }

    public void showLevelFinishedCampaignScreen(GameContext gameContext, CampaignLevel campaignLevelEnum) {
        showScreen(ScreenTypeEnum.LEVELFINISHED_CAMPAIGN, gameContext, campaignLevelEnum);
    }

    public void showLevelFinishedTournamentScreen(TournamentContext tournamentContext, GameContext gameContext) {
        showScreen(ScreenTypeEnum.LEVELFINISHED_TOURNAMENT, tournamentContext, gameContext);
    }

    public void showLevelFinishedTournamentWinScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        showScreen(ScreenTypeEnum.LEVELFINISHED_TOURNAMENT_WIN, usersWithLevelFinished, gameContext);
    }
    ////////////LIVE GAME////////////////

    public void showSearchUserScreen() {
        showScreen(ScreenTypeEnum.LIVE_GAME_SEARCH_USER);
    }

    public void showWaitingForUserScreen(GameContext gameContext, int liveGameId) {
        showScreen(ScreenTypeEnum.LIVE_GAME_WAITING_OTHER_USER, gameContext, liveGameId);
    }

    public void showChallengePlayerVersusPlayerScreen(GameContext gameContext, int liveGameId) {
        showScreen(ScreenTypeEnum.LIVE_GAME_START_SCREEN, gameContext, liveGameId);
    }

    public void startGameChallengeScreen(GameContext gameContext, int liveGameId) {
        showScreen(ScreenTypeEnum.GAME_CHALLENGE, gameContext, liveGameId, gameContext);
    }

    public void showLevelFinishedChallengeScreen(GameContext gameContext, int liveGameId) {
        showScreen(ScreenTypeEnum.LEVELFINISHED_CHALLENGE, gameContext, liveGameId);
    }

    public void showChallengeOnlineOptionsScreen(BaseUserInfo opponent) {
        showScreen(ScreenTypeEnum.LIVE_GAME_OPTIONS, opponent);
    }
}
