package libgdx.ui.implementations.games.astronomy.questionconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class AstronomyQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Collections.<QuestionDifficulty>singletonList(AstronomyQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Collections.<QuestionDifficulty>singletonList(AstronomyQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(AstronomyQuestionDifficultyLevel._0);
    }
}
