package test;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import libgdx.constants.user.AccountCreationSource;
import libgdx.controls.popup.RatingService;
import libgdx.game.Game;
import libgdx.game.external.LoginService;
import libgdx.preferences.PreferencesService;
import libgdx.preferences.SettingsService;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.controls.button.builders.AchievementItemBuilder;
import libgdx.ui.game.TournamentAppInfoService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.games.geoquiz.GeoQuizDependencyManager;
import libgdx.ui.implementations.games.hangmanarena.HangmanArenaDependencyManager;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.levelfinished.service.LevelFinishedScreenService;
import libgdx.ui.screens.levelfinished.service.challenge.ChallengeLevelFinishedScreenService;
import libgdx.ui.screens.livegame.PlayerVersusPlayerScreen;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;
import libgdx.ui.screens.mainmenu.popup.rewards.WatchVideoAdRewardConfig;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.services.dbapi.CampaignDbApiService;
import libgdx.ui.services.dbapi.ConstantsDbApiService;
import libgdx.dbapi.DbApiService;
import libgdx.ui.services.dbapi.GameStatsDbApiService;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.dbapi.UserGamesDbApiService;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.services.game.UserExtraInfoService;
import libgdx.ui.services.game.achievements.AchievementsOtherNotificationService;
import libgdx.ui.services.game.campaign.CampaignService;
import libgdx.ui.services.game.constants.ConstantsService;
import libgdx.ui.services.game.diamond.DiamondService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.game.userexperience.UserExperienceService;
import libgdx.ui.services.listeners.livegame.aftergame.RematchGameListener;
import libgdx.ui.services.listeners.livegame.beforegame.AcceptLiveGameListener;
import libgdx.ui.services.listeners.livegame.beforegame.StartGameListener;
import libgdx.ui.services.listeners.livegame.ingame.GameFinishedListener;
import libgdx.ui.services.listeners.livegame.ingame.GameInfoChangeListener;
import libgdx.ui.services.listeners.user.UserExtraInfoListener;
import libgdx.login.GuestUserService;
import libgdx.ui.util.TestDataCreator;
import libgdx.utils.DateUtils;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.startgame.test.DefaultBillingService;
import libgdx.utils.startgame.test.DefaultFacebookService;
import test.services.TestUtils;
import test.services.dbapi.service.TestCampaignDbApiService;
import test.services.dbapi.service.TestConstantsDbApiService;
import test.services.dbapi.service.TestGameStatsDbApiService;
import test.services.dbapi.service.TestLiveGameDbApiService;
import test.services.dbapi.service.TestShopTransactionsDbApiService;
import test.services.dbapi.service.TestUserGamesDbApiService;
import test.services.dbapi.service.TestUserInventoryDbApiService;
import test.services.dbapi.service.TestUsersDbApiService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UsersDbApiService.class, UserExperienceService.class, SpriteBatch.class, RatingService.class, RewardsService.class, GuestUserService.class, ScreenDimensionsManager.class,
        LiveGameService.class, GameInfoChangeListener.class, UserExtraInfoListener.class, TestDataCreator.class, AcceptLiveGameListener.class,
        StartGameListener.class, TournamentGame.class, PlayerVersusPlayerScreen.class, GameFinishedListener.class, LevelFinishedScreenService.class, StartScreenWithCoinsService.class,
        RematchGameListener.class, DiamondService.class, GameTransactionsService.class, AchievementsOtherNotificationService.class,
        ChallengeLevelFinishedScreenService.class, ShopTransactionsDbApiService.class, UserExtraInfoService.class, ConstantsService.class, CampaignService.class, AchievementItemBuilder.class,
        HangmanArenaDependencyManager.class, GeoQuizDependencyManager.class, SettingsService.class, AbstractScreen.class
})
public class TournamentGameTestDbApiService extends DbApiService implements ApplicationListener {

    protected ScreenManager screenManager;

    protected TestShopTransactionsDbApiService shopTransactionsDbApiService;
    protected TestGameStatsDbApiService gameStatsDbApiService;
    protected TestUsersDbApiService usersDbApiService;
    protected TestUserGamesDbApiService userGamesDbApiService;
    protected TestLiveGameDbApiService liveGameDbApiService;
    protected TestUserInventoryDbApiService userInventoryDbApiService;
    protected TestConstantsDbApiService constantsDbApiService;
    protected TestCampaignDbApiService campaignDbApiService;

    protected PreferencesService preferencesService;
    protected Preferences preferences;

    protected BaseUserInfo currentUser;
    protected BaseUserInfo opponent;
    protected BaseUserInfo randomUser;
    protected int currentUserId;
    protected int opponentId;

    @Before
    public void setup() throws Exception {
        createMocks();
        TournamentGame.getInstance().getAssetManager().finishLoading();
        usersDbApiService.createUser("528701832", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("333111", "hhh aaa", AccountCreationSource.GOOGLE);
        usersDbApiService.createUser("333222", "hhh aaa", AccountCreationSource.FACEBOOK);
        currentUser = usersDbApiService.getUser("528701832", AccountCreationSource.FACEBOOK);
        currentUserId = this.currentUser.getId();
        opponent = usersDbApiService.getUser("333111", AccountCreationSource.GOOGLE);
        opponentId = this.opponent.getId();
        randomUser = usersDbApiService.getUser("333222", AccountCreationSource.FACEBOOK);
        screenManager = TournamentGame.getInstance().getScreenManager();
    }

    private void createMocks() throws Exception {
        shopTransactionsDbApiService = new TestShopTransactionsDbApiService();
        gameStatsDbApiService = new TestGameStatsDbApiService();
        userGamesDbApiService = new TestUserGamesDbApiService();
        liveGameDbApiService = new TestLiveGameDbApiService();
        usersDbApiService = new TestUsersDbApiService();
        userInventoryDbApiService = new TestUserInventoryDbApiService();
        constantsDbApiService = new TestConstantsDbApiService();
        campaignDbApiService = new TestCampaignDbApiService();

        PowerMockito.whenNew(UsersDbApiService.class).withAnyArguments().thenReturn(usersDbApiService);
        PowerMockito.whenNew(ShopTransactionsDbApiService.class).withAnyArguments().thenReturn(shopTransactionsDbApiService);
        PowerMockito.whenNew(GameStatsDbApiService.class).withAnyArguments().thenReturn(gameStatsDbApiService);
        PowerMockito.whenNew(UserGamesDbApiService.class).withAnyArguments().thenReturn(userGamesDbApiService);
        PowerMockito.whenNew(LiveGameDbApiService.class).withAnyArguments().thenReturn(liveGameDbApiService);
        PowerMockito.whenNew(UserInventoryDbApiService.class).withAnyArguments().thenReturn(userInventoryDbApiService);
        PowerMockito.whenNew(ConstantsDbApiService.class).withAnyArguments().thenReturn(constantsDbApiService);
        PowerMockito.whenNew(CampaignDbApiService.class).withAnyArguments().thenReturn(campaignDbApiService);

        HeadlessApplicationConfiguration conf = new HeadlessApplicationConfiguration();
        new HeadlessApplication(this, conf);
        Gdx.gl = PowerMockito.mock(GL20.class);
        Gdx.gl20 = PowerMockito.mock(GL20.class);

        ShaderProgram shaderProgram = Mockito.mock(ShaderProgram.class);
        PowerMockito.when(shaderProgram.isCompiled()).thenReturn(true);
        PowerMockito.mockStatic(SpriteBatch.class);
        PowerMockito.when(SpriteBatch.createDefaultShader()).thenReturn(shaderProgram);

        Gdx.graphics = PowerMockito.mock(Graphics.class);
        PowerMockito.when(Gdx.graphics.getHeight()).thenReturn(853);
        PowerMockito.when(Gdx.graphics.getWidth()).thenReturn(480);

        PowerMockito.whenNew(TestDataCreator.class).withAnyArguments().thenReturn(new TestDataCreator() {
            @Override
            public String getUserId() {
                return "528701832";
            }

            @Override
            public UsersDbApiService getUsersDbApiService() {
                return usersDbApiService;
            }
        });

        preferences = Mockito.mock(Preferences.class);
        preferencesService = Mockito.mock(PreferencesService.class);
        PowerMockito.when(preferencesService.getPreferences()).thenReturn(preferences);
        PowerMockito.whenNew(PreferencesService.class).withAnyArguments().thenReturn(preferencesService);
        Mockito.when(preferencesService.getPreferences().getInteger("TransactionAmountEnum_VERSION")).thenReturn(0);
        Mockito.when(preferencesService.getPreferences().getString("TransactionAmountEnum_VALUE")).thenReturn("");

        TournamentGame game = new TournamentGame(new DefaultFacebookService(), new DefaultBillingService(), getAppInfoService()){
            @Override
            protected LoginService createLoginService() {
                return new LoginServiceImpl();
            }
        };
        game.create();

        TournamentGame.getInstance().setScreen(null);
    }

    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl();
    }

    protected LoginServiceImpl getLoginService() {
        return new LoginServiceImpl();
    }

//    protected void mockDateTimeNow() {
//        Date dateTime = new Date();
//        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
//        String str = fmt.print(dt);
//        DateTime date = DateTime.parse(str, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
//
//        PowerMockito.mockStatic(DateTime.class);
//        PowerMockito.when(DateTime.now()).thenReturn(date);
//    }

    @After
    public void after() {
        clearAllTables();
    }

    public static void assertDateTimeNow(String date) {
        assertDateTimeNow(DateUtils.getDate(date).getTime());
    }

    public static void assertDateTimeNow(Long date) {
        Date dateTime = new Date(date);
        Calendar instance1 = Calendar.getInstance();
        instance1.setTimeInMillis(new Date().getTime());
        Calendar instance2 = Calendar.getInstance();
        instance2.setTimeInMillis(dateTime.getTime());
        assertEquals(instance1.get(Calendar.YEAR), instance2.get(Calendar.YEAR));
        assertEquals(instance1.get(Calendar.MONTH), instance2.get(Calendar.MONTH));
        assertEquals(instance1.get(Calendar.DAY_OF_MONTH), instance2.get(Calendar.DAY_OF_MONTH));
        assertEquals(instance1.get(Calendar.HOUR_OF_DAY), instance2.get(Calendar.HOUR_OF_DAY));
        // (until test runs it takes maximum 30 seconds - BUFFER) expected is greater
        // than db Value, but lesser than db Value - 30 seconds
        assertTrue((new Date().after(dateTime) || new Date().compareTo(dateTime) == 0) && DateUtils.minusSeconds(30).before(dateTime));
    }

    public void clearAllTables() {
        TestUtils.waitt();
        submitInfoPost("testApi", formParametersString(new HashMap<>()));
        TestUtils.waitt();
    }

    public GameContext createGameContext() {
        return createGameContext(getGameConfig());
    }

    public GameContext createGameContext(GameConfig gameConfig) {
        return new GameContextService().createGameContext(currentUser, new ArrayList<>(), gameConfig);
    }

    public GameContext createGameContext(Question question) {
        return new GameContextService().createGameContext(currentUser, new ArrayList<>(), getGameConfig(question), question);
    }

    public GameConfig getGameConfig() {
        return new GameConfig(new QuestionConfig(TestDataCreator.QUESTION_0.getQuestionDifficultyLevel(), TestDataCreator.QUESTION_0.getQuestionCategory()), GameTypeStage.tournament_1, "1");
    }

    public GameConfig getGameConfig(Question question) {
        return new GameConfig(new QuestionConfig(question.getQuestionDifficultyLevel(), question.getQuestionCategory()), GameTypeStage.tournament_1, "1");
    }

    @Override
    public String getApiModuleName() {
        return "test";
    }

    @Override
    public String formParametersString(Map<String, Object> params) {
        return TestUtils.formTestParametersString(super.formParametersString(params));
    }

    @Override
    public String getBaseUrl() {
        return TestUtils.getBaseUrl();
    }

    @Override
    public void create() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

    public class LoginServiceImpl implements LoginService {
        private boolean isUserLoggedIn = true;

        @Override
        public boolean isUserLoggedIn() {
            return isUserLoggedIn && TournamentGame.getInstance().hasInternet();
        }

        @Override
        public void goToMainMenu() {
            TournamentGame.getInstance().getScreenManager().showMainScreen();
        }

        @Override
        public void loginClick(AccountCreationSource accountCreationSource, final Runnable redirectAfterLogin) {
            isUserLoggedIn = !isUserLoggedIn();
            redirectAfterLogin.run();
        }

        @Override
        public String getExternalId() {
            if (isUserLoggedIn()) {
                return TestDataCreator.createCurrentUser().getExternalId();
            } else {
                return TestDataCreator.createAnonCurrentUser().getExternalId();
            }
        }

        @Override
        public AccountCreationSource getAccountCreationSource() {
            return AccountCreationSource.FACEBOOK;
        }

        @Override
        public String getFullName() {
            if (isUserLoggedIn()) {
                return TestDataCreator.createCurrentUser().getFullName();
            } else {
                return TestDataCreator.createAnonCurrentUser().getFullName();
            }
        }
    }


    public class AppInfoServiceImpl implements TournamentAppInfoService {
        @Override
        public String getGameIdPrefix() {
            return "hangman";
        }

        @Override
        public void showPopupAd() {
        }

        @Override
        public float gameScreenTopMargin() {
            return 0;
        }

        @Override
        public String getStoreAppId() {
            return  "com.bogdanICE.Raspunde";
        }

        @Override
        public boolean screenShotMode() {
            return false;
        }

        @Override
        public String proVersionStoreAppId() {
            return null;
        }

        @Override
        public boolean isProVersion() {
            return false;
        }

        @Override
        public boolean googleFacebookLoginEnabled() {
            return false;
        }

        @Override
        public void showRewardedVideoAd() {
            new RewardsService(new WatchVideoAdRewardConfig()).giveReward(TournamentGame.getInstance().getCurrentUser().getId(), "x");
            TournamentGame.getInstance().getScreenManager().showMainScreen();
        }

        @Override
        public String getAppName() {
            switch (getLanguage()) {
                case "ro":
                    return "Spânzurătoarea Arena";
                case "en":
                    return "Hangman Arena";
                case "de":
                    return "Galgenmännchen Arena";
                case "fr":
                    return "Pendu  Arena";
                case "it":
                    return "L'impiccato Arena";
            }
            return "Hangman Arena";
        }

        @Override
        public String getLanguage() {
            return TournamentGameTestDbApiService.this.getLanguage();
        }

        @Override
        public String getMainResourcesFolder() {
            return "main_resources/main/";
        }

        @Override
        public String getResourcesFolder() {
            return "tournament_resources/main/";
        }


        @Override
        public String getImplementationGameResourcesFolder() {
            String gameId = Game.getInstance().getGameIdPrefix();
            String[] gameIdParts = gameId.split("_");
            String path = "tournament_resources/implementations/" + gameIdParts[0] + "/";
            if (gameIdParts.length > 1) {
                path = path + gameIdParts[1] + "/";
            }
            return path;
        }

    }

    protected String getLanguage() {
        return "ro";
    }
}