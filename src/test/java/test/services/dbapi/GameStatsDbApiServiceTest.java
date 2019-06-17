package test.services.dbapi;

import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.constants.user.AccountCreationSource;
import libgdx.ui.model.stats.GameStats;
import libgdx.ui.model.user.BaseUserInfo;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameStatsDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void createGameStats() {
        GameStats gameStats = gameStatsDbApiService.getGameStats(123);
        assertNull(gameStats);
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        TestUtils.waitt();
        gameStats = gameStatsDbApiService.getGameStats(usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK).getId());
        assertEquals(usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK), gameStats.getUserId());
        assertEquals(0, gameStats.getTournamentsStarted());
        assertEquals(0, gameStats.getTournamentsWon());
        assertEquals(0, gameStats.getQuestionsStarted());
        assertEquals(0, gameStats.getQuestionsWon());
    }

    @Test
    public void getGameStats() {
        GameStats gameStats = gameStatsDbApiService.getGameStats(123);
        assertNull(gameStats);
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        TestUtils.waitt();
        gameStats = gameStatsDbApiService.getGameStats(usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK).getId());
        assertEquals(usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK), gameStats.getUserId());
        assertEquals(0, gameStats.getTournamentsStarted());
        assertEquals(0, gameStats.getTournamentsWon());
        assertEquals(0, gameStats.getQuestionsStarted());
        assertEquals(0, gameStats.getQuestionsWon());
    }

    @Test
    public void incrementGameStatsTournamentsStarted() {
        usersDbApiService.createUser("3331", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3332", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3333", "hhh aaa", AccountCreationSource.FACEBOOK);
        BaseUserInfo user1 = usersDbApiService.getUser("3331", AccountCreationSource.FACEBOOK);
        BaseUserInfo user2 = usersDbApiService.getUser("3332", AccountCreationSource.FACEBOOK);
        BaseUserInfo user3 = usersDbApiService.getUser("3333", AccountCreationSource.FACEBOOK);

        TestUtils.waitt();
        assertEquals(this.currentUser.getId(), gameStatsDbApiService.getGameStats(currentUser.getId()).getUserId());
        assertEquals(0, gameStatsDbApiService.getGameStats(currentUser.getId()).getTournamentsStarted());

        gameStatsDbApiService.incrementGameStatsTournamentsStarted(this.currentUser.getId(), "1");
        TestUtils.waitt();
        GameStats gameStats = gameStatsDbApiService.getGameStats(currentUser.getId());
        assertEquals(this.currentUser.getId(), gameStats.getUserId());
        assertEquals(1, gameStats.getTournamentsStarted());
        assertEquals(0, gameStats.getTournamentsWon());
        assertEquals(0, gameStats.getQuestionsStarted());
        assertEquals(0, gameStats.getQuestionsWon());

        gameStatsDbApiService.incrementGameStatsTournamentsStarted(user1.getId(), "2");
        gameStatsDbApiService.incrementGameStatsTournamentsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsStarted(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "1");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "2");
        gameStatsDbApiService.incrementGameStatsQuestionsWon(user1.getId(), "3");
        TestUtils.waitt();
        gameStats = gameStatsDbApiService.getGameStats(user1.getId());
        assertEquals(user1.getId(), gameStats.getUserId());
        assertEquals(1, gameStats.getTournamentsStarted());
        assertEquals(1, gameStats.getTournamentsWon());
        assertEquals(1, gameStats.getQuestionsStarted());
        assertEquals(3, gameStats.getQuestionsWon());
    }

}
