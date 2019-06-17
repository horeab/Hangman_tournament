package libgdx.ui.implementations.games.scoalasofer.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum ScoalaSoferQuestionDifficultyLevel implements QuestionDifficulty<ScoalaSoferQuestionDifficultyLevel> {

    _0,
    _1,
    _2,;

    public int getIndex() {
        return ordinal();
    }

}
