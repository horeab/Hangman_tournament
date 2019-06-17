package libgdx.ui.services.listeners.livegame.ingame;

import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.game.ChallengeGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerAll;
import libgdx.ui.services.listeners.ListenerConfig;
import libgdx.ui.services.listeners.ListenerValueChange;

public class GameInfoChangeListener<TScreen extends AbstractScreen & ChallengeGameScreen> implements ListenerAll {

    public static int DEFAULT_QUERY_PERIOD_MILLIS = 1000;

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();
    private LiveGameDbApiService liveGameDbApiService = new LiveGameDbApiService();

    private ListenerValueChange listenerValueChangeUser1;
    private ListenerValueChange listenerValueChangeUser2;
    private TScreen screen;
    private boolean isUser1LoggedIn;

    public GameInfoChangeListener(final int liveGameId, boolean isUser1LoggedIn, TScreen screen) {
        this.screen = screen;
        this.isUser1LoggedIn = isUser1LoggedIn;
        listenerValueChangeUser1 = new ListenerValueChange<TScreen, LiveGameDbApiService>(liveGameId,
                screen, new ListenerConfig(LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, Integer.class)) {
            @Override
            public void executeOperations(Object param) {
                executeUser1GameInfoOperations(getDbApiService().getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), (Integer) param);
            }

            @Override
            protected LiveGameDbApiService createDbApiService() {
                return liveGameDbApiService;
            }
        };

        listenerValueChangeUser2 = new ListenerValueChange<TScreen, LiveGameDbApiService>(liveGameId,
                screen, new ListenerConfig(LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, Integer.class)) {
            @Override
            public void executeOperations(Object param) {
                executeUser2GameInfoOperations(getDbApiService().getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFO, String.class), (Integer) param);
            }

            @Override
            protected LiveGameDbApiService createDbApiService() {
                return liveGameDbApiService;
            }
        };
        listenerValueChangeUser1.setQueryPeriodMillis(DEFAULT_QUERY_PERIOD_MILLIS);
        listenerValueChangeUser2.setQueryPeriodMillis(DEFAULT_QUERY_PERIOD_MILLIS);
    }

    @Override
    public void start() {
        listenerValueChangeUser1.start();
        listenerValueChangeUser2.start();
    }

    public void executeUser1GameInfoOperations(String gameInfo, int gameInfoVersion) {
        if (isUser1LoggedIn) {
            liveGameActionsService.currentUserPressedAnswer(gameInfo, gameInfoVersion, screen);
        } else {
            liveGameActionsService.opponentPressedAnswer(gameInfo, gameInfoVersion, screen);
        }
    }

    public void executeUser2GameInfoOperations(String gameInfo, int gameInfoVersion) {
        if (isUser1LoggedIn) {
            liveGameActionsService.opponentPressedAnswer(gameInfo, gameInfoVersion, screen);
        } else {
            liveGameActionsService.currentUserPressedAnswer(gameInfo, gameInfoVersion, screen);
        }
    }

}
