package test.services.dbapi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.ui.model.inventory.ExperienceWithUser;
import libgdx.game.model.BaseUserInfo;
import libgdx.dbapi.UniqueDbOperationContainer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserInventoryDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void getTotalCoins() {
        assertEquals(200, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3, "1");
        assertEquals(100, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "1");
        assertEquals(250, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "2");
        assertEquals(400, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "3");
        assertEquals(550, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
    }

    @Test
    public void getExperience() {
        assertEquals(0, userInventoryDbApiService.getExperience(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3, "1");
        assertEquals(2, userInventoryDbApiService.getExperience(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2, "1");
        assertEquals(14, userInventoryDbApiService.getExperience(randomUser.getId()));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "2");
        assertEquals(24, userInventoryDbApiService.getExperience(randomUser.getId()));
    }


    @Test
    public void selectLeaderboardExperience() {
        UniqueDbOperationContainer.getInstance().clear();
        List<ExperienceWithUser> experienceWithUsers = new ArrayList<>(userInventoryDbApiService.selectLeaderboardExperience(randomUser.getId()));
        assertEquals(3, experienceWithUsers.size());

        usersDbApiService.createUser("3331", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3332", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3333", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3334", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3335", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3336", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3337", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3338", "hhh aaa", AccountCreationSource.FACEBOOK);
        usersDbApiService.createUser("3339", "hhh aaa", AccountCreationSource.FACEBOOK);
        BaseUserInfo user1 = usersDbApiService.getUser("3331", AccountCreationSource.FACEBOOK);
        BaseUserInfo user2 = usersDbApiService.getUser("3332", AccountCreationSource.FACEBOOK);
        BaseUserInfo user3 = usersDbApiService.getUser("3333", AccountCreationSource.FACEBOOK);
        BaseUserInfo user4 = usersDbApiService.getUser("3334", AccountCreationSource.FACEBOOK);
        BaseUserInfo user5 = usersDbApiService.getUser("3335", AccountCreationSource.FACEBOOK);
        BaseUserInfo user6 = usersDbApiService.getUser("3336", AccountCreationSource.FACEBOOK);
        BaseUserInfo user7 = usersDbApiService.getUser("3337", AccountCreationSource.FACEBOOK);
        BaseUserInfo user8 = usersDbApiService.getUser("3338", AccountCreationSource.FACEBOOK);
        BaseUserInfo user9 = usersDbApiService.getUser("3339", AccountCreationSource.FACEBOOK);

        experienceWithUsers = new ArrayList<>(userInventoryDbApiService.selectLeaderboardExperience(user9.getId()));
        assertEquals(6, experienceWithUsers.size());

        assertEquals(0, experienceWithUsers.get(0).getExperience());
        assertEquals(0, experienceWithUsers.get(1).getExperience());
        assertEquals(0, experienceWithUsers.get(2).getExperience());
        assertEquals(0, experienceWithUsers.get(3).getExperience());
        assertEquals(0, experienceWithUsers.get(4).getExperience());
        assertEquals(0, experienceWithUsers.get(5).getExperience());

        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "1");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "1");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "2");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "3");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "4");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "5");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "6");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "7");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "8");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "9");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "9");
        shopTransactionsDbApiService.createShopTransaction(user6.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "9");

        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "1");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "2");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "3");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "4");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "5");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "5");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "5");
        shopTransactionsDbApiService.createShopTransaction(user2.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "6");

        shopTransactionsDbApiService.createShopTransaction(user8.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "1");
        shopTransactionsDbApiService.createShopTransaction(user8.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "2");
        shopTransactionsDbApiService.createShopTransaction(user8.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "3");

        shopTransactionsDbApiService.createShopTransaction(user3.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "1");
        shopTransactionsDbApiService.createShopTransaction(user3.getId(), ShopTransactionTypeEnum.WIN_LEVEL_0, "2");

        experienceWithUsers = new ArrayList<>(userInventoryDbApiService.selectLeaderboardExperience(user9.getId()));
        assertEquals(6, experienceWithUsers.size());

        assertEquals(90, experienceWithUsers.get(0).getExperience());
        assertEquals(user6.getId(), experienceWithUsers.get(0).getUser().getId());
        assertEquals(1, experienceWithUsers.get(0).getRank());

        assertEquals(60, experienceWithUsers.get(1).getExperience());
        assertEquals(user2.getId(), experienceWithUsers.get(1).getUser().getId());
        assertEquals(2, experienceWithUsers.get(1).getRank());

        assertEquals(30, experienceWithUsers.get(2).getExperience());
        assertEquals(user8.getId(), experienceWithUsers.get(2).getUser().getId());
        assertEquals(3, experienceWithUsers.get(2).getRank());

        assertEquals(0, experienceWithUsers.get(3).getExperience());
        assertEquals(user5.getId(), experienceWithUsers.get(3).getUser().getId());
        assertEquals(10, experienceWithUsers.get(3).getRank());

        assertEquals(0, experienceWithUsers.get(4).getExperience());
        assertEquals(user7.getId(), experienceWithUsers.get(4).getUser().getId());
        assertEquals(11, experienceWithUsers.get(4).getRank());

        assertEquals(0, experienceWithUsers.get(5).getExperience());
        assertEquals(user9.getId(), experienceWithUsers.get(5).getUser().getId());
        assertEquals(12, experienceWithUsers.get(5).getRank());

    }
}
