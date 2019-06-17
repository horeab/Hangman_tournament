package libgdx.ui.implementations.games.capitals.questionconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class CapitalsQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Collections.<QuestionDifficulty>singletonList(CapitalsQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Collections.<QuestionDifficulty>singletonList(CapitalsQuestionDifficultyLevel._1);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(CapitalsQuestionDifficultyLevel._2);
    }
}
