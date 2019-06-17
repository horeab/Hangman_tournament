package libgdx.ui.implementations.games.countryhistro.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum CountryHistRoQuestionDifficultyLevel implements QuestionDifficulty<CountryHistRoQuestionDifficultyLevel> {

    _0,
    _1,
    ;

    public int getIndex() {
        return ordinal();
    }

}
