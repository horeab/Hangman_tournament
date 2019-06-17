package libgdx.ui.services.listeners.livegame.ingame;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueMetConfig;
import libgdx.ui.services.listeners.ListenerValueMet;

public class GameFinishedListener<TScreen extends AbstractGameScreen> extends ListenerValueMet<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    public GameFinishedListener(int liveGameId, TScreen screen) {
        super(liveGameId, screen,
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1STATUS, LiveGameStatusEnum.GAME_FINISHED.getStatus(), Integer.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.GAME_FINISHED.getStatus(), Integer.class));
    }

    @Override
    protected void executeOperations() {
        liveGameActionsService.executeBeforeLevelFinished(getScreen());
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
