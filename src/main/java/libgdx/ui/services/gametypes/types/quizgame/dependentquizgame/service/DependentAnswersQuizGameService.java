package libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import libgdx.ui.model.game.question.Question;
import libgdx.ui.services.gametypes.types.QuestionParser;
import libgdx.ui.services.gametypes.types.quizgame.QuizGameService;

//id:question:answer:answer_options:image
public class DependentAnswersQuizGameService extends QuizGameService {

    private QuestionParser questionParser = new QuestionParser();

    public DependentAnswersQuizGameService(Question question) {
        super(question);
    }

    @Override
    public String getRandomUnpressedAnswerFromQuestion(Set<String> answersIds) {
        List<String> answers = getAnswers();
        Collections.shuffle(answers);
        return answers.get(0);
    }

    @Override
    public List<String> getAnswers() {
        return questionParser.getAnswers(question.getQuestionString());
    }

    @Override
    public List<String> getAllAnswerOptions() {
        return questionParser.getAllAnswerOptions(question);
    }

}
