package test.services.services.gameservice;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanGameService;
import libgdx.ui.util.TestDataCreator;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HangmanGameServiceTest extends GameServiceTest {

    public final static Question QUESTION_1 = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Euro obligaţi-une");

    private HangmanGameService hangmanGameService;

    private String word;
    private GameUser currentUserGameUser;

    @Before
    public void setUp() {
        screenManager.showMainScreen();
        TestUtils.waitt(1500);
        GameContext gameContext = createGameContext(QUESTION_1);
        currentUserGameUser = gameContext.getCurrentUserGameUser();
        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion());
        word = "Euro obligaţi-une";
    }

    @Ignore
    @Test
    public void testAllQuestions() throws Exception {
        testQuestions();
    }

    @Test
    public void isAnswerCorrectInQuestion() {
        //contains ţ but t should be ok
        Question question = new Question(2, HangmanArenaQuestionDifficultyLevel._5, HangmanArenaQuestionCategoryEnum.cat4, "Euro obligaţi-une");
        GameContext gameContext = createGameContext(question);
        currentUserGameUser = gameContext.getCurrentUserGameUser();
        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS;
            }
        };
        assertTrue(hangmanGameService.isAnswerCorrectInQuestion(new GameAnswerInfo("t", 1L).getAnswer()));

        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS + "ţ";
            }
        };
        assertFalse(hangmanGameService.isAnswerCorrectInQuestion(new GameAnswerInfo("t", 1L).getAnswer()));
    }

    @Test
    public void pressedLetter() {
        assertEquals(0, currentUserGameUser.getGameQuestionInfo().getAnswerIds().size());
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("e", 1L)));
        assertLetterPressed(1, "e", 1L);
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("U", 2L)));
        assertLetterPressed(2, "u", 2L);
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("r", 3L)));
        assertLetterPressed(3, "r", 3L);
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("O", 4L)));
        assertLetterPressed(4, "o", 4L);
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("X", 6L)));
        assertLetterPressed(5, "x", 6L);

    }

    private void assertLetterPressed(int expectedLettersPressed, String letterPressed, long expectedLetterPressedMillis) {
        assertEquals(expectedLettersPressed, currentUserGameUser.getGameQuestionInfo().getAnswers().size());
        assertEquals(letterPressed, getGameInfoAnswer(letterPressed, currentUserGameUser.getGameQuestionInfo().getAnswers()).getAnswer());
        assertEquals(Long.valueOf(expectedLetterPressedMillis), getGameInfoAnswer(letterPressed, currentUserGameUser.getGameQuestionInfo().getAnswers()).getMillisAnswered());
    }

    private GameAnswerInfo getGameInfoAnswer(String letterPressed, Set<GameAnswerInfo> gameAnswerInfoList) {
        for (GameAnswerInfo gameAnswerInfo : gameAnswerInfoList) {
            if (gameAnswerInfo.getAnswer().equals(letterPressed)) {
                return gameAnswerInfo;
            }
        }
        return null;
    }

    @Test
    public void isAnswerFound() {
        assertFalse(hangmanGameService.isGameFinishedSuccessful(currentUserGameUser.getGameQuestionInfo().getAnswerIds()));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("e", 1L)));
        hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("e", 1L));
        assertFalse(hangmanGameService.isGameFinishedSuccessful(currentUserGameUser.getGameQuestionInfo().getAnswerIds()));
        hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("u", 2L));
        hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("r", 3L));
        hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("o", 4L));
        GameQuestionInfo gameQuestionInfo = currentUserGameUser.getGameQuestionInfo();
        assertFalse(hangmanGameService.isGameFinishedSuccessful(gameQuestionInfo.getAnswerIds()));
        resolveWord1();
        assertTrue(hangmanGameService.isGameFinishedSuccessful(gameQuestionInfo.getAnswerIds()));
    }

    @Test
    public void getWordLetters() {
        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS + "ţ";
            }
        };
        String hangmanWord = "a b-cd ţ";
        Set<String> letters = hangmanGameService.getNormalizedWordLetters(hangmanWord);
        assertTrue(letters.containsAll(Arrays.asList("a", "b", "c", "d", "ţ")));
        assertEquals(5, letters.size());

        letters = hangmanGameService.getWordLetters(hangmanWord);
        assertTrue(letters.containsAll(Arrays.asList("a", "b", "c", "d", "ţ")));
        assertEquals(5, letters.size());

        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS;
            }
        };
        letters = hangmanGameService.getNormalizedWordLetters(hangmanWord);
        assertTrue(letters.containsAll(Arrays.asList("a", "b", "c", "d", "t")));
        assertEquals(5, letters.size());

        letters = hangmanGameService.getWordLetters(hangmanWord);
        assertTrue(letters.containsAll(Arrays.asList("a", "b", "c", "d", "ţ")));
        assertEquals(5, letters.size());
    }

    @Test
    public void getCurrentWordState() {
        GameQuestionInfo gameQuestionInfo = currentUserGameUser.getGameQuestionInfo();
        assertEquals("____ ________-___", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("e", 1L)));
        assertEquals("E___ ________-__e", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("t", 1L)));
        assertEquals("E___ ________-__e", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("ţ", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("ţ", 1L)));
        assertEquals("E___ ______ţ_-__e", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("U", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("r", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("o", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("b", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("L", 1L)));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("X", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("e", 1L)));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("h", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("A", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("i", 1L)));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("y", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("u", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("U", 1L)));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("k", 1L)));
        assertFalse(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("k", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("u", 1L)));
        assertEquals("Euro obli_aţi-u_e", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("g", 1L)));
        assertTrue(hangmanGameService.addAnswerToGameInfo(currentUserGameUser, new GameAnswerInfo("n", 1L)));
        assertEquals(word, hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
        assertEquals(16, gameQuestionInfo.getAnswerIds().size());

        GameContext gameContext = createGameContext(QUESTION_1);
        currentUserGameUser = gameContext.getCurrentUserGameUser();
        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS + "ţ";
            }
        };
        assertEquals("Euro obligaţi-une", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));

        gameContext = createGameContext(QUESTION_1);
        currentUserGameUser = gameContext.getCurrentUserGameUser();
        hangmanGameService = new HangmanGameService(currentUserGameUser.getGameQuestionInfo().getQuestion()) {
            @Override
            public String getAvailableLetters() {
                return HangmanGameService.STANDARD_LETTERS;
            }
        };
        assertEquals("Euro obligaţi-une", hangmanGameService.getCurrentWordState(gameQuestionInfo.getQuestion().getQuestionString(), gameQuestionInfo.getAnswerIds()));
    }

    @Test
    public void simulatePressedLetter() {
        //Gnocchi

        int totalGames = 10;
        int expectedMinFoundAnswers = 3;
        int expectedMinGameOver = 5;
        int actualFoundAnswers = 0;
        int actualGameOver = 0;
        int actualTimesGreaterThan = 0;
        for (int i = 0; i < totalGames; i++) {
            GameContext gameContext = createGameContext(TestDataCreator.QUESTION_0);
            currentUserGameUser = gameContext.getCurrentUserGameUser();
            GameQuestionInfo gameQuestionInfo = currentUserGameUser.getGameQuestionInfo();
            hangmanGameService = new HangmanGameService(gameQuestionInfo.getQuestion());
            GameAnswerInfo answerInfo = hangmanGameService.simulatePressedAnswer(gameQuestionInfo);
            while (answerInfo != null) {
                currentUserGameUser.addAnswerToGameQuestionInfo(answerInfo.getAnswer(), answerInfo.getMillisAnswered());
                answerInfo = hangmanGameService.simulatePressedAnswer(gameQuestionInfo);
            }
            if (hangmanGameService.isGameFinishedSuccessful(gameQuestionInfo.getAnswerIds())) {
                actualFoundAnswers++;
            } else if (hangmanGameService.isGameFinishedFailed(gameQuestionInfo.getAnswerIds())) {
                actualGameOver++;
            }
            if (hangmanGameService.isGameFinishedSuccessful(gameQuestionInfo.getAnswerIds())) {
                //word has 6 letters
                if (gameQuestionInfo.getTotalAnswerMillis() > 35000 && gameQuestionInfo.getTotalAnswerMillis() < 55000) {
                    actualTimesGreaterThan++;
                }
                System.out.print(gameQuestionInfo.getTotalAnswerMillis() + "       -       ");
            }
        }
        assertTrue("FoundAnswers" + actualFoundAnswers, actualFoundAnswers >= expectedMinFoundAnswers);
        assertTrue("GameOver" + actualGameOver, actualGameOver >= expectedMinGameOver);
        assertTrue("The percent of good time should be greater than 85, actual:" + (actualTimesGreaterThan / Float.valueOf(actualFoundAnswers)) * 100, (actualTimesGreaterThan / Float.valueOf(actualFoundAnswers)) * 100 > 85);

    }

    @Test
    public void getRandomUnpressedLetterFromHangmanWord() {
        currentUserGameUser.addAnswerToGameQuestionInfo("e", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("u", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("a", 1L);

        List<String> lettersToBePressed = new ArrayList<>(Arrays.asList("r", "o", "b", "l", "i", "g", "ţ", "n"));
        boolean pressedLetterFound = false;
        for (int i = 0; i < 200; i++) {
            String randomUnpressedLetterFromHangmanWord = hangmanGameService.getRandomUnpressedAnswerFromQuestion(currentUserGameUser.getGameQuestionInfo().getAnswerIds());
            if (currentUserGameUser.getGameQuestionInfo().getAnswerIds().contains(randomUnpressedLetterFromHangmanWord)) {
                pressedLetterFound = true;
                break;
            }
            lettersToBePressed.remove(randomUnpressedLetterFromHangmanWord);
        }
        assertFalse(pressedLetterFound);
        assertEquals(0, lettersToBePressed.size());
    }

    private void resolveWord1() {
        currentUserGameUser.addAnswerToGameQuestionInfo("e", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("t", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("ţ", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("u", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("r", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("o", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("b", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("l", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("e", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("h", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("a", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("i", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("u", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("g", 1L);
        currentUserGameUser.addAnswerToGameQuestionInfo("n", 1L);
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.hangman.name();
            }
        };
    }
}
