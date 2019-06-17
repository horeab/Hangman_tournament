package libgdx.ui.implementations.games.bundesde.questionconfig;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class BundesDeQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Arrays.<QuestionDifficulty>asList(BundesDeQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Arrays.<QuestionDifficulty>asList(BundesDeQuestionDifficultyLevel._1);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(BundesDeQuestionDifficultyLevel._2);
    }
}
