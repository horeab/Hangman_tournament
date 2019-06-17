package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.service.UniqueAnswersQuizGameService;

public class SubmitQuestionsGameService extends UniqueAnswersQuizGameService {

    private Integer answerOptionsPressedToVerify;
    private Integer initialAnswerOptionsPressedToVerify;

    public SubmitQuestionsGameService(Question question) {
        super(question);
    }

    @Override
    public boolean addAnswerToGameInfo(GameUser gameUser, GameAnswerInfo gameAnswerInfo) {
        this.answerOptionsPressedToVerify--;
        return super.addAnswerToGameInfo(gameUser, gameAnswerInfo);
    }

    public void setInitialAnswerOptionsPressedToVerify(int answerOptionsPressedToVerify) {
        if (initialAnswerOptionsPressedToVerify == null && this.answerOptionsPressedToVerify == null) {
            this.initialAnswerOptionsPressedToVerify = answerOptionsPressedToVerify;
            this.answerOptionsPressedToVerify = answerOptionsPressedToVerify;
        }
    }

    @Override
    public boolean isGameFinishedSuccessful(Set<String> pressedAnswers) {
        List<String> answers = new ArrayList<>();
        for (String string : getAnswers()) {
            answers.add(string);
        }
        return answerOptionsPressedToVerify != null && answerOptionsPressedToVerify <= 0 && pressedAnswers.containsAll(answers) && answers.size() == pressedAnswers.size();
    }

    @Override
    public boolean isGameFinishedFailed(Set<String> answersIds) {
        return answerOptionsPressedToVerify != null && answerOptionsPressedToVerify <= 0 && !isGameFinishedSuccessful(answersIds);
    }

    @Override
    protected int getSimulatePressedLetterCorrectAnswerFactor() {
        int answerFactor = super.getSimulatePressedLetterCorrectAnswerFactor();
        return getRandomCorrectAnswerFactor(initialAnswerOptionsPressedToVerify == 1 ? Math.round(answerFactor / 2f) : Math.round(answerFactor / 5f));
    }

    public int getInitialAnswerOptionsPressedToVerify() {
        return initialAnswerOptionsPressedToVerify;
    }

    @Override
    public int getNrOfWrongAnswersPressed(Set<String> answersIds) {
        return isGameFinishedFailed(answersIds) ? 1 : 0;
    }
}
