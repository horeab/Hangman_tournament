package test.services.listeners;


import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.listeners.ListenerValueMetConfig;
import libgdx.ui.services.listeners.ListenerValueMet;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class ListenerValueMetTest extends LiveGameTest {

    @Test
    public void testWithCorrectExpectedValue() {
        test(123L, 1);
    }

    @Test
    public void testWithWrongExpectedValue() {
        test(1234L, 0);
    }

    private void test(long activeVersion, int expectedExecutions) {
        final MutableInt timesOperationsExecuted = new MutableInt(0);
        ListenerValueMet<AbstractScreen, LiveGameDbApiService> listener = createListener(timesOperationsExecuted);
        listener.start();

        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1", 1);
        liveGameService.updateUser2GameInfo(liveGameId, "gameInfo2", 1);
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION, activeVersion);
        TestUtils.waitt(listener.getQueryPeriodMillis() * 3);

        assertEquals(Integer.valueOf(expectedExecutions), timesOperationsExecuted.getValue());
    }

    private ListenerValueMet<AbstractScreen, LiveGameDbApiService> createListener(MutableInt timesOperationsExecuted) {
        return new ListenerValueMet<AbstractScreen, LiveGameDbApiService>(liveGameId, (AbstractScreen) TournamentGame.getInstance().getScreen(),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, "gameInfo1", String.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFO, "gameInfo2", String.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION, 123L, Long.class)
        ) {
            @Override
            public void executeOperations() {
                timesOperationsExecuted.setValue(timesOperationsExecuted.getValue() + 1);
            }

            @Override
            protected LiveGameDbApiService createDbApiService() {
                return liveGameDbApiService;
            }
        };
    }

}
