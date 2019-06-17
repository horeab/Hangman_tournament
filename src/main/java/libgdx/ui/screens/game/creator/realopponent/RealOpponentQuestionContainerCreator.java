package libgdx.ui.screens.game.creator.realopponent;

import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenQuestionContainerCreator;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.listeners.livegame.ingame.OpponentHasGameInfoVersionValueListener;

public class RealOpponentQuestionContainerCreator<TGameService extends GameService> extends AbstractGameScreenQuestionContainerCreator<ChallengeGameScreen, TGameService> {

    public RealOpponentQuestionContainerCreator(ChallengeGameScreen abstractScreen, GameContext gameContext) {
        super(abstractScreen, gameContext);
    }

    @Override
    public void buttonClick(final String answer) {
        getGameControlsService().disableTouchableAllControls();
        getGameScreen().updateCurrentUserPressedAnswerToDb(createGameAnswerInfo(answer));
    }

    protected GameAnswerInfo createGameAnswerInfo(String answer) {
        return new GameAnswerInfo(answer, getGameScreen().getMillisPassedSinceScreenDisplayed());
    }
}
