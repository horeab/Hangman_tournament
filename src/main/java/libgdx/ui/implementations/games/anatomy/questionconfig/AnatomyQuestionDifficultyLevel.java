package libgdx.ui.implementations.games.anatomy.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum AnatomyQuestionDifficultyLevel implements QuestionDifficulty<AnatomyQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
