package libgdx.ui.screens.game.creator.realopponent;

import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.model.game.GameContext;

public class RealOpponentScreenCreator extends AbstractGameScreenCreator<ChallengeGameScreen, RealOpponentQuestionContainerCreator> {


    public RealOpponentScreenCreator(ChallengeGameScreen challengeGameScreen, GameContext gameContext) {
        super(challengeGameScreen, gameContext);
    }

    @Override
    protected RealOpponentQuestionContainerCreator createGameScreenQuestionContainerCreator() {
        return getGameContext().getCurrentUserCreatorDependencies().getRealOpponentQuestionContainerCreator(getGameScreen(), getGameContext());
    }

}
