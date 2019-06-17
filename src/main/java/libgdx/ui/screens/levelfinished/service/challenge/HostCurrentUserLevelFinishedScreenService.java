package libgdx.ui.screens.levelfinished.service.challenge;

import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.GameContext;

public  class HostCurrentUserLevelFinishedScreenService extends ChallengeLevelFinishedScreenService {

    public HostCurrentUserLevelFinishedScreenService(GameContext gameContext, int liveGameId) {
        super(gameContext, liveGameId);
    }

    @Override
    public GameUser getUser1() {
        return gameContext.getCurrentUserGameUser();
    }

    @Override
    public GameUser getUser2() {
        return gameContext.getOpponentGameUser();
    }
}
