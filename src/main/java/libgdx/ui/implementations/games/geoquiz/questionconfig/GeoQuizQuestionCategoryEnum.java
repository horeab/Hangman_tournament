package libgdx.ui.implementations.games.geoquiz.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;

public enum GeoQuizQuestionCategoryEnum implements QuestionCategory {

    cat0(DependentQuizGameCreatorDependencies.class),//Objectives
    cat1(DependentQuizGameCreatorDependencies.class),//Flags
    cat2(DependentQuizGameCreatorDependencies.class),//Capitals
    cat3(DependentQuizGameCreatorDependencies.class),//Map
    cat4(DependentQuizGameCreatorDependencies.class),//Natural Objectives;
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    GeoQuizQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
