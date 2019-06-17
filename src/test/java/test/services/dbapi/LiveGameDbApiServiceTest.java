package test.services.dbapi;

import org.junit.Test;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.util.TestDataCreator;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LiveGameDbApiServiceTest extends LiveGameTest {

    @Test
    public void createLiveGame() {
        liveGameDbApiService.createLiveGame(opponentId, currentUserId, new Question[]{TestDataCreator.QUESTION_0}, getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        assertTrue(liveGame.getId() >= 1);
        assertEquals(opponentId, liveGame.getUser1Id());
        assertEquals(currentUserId, liveGame.getUser2Id());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser1Status());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser2Status());
        assertNull(liveGame.getUser1GameInfo());
        assertNull(liveGame.getUser2GameInfo());
        assertEquals(Integer.valueOf(0), liveGame.getUser1GameInfoVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2GameInfoVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser1ActiveVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2ActiveVersion());
        assertEquals("{\"c\":{\"l\":[\"_3\"],\"c\":[\"cat5\"],\"a\":5},\"s\":\"challenge_3\",\"q\":\"1296844025000\"}", liveGame.getGameConfig());
    }

    @Test
    public void getLiveGame() {
        assertTrue(liveGame.getId() >= 1);
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser1Status());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser2Status());
        assertNull(liveGame.getUser1GameInfo());
        assertNull(liveGame.getUser2GameInfo());
        assertEquals(Integer.valueOf(0), liveGame.getUser1GameInfoVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2GameInfoVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser1ActiveVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2ActiveVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser1HasUser2GameInfoVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2HasUser1GameInfoVersion());
        assertEquals("{\"c\":{\"l\":[\"_3\"],\"c\":[\"cat5\"],\"a\":5},\"s\":\"challenge_3\",\"q\":\"1296844025000\"}", liveGame.getGameConfig());
    }

    @Test
    public void getColumnValue() {
        assertTrue(liveGameDbApiService.getColumnValue(liveGame.getId(), "id", Integer.class) >= 1);
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), "user1Status", Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), "user2Status", Integer.class));
        assertNull(liveGameDbApiService.getColumnValue(liveGame.getId(), "user1GameInfo", String.class));
        assertNull(liveGameDbApiService.getColumnValue(liveGame.getId(), "user2GameInfo", String.class));
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION, Integer.class));
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER2ACTIVEVERSION, Integer.class));
        assertEquals("{\"c\":{\"l\":[\"_3\"],\"c\":[\"cat5\"],\"a\":5},\"s\":\"challenge_3\",\"q\":\"1296844025000\"}", liveGameDbApiService.getColumnValue(liveGame.getId(), "gameConfig", String.class));
    }


    @Test
    public void updateColumnValue() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser2Status());

        liveGameDbApiService.updateColumnValue(liveGame.getId(), "user2Status", LiveGameStatusEnum.REJECT_MATCH.getStatus());
        liveGameDbApiService.updateColumnValue(liveGame.getId(), "user2GameInfo", "test");
        TestUtils.waitt();

        liveGame = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        assertEquals(LiveGameStatusEnum.REJECT_MATCH.getStatus(), liveGame.getUser2Status());
        assertEquals(LiveGameStatusEnum.REJECT_MATCH.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), "user2Status", Integer.class));
        assertEquals("test", liveGameDbApiService.getColumnValue(liveGame.getId(), "user2GameInfo", String.class));
    }

    @Test
    public void deleteEntity() {
        assertTrue(liveGameDbApiService.getColumnValue(liveGameId, "id", Integer.class) >= 1);
        liveGameDbApiService.deleteEntity(liveGameId);
        TestUtils.waitt();
        assertNull(liveGameDbApiService.getColumnValue(liveGameId, "id", Integer.class));

    }

}
