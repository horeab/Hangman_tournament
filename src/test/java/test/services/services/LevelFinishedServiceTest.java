package test.services.services;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;
import libgdx.ui.implementations.config.dependecies.MultiPlayerLevelFinishedService;
import libgdx.ui.implementations.config.dependecies.SinglePlayerLevelFinishedService;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;
import test.services.LiveGameTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LevelFinishedServiceTest extends LiveGameTest {

    public static GameConfig GAME_CONFIG;
    public static Question QUESTION;

    @Before
    public void setup() throws Exception {
        super.setup();
        GAME_CONFIG = new GameConfig(new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5, 1), GameTypeStage.challenge_3, DateUtils.getDateString(DateUtils.minusDays(100)));
        RandomCategoryAndDifficulty randomCategoryAndDifficulty = GAME_CONFIG.getQuestionConfig().getRandomCategoryAndDifficulty();
        QUESTION = new Question( 2, randomCategoryAndDifficulty.getQuestionDifficulty(), randomCategoryAndDifficulty.getQuestionCategory(), "Gnocchi");

    }


    @Test
    public void isGameFinished_MultiPlayer() {
        GameContext gameContextMultiPlayer = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser1 = gameContextMultiPlayer.getCurrentUserGameUser();
        GameUser gameUser2 = gameContextMultiPlayer.getOpponentGameUser();

        MultiPlayerLevelFinishedService multiPlayerLevelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        resolveWord(gameUser1);

        resolveWord(gameUser2);
        wrongResolved(gameUser2);
        assertFalse(multiPlayerLevelFinishedService.isGameFinished(gameContextMultiPlayer));

        assertFalse(multiPlayerLevelFinishedService.isGameFinished(gameContextMultiPlayer));
        resolveWord(gameUser1);
        wrongResolved(gameUser2);
        assertTrue(multiPlayerLevelFinishedService.isGameFinished(gameContextMultiPlayer));
    }

    @Test
    public void isQuestionFinished_isGameFinished_SinglePlayer() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser = gameContext.getCurrentUserGameUser();

        SinglePlayerLevelFinishedService singlePlayerLevelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService();

        wrongResolved(gameUser);

        wrongResolved(gameUser);
        assertFalse(singlePlayerLevelFinishedService.isGameFinished(gameContext));

        resolveWord(gameUser);
        assertTrue(singlePlayerLevelFinishedService.isGameFinished(gameContext));
    }

    @Test
    public void isGameFinished_oneQuestion_twoQuestions() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        //two question
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        resolveWord(gameUser1);
        resolveWord(gameUser2);
        assertFalse(levelFinishedService.isGameFinished(gameContext));
        resolveWord(gameUser1);
        TestDataCreator.onlyOneLetterPressed(gameUser2);
        assertTrue(levelFinishedService.isGameFinished(gameContext));
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());

        //one question
        gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, TestDataCreator.QUESTION_0);
        gameUser1 = gameContext.getCurrentUserGameUser();
        gameUser2 = gameContext.getOpponentGameUser();
        resolveWord(gameUser1);
        TestDataCreator.onlyOneLetterPressed(gameUser2);
        assertTrue(levelFinishedService.isGameFinished(gameContext));
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());

    }

    @Test
    public void isGameFinished() {
        GameContext gameContextMultiPlayer = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2, TestDataCreator.QUESTION_3, TestDataCreator.QUESTION_4, TestDataCreator.QUESTION_5, TestDataCreator.QUESTION_6);
        GameUser gameUser1 = gameContextMultiPlayer.getCurrentUserGameUser();
        GameUser gameUser2 = gameContextMultiPlayer.getOpponentGameUser();
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();

        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));

        //user1 already correctly more than remaining questions user2
        resolveWord(gameUser1);
        resolveWord(gameUser1);
        resolveWord(gameUser1);
        resolveWord(gameUser1);
        resolveWord(gameUser1);
        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        wrongResolved(gameUser2);
        wrongResolved(gameUser2);
        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        wrongResolved(gameUser2);
        assertTrue(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContextMultiPlayer);
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());

        //user2 already correctly more than remaining questions user2
        gameContextMultiPlayer = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2, TestDataCreator.QUESTION_3, TestDataCreator.QUESTION_4, TestDataCreator.QUESTION_5, TestDataCreator.QUESTION_6);
        gameUser1 = gameContextMultiPlayer.getCurrentUserGameUser();
        gameUser2 = gameContextMultiPlayer.getOpponentGameUser();
        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        resolveWord(gameUser2);
        resolveWord(gameUser2);
        resolveWord(gameUser2);
        resolveWord(gameUser2);
        wrongResolved(gameUser2);
        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        wrongResolved(gameUser1);
        wrongResolved(gameUser1);
        wrongResolved(gameUser1);
        assertFalse(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        wrongResolved(gameUser1);
        assertTrue(levelFinishedService.isGameFinished(gameContextMultiPlayer));
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContextMultiPlayer);
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void totalWonQuestions() {
        GameContext gameContextMultiPlayer = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser1 = gameContextMultiPlayer.getCurrentUserGameUser();
        GameUser gameUser2 = gameContextMultiPlayer.getOpponentGameUser();
        resolveWordMoreTime(gameUser1);
        resolveWord(gameUser2);
        assertEquals(1, gameUser1.getWonQuestions());
        assertEquals(1, gameUser2.getWonQuestions());

        TestDataCreator.onlyOneLetterPressed(gameUser1);
        wrongResolved(gameUser2);
        assertEquals(1, gameUser1.getWonQuestions());
        assertEquals(1, gameUser2.getWonQuestions());

        wrongResolved(gameUser1);
        resolveWord(gameUser2);
        assertEquals(1, gameUser1.getWonQuestions());
        assertEquals(2, gameUser2.getWonQuestions());
    }

    @Test
    public void createUsersWithGameFinished_user2IsNull_user1Lose() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService();
        GameUser gameUser = gameContext.getCurrentUserGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertEquals(this.currentUser, usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void createUsersWithGameFinished_user2IsNull_user1Win() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser = gameContext.getCurrentUserGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertEquals(this.currentUser, usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void createUsersWithGameFinished_user1Win() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWord(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser1);
        wrongResolvedMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void createUsersWithGameFinished_user2Win() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        wrongResolved(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser1);
        resolveWord(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        wrongResolved(gameUser1);
        resolveWord(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertEquals(this.currentUser, usersWithLevelFinished.getUserThatLost());
        assertEquals(this.randomUser, usersWithLevelFinished.getUserThatWon());
    }


    @Test
    public void createUsersWithGameFinished_user2Win_withMoreTime() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2, TestDataCreator.QUESTION_3, TestDataCreator.QUESTION_4);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

    }

    @Test
    public void createUsersWithGameFinished_user1Win_withMoreTime() {
        LevelFinishedService levelFinishedService = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2, TestDataCreator.QUESTION_3);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        UsersWithLevelFinished usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());

        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        usersWithLevelFinished = levelFinishedService.createUsersWithGameFinished(gameContext);
        assertNull(usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());
    }


    @Test
    public void createUsersWithGameFinished_oneQuestion_twoQuestions() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        wrongResolved(gameUser1);
        resolveWord(gameUser2);
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService().createUsersWithGameFinished(gameContext);
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());

        gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG,
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1);
        gameUser1 = gameContext.getCurrentUserGameUser();
        gameUser2 = gameContext.getOpponentGameUser();
        wrongResolved(gameUser1);
        resolveWord(gameUser2);
        resolveWordMoreTime(gameUser1);
        wrongResolved(gameUser2);
        usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService().createUsersWithGameFinished(gameContext);
        assertEquals(gameUser2.getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameUser1.getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void createUsersWithGameFinished_user2OnlyOneLetterPressed_user1CorrectResult() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        resolveWordMoreTime(gameUser1);
        TestDataCreator.onlyOneLetterPressed(gameUser2);
        prepareTest(gameContext, this.currentUser, randomUser);
    }

    @Test
    public void createUsersWithGameFinished_user2OnlyOneLetterPressed_user1WrongResult() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        wrongResolved(gameUser1);
        TestDataCreator.onlyOneLetterPressed(gameUser2);
        prepareTest(gameContext, randomUser, this.currentUser);
    }

    @Test
    public void createUsersWithGameFinished_user1OnlyOneLetterPressed_user2CorrectResult() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        TestDataCreator.onlyOneLetterPressed(gameUser1);
        resolveWordMoreTime(gameUser2);
        prepareTest(gameContext, randomUser, this.currentUser);
    }

    @Test
    public void createUsersWithGameFinished_user1OnlyOneLetterPressed_user2WrongResult() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        TestDataCreator.onlyOneLetterPressed(gameUser1);
        wrongResolved(gameUser2);
        prepareTest(gameContext, this.currentUser, randomUser);
    }

    @Test
    public void createUsersWithGameFinished_bothUserResolveQuestion_user1HasBetterTime() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        resolveWord(gameUser1);
        resolveWordMoreTime(gameUser2);
        prepareTest(gameContext, this.currentUser, randomUser);
    }

    @Test
    public void createUsersWithGameFinished_bothUserResolveQuestion_user2HasBetterTime() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        resolveWordMoreTime(gameUser1);
        resolveWord(gameUser2);
        prepareTest(gameContext, randomUser, this.currentUser);
    }

    @Test
    public void createUsersWithGameFinished_bothUserWrongResolved_user1FirstFailTimeOneQuestion() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        wrongResolved(gameUser1);
        wrongResolvedMoreTime(gameUser2);
        prepareTest(gameContext, randomUser, this.currentUser);
    }

    @Test
    public void createUsersWithGameFinished_bothUserWrongResolved_user2MoreSlowFailTimeTwoQuestions() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION, TestDataCreator.QUESTION_1);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        wrongResolved(gameUser1);
        wrongResolvedMoreTime(gameUser2);
        wrongResolved(gameUser1);
        wrongResolvedMoreTime(gameUser2);
        prepareTest(gameContext, currentUser, this.randomUser);
    }

    @Test
    public void createUsersWithGameFinished_bothUserWrongResolved_user2FirstFailTime() {
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), GAME_CONFIG, QUESTION);
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        wrongResolvedMoreTime(gameUser1);
        wrongResolved(gameUser2);
        prepareTest(gameContext, this.currentUser, randomUser);
    }

    private void prepareTest(GameContext gameContext, BaseUserInfo userThatWon, BaseUserInfo userThatLost) {
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService().createUsersWithGameFinished(gameContext);
        assertEquals(userThatWon, usersWithLevelFinished.getUserThatWon());
        assertEquals(userThatLost, usersWithLevelFinished.getUserThatLost());
    }

    public static void resolveWord(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("g", 1L);
        gameUser.addAnswerToGameQuestionInfo("n", 2L);
        gameUser.addAnswerToGameQuestionInfo("o", 3L);
        gameUser.addAnswerToGameQuestionInfo("c", 4L);
        gameUser.addAnswerToGameQuestionInfo("i", 6L);
        gameUser.addAnswerToGameQuestionInfo("h", 5L);
    }

    public static void resolveWordMoreTime(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("g", 1L);
        gameUser.addAnswerToGameQuestionInfo("n", 2L);
        gameUser.addAnswerToGameQuestionInfo("o", 3L);
        gameUser.addAnswerToGameQuestionInfo("c", 4L);
        gameUser.addAnswerToGameQuestionInfo("i", 6L);
        gameUser.addAnswerToGameQuestionInfo("h", 15L);
    }

    public static void wrongResolved(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("x", 1L);
        gameUser.addAnswerToGameQuestionInfo("z", 2L);
        gameUser.addAnswerToGameQuestionInfo("y", 3L);
        gameUser.addAnswerToGameQuestionInfo("j", 4L);
        gameUser.addAnswerToGameQuestionInfo("k", 5L);
        gameUser.addAnswerToGameQuestionInfo("w", 6L);
    }

    public static void wrongResolvedMoreTime(GameUser gameUser) {
        gameUser.addAnswerToGameQuestionInfo("x", 1L);
        gameUser.addAnswerToGameQuestionInfo("z", 2L);
        gameUser.addAnswerToGameQuestionInfo("y", 3L);
        gameUser.addAnswerToGameQuestionInfo("j", 4L);
        gameUser.addAnswerToGameQuestionInfo("k", 5L);
        gameUser.addAnswerToGameQuestionInfo("w", 16L);
    }

}
