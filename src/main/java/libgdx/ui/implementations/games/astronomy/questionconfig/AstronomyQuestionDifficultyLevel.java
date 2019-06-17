package libgdx.ui.implementations.games.astronomy.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum AstronomyQuestionDifficultyLevel implements QuestionDifficulty<AstronomyQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
