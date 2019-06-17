package libgdx.ui.implementations.games.paintings.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;

public enum PaintingsQuestionCategoryEnum implements QuestionCategory {

    cat0(DependentQuizGameCreatorDependencies.class),
    cat1(DependentQuizGameCreatorDependencies.class),
    cat2(DependentQuizGameCreatorDependencies.class),
    cat3(DependentQuizGameCreatorDependencies.class),
    cat4(DependentQuizGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    PaintingsQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
