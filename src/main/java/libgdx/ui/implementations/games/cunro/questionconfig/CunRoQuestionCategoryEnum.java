package libgdx.ui.implementations.games.cunro.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.screencreator.HangmanScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum CunRoQuestionCategoryEnum implements QuestionCategory {

    cat0(UniqueQuizGameCreatorDependencies.class),//Cultura
    cat1(ImageClickGameCreatorDependencies.class),//Istorie
    cat2(ImageClickGameCreatorDependencies.class),//Geografie
    cat3(UniqueQuizGameCreatorDependencies.class),//Mancare
    cat4(ImageClickGameCreatorDependencies.class),//Recunoaste Orasul
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    CunRoQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
