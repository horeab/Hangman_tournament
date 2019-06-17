package test.services.services;

import com.google.gson.Gson;

import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.util.TestDataCreator;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LiveGameServiceTest extends LiveGameTest {

    @Test
    public void createLiveGame() {
        liveGameService.createLiveGame(randomUser.getId(), this.opponent.getId(), getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        assertTrue(liveGame.getId() >= 1);
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser1Status());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser2Status());
        assertNull(liveGame.getUser1GameInfo());
        assertNull(liveGame.getUser2GameInfo());
        assertEquals(Integer.valueOf(0), liveGame.getUser1ActiveVersion());
        assertEquals(Integer.valueOf(0), liveGame.getUser2ActiveVersion());
        assertEquals("{\"c\":{\"l\":[\"_3\"],\"c\":[\"cat5\"],\"a\":5},\"s\":\"challenge_3\",\"q\":\"1296844025000\"}", liveGame.getGameConfig());
    }

    @Test
    public void updateUserGameInfo() {
        assertNull(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, Integer.class));
        assertNull(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFO, String.class));
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, Integer.class));
        GameContext gameContext = createGameContext(TestDataCreator.QUESTION_0);
        gameContext.getCurrentUserGameUser().addAnswerToGameQuestionInfo("a", 1L);
        liveGameService.updateUser1GameInfo(liveGameId, new Gson().toJson(gameContext.getCurrentUserGameUser().getGameQuestionInfo()), 1);
        liveGameService.updateUser2GameInfo(liveGameId, "{\"xxx", 1);
        TestUtils.waitt();
        assertEquals("{\"question\":{\"l\":2,\"y\":\"_5\",\"t\":\"cat4\",\"s\":\"Gnocchi\"},\"status\":\"OPEN\",\"answers\":[{\"answer\":\"a\",\"millisAnswered\":1}]}", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(Integer.valueOf(1), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, Integer.class));
        assertEquals("{\"xxx", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFO, String.class));
        assertEquals(Integer.valueOf(1), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, Integer.class));
    }

    @Test
    public void getCurrentUserOpponentGameInfoVersion(){
        assertEquals(0, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[0]);
        assertEquals(0, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[1]);
    }

    @Test
    public void updateUserActiveChange() {
        liveGameService.updateUserActiveChange(liveGameId);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION, Integer.class));
        //randomUser is current user
        assertEquals(Integer.valueOf(1), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2ACTIVEVERSION, Integer.class));
    }

    @Test
    public void deleteLiveGame() {
        assertTrue(liveGameDbApiService.getColumnValue(liveGameId, "id", Integer.class) >= 1);
        liveGameService.deleteLiveGame(liveGameId);
        TestUtils.waitt();
        assertNull(liveGameDbApiService.getColumnValue(liveGameId, "id", Integer.class));
    }


    @Test
    public void startGameNotCreator() {
        new LiveGameService().createLiveGame(this.opponent.getId(), randomUser.getId(), getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame = liveGameDbApiService.getLiveGame(this.opponent.getId(), randomUser.getId());

        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        new LiveGameService().startGame(liveGame.getId());
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void startGameCreator() {
        new LiveGameService().createLiveGame(currentUser.getId(), this.opponent.getId(), getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame = liveGameDbApiService.getLiveGame(currentUser.getId(), this.opponent.getId());

        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        new LiveGameService().startGame(liveGame.getId());
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGame.getId(), LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void rejectChallenge() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        liveGameService.rejectChallenge(liveGameId);
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.REJECT_MATCH.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void acceptChallenge() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        liveGameService.acceptChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        verify(liveGameActionsService, times(1)).startChallengeWithCoins(liveGameInvite.getCreatorUser(), liveGameInvite.getQuestions(), liveGameInvite.getLiveGameId(), liveGameInvite.getGameConfig(), (AbstractScreen) TournamentGame.getInstance().getScreen());
    }

    @Test
    public void isUser1LoggedIn() {
        assertTrue(new LiveGameService().isUser1LoggedIn(new LiveGameService().createLiveGame(currentUser.getId(), this.opponent.getId(), getGameConfig()).getId()));
        assertFalse(new LiveGameService().isUser1LoggedIn(new LiveGameService().createLiveGame(this.opponent.getId(), currentUser.getId(), getGameConfig()).getId()));
        assertFalse(new LiveGameService().isUser1LoggedIn(1111));
    }

    @Test
    public void gameFinished() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        liveGameService.gameFinished(liveGameId);
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_FINISHED.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_FINISHED.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void wantRematch() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        liveGameService.wantRematch(liveGameId);
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.WANT_MATCH.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void rejectRematch() {
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        liveGameService.abandonMatch(liveGameId);
        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.REJECT_MATCH.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
    }

    @Test
    public void updateCurrentUserHasOpponentGameInfoVersion() {
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION, Integer.class));
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2HASUSER1GAMEINFOVERSION, Integer.class));
        liveGameService.updateCurrentUserHasOpponentGameInfoVersion(liveGameId, 4);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(0), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION, Integer.class));
        assertEquals(Integer.valueOf(4), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2HASUSER1GAMEINFOVERSION, Integer.class));
    }

    @Test
    public void getCurrentUserFields() {
        new LiveGameService().createLiveGame(this.opponent.getId(), currentUserId, getGameConfig());
        new LiveGameService().createLiveGame(currentUser.getId(), this.opponent.getId(), getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame1 = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        LiveGame liveGame2 = liveGameDbApiService.getLiveGame(currentUserId, opponentId);
        assertEquals("user2Status", new LiveGameService().getCurrentUserStatusField(liveGame1.getId()));
        assertEquals("user1Status", new LiveGameService().getCurrentUserStatusField(liveGame2.getId()));

    }

    @Test
    public void getOpponentFields() {
        new LiveGameService().createLiveGame(this.opponent.getId(), currentUserId, getGameConfig());
        new LiveGameService().createLiveGame(currentUser.getId(), this.opponent.getId(), getGameConfig());
        TestUtils.waitt();
        LiveGame liveGame1 = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        LiveGame liveGame2 = liveGameDbApiService.getLiveGame(currentUserId, opponentId);
        assertEquals("user1Status", new LiveGameService().getOpponentStatusField(liveGame1.getId()));
        assertEquals("user2Status", new LiveGameService().getOpponentStatusField(liveGame2.getId()));
        assertEquals("user1ActiveVersion", new LiveGameService().getOpponentActiveChangeField(liveGame1.getId()));
        assertEquals("user2ActiveVersion", new LiveGameService().getOpponentActiveChangeField(liveGame2.getId()));

    }

}
