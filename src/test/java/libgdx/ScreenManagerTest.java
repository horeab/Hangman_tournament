package libgdx;

import com.google.gson.Gson;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaCampaignLevelEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
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
import libgdx.ui.screens.levelfinished.campaign.LevelFinishedCampaignCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.challenge.LevelFinishedChallengeCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.practice.LevelFinishedPracticeCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.tournament.LevelFinishedTournamentCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.tournament.LevelFinishedTournamentWinScreen;
import libgdx.ui.screens.livegame.PlayerVersusPlayerScreen;
import libgdx.ui.screens.livegame.SearchUserScreen;
import libgdx.ui.screens.livegame.WaitingForOtherUserScreen;
import libgdx.ui.screens.mainmenu.MainMenuScreen;
import libgdx.ui.screens.tournament.NewTournamentOnlineScreen;
import libgdx.ui.screens.tournament.NextStageTournamentOnlineScreen;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.tournament.CreateTournamentService;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;
import libgdx.ui.util.TournamentStage;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ScreenManagerTest extends TournamentGameTestDbApiService {

    @Test
    public void goToNextStageTournamentScreen() {
        screenManager.goToNextStageTournamentScreen(new TournamentContext(TournamentStage.STAGE_0, new CreateTournamentService(TestDataCreator.createUsers()).createNewTournamentUsers()), getGameConfig());
        assertAndProcessScreen(NextStageTournamentOnlineScreen.class);
    }

    @Test
    public void showCampaignScreen() {
        screenManager.showCampaignScreen();
        assertAndProcessScreen(libgdx.ui.screens.campaign.CampaignScreen.class);
    }

    @Test
    public void showAchievementsScreen() {
        screenManager.showAchievementsScreen();
        assertAndProcessScreen(AchievementsScreen.class);
    }

    @Test
    public void startNewTournamentOnlineScreen() {
        screenManager.startNewTournamentOnlineScreen(TestDataCreator.createUsers(), getGameConfig());
        assertAndProcessScreen(NewTournamentOnlineScreen.class);
    }

    @Override
    public GameConfig getGameConfig() {
        return new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString());
    }

    @Test
    public void startGameCampaignScreen() {
        screenManager.startGameCampaignScreen(TestDataCreator.createEmptyGameContext(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.campaign_level_0_4, DateUtils.getNowDateString()), new ArrayList<>()), HangmanArenaCampaignLevelEnum.LEVEL_0_1);
        assertAndProcessScreen(CampaignGameScreen.class);
    }


    @Test
    public void showLevelFinishedCampaignScreen() {
        initialScreen();
        screenManager.showLevelFinishedCampaignScreen(TestDataCreator.createGameContext(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.campaign_level_0, DateUtils.getNowDateString()), new ArrayList<>(), TestDataCreator.QUESTION_0), HangmanArenaCampaignLevelEnum.LEVEL_0);
        assertAndProcessScreen(LevelFinishedCampaignCurrentUserWinScreen.class);
    }


    @Test
    public void showLeaderboard() {
        screenManager.showLeaderboardScreen();
        assertAndProcessScreen(LeaderboardScreen.class);
    }

    @Test
    public void showTournamentOnlineOptionsScreen() {
        screenManager.showTournamentOnlineOptionsScreen();
        assertAndProcessScreen(TournamentOptionsScreen.class);
    }

    @Test
    public void showPracticeOnlineOptionsScreen() {
        screenManager.showPracticeOnlineOptionsScreen();
        assertAndProcessScreen(PracticeOptionsScreen.class);
    }

    @Test
    public void startGamePracticeScreen() {
        screenManager.startGamePracticeScreen(TestDataCreator.createEmptyGameContext(getGameConfig(),new ArrayList<>()));
        TestUtils.waitt(1000);
        assertAndProcessScreen(PracticeGameScreen.class);
    }

    @Test
    public void startGameTournamentScreen() {
        screenManager.startGameTournamentScreen(TestDataCreator.createEmptyGameContext(getGameConfig(),new ArrayList<>()), new TournamentContext(TournamentStage.STAGE_0, new ArrayList<>()));
        assertAndProcessScreen(TournamentGameScreen.class);
    }

    @Test
    public void showLevelFinishedPracticeScreen() {
        initialScreen();
        screenManager.showLevelFinishedPracticeScreen(TestDataCreator.createGameContext(getGameConfig(), TestDataCreator.QUESTION_0));
        assertAndProcessScreen(LevelFinishedPracticeCurrentUserWinScreen.class);
    }

    @Test
    public void showLevelFinishedTournamentWinScreen() {
        screenManager.showMainScreen();
        TestUtils.waitt(1000);
        GameContext gameContext = TestDataCreator.createGameContext(getGameConfig(), TestDataCreator.QUESTION_0);
        screenManager.showLevelFinishedTournamentWinScreen(TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService().createUsersWithGameFinished(gameContext), gameContext);
        assertAndProcessScreen(LevelFinishedTournamentWinScreen.class);
    }

    @Test
    public void showMainMenuScreen() {
        screenManager.showMainScreen();
        assertAndProcessScreen(MainMenuScreen.class);
    }

    @Test
    public void showLevelFinishedTournamentScreen() {
        screenManager.showMainScreen();
        TestUtils.waitt(1000);
        screenManager.showLevelFinishedTournamentScreen(new TournamentContext(TournamentStage.STAGE_0, new ArrayList<>()), TestDataCreator.createGameContext(getGameConfig(), TestDataCreator.QUESTION_0));
        assertAndProcessScreen(LevelFinishedTournamentCurrentUserWinScreen.class);
    }

    @Test
    public void showSearchUserScreen() {
        screenManager.showSearchUserScreen();
        assertAndProcessScreen(SearchUserScreen.class);
    }

    @Test
    public void showWaitingForUserScreen() {
        LiveGame liveGame = createLiveGame();
        screenManager.showWaitingForUserScreen(TestDataCreator.createEmptyGameContextWithTwoUsers(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.challenge_2, DateUtils.getNowDateString())), liveGame.getId());
        TestUtils.waitt(1000);
        assertAndProcessScreen(WaitingForOtherUserScreen.class);
    }

    @Test
    public void showChallengePlayerVersusPlayerScreen() {
        LiveGame liveGame = createLiveGame();
        TestUtils.waitt();
        screenManager.showChallengePlayerVersusPlayerScreen(TestDataCreator.createEmptyGameContextWithTwoUsers(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.challenge_2, DateUtils.getNowDateString())), liveGame.getId());
        assertAndProcessScreen(PlayerVersusPlayerScreen.class);
    }

    @Test
    public void startGameChallengeScreen() {
        LiveGame liveGame = createLiveGame();
        TestUtils.waitt();
        screenManager.startGameChallengeScreen(TestDataCreator.createEmptyGameContext(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.challenge_2, DateUtils.getNowDateString()),new ArrayList<>()), liveGame.getId());
        TestUtils.waitt(1000);
        assertAndProcessScreen(ChallengeGameScreen.class);
    }

    @Test
    public void showLevelFinishedChallengeScreen() {
        LiveGame liveGame = createLiveGame();
        TestUtils.waitt();
        GameContext gameContext = createGameContext(new GameConfig(new QuestionConfig(TestDataCreator.QUESTION_0.getQuestionCategory()), GameTypeStage.challenge_2, "1"));
        TestDataCreator.resolveWord(gameContext.getCurrentUserGameUser());
        TestDataCreator.resolveWordMoreTime(gameContext.getOpponentGameUser());
        new LiveGameService().updateUser1GameInfo(liveGame.getId(), new Gson().toJson(gameContext.getCurrentUserGameUser().getGameQuestionInfo()), 0);
        new LiveGameService().updateUser2GameInfo(liveGame.getId(), new Gson().toJson(gameContext.getOpponentGameUser()), 0);
        TestUtils.waitt();
        screenManager.showLevelFinishedChallengeScreen(gameContext, liveGame.getId());
        TestUtils.waitt(1000);
        assertAndProcessScreen(LevelFinishedChallengeCurrentUserWinScreen.class);
    }

    @Test
    public void showChallengeOnlineOptionsScreen() {
        screenManager.showChallengeOnlineOptionsScreen(TestDataCreator.createCurrentUser());
        assertAndProcessScreen(ChallengeOptionsScreen.class);
    }

    private void assertAndProcessScreen(Class<?> expectedScreenType) {
        TestUtils.waitt(500);
        assertNotNull(TournamentGame.getInstance().getScreen());
        assertEquals("Screen type was: " + TournamentGame.getInstance().getScreen().getClass().toString(),
                expectedScreenType, TournamentGame.getInstance().getScreen().getClass());
        ((AbstractScreen) TournamentGame.getInstance().getScreen()).buildStage();
        TournamentGame.getInstance().setScreen(null);
    }

    private LiveGame createLiveGame() {
        LiveGameService liveGameService = new LiveGameService();
        usersDbApiService.createUser("111", "hhh aaa", AccountCreationSource.GOOGLE);
        usersDbApiService.createUser("528701832", "hhh aaa", AccountCreationSource.FACEBOOK);
        TestUtils.waitt(500);
        BaseUserInfo user1 = getUser1();
        BaseUserInfo user2 = getUser2();
        liveGameService.createLiveGame(user1.getId(), user2.getId(), getGameConfig());
        LiveGame liveGame = liveGameDbApiService.getLiveGame(user1.getId(), user2.getId());
        TestUtils.waitt(500);
        return liveGame;
    }

    private void initialScreen() {
        //because word is resolved and needs a game screen
        screenManager.showMainScreen();
        TestUtils.waitt(500);
    }

    private BaseUserInfo getUser2() {
        return usersDbApiService.getUser("528701832", AccountCreationSource.FACEBOOK);
    }

    private BaseUserInfo getUser1() {
        return usersDbApiService.getUser("111", AccountCreationSource.GOOGLE);
    }

    public GameContext createGameContext(GameConfig gameConfig) {
        return new GameContextService().createGameContext(getUser1(), new ArrayList<>(Arrays.asList(getUser2())), new GameConfig(new QuestionConfig(TestDataCreator.QUESTION_0.getQuestionCategory()), GameTypeStage.tournament_1, "1"));
    }


}
