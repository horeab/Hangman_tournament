package libgdx.ui.implementations.games.judetelerom.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.ImageClickGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;

public enum JudeteleRomQuestionCategoryEnum implements QuestionCategory {

    cat0(ImageClickGameCreatorDependencies.class),//Find on map
    cat1(DependentQuizGameCreatorDependencies.class),//Car ID
    cat2(DependentQuizGameCreatorDependencies.class),//Tourist attractions
    cat3(DependentQuizGameCreatorDependencies.class),//Trivia
    cat4(HangmanGameCreatorDependencies.class),//Capital city
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    JudeteleRomQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
