package libgdx.ui.implementations.games.countrygeo.questionconfig;

import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum CountryGeoQuestionDifficultyLevel implements QuestionDifficulty<CountryGeoQuestionDifficultyLevel> {

    _0,
    ;

    public int getIndex() {
        return ordinal();
    }

}
