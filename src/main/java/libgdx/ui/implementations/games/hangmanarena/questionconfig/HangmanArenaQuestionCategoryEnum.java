package libgdx.ui.implementations.games.hangmanarena.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;

public enum HangmanArenaQuestionCategoryEnum implements QuestionCategory {

    cat0(HangmanGameCreatorDependencies.class),//Countries
    cat1(HangmanGameCreatorDependencies.class),//Animals
    cat2(HangmanGameCreatorDependencies.class),//Plants
    cat3(HangmanGameCreatorDependencies.class),//Kitchen
    cat4(HangmanGameCreatorDependencies.class),//Food
    cat5(HangmanGameCreatorDependencies.class),//Mixed;
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    HangmanArenaQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
