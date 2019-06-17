package libgdx.ui.implementations.games.kennstde.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum KennstDeQuestionDifficultyLevel implements QuestionDifficulty<KennstDeQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
