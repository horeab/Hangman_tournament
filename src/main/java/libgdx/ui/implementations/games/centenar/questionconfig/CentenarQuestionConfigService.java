package libgdx.ui.implementations.games.centenar.questionconfig;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class CentenarQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Arrays.<QuestionDifficulty>asList(CentenarQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Arrays.<QuestionDifficulty>asList(CentenarQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(CentenarQuestionDifficultyLevel._0);
    }
}
