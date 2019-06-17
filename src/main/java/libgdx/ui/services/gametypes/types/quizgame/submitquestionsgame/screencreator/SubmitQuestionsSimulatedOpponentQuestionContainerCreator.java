package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameService;

public class SubmitQuestionsSimulatedOpponentQuestionContainerCreator extends SimulatedOpponentQuestionContainerCreator<SubmitQuestionsGameService> {

    public SubmitQuestionsSimulatedOpponentQuestionContainerCreator(AbstractGameScreen abstractScreen, GameContext gameContext) {
        super(abstractScreen, gameContext);
    }

    @Override
    protected SubmitQuestionsGameService getOpponentGameService() {
        SubmitQuestionsGameService opponentGameService = super.getOpponentGameService();
        opponentGameService.setInitialAnswerOptionsPressedToVerify(opponentGameService.getAnswers().size());
        return opponentGameService;
    }
}
