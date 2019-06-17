package test.services.services;

import org.junit.Before;
import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.screens.actionoptions.gamescreens.PracticeOptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.diamond.DiamondService;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.util.TournamentStage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTransactionsServiceTest extends TournamentGameTestDbApiService {

    private DiamondService diamondService;
    private GameTransactionsService gameTransactionsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        diamondService = new DiamondService(randomUser.getId());
        gameTransactionsService = new GameTransactionsService(randomUser.getId());
    }

    @Test
    public void gameWin() {
        GameTypeStage gameTypeStage = GameTypeStage.tournament_1;
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
        assertEquals(0, diamondService.getTotalDiamonds());
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), gameTypeStage, "x"));
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
        assertEquals(0, diamondService.getTotalDiamonds());
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), gameTypeStage, "xx"));
        assertEquals(2, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
        assertEquals(1, diamondService.getTotalDiamonds());
    }

    @Test
    public void tournamentWin() {
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), GameTypeStage.tournament_1.getPrizeTransaction()));
        assertEquals(0, diamondService.getTotalDiamonds());
        gameTransactionsService.tournamentWin(TournamentStage.STAGE_2, new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x"));
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), GameTypeStage.tournament_1.getPrizeTransaction()));
        assertEquals(0, diamondService.getTotalDiamonds());
        //method is called with Stage1, so no tournament win should be registered
        gameTransactionsService.tournamentWin(TournamentStage.STAGE_1, new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "xx"));
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), GameTypeStage.tournament_1.getPrizeTransaction()));
        assertEquals(0, diamondService.getTotalDiamonds());
        gameTransactionsService.tournamentWin(TournamentStage.STAGE_2, new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "xx2"));
        assertEquals(2, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), GameTypeStage.tournament_1.getPrizeTransaction()));
        assertEquals(1, diamondService.getTotalDiamonds());
    }

    @Test
    public void buyRemoveAds() {
        UserInventory userInventory = userInventoryDbApiService.getUserInventory(randomUser.getId());
        assertEquals(200, userInventory.getCoins());
        assertEquals(0, userInventory.getDiamond());
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.WIN_BUY_REMOVE_ADS));
        gameTransactionsService.buyRemoveAds();
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), ShopTransactionTypeEnum.WIN_BUY_REMOVE_ADS));
        userInventory = userInventoryDbApiService.getUserInventory(randomUser.getId());
        assertEquals(TransactionAmountEnum.WIN_BUY_REMOVE_ADS.getCoins() + 200, userInventory.getCoins());
        assertEquals(TransactionAmountEnum.WIN_BUY_REMOVE_ADS.getDiamond(), userInventory.getDiamond());
    }

    @Test
    public void gamePay() {
        GameTypeStage gameTypeStage = GameTypeStage.tournament_1;
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getEntryFeeTransaction()));
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), gameTypeStage, "x"));
        assertEquals(1, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getEntryFeeTransaction()));
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), gameTypeStage, "xx"));
        assertEquals(2, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getEntryFeeTransaction()));
        assertEquals(0, shopTransactionsDbApiService.selectTotalAmountShopTransactionType(randomUser.getId(), gameTypeStage.getPrizeTransaction()));
    }


    @Test
    public void userHasEnoughCoinsToStartActionWithCoins() {
        assertEquals(200, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
//        assertEquals(3, userInventoryDbApiService.getTotalDiamonds(randomUser.getId(), ));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_3, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));

        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_3, "x"));
        assertEquals(100, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
//        assertEquals(2, userInventoryDbApiService.getTotalDiamonds(randomUser.getId(), ));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_3, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));

        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_2, "xx"));
        assertEquals(50, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
//        assertEquals(1, userInventoryDbApiService.getTotalDiamonds(randomUser.getId(), ));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_3, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_2, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));

        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_2, "xxx"));
        assertEquals(0, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        assertEquals(0, userInventoryDbApiService.getTotalDiamonds(randomUser.getId()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_3, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_2, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.practice_2, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.practice_1, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
    }


    @Test
    public void userHasEnoughCoinsToStartActionWithDiamonds() {
        new DiamondService(randomUser.getId()).winDiamond("1");
        new DiamondService(randomUser.getId()).winDiamond("1");
        new DiamondService(randomUser.getId()).winDiamond("1");
        assertEquals(200, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        assertEquals(1, userInventoryDbApiService.getTotalDiamonds(randomUser.getId()));
        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.campaign_level_0, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));

        new DiamondService(randomUser.getId()).winDiamond("11");
        gameTransactionsService.gamePay(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.campaign_level_0, "x"));
        assertEquals(0, userInventoryDbApiService.getTotalCoins(randomUser.getId()));
        assertEquals(1, userInventoryDbApiService.getTotalDiamonds(randomUser.getId()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.campaign_level_0, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));

        assertTrue(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.practice_1, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
        assertFalse(gameTransactionsService.userHasEnoughResourcesToStartAction(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.practice_2, "x").getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()));
    }

}