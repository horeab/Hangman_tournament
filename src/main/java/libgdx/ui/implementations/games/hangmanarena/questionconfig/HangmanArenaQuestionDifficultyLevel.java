package libgdx.ui.implementations.games.hangmanarena.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum HangmanArenaQuestionDifficultyLevel implements QuestionDifficulty<HangmanArenaQuestionDifficultyLevel> {

    _0,

    _1,

    _2,

    _3,

    _4,

    _5,;

    public int getIndex() {
        return ordinal();
    }

}
