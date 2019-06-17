package test.services.listeners;


import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.listeners.livegame.ingame.GameFinishedListener;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class GameFinishedListenerTest extends LiveGameTest {

    @Test
    public void testWithCorrectExpectedValue() {
        final MutableInt timesOperationsExecuted = new MutableInt(0);
        GameFinishedListener listener = new GameFinishedListener(liveGameId, (AbstractGameScreen) TournamentGame.getInstance().getScreen()) {
            @Override
            public void executeOperations() {
                timesOperationsExecuted.setValue(timesOperationsExecuted.intValue() + 1);
            }
        };
        listener.start();
        TestUtils.waitt(listener.getQueryPeriodMillis() * 3);

        assertEquals(Integer.valueOf(0), timesOperationsExecuted.getValue());

        liveGameService.gameFinished(liveGameId);
        TestUtils.waitt(listener.getQueryPeriodMillis() * 3);

        assertEquals(Integer.valueOf(1), timesOperationsExecuted.getValue());
    }

}
