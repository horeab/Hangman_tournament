package libgdx.ui.implementations.games.cunro.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum CunRoQuestionDifficultyLevel implements QuestionDifficulty<CunRoQuestionDifficultyLevel> {

    _0,;

    public int getIndex() {
        return ordinal();
    }

}
