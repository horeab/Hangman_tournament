package test.services.dbapi;

import org.junit.Test;

import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.model.shop.ShopTransaction;
import libgdx.ui.services.dbapi.UniqueDbOperationContainer;

import static org.junit.Assert.assertEquals;

public class ShopTransactionsDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void createShopTransaction() {
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2, "1");
        List<ShopTransaction> shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(randomUser.getId());
        assertEquals(2, shopTransactions.size());

        ShopTransaction st1 = shopTransactions.get(0);
        ShopTransaction st2 = shopTransactions.get(1);

        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER.getTransactionAmountEnum().getCoins(), st1.getCoinsAmount());
        assertDateTimeNow(st1.getEntityCreationDate());
        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER, st1.getTransactionType());

        assertEquals(ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2.getTransactionAmountEnum().getCoins(), st2.getCoinsAmount());
        assertDateTimeNow(st2.getEntityCreationDate());
        assertEquals(ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2, st2.getTransactionType());
        assertEquals(ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2.getExperienceGain(), st2.getExperienceGain());
    }

    @Test
    public void selectTotalAmountShopTransactionType() {
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId()));
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE));
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "1");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "2");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, "3");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3, "1");
        assertEquals(3, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE));
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3));
        assertEquals(4, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.WIN_BONUS_FACEBOOK_SHARE, ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3));
    }

    @Test
    public void selectShopTransactionsForUser() {
        UniqueDbOperationContainer.getInstance().clear();
        List<ShopTransaction> shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(randomUser.getId());
        assertEquals(1, shopTransactions.size());
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, "1");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, "1");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, "1");
        shopTransactionsDbApiService.createShopTransaction(randomUser.getId(), ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, "2");
        shopTransactions = shopTransactionsDbApiService.selectShopTransactionsForUser(randomUser.getId());
        assertEquals(3, shopTransactions.size());

        ShopTransaction st1 = shopTransactions.get(0);
        ShopTransaction st2 = shopTransactions.get(1);

        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER.getTransactionAmountEnum().getCoins(), st1.getCoinsAmount());
        assertDateTimeNow(st1.getEntityCreationDate());
        assertEquals(ShopTransactionTypeEnum.WIN_CREATE_USER, st1.getTransactionType());

        assertEquals(ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3.getTransactionAmountEnum().getCoins(), st2.getCoinsAmount());
        assertDateTimeNow(st2.getEntityCreationDate());
        assertEquals(ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, st2.getTransactionType());
    }

}
