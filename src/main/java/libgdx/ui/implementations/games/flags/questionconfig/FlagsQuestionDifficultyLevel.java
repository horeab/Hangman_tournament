package libgdx.ui.implementations.games.flags.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum FlagsQuestionDifficultyLevel implements QuestionDifficulty<FlagsQuestionDifficultyLevel> {

    _0,

    _1,

    _2,
    ;

    public int getIndex() {
        return ordinal();
    }

}
