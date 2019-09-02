package test.services.dbapi;

import org.junit.Test;

import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.model.shop.ShopTransaction;
import libgdx.ui.model.stats.GameStats;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.actionoptions.gamescreens.PracticeOptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.util.TestDataCreator;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UsersDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void createUser() {
        assertNull(usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK));
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        BaseUserInfo loadedUser = usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK);
        assertTrue(loadedUser.getId() > 0);
        assertEquals("333", loadedUser.getExternalId());
        assertEquals("hhh aaa", loadedUser.getFullName());
        assertEquals(AccountCreationSource.FACEBOOK, loadedUser.getAccountCreationSource());
        assertEquals(loadedUser.getExternalId(), loadedUser.getExternalId());
        assertEquals(loadedUser.getAccountCreationSource(), loadedUser.getAccountCreationSource());

        TestUtils.waitt();
        List<ShopTransaction> shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(loadedUser.getId());
        assertEquals(1, shopTransactions.size());
        ShopTransaction st = shopTransactions.get(0);
        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER.getTransactionAmountEnum().getCoins(), st.getCoinsAmount());
        assertDateTimeNow(st.getEntityCreationDate());
        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER, st.getTransactionType());

        UserInventory userInventory = userInventoryDbApiService.getUserInventory(loadedUser.getId());
        assertEquals(200, userInventory.getCoins());
        assertEquals(0, userInventory.getDiamond());
        assertEquals(loadedUser.getId(), userInventory.getUserId());

        GameStats gameStats = gameStatsDbApiService.getGameStats(loadedUser.getId());
        assertEquals(0, gameStats.getTournamentsWon());
    }

    @Test
    public void deleteEntity() {
        assertNull(usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK));
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        BaseUserInfo loadedUser = usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK);
        assertNotNull(loadedUser);
        TestUtils.waitt();
        assertEquals(1, shopTransactionsDbApiService.selectShopTransactionsForUser(loadedUser.getId()).size());
        assertNotNull(gameStatsDbApiService.getGameStats(loadedUser.getId()));
        liveGameDbApiService.createLiveGame(loadedUser.getId(), randomUser.getId(), new Question[]{TestDataCreator.QUESTION_0}, new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.challenge_2, "x"));
        assertNotNull(liveGameDbApiService.getLiveGame(loadedUser.getId(), randomUser.getId()));
        userGamesDbApiService.createUserGame(loadedUser.getId(), randomUser.getId(), "x");
        TestUtils.waitt();
        assertEquals(1, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(loadedUser.getId(), randomUser.getId()));

        usersDbApiService.deleteEntity(loadedUser.getId());

        assertNull(usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK));
        assertEquals(0, shopTransactionsDbApiService.selectShopTransactionsForUser(loadedUser.getId()).size());
        assertNull(gameStatsDbApiService.getGameStats(loadedUser.getId()));
        assertNull(liveGameDbApiService.getLiveGame(loadedUser.getId(), randomUser.getId()));
        assertEquals(0, userGamesDbApiService.selectTotalGamesUser1AgainstUser2(loadedUser.getId(), randomUser.getId()));
    }

    @Test
    public void getUser() {
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        BaseUserInfo loadedUser = usersDbApiService.getUser("333", AccountCreationSource.FACEBOOK);
        assertEquals("333", loadedUser.getExternalId());
        assertEquals("hhh aaa", loadedUser.getFullName());
        assertEquals(AccountCreationSource.FACEBOOK, loadedUser.getAccountCreationSource());

        BaseUserInfo loadedUser2 = usersDbApiService.getUser(opponent.getId());
        assertEquals("333111", loadedUser2.getExternalId());
        assertEquals("hhh aaa", loadedUser2.getFullName());
        assertEquals(AccountCreationSource.GOOGLE, loadedUser2.getAccountCreationSource());
    }

    @Test
    public void getUserId() {
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        int userId = usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK);
        assertTrue(userId > 0);
    }

    @Test
    public void updateLastTimeActiveDateForUser() {
        usersDbApiService.createUser("333", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.updateLastTimeActiveDateForUser(usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK));
        int userId = usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK);
        assertTrue(userId > 0);
    }

    @Test
    public void selectUsersForTournament() {
        List<BaseUserInfo> users = usersDbApiService.selectUsersForTournament();
        assertEquals(3, users.size());
        for (int i = 0; i < 15; i++) {
            usersDbApiService.createUser("333" + i, "hhh aaa", AccountCreationSource.FACEBOOK);
        }
        TestUtils.waitt();
        TestUtils.waitt();
        users = usersDbApiService.selectUsersForTournament();
        for (BaseUserInfo userInfo : users) {
            assertTrue(userInfo.getAccountCreationSource() == AccountCreationSource.FACEBOOK || userInfo.getAccountCreationSource() == AccountCreationSource.GOOGLE);
        }
        assertEquals(8, users.size());
        // check that users are random
        assertFalse(Integer.valueOf(users.get(0).getExternalId()).equals(Integer.valueOf(Integer.valueOf(users.get(1).getExternalId()) - 1))
                && Integer.valueOf(users.get(1).getExternalId()).equals(Integer.valueOf(Integer.valueOf(users.get(2).getExternalId()) - 1))
                && Integer.valueOf(users.get(2).getExternalId()).equals(Integer.valueOf(Integer.valueOf(users.get(3).getExternalId()) - 1))
                && Integer.valueOf(users.get(3).getExternalId()).equals(Integer.valueOf(Integer.valueOf(users.get(4).getExternalId()) - 1)));
    }

    @Test
    public void selectUserNameContainsText() {
        List<BaseUserInfo> users = usersDbApiService.selectUserNameContainsText("");
        assertEquals(3, users.size());

        usersDbApiService.createUser("1", "AAA abcde", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("2", "AAA fghjk", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3", "aa baeer", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("4", "AAA eee", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("5", "aa bbb", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("6", "aaa ggg", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("7", "aaa rrr", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("8", "aaa ccc", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("9", "aaa fff", AccountCreationSource.FACEBOOK);

        users = usersDbApiService.selectUserNameContainsText("");
        assertEquals(5, users.size());

        users = usersDbApiService.selectUserNameContainsText("aa");
        assertEquals(5, users.size());

        users = usersDbApiService.selectUserNameContainsText("f");
        assertEquals(2, users.size());
    }

    @Test
    public void getLastTimeActiveDateForUser() {
        assertNull(usersDbApiService.getLastTimeActiveDateForUser(usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK)));
        usersDbApiService.createUser("333", "aaa fff", AccountCreationSource.FACEBOOK);
        assertDateTimeNow(usersDbApiService.getLastTimeActiveDateForUser(usersDbApiService.getUserId("333", AccountCreationSource.FACEBOOK)).getTime());
    }

}
