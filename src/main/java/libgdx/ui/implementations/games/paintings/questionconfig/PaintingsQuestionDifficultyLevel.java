package libgdx.ui.implementations.games.paintings.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum PaintingsQuestionDifficultyLevel implements QuestionDifficulty<PaintingsQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
