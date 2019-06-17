package test.services.listeners;


import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.listeners.ListenerValueChange;
import libgdx.ui.services.listeners.ListenerConfig;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class ListenerValueChangeTest extends LiveGameTest {

    @Test
    public void testWithDbValues() {
        final MutableInt timesOperationsExecuted = new MutableInt(0);
        ListenerValueChange listenerValueChange = createListener(timesOperationsExecuted);
        listenerValueChange.start();

        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        float waitMultiplier = 1.5f;
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo2",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(2), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo3",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(3), timesOperationsExecuted.getValue());
    }

    @Test
    public void testWithPreviousDbValues() {
        final MutableInt timesOperationsExecuted = new MutableInt(0);
        float waitMultiplier = 1.5f;

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo4",0);
        TestUtils.waitt();

        ListenerValueChange listenerValueChange = createListener(timesOperationsExecuted);
        listenerValueChange.setQueryPeriodMillis(listenerValueChange.getQueryPeriodMillis() / 3);
        listenerValueChange.start();

        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo2",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(2), timesOperationsExecuted.getValue());

        liveGameService.updateUser1GameInfo(liveGameId, "gameInfo3",0);
        TestUtils.waitt(Math.round(listenerValueChange.getQueryPeriodMillis() * waitMultiplier));
        assertEquals(Integer.valueOf(3), timesOperationsExecuted.getValue());
    }


    @Test
    public void testWithoutDbValues() {
        final MutableInt timesOperationsExecuted = new MutableInt(0);
        ListenerValueChange listenerValueChange = createListener(timesOperationsExecuted);
        listenerValueChange.setQueryPeriodMillis(listenerValueChange.getQueryPeriodMillis() / 3);
        listenerValueChange.start();


        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());

        TestUtils.waitt(listenerValueChange.getQueryPeriodMillis() * 5);

        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());
    }

    private ListenerValueChange createListener(MutableInt timesOperationsExecuted) {
        return new ListenerValueChange<AbstractScreen, LiveGameDbApiService>(liveGameId, (AbstractScreen) TournamentGame.getInstance().getScreen(),
                new ListenerConfig(LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class) {
                }) {
            @Override
            public void executeOperations(Object param) {
                timesOperationsExecuted.setValue(timesOperationsExecuted.getValue() + 1);
            }

            @Override
            protected LiveGameDbApiService createDbApiService() {
                return liveGameDbApiService;
            }
        };
    }


}
