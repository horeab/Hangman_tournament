package libgdx.ui.services.listeners.livegame.aftergame;

import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.listeners.ListenerConfig;
import libgdx.ui.services.listeners.ListenerValueChange;

public class OpponentRematchStatusChangeListener<TScreen extends AbstractLevelFinishedChallengeScreen> extends ListenerValueChange<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    public OpponentRematchStatusChangeListener(int liveGameId, TScreen screen) {
        super(liveGameId, screen,
                new ListenerConfig(getOpponentUserStatusField(liveGameId), Integer.class));
    }

    private static String getOpponentUserStatusField(int liveGameId) {
        return new LiveGameService().getOpponentStatusField(liveGameId);
    }

    @Override
    public void executeOperations(Object param) {
        liveGameActionsService.displayRematchNotificationPopup((Integer) param, getScreen());
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
