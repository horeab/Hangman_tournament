package libgdx.ui.screens.levelfinished.service;

import libgdx.ui.screens.levelfinished.service.challenge.ChallengeLevelFinishedScreenService;
import libgdx.ui.screens.levelfinished.service.challenge.HostCurrentUserLevelFinishedScreenService;
import libgdx.ui.screens.levelfinished.service.challenge.HostOpponentLevelFinishedScreenService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.game.livegame.LiveGameService;

public class LevelFinishedScreenServiceFactory {

    public LevelFinishedScreenService createStandardLevelFinishedScreenService() {
        return new LevelFinishedScreenService();
    }

    public ChallengeLevelFinishedScreenService createChallengeLevelFinishedScreenService(GameContext gameContext, int liveGameId) {
        if (new LiveGameService().isUser1LoggedIn(liveGameId)) {
            return new HostCurrentUserLevelFinishedScreenService(gameContext, liveGameId);
        } else {
            return new HostOpponentLevelFinishedScreenService(gameContext, liveGameId);
        }
    }

}
