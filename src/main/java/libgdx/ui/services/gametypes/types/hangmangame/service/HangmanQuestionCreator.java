package libgdx.ui.services.gametypes.types.hangmangame.service;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.services.questions.QuestionCreator;

public class HangmanQuestionCreator extends QuestionCreator {

    public HangmanQuestionCreator(QuestionDifficulty questionDifficulty, QuestionCategory questionCategory) {
        super(questionDifficulty, questionCategory);
    }

    @Override
    protected boolean isQuestionValid(Question question) {
        return question.getQuestionString().length() > 3;
    }
}
