package libgdx.ui.implementations.games.astronomy.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum AstronomyQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),//Our Solar System
    cat1(UniqueQuizGameCreatorDependencies.class),//Stars
    cat2(UniqueQuizGameCreatorDependencies.class),//Basic Astronomy
    cat3(UniqueQuizGameCreatorDependencies.class),//Advanced Astronomy
    cat4(UniqueQuizGameCreatorDependencies.class),//Astronomical Terms
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    AstronomyQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
        this.questionCreator = questionCreator;
    }

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public Class<? extends CreatorDependencies> getCreatorDependencies() {
        return questionCreator;
    }
}
