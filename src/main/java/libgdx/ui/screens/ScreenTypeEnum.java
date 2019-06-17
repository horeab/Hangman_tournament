package libgdx.ui.screens;

import java.util.List;

import libgdx.game.Game;
import libgdx.screen.ScreenType;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.achievements.AchievementsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.actionoptions.gamescreens.ChallengeOptionsScreen;
import libgdx.ui.screens.actionoptions.gamescreens.PracticeOptionsScreen;
import libgdx.ui.screens.actionoptions.gamescreens.TournamentOptionsScreen;
import libgdx.ui.screens.game.screens.CampaignGameScreen;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.screens.game.screens.PracticeGameScreen;
import libgdx.ui.screens.game.screens.TournamentGameScreen;
import libgdx.ui.screens.leaderboard.LeaderboardScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.screens.levelfinished.service.LevelFinishedScreenServiceFactory;
import libgdx.ui.screens.levelfinished.tournament.LevelFinishedTournamentWinScreen;
import libgdx.ui.screens.livegame.PlayerVersusPlayerScreen;
import libgdx.ui.screens.livegame.SearchUserScreen;
import libgdx.ui.screens.livegame.WaitingForOtherUserScreen;
import libgdx.ui.screens.tournament.NewTournamentOnlineScreen;
import libgdx.ui.screens.tournament.NextStageTournamentOnlineScreen;
import libgdx.ui.model.game.GameContext;

public enum ScreenTypeEnum implements ScreenType {

    MAIN_MENU {
        public AbstractScreen getScreen(Object... params) {
            return TournamentGame.getInstance().getSubGameDependencyManager().getTournamentGameScreenCreator().createMainMenuScreen();
        }
    },
    LEADERBOARD {
        public AbstractScreen getScreen(Object... params) {
            return new LeaderboardScreen();
        }
    },
    CAMPAIGN {
        public AbstractScreen getScreen(Object... params) {
            return new libgdx.ui.screens.campaign.CampaignScreen();
        }
    },
    ACHIEVEMENTS {
        public AbstractScreen getScreen(Object... params) {
            return new AchievementsScreen();
        }
    },
    ///////////////////////TOURNAMENT//////////////////////
    TOURNAMENT_ONLINE_OPTIONS {
        public AbstractScreen getScreen(Object... params) {
            return new TournamentOptionsScreen();
        }
    },
    TOURNAMENT_NEW {
        public AbstractScreen getScreen(Object... params) {
            return new NewTournamentOnlineScreen((List) params[0], (GameConfig) params[1]);
        }
    },
    TOURNAMENT_NEXT_STAGE {
        public AbstractScreen getScreen(Object... params) {
            return new NextStageTournamentOnlineScreen((TournamentContext) params[0], (GameConfig) params[1]);
        }
    },
    ///////////////////////GAME//////////////////////
    PRACTICE_ONLINE_OPTIONS {
        public AbstractScreen getScreen(Object... params) {
            return new PracticeOptionsScreen();
        }
    },
    GAME_CAMPAIGN {
        public AbstractScreen getScreen(Object... params) {
            return new CampaignGameScreen((GameContext) params[0], (CampaignLevel) params[1]);
        }
    },
    GAME_PRACTICE {
        public AbstractScreen getScreen(Object... params) {
            return new PracticeGameScreen((GameContext) params[0]);
        }
    },
    GAME_TOURNAMENT {
        public AbstractScreen getScreen(Object... params) {
            return new TournamentGameScreen((GameContext) params[0], (TournamentContext) params[1]);
        }
    },
    ///////////////////////LEVELFINISHED//////////////////////
    LEVELFINISHED_CAMPAIGN {
        public AbstractScreen getScreen(Object... params) {
            return new LevelFinishedScreenServiceFactory()
                    .createStandardLevelFinishedScreenService().getLevelFinishedCampaignScreen((GameContext) params[0], (CampaignLevel) params[1]);
        }
    },
    LEVELFINISHED_PRACTICE {
        public AbstractScreen getScreen(Object... params) {
            return new LevelFinishedScreenServiceFactory()
                    .createStandardLevelFinishedScreenService().getLevelFinishedPracticeScreen((GameContext) params[0]);
        }
    },
    LEVELFINISHED_TOURNAMENT {
        public AbstractScreen getScreen(Object... params) {
            return new LevelFinishedScreenServiceFactory()
                    .createStandardLevelFinishedScreenService()
                    .getLevelFinishedTournamentScreen((TournamentContext) params[0], (GameContext) params[1]);
        }
    },
    LEVELFINISHED_CHALLENGE {
        public AbstractScreen getScreen(Object... params) {
            return new LevelFinishedScreenServiceFactory()
                    .createChallengeLevelFinishedScreenService((GameContext) params[0], (Integer) params[1])
                    .getGameFinishedChallengeScreen();
        }
    },
    LEVELFINISHED_TOURNAMENT_WIN {
        public AbstractScreen getScreen(Object... params) {
            return new LevelFinishedTournamentWinScreen((UsersWithLevelFinished) params[0], (GameContext) params[1]);
        }
    },
    ///////////////////////////////////////////////////////
    LIVE_GAME_OPTIONS {
        public AbstractScreen getScreen(Object... params) {
            return new ChallengeOptionsScreen((BaseUserInfo) params[0]);
        }
    },
    LIVE_GAME_SEARCH_USER {
        public AbstractScreen getScreen(Object... params) {
            return new SearchUserScreen();
        }
    },
    LIVE_GAME_WAITING_OTHER_USER {
        public AbstractScreen getScreen(Object... params) {
            return new WaitingForOtherUserScreen((GameContext) params[0], (Integer) params[1]);
        }
    },
    LIVE_GAME_START_SCREEN {
        public AbstractScreen getScreen(Object... params) {
            return new PlayerVersusPlayerScreen((GameContext) params[0], (Integer) params[1]);
        }
    },
    GAME_CHALLENGE {
        public AbstractScreen getScreen(Object... params) {
            return new ChallengeGameScreen((GameContext) params[0], (Integer) params[1]);
        }
    },;

}