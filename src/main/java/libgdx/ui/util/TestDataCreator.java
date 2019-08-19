package libgdx.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import libgdx.ui.game.TournamentAppInfoService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameIdEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.game.external.LoginService;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyQuestionCategoryEnum;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyQuestionDifficultyLevel;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyQuestionCategoryEnum;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyQuestionDifficultyLevel;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionCategoryEnum;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionDifficultyLevel;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsQuestionCategoryEnum;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionCategoryEnum;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionDifficultyLevel;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoQuestionCategoryEnum;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoQuestionDifficultyLevel;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionCategoryEnum;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionDifficultyLevel;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsQuestionCategoryEnum;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionCategoryEnum;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionDifficultyLevel;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionCategoryEnum;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionDifficultyLevel;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsQuestionCategoryEnum;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionCategoryEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionDifficultyLevel;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;
import libgdx.ui.screens.mainmenu.popup.rewards.WatchVideoAdRewardConfig;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.startgame.test.DefaultAppInfoService;

public class TestDataCreator {

    public String getUserId() {
        return "1508812787";
//        return "528701832uuu";
    }

    public static BaseUserInfo createCurrentUser() {
        TestDataCreator testDataCreator = new TestDataCreator();
        UsersDbApiService usersDbApiService = testDataCreator.getUsersDbApiService();
        String externalId = testDataCreator.getUserId();
//        if (usersDbApiService.getLastTimeActiveDateForUser(usersDbApiService.getUserId("528701832", AccountCreationSource.FACEBOOK)).plusSeconds(10).isAfter(DateTime.now())) {
//            externalId = "222x";
//        }
        AccountCreationSource accountCreationSource = TournamentGame.getInstance().getLoginService().getAccountCreationSource();
        if (usersDbApiService.getUserId(externalId, accountCreationSource) == -1) {
//            String fullName = "Franz Berg";
//            String fullName = "John Doe";
//            String fullName = "Edouard Thibault";
            String fullName = TestDataCreatorUtil.getFullName();
            usersDbApiService.createUser(externalId, fullName, accountCreationSource);
        }
        return usersDbApiService.getUser(externalId, accountCreationSource);

    }

    public UsersDbApiService getUsersDbApiService() {
        return new UsersDbApiService();
    }

    public static TournamentAppInfoService createAppInfoService() {
        return new TournamentAppInfoService() {

            @Override
            public String getGameIdPrefix() {
//                return GameIdEnum.hangman.name();
                return GameIdEnum.geoquiz.name();
//                return GameIdEnum.centenar.name();
//                return GameIdEnum.judetelerom.name();
//                return GameIdEnum.scoalasofer.name();
//                return GameIdEnum.conthistory_eur.name();
//                return GameIdEnum.conthistory_asia.name();
//                return GameIdEnum.countryhistro.name();
//                return GameIdEnum.bundesde.name();
//                return GameIdEnum.cunro.name();
//                return GameIdEnum.kennstde.name();
//                return GameIdEnum.flags.name();
//                return GameIdEnum.capitals.name();
//                return GameIdEnum.animals.name();
//                return GameIdEnum.countrygeo.name();
//                return GameIdEnum.anatomy.name();
//                return GameIdEnum.paintings.name();
//                return GameIdEnum.astronomy.name();
            }

            @Override
            public boolean isPortraitMode() {
                return true;
            }

            @Override
            public String getLanguage() {
                return Language.hi.name();
            }

            @Override
            public boolean screenShotMode() {
                return false;
            }

            @Override
            public boolean googleFacebookLoginEnabled() {
                return false;
            }

            @Override
            public void showPopupAd() {
            }

            @Override
            public String getProVersionStoreAppId() {
                return null;
//                return getStoreAppId();
            }

            @Override
            public boolean isProVersion() {
                return false;
            }

            @Override
            public void showRewardedVideoAd() {
                new RewardsService(new WatchVideoAdRewardConfig()).giveReward(TournamentGame.getInstance().getCurrentUser().getId(), "x");
                TournamentGame.getInstance().getScreenManager().showMainScreen();
            }

            @Override
            public String getAppName() {
                return TestDataCreatorUtil.getAppNameString(getGameIdPrefix(), getLanguage());
            }

            @Override
            public String getStoreAppId() {
                return "com.bogdanICE.Raspunde";
            }

            @Override
            public String getMainResourcesFolder() {
                return new DefaultAppInfoService().getMainResourcesFolder();
            }

            @Override
            public String getResourcesFolder() {
                return new DefaultAppInfoService().getResourcesFolder();
            }

            @Override
            public float gameScreenTopMargin() {
                return 0;
            }

            @Override
            public String getImplementationGameResourcesFolder() {
                return new DefaultAppInfoService().getImplementationGameResourcesFolder();
            }

        };
    }

    public final static List<String> ALL_LANG = Arrays.asList("de", "da", "en", "fr", "hu", "it", "nl", "pl", "pt", "ro", "es", "tr");

    public final static Question QUESTION_TEST = new Question(2,
            HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5, "şampiyon");

    public static QuestionConfig getQuestionConfig() {
        switch (GameIdEnum.valueOf(TournamentGame.getInstance().getGameIdPrefix())) {
            case hangman:
                return new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat0, 12);
            case geoquiz:
                return new QuestionConfig(GeoQuizQuestionDifficultyLevel._0, GeoQuizQuestionCategoryEnum.cat3, 5);
            case judetelerom:
                return new QuestionConfig(JudeteleRomQuestionDifficultyLevel._0, JudeteleRomQuestionCategoryEnum.cat4, 5);
            case scoalasofer:
                return new QuestionConfig(ScoalaSoferQuestionDifficultyLevel._0, ScoalaSoferQuestionCategoryEnum.cat2, 5);
            case centenar:
                return new QuestionConfig(CentenarQuestionDifficultyLevel._0, CentenarQuestionCategoryEnum.cat4, 5);
            case bundesde:
                return new QuestionConfig(BundesDeQuestionDifficultyLevel._0, BundesDeQuestionCategoryEnum.cat0, 5);
            case cunro:
                return new QuestionConfig(CunRoQuestionDifficultyLevel._0, CunRoQuestionCategoryEnum.cat4, 15);
            case flags:
                return new QuestionConfig(FlagsQuestionDifficultyLevel._0, FlagsQuestionCategoryEnum.cat4, 15);
            case anatomy:
                return new QuestionConfig(AnatomyQuestionDifficultyLevel._0, AnatomyQuestionCategoryEnum.cat4, 15);
            case countrygeo:
                return new QuestionConfig(CountryGeoQuestionDifficultyLevel._0, CountryGeoQuestionCategoryEnum.cat3, 15);
            case paintings:
                return new QuestionConfig(PaintingsQuestionDifficultyLevel._0, PaintingsQuestionCategoryEnum.cat2, 12);
            case capitals:
                return new QuestionConfig(CapitalsQuestionDifficultyLevel._0, CapitalsQuestionCategoryEnum.cat2, 12);
            case astronomy:
                return new QuestionConfig(AstronomyQuestionDifficultyLevel._0, AstronomyQuestionCategoryEnum.cat0, 2);
            default:
                return null;
        }
    }

    public static BaseUserInfo createAnonCurrentUser() {
        return new BaseUserInfo(-1, "XXX", TournamentGame.getInstance().getLoginService().getAccountCreationSource(), TournamentGameLabel.tournament_currentuser_anon_name.getText());
    }

    public static GameContext createGameContext(GameConfig gameConfig, Question... questions) {
        return createGameContext(gameConfig, Collections.singletonList(getUserList().get(0)), questions);
    }

    public static GameContext createGameContext(GameConfig gameConfig, List<BaseUserInfo> opponents, Question... questions) {
        GameContext gameContext = new GameContextService().createGameContext(createCurrentUser(), opponents, gameConfig, questions);
        for (int i = 0; i < gameContext.getCurrentUserGameUser().getAllQuestionInfos().size(); i++) {
            resolveWord(gameContext.getCurrentUserGameUser());
        }
        return gameContext;
    }

    public static GameContext createEmptyGameContext(GameConfig gameConfig, Question... questions) {
        return new GameContextService().createGameContext(createCurrentUser(), new ArrayList<BaseUserInfo>(), gameConfig);
    }

    public static GameContext createEmptyGameContext(GameConfig gameConfig, List<BaseUserInfo> opponents, Question... questions) {
//        return new GameContextService().createGameContext(createCurrentUser(), Collections.singletonList(getUserList().get(0)), gameConfig, questions);
        return new GameContextService().createGameContext(createCurrentUser(), opponents, gameConfig);
    }

    public static GameContext createEmptyGameContextWithTwoUsers(GameConfig gameConfig, Question... questions) {
        return new GameContextService().createGameContext(createCurrentUser(), Collections.singletonList(getUserList().get(0)), gameConfig, questions);
    }

    public static List<BaseUserInfo> createUsers() {
        return getUserList();
    }

    public static List<BaseUserInfo> getUserList() {
        switch (TournamentGame.getInstance().getAppInfoService().getLanguage()) {
            case "ro":
                return TestDataCreatorUtil.createUsersRo();
            case "de":
                return TestDataCreatorUtil.createUsersDe();
            default:
                return TestDataCreatorUtil.createUsersEn();
        }
    }

    public static LoginService createLoginService() {
        return new LoginService() {
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
                    return createCurrentUser().getExternalId();
                } else {
                    return createAnonCurrentUser().getExternalId();
                }
            }

            @Override
            public AccountCreationSource getAccountCreationSource() {
                return AccountCreationSource.FACEBOOK;
            }

            @Override
            public String getFullName() {
                if (isUserLoggedIn()) {
                    return createCurrentUser().getFullName();
                } else {
                    return createAnonCurrentUser().getFullName();
                }
            }
        };
    }


    public static List<BaseUserInfo> createAnonUsers() {
        String anonName = TournamentGameLabel.tournament_bot_anon_name.getText();
        List<BaseUserInfo> anonUsers = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            String name = anonName + " " + Integer.valueOf(i + 1);
            anonUsers.add(new BaseUserInfo(-1, name, AccountCreationSource.FACEBOOK, name));
        }
        return anonUsers;
    }

    public final static Question QUESTION_0 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_1 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_2 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_3 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_4 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_5 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");
    public final static Question QUESTION_6 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Gnocchi");

    private static final String UNIQUE_QUESTION_STRING = "3:What planet besides Earth has seasons?:Venus##Mars##Mercury##Jupiter:1:";
    public final static Question QUESTION_UNIQUE_1 = new Question(2, AstronomyQuestionDifficultyLevel._0, AstronomyQuestionCategoryEnum.cat4, UNIQUE_QUESTION_STRING);

    public static void resolveWord(GameUser gameUser) {
        String questionString = gameUser.getGameQuestionInfo().getQuestion().getQuestionString();
        if (questionString.equals("Gnocchi")) {
            gameUser.addAnswerToGameQuestionInfo("g", 1L);
            gameUser.addAnswerToGameQuestionInfo("n", 2L);
            gameUser.addAnswerToGameQuestionInfo("o", 3L);
            gameUser.addAnswerToGameQuestionInfo("c", 4L);
            gameUser.addAnswerToGameQuestionInfo("i", 6L);
            gameUser.addAnswerToGameQuestionInfo("h", 5L);
        } else if (questionString.equals(UNIQUE_QUESTION_STRING)) {
            gameUser.addAnswerToGameQuestionInfo("0", 1L);
        }
    }

    public static void resolveWordMoreTime(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("g", 1L);
        gameUser.addAnswerToGameQuestionInfo("n", 2L);
        gameUser.addAnswerToGameQuestionInfo("o", 3L);
        gameUser.addAnswerToGameQuestionInfo("c", 4L);
        gameUser.addAnswerToGameQuestionInfo("i", 16L);
        gameUser.addAnswerToGameQuestionInfo("h", 5L);
    }

    public static void onlyOneLetterPressed(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("x", 1L);
    }

    public static void wrongResolved(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("x", 1L);
        gameUser.addAnswerToGameQuestionInfo("z", 2L);
        gameUser.addAnswerToGameQuestionInfo("y", 3L);
        gameUser.addAnswerToGameQuestionInfo("j", 4L);
        gameUser.addAnswerToGameQuestionInfo("k", 5L);
        gameUser.addAnswerToGameQuestionInfo("w", 6L);
    }
}
