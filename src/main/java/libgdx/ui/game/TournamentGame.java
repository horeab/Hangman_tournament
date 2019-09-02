package libgdx.ui.game;

import com.google.gson.Gson;

import java.util.List;

import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.constants.user.AccountCreationSource;
import libgdx.game.external.BillingService;
import libgdx.game.external.FacebookService;
import libgdx.game.external.LoginService;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.ScreenManager;
import libgdx.dbapi.UniqueDbOperationContainer;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.game.constants.ConstantsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.login.GuestUserLoginService;
import libgdx.ui.util.DataCreator;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;

public class TournamentGame extends libgdx.game.Game<
        TournamentAppInfoService,
        TournamentMainDependencyManager,
        TournamentGameDependencyManager,
        AbstractScreen,
        ScreenManager,
        GameIdEnum> {

    private static TournamentGame instance;

    private UsersDbApiService usersDbApiService;

    public TournamentGame(FacebookService facebookService,
                          BillingService billingService,
                          TournamentAppInfoService appInfoService) {
        super(facebookService, billingService, appInfoService, new TournamentMainDependencyManager());
        instance = this;
        this.usersDbApiService = new UsersDbApiService();
    }

    public static TournamentGame getInstance() {
        return instance;
    }

    @Override
    public void create() {
        super.create();
        loginService = createLoginService();
        UniqueDbOperationContainer.getInstance().clear();
        if (hasInternet()) {
            new ConstantsService().loadConstantsInMemory();
        }

    }

    protected LoginService createLoginService() {
        return new GuestUserLoginService();
    }

    private void initLogin() {
        if (!getLoginService().isUserLoggedIn() && !getAppInfoService().googleFacebookLoginEnabled()) {
            getLoginService().loginClick(AccountCreationSource.INTERNAL, new Runnable() {
                @Override
                public void run() {
                    getScreenManager().showMainScreen();
                }
            });
        }
    }

    public BaseUserInfo getCurrentUser() {
        String externalId = getLoginService().getExternalId();
        AccountCreationSource accountCreationSource = getLoginService().getAccountCreationSource();
        return new BaseUserInfo(usersDbApiService.getUserId(externalId, accountCreationSource), externalId, accountCreationSource, getLoginService().getFullName());
    }

    @Override
    public void executeAfterAssetsLoaded() {
        super.executeAfterAssetsLoaded();
        DataCreator.createUsers();
        initLogin();
    }

    @Override
    protected void displayScreenAfterAssetsLoad() {
        ScreenManager screenManager = getScreenManager();
        getLoginService().goToMainMenu();
        ///////////////////////////////////////////////////
//        screenManager.showTournamentOnlineOptionsScreen();
//        screenManager.startNewTournamentOnlineScreen( new UsersDbApiService().selectUsersForTournament(), new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString()));
//        screenManager.showPracticeOnlineOptionsScreen();
//        screenManager.showChallengeOnlineOptionsScreen(TestDataCreator.createGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString())).getCurrentUserGameUser().getBaseUserInfo());
        ///////////////////////////////////////////////////
//        screenManager.startGameChallengeScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.practice_1, DateUtils.getNowDateString()), new ArrayList<>()), createLiveGame(TestDataCreator.QUESTION_UNIQUE_1, Collections.singletonList(TestDataCreator.getUserList().get(0))));
//        screenManager.startGamePracticeScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.practice_1, DateUtils.getNowDateString())));
//        screenManager.startGameTournamentScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString())),
//                new TournamentContext(TournamentStage.STAGE_0, new ArrayList<TournamentUser>()));
//        screenManager.startGameCampaignScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.campaign_level_0_0, DateUtils.getNowDateString())), GeoQuizCampaignLevelEnum.LEVEL_0_0);
//        ///////////////////////////////////////////////////
//        screenManager.showLevelFinishedChallengeScreen(TestDataCreator.createGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.practice_1, DateUtils.getNowDateString())), createLiveGame(TestDataCreator.QUESTION_UNIQUE_1, Collections.singletonList(TestDataCreator.getUserList().get(0))));
//        screenManager.showLevelFinishedPracticeScreen(TestDataCreator.createGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.practice_1, DateUtils.getNowDateString())));
//        screenManager.showLevelFinishedCampaignScreen(TestDataCreator.createGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.campaign_level_0_0, DateUtils.getNowDateString())), GeoQuizCampaignLevelEnum.LEVEL_0_0);
//        screenManager.showLevelFinishedTournamentScreen( new ArrayList<TournamentUser>(), TournamentStage.STAGE_0, TestDataCreator.createGameContext(), new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString()));
//        screenManager.showLevelFinishedTournamentWinScreen(new MultiPlayerLevelFinishedService().createUsersWithGameFinished(TestDataCreator.createGameContext()), new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString()));
//        screenManager.showLeaderboardScreen();
//        screenManager.showAchievementsScreen();
//        screenManager.showCampaignScreen();
        ///////////////////////////////////////////////////////
//        screenManager.showSearchUserScreen();
//        screenManager.showWaitingForUserScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.challenge_1, DateUtils.getNowDateString()), Collections.singletonList(TestDataCreator.getUserList().get(0))), createLiveGame(TestDataCreator.QUESTION_UNIQUE_1, Collections.singletonList(TestDataCreator.getUserList().get(0))));
//        screenManager.showChallengePlayerVersusPlayerScreen(TestDataCreator.createEmptyGameContext(new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.challenge_1, DateUtils.getNowDateString()), Collections.singletonList(TestDataCreator.getUserList().get(0))), createLiveGame(TestDataCreator.QUESTION_UNIQUE_1, Collections.singletonList(TestDataCreator.getUserList().get(0))));
    }

    private int createLiveGame(Question question, List<BaseUserInfo> opponents) {
        GameConfig gameConfig = new GameConfig(TestDataCreator.getQuestionConfig(), GameTypeStage.challenge_1, DateUtils.getNowDateString());
        GameContext gameContext = TestDataCreator.createGameContext(gameConfig, opponents, question);
        LiveGameService liveGameService = new LiveGameService();
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        BaseUserInfo currentUser = currentUserGameUser.getBaseUserInfo();
        GameUser opponentGameUser = gameContext.getOpponentGameUser();
        BaseUserInfo opponent = new UsersDbApiService().getUser(opponentGameUser.getBaseUserInfo().getExternalId(), opponentGameUser.getBaseUserInfo().getAccountCreationSource());
        int liveGameId = liveGameService.createLiveGame(currentUser.getId(), opponent.getId(), gameConfig).getId();
//        TestDataCreator.resolveWord(currentUserGameUser);
        TestDataCreator.onlyOneLetterPressed(opponentGameUser);
        liveGameService.updateUser1GameInfo(liveGameId, new Gson().toJson(currentUserGameUser.getAllQuestionInfos().get(0)), 0);
        liveGameService.updateUser2GameInfo(liveGameId, new Gson().toJson(opponentGameUser.getAllQuestionInfos().get(0)), 0);
        return liveGameId;
    }

}
