package libgdx.ui.implementations.games.scoalasofer.questionconfig;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.DependentQuizGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.SubmitQuestionsGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.UniqueQuizGameCreatorDependencies;

public enum ScoalaSoferQuestionCategoryEnum implements QuestionCategory {

    cat0(SubmitQuestionsGameCreatorDependencies.class),
    cat1(SubmitQuestionsGameCreatorDependencies.class),
    cat2(SubmitQuestionsGameCreatorDependencies.class),
    cat3(SubmitQuestionsGameCreatorDependencies.class),
    cat4(SubmitQuestionsGameCreatorDependencies.class),
    cat5(SubmitQuestionsGameCreatorDependencies.class),
    cat6(SubmitQuestionsGameCreatorDependencies.class),
    cat7(SubmitQuestionsGameCreatorDependencies.class),
    cat8(SubmitQuestionsGameCreatorDependencies.class),
    cat9(SubmitQuestionsGameCreatorDependencies.class),
    cat10(SubmitQuestionsGameCreatorDependencies.class),
    cat11(SubmitQuestionsGameCreatorDependencies.class),
    cat12(SubmitQuestionsGameCreatorDependencies.class),
    cat13(SubmitQuestionsGameCreatorDependencies.class),
    cat14(SubmitQuestionsGameCreatorDependencies.class),
    ;

    private Class<? extends CreatorDependencies> questionCreator;

    ScoalaSoferQuestionCategoryEnum(Class<? extends CreatorDependencies> questionCreator) {
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
