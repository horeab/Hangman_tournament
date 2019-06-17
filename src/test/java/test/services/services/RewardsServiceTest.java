package test.services.services;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.model.shop.ShopTransaction;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardConfig;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;
import libgdx.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RewardsServiceTest extends TournamentGameTestDbApiService {

    private RewardsService rewardsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        rewardsService = new RewardsService(new RewardConfig(3, ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE));
    }

    @Test
    public void isValidGiveRewardForItem() {
        Mockito.when(preferencesService.getPreferences().getLong("dateLastRewardMade" + ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE.name(), 0)).thenReturn(DateUtils.minusDays(2).getTime());
        assertFalse(rewardsService.isValidGiveRewardForItem());

        Mockito.when(preferencesService.getPreferences().getLong("dateLastRewardMade" + ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE.name(), 0)).thenReturn(DateUtils.minusDays(4).getTime());
        assertTrue(rewardsService.isValidGiveRewardForItem());
    }

    @Test
    public void giveReward() {
        List<ShopTransaction> shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(randomUser.getId());
        assertEquals(1, shopTransactions.size());

//        mockDateTimeNow();
        rewardsService.giveReward(randomUser.getId(), "xx");

        verify(preferencesService, times(1)).putLong("dateLastRewardMade" + ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE.name(), DateUtils.getNowMillis());
        shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(randomUser.getId());
        ShopTransaction st = shopTransactions.get(1);
        assertEquals(2, shopTransactions.size());
        assertEquals(ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, shopTransactions.get(1).getTransactionType());
    }

}
