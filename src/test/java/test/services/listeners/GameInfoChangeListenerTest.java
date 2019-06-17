package test.services.listeners;


import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.listeners.livegame.ingame.GameInfoChangeListener;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class GameInfoChangeListenerTest extends LiveGameTest {

    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            TestUtils.waitt();
            final MutableInt user1GameInfoChange = new MutableInt(0);
            final MutableInt user2GameInfoChange = new MutableInt(0);
            GameInfoChangeListener<ChallengeGameScreen> gameInfoChangeListener = new GameInfoChangeListener<ChallengeGameScreen>
                    (liveGameId, liveGameService.isUser1LoggedIn(liveGameId), (ChallengeGameScreen)
                            TournamentGame.getInstance().getScreen()) {
                @Override
                public void executeUser1GameInfoOperations(String gameInfo, int gameInfoVersion) {
                    user1GameInfoChange.setValue(user1GameInfoChange.getValue() + 1);
                }

                @Override
                public void executeUser2GameInfoOperations(String gameInfo, int gameInfoVersion) {
                    user2GameInfoChange.setValue(user2GameInfoChange.getValue() + 1);
                }

            };

            gameInfoChangeListener.start();
            float waitMultiplier = 1.5f;
            TestUtils.waitt(Math.round(GameInfoChangeListener.DEFAULT_QUERY_PERIOD_MILLIS * waitMultiplier));
            assertEquals(Integer.valueOf(0), user1GameInfoChange.getValue());
            assertEquals(Integer.valueOf(0), user2GameInfoChange.getValue());

            liveGameService.updateUser1GameInfo(liveGameId, "gameInfo1", 1);
            TestUtils.waitt(Math.round(GameInfoChangeListener.DEFAULT_QUERY_PERIOD_MILLIS * waitMultiplier));
            assertEquals(Integer.valueOf(1), user1GameInfoChange.getValue());
            assertEquals(Integer.valueOf(0), user2GameInfoChange.getValue());
            assertEquals(Integer.valueOf(1), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, Integer.class));

            liveGameService.updateUser1GameInfo(liveGameId, "gameInfo11", 3);
            liveGameService.updateUser2GameInfo(liveGameId, "gameInfo2", 4);
            TestUtils.waitt(Math.round(GameInfoChangeListener.DEFAULT_QUERY_PERIOD_MILLIS * waitMultiplier));
            assertEquals(Integer.valueOf(2), user1GameInfoChange.getValue());
            assertEquals(Integer.valueOf(1), user2GameInfoChange.getValue());
            assertEquals(Integer.valueOf(3), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, Integer.class));
            assertEquals(Integer.valueOf(4), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, Integer.class));

            liveGameService.updateUser1GameInfo(liveGameId, "gameInfo12", 5);
            liveGameService.updateUser2GameInfo(liveGameId, "gameInfo22", 5);
            TestUtils.waitt(Math.round(GameInfoChangeListener.DEFAULT_QUERY_PERIOD_MILLIS * waitMultiplier));
            assertEquals(Integer.valueOf(3), user1GameInfoChange.getValue());
            assertEquals(Integer.valueOf(2), user2GameInfoChange.getValue());

            liveGameService.updateUser1GameInfo(liveGameId, "xxx", 0);
            TestUtils.waitt(Math.round(GameInfoChangeListener.DEFAULT_QUERY_PERIOD_MILLIS * waitMultiplier));
            assertEquals(Integer.valueOf(4), user1GameInfoChange.getValue());
        }
    }

}
