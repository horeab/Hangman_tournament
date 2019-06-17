package libgdx.ui.implementations.games.geoquiz.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum GeoQuizQuestionDifficultyLevel implements QuestionDifficulty<GeoQuizQuestionDifficultyLevel> {

    _0,

    _1,

    _2,

    _3,;

    public int getIndex() {
        return ordinal();
    }

}
