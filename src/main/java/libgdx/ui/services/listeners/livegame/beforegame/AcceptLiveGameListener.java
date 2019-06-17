package libgdx.ui.services.listeners.livegame.beforegame;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueMetConfig;
import libgdx.ui.services.listeners.ListenerValueMet;

public class AcceptLiveGameListener extends ListenerValueMet<AbstractScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    private GameContext gameContext;

    public AcceptLiveGameListener(GameContext gameContext, int liveGameId, AbstractScreen screen) {
        super(liveGameId, screen,
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1STATUS, LiveGameStatusEnum.WAITING_GAME_START.getStatus(), Integer.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.WAITING_GAME_START.getStatus(), Integer.class));
        this.gameContext = gameContext;
    }

    @Override
    protected void executeOperations() {
        liveGameActionsService.showChallengePlayerVersusPlayerScreen(gameContext, getEntityId(), getScreen());
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
