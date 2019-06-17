package libgdx.ui.implementations.games.capitals.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;

public enum CapitalsQuestionCategoryEnum implements QuestionCategory {

    cat0(DependentQuizGameCreatorDependencies.class),//Europe
    cat1(DependentQuizGameCreatorDependencies.class),//North America
    cat2(DependentQuizGameCreatorDependencies.class),//Asia and Oceania
    cat3(DependentQuizGameCreatorDependencies.class),//South America
    cat4(DependentQuizGameCreatorDependencies.class),//Africa
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    CapitalsQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
