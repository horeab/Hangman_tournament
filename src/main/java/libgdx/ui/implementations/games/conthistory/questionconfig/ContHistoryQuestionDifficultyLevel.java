package libgdx.ui.implementations.games.conthistory.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum ContHistoryQuestionDifficultyLevel implements QuestionDifficulty<ContHistoryQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
