package libgdx.ui.implementations.games.judetelerom.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum JudeteleRomQuestionDifficultyLevel implements QuestionDifficulty<JudeteleRomQuestionDifficultyLevel> {

    _0,

    _1,

    _2,;

    public int getIndex() {
        return ordinal();
    }

}
