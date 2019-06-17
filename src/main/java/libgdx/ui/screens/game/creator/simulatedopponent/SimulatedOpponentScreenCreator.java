package libgdx.ui.screens.game.creator.simulatedopponent;

import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.screens.game.creator.realopponent.RealOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.model.game.GameContext;

public class SimulatedOpponentScreenCreator extends AbstractGameScreenCreator<AbstractGameScreen, SimulatedOpponentQuestionContainerCreator> {

    public SimulatedOpponentScreenCreator(AbstractGameScreen gameScreen, GameContext gameContext) {
        super(gameScreen, gameContext);
    }

    @Override
    protected SimulatedOpponentQuestionContainerCreator createGameScreenQuestionContainerCreator() {
        return getGameContext().getCurrentUserCreatorDependencies().getSimulatedOpponentQuestionContainerCreator(getGameScreen(), getGameContext());
    }
}
