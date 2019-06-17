package libgdx.ui.services.gametypes.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;
import libgdx.utils.EnumUtils;

public class QuestionParser {

    public List<String> getAnswers(String questionString) {
        try {
            return Arrays.asList(questionString.split(":")[2]);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<String> getAllAnswerOptions(Question question) {
        List<String> answerOptions = new ArrayList<>(getAnswers(question.getQuestionString()));
        List<Question> allQuestions = getAllQuestions(question.getQuestionDifficultyLevel(), question.getQuestionCategory());
        for (String fileId : new QuestionParser().getAnswerIds(question.getQuestionString(), 3)) {
            answerOptions.addAll(getAnswers(getQuestionForFileId(fileId, allQuestions).getQuestionString()));
        }
        Collections.shuffle(answerOptions);
        return answerOptions;
    }

    public List<Question> getAllQuestions(QuestionDifficulty questionDifficultyLevel, QuestionCategory questionCategory) {
        return CreatorDependenciesContainer.getCreator(questionCategory.getCreatorDependencies()).getQuestionCreator(questionDifficultyLevel, questionCategory).getAllQuestions(Arrays.asList((QuestionDifficulty[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum())), questionCategory);
    }

    private Question getQuestionForFileId(String fileId, List<Question> allQuestions) {
        for (Question question : allQuestions) {
            if (question.getQuestionString().split(":")[0].equals(fileId)) {
                return question;
            }
        }
        return null;
    }

    private List<String> getAnswerIds(String questionString, int position) {
        String[] answers = questionString.split(":")[position].split(",");
        List<String> toReturn = new ArrayList<>();
        for (String answer : answers) {
            toReturn.add(answer.trim());
        }
        return toReturn;
    }
}
