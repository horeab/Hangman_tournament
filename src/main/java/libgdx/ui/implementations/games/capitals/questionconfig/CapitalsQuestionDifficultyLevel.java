package libgdx.ui.implementations.games.capitals.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum CapitalsQuestionDifficultyLevel implements QuestionDifficulty<CapitalsQuestionDifficultyLevel> {

    _0,

    _1,

    _2,
    ;

    public int getIndex() {
        return ordinal();
    }

}
