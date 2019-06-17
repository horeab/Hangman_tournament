package test.services.dbapi;

import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.constants.user.AccountCreationSource;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class UserGamesDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void createUserGame() {

        assertEquals(0, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "1");
        TestUtils.waitt();
        assertEquals(1, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));
    }

    @Test
    public void selectTotalGamesUser1AgainstUser2() {
        usersDbApiService.createUser("111", "hhh aaa", AccountCreationSource.GOOGLE);
        usersDbApiService.createUser("222", "hhh aaa", AccountCreationSource.FACEBOOK);

        assertEquals(0, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "1");
        TestUtils.waitt();
        assertEquals(1, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));

        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "2");
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "2");
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "2");
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "3");
        TestUtils.waitt();
        assertEquals(3, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));


        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "4");
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "5");
        userGamesDbApiService.createUserGame(randomUser.getId(), opponent.getId(), "6");
        userGamesDbApiService.createUserGame(opponent.getId(), randomUser.getId(), "7");
        TestUtils.waitt();
        assertEquals(6, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(opponent.getId(), randomUser.getId()));
        assertEquals(1, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(randomUser.getId(), opponent.getId()));
    }
}
