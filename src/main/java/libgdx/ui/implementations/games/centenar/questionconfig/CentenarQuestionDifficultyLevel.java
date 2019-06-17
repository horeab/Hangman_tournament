package libgdx.ui.implementations.games.centenar.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum CentenarQuestionDifficultyLevel implements QuestionDifficulty<CentenarQuestionDifficultyLevel> {

    _0,;

    public int getIndex() {
        return ordinal();
    }
}
