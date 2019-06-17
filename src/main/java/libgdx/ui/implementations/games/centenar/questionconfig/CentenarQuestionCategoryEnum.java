package libgdx.ui.implementations.games.centenar.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum CentenarQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),//Centenar
    cat1(UniqueQuizGameCreatorDependencies.class),//Istorie
    cat2(UniqueQuizGameCreatorDependencies.class),//Geografie
    cat3(DependentQuizGameCreatorDependencies.class),//Judete
    cat4(ImageClickGameCreatorDependencies.class),//Relief
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    CentenarQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
