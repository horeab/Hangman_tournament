package libgdx.ui.implementations.games.animals.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum AnimalsQuestionDifficultyLevel implements QuestionDifficulty<AnimalsQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
