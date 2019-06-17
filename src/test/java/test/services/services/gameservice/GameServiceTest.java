package test.services.services.gameservice;

import org.apache.commons.lang3.StringUtils;
import org.powermock.api.mockito.PowerMockito;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.services.gametypes.types.imageclickgame.service.ImageClickGameService;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.service.DependentAnswersQuizGameService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameService;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.service.UniqueAnswersQuizGameService;
import libgdx.ui.services.questions.QuestionConfigFileHandler;
import libgdx.ui.services.questions.QuestionCreator;
import libgdx.ui.util.TestDataCreator;
import libgdx.utils.EnumUtils;
import test.TournamentGameTestDbApiService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class GameServiceTest extends TournamentGameTestDbApiService {

    public void testQuestions() throws Exception {
        TournamentGameDependencyManager subGameDependencyManager = TournamentGame.getInstance().getSubGameDependencyManager();
        for (String lang : getAllLang()) {
            QuestionCreator quizQuestionCreator = createQuestionsCreator(lang);
            for (QuestionCategory categoryEnum : (QuestionCategory[]) EnumUtils.getValues(subGameDependencyManager.getQuestionCategoryTypeEnum())) {
                List<Question> questions = quizQuestionCreator.getAllQuestions(Arrays.asList(getQuestionDifficulties(categoryEnum)), categoryEnum);
                assertAllQuestions(lang, questions);
            }
        }
    }

    public QuestionDifficulty[] getQuestionDifficulties(QuestionCategory questionCategory) {
        TournamentGameDependencyManager subGameDependencyManager = TournamentGame.getInstance().getSubGameDependencyManager();
        return (QuestionDifficulty[]) EnumUtils.getValues(subGameDependencyManager.getQuestionDifficultyTypeEnum());
    }

    public QuestionCreator createQuestionsCreator(String lang) throws Exception {
        TournamentGameDependencyManager subGameDependencyManager = TournamentGame.getInstance().getSubGameDependencyManager();
        RandomCategoryAndDifficulty randomCategoryAndDifficulty = subGameDependencyManager.getQuestionConfigService().getPracticeQuestionConfig().getRandomCategoryAndDifficulty();
        QuestionCreator quizQuestionCreator = new QuestionCreator(randomCategoryAndDifficulty.getQuestionDifficulty(), randomCategoryAndDifficulty.getQuestionCategory()) {
            @Override
            public QuestionConfigFileHandler getConfigFileHandler() {
                return new QuestionConfigFileHandler() {
                    @Override
                    protected String getLanguage() {
                        return lang;
                    }
                };
            }
        };
        PowerMockito.whenNew(QuestionCreator.class).withAnyArguments().thenReturn(quizQuestionCreator);
        return quizQuestionCreator;
    }

    public void assertAllQuestions(String lang, List<Question> questions) {
        for (Question question : questions) {
//            System.out.println(question.getQuestionString());
            printInfo(question.getQuestionString());
            String[] split = question.getQuestionString().split(":");
            //question id is same as the image
            GameService gameService = GameServiceContainer.getGameService(currentUserId, question);
            int imageToBeDisplayedPositionInString = gameService.getImageToBeDisplayedPositionInString();
            if (split.length == imageToBeDisplayedPositionInString + 1 && StringUtils.isNotBlank(split[imageToBeDisplayedPositionInString])) {
                String imageId = split[split.length - 1];
                assertTrue(lang + " - " + question.getQuestionString(), split[0].equals(imageId) || Integer.parseInt(imageId) == 999);
                assertNotNull(lang + " - " + question.getQuestionString(), gameService.getQuestionImage());
            }
            String questionToBeDisplayed = gameService.getQuestionToBeDisplayed();
            if (StringUtils.isNotBlank(questionToBeDisplayed)) {
                assertTrue(lang + " - " + questionToBeDisplayed, removeDiacritics(questionToBeDisplayed).matches(getQuestionsRegex()));
            }
            try {
                assertAnswerOptions(lang, gameService);
            } catch (Exception e) {
                fail(e.getMessage() + " - " + lang + " - " + question.getQuestionString());
            }
        }
    }

    private void printInfo(String info) {
        System.out.println(info.split(":")[2]);
    }

    public void assertAnswerOptions(String lang, GameService gameService) {
        if (gameService instanceof DependentAnswersQuizGameService) {
            for (String answer : gameService.getAllAnswerOptions()) {
                assertTrue(answer.length() >= 2);
            }
//            System.out.println(gameService.getAllAnswerOptions().toString());
            assertTrue(((DependentAnswersQuizGameService) gameService).getAnswers().size() == 1);
            assertEquals(gameService.getAllAnswerOptions().toString(), 4, gameService.getAllAnswerOptions().size());
        } else if (gameService instanceof SubmitQuestionsGameService) {
            assertTrue(gameService.getAllAnswerOptions().size() > 2);
        } else if (gameService instanceof ImageClickGameService) {
            assertEquals(4, gameService.getAllAnswerOptions().size());
        } else if (gameService instanceof UniqueAnswersQuizGameService) {
            assertTrue(gameService.getAllAnswerOptions().size() > 2);
            assertTrue(((UniqueAnswersQuizGameService) gameService).getAnswers().size() == 1);
        }
        for (String answer : gameService.getAllAnswerOptions()) {
            assertTrue(StringUtils.isNotBlank(answer));
            assertTrue(lang + " - " + answer, removeDiacritics(answer).matches(getAnswersRegex()));
        }
    }

    public String getQuestionsRegex() {
        return "^[a-zA-Z0-9, \\-\\'\\?\\.\\/\\,\\(\\)\\\"\\%]*$";
    }

    public String getAnswersRegex() {
        return "^[a-zA-Z0-9#, \\-\\'\\/\\.\\;\\(\\)\\,\\\"\\%]*$";
    }

    protected List<String> getAllLang() {
        return TestDataCreator.ALL_LANG;
    }

    protected String removeDiacritics(String string) {
        return string.replaceAll("[^\\p{ASCII}]", "");
    }

    protected abstract AppInfoServiceImpl getAppInfoService();

    protected abstract void testAllQuestions() throws Exception;

}
