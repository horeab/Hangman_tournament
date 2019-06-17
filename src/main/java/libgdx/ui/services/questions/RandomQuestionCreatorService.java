package libgdx.ui.services.questions;

import java.util.Arrays;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;

public class RandomQuestionCreatorService {

    public Question[] createRandomQuestions(QuestionConfig questionConfig) {
        int questionAmount = questionConfig.getAmountOfQuestions();
        Question[] randomQuestions = new Question[questionAmount];
        for (int i = 0; i < questionAmount; i++) {
            RandomCategoryAndDifficulty randomCategoryAndDifficulty = questionConfig.getRandomCategoryAndDifficulty();
            QuestionCategory randomQuestionCategory = randomCategoryAndDifficulty.getQuestionCategory();
            QuestionCreator questionCreator = CreatorDependenciesContainer.getCreator(randomQuestionCategory.getCreatorDependencies()).getQuestionCreator(randomCategoryAndDifficulty.getQuestionDifficulty(), randomQuestionCategory);
            int repeats = 0;
            Question randomQuestion = questionCreator.createRandomQuestion();
            while (Arrays.asList(randomQuestions).contains(randomQuestion) || !questionCreator.isQuestionValid(randomQuestion)) {
                if (repeats > 100) {
                    break;
                }
                randomQuestion = questionCreator.createRandomQuestion();
                repeats++;
            }
            randomQuestions[i] = randomQuestion;
        }
        return randomQuestions;
    }
}
