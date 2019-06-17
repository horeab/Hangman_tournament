package libgdx.ui.services.listeners.livegame.ingame;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.listeners.ListenerConfig;
import libgdx.ui.services.listeners.ListenerValueChange;

public class OpponentAbandonGameListener<TScreen extends AbstractGameScreen> extends ListenerValueChange<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    public OpponentAbandonGameListener(int liveGameId, TScreen screen) {
        super(liveGameId, screen,
                new ListenerConfig(getOpponentUserStatusField(liveGameId), Integer.class));
    }

    private static String getOpponentUserStatusField(int liveGameId) {
        return new LiveGameService().getOpponentStatusField(liveGameId);
    }

    @Override
    public void executeOperations(Object param) {
        if (LiveGameStatusEnum.REJECT_MATCH.getStatus().equals((Integer) param)) {
            liveGameActionsService.showLevelFinishedChallengeScreen(getScreen(), getScreen().getGameContext(), getEntityId());
        }
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
