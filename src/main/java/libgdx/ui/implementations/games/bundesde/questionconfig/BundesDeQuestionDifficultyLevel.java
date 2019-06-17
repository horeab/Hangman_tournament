package libgdx.ui.implementations.games.bundesde.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum BundesDeQuestionDifficultyLevel implements QuestionDifficulty<BundesDeQuestionDifficultyLevel> {

    _0,

    _1,

    _2,;

    public int getIndex() {
        return ordinal();
    }

}
