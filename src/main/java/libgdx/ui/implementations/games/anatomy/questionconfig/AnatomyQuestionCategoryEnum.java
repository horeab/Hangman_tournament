package libgdx.ui.implementations.games.anatomy.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.service.ImageClickGameService;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum AnatomyQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),
    cat1(UniqueQuizGameCreatorDependencies.class),
    cat2(ImageClickGameCreatorDependencies.class),
    cat3(ImageClickGameCreatorDependencies.class),
    cat4(ImageClickGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    AnatomyQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
