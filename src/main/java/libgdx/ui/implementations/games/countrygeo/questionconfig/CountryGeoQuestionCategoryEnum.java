package libgdx.ui.implementations.games.countrygeo.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum CountryGeoQuestionCategoryEnum implements QuestionCategory {

    cat0(ImageClickGameCreatorDependencies.class),
    cat1(ImageClickGameCreatorDependencies.class),
    cat2(ImageClickGameCreatorDependencies.class),
    cat3(ImageClickGameCreatorDependencies.class),
    cat4(ImageClickGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    CountryGeoQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
