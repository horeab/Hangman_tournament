package libgdx.ui.implementations.games.countryhistro.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum CountryHistRoQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),
    cat1(UniqueQuizGameCreatorDependencies.class),
    cat2(UniqueQuizGameCreatorDependencies.class),
    cat3(UniqueQuizGameCreatorDependencies.class),
    cat4(UniqueQuizGameCreatorDependencies.class),
    cat5(UniqueQuizGameCreatorDependencies.class),
    cat6(UniqueQuizGameCreatorDependencies.class),
    cat7(UniqueQuizGameCreatorDependencies.class),
    cat8(UniqueQuizGameCreatorDependencies.class),
    cat9(UniqueQuizGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    CountryHistRoQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
