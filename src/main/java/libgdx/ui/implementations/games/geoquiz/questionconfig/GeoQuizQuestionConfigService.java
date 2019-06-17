package libgdx.ui.implementations.games.geoquiz.questionconfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class GeoQuizQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Collections.<QuestionDifficulty>singletonList(GeoQuizQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Collections.<QuestionDifficulty>singletonList(GeoQuizQuestionDifficultyLevel._1);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(GeoQuizQuestionDifficultyLevel._2, GeoQuizQuestionDifficultyLevel._3);
    }
}
