package libgdx.ui.implementations.games.bundesde.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;

public enum BundesDeQuestionCategoryEnum implements QuestionCategory {

    cat0(ImageClickGameCreatorDependencies.class),//Find on map
    cat1(DependentQuizGameCreatorDependencies.class),//Flags
    cat2(DependentQuizGameCreatorDependencies.class),//Tourist attractions
    cat3(DependentQuizGameCreatorDependencies.class),//Trivia
    cat4(HangmanGameCreatorDependencies.class),//Capital city
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    BundesDeQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
