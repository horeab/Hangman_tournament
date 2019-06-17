package libgdx.ui.services.listeners.livegame.beforegame;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.game.ChallengeGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueMetConfig;
import libgdx.ui.services.listeners.ListenerValueMet;

public class StartGameListener<TScreen extends AbstractScreen & ChallengeGameScreen> extends ListenerValueMet<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    public StartGameListener(int liveGameId, TScreen screen) {
        super(liveGameId, (TScreen) screen,
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1STATUS, LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), Integer.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), Integer.class));
    }

    @Override
    protected void executeOperations() {
        liveGameActionsService.usersAreLoaded(getScreen());
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
