package test.services.services;

import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.services.game.userexperience.UserExperienceService;

import static org.junit.Assert.assertEquals;

public class UserExperienceServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void getUserExperienceLevel() {
        UserExperienceService userExperienceService = new UserExperienceService();
        assertEquals(1, userExperienceService.getUserExperienceLevel(userInventoryDbApiService.getExperience(randomUser.getId())));

        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_PRACTICE_OPT_1, "1");
        assertEquals(1, userExperienceService.getUserExperienceLevel(userInventoryDbApiService.getExperience(randomUser.getId())));

        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_PRACTICE_OPT_1, "2");
        assertEquals(1, userExperienceService.getUserExperienceLevel(userInventoryDbApiService.getExperience(randomUser.getId())));

        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_PRACTICE_OPT_1, "3");
        assertEquals(2, userExperienceService.getUserExperienceLevel(userInventoryDbApiService.getExperience(randomUser.getId())));

        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_3, "3");
        assertEquals(3, userExperienceService.getUserExperienceLevel(userInventoryDbApiService.getExperience(randomUser.getId())));
    }
}
