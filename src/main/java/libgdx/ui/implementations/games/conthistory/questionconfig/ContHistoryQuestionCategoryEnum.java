package libgdx.ui.implementations.games.conthistory.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum ContHistoryQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),
    cat1(UniqueQuizGameCreatorDependencies.class),
    cat2(UniqueQuizGameCreatorDependencies.class),
    cat3(UniqueQuizGameCreatorDependencies.class),
    cat4(UniqueQuizGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    ContHistoryQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
