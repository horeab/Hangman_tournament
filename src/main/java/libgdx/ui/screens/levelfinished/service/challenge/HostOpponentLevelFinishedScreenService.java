package libgdx.ui.screens.levelfinished.service.challenge;

import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.GameContext;

public class HostOpponentLevelFinishedScreenService extends ChallengeLevelFinishedScreenService {

    public HostOpponentLevelFinishedScreenService(GameContext gameContext, int liveGameId) {
        super(gameContext, liveGameId);
    }

    @Override
    public GameUser getUser1() {
        return gameContext.getOpponentGameUser();
    }

    @Override
    public GameUser getUser2() {
        return gameContext.getCurrentUserGameUser();
    }
}
