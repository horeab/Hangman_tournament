package libgdx.ui.services.game;

import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeDiamondConfiguration;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.diamond.DiamondService;
import libgdx.ui.util.TournamentStage;

public class GameTransactionsService {

    private ShopTransactionsDbApiService shopTransactionsDbApiService = new ShopTransactionsDbApiService();
    private UserInventoryDbApiService userInventoryDbApiService = new UserInventoryDbApiService();

    private int currentUserId;
    private DiamondService diamondService;

    public GameTransactionsService(int currentUserId) {
        this.currentUserId = currentUserId;
        this.diamondService = new DiamondService(currentUserId);
    }

    public void tournamentWin(TournamentStage tournamentStage, GameConfig gameConfig) {
        if (tournamentStage == TournamentStage.STAGE_2) {
            gameWin(gameConfig);
        }
    }

    public void gameWin(GameConfig gameConfig) {
        if (gameConfig.getGameTypeDiamondConfiguration() != null && diamondService.diamondRewardIfGameWin(gameConfig.getGameTypeStage().getGameType())) {
            diamondService.winDiamond(gameConfig.getUniqueId());
        }
        createShopTransaction(gameConfig.getGameTypeStage().getPrizeTransaction(), gameConfig.getUniqueId(), gameConfig.getGameTypeStage().isUniqueTransaction());
    }

    public int getTotalWinTransactionsForGameTypeDiamondConfiguration(GameType gameType) {
        return shopTransactionsDbApiService.selectTotalAmountShopTransactionType(currentUserId, GameTypeStage.getWinTransactions(gameType));
    }

    public void gamePay(GameConfig gameConfig) {
        createShopTransaction(gameConfig.getGameTypeStage().getEntryFeeTransaction(), gameConfig.getUniqueId(), gameConfig.getGameTypeStage().isUniqueTransaction());
    }

    public void buyRemoveAds() {
        createShopTransaction(ShopTransactionTypeEnum.WIN_BUY_REMOVE_ADS, ShopTransactionTypeEnum.WIN_BUY_REMOVE_ADS.name(), true);
    }

    private void createShopTransaction(ShopTransactionTypeEnum transactionTypeEnum, String uniqueId, boolean uniqueTransaction) {
        if (isTransactionUniqueAndAlreadyExecuted(transactionTypeEnum, uniqueTransaction)) {
            return;
        }
        shopTransactionsDbApiService.createShopTransaction(currentUserId, transactionTypeEnum, uniqueId);
    }

    public boolean isTransactionUniqueAndAlreadyExecuted(ShopTransactionTypeEnum transactionTypeEnum, boolean uniqueTransaction) {
        return uniqueTransaction && shopTransactionsDbApiService.selectTotalAmountShopTransactionType(currentUserId, transactionTypeEnum) >= 1;
    }

    public boolean userHasEnoughResourcesToStartAction(TransactionAmountEnum entryFee) {
        UserInventory userInventory = userInventoryDbApiService.getUserInventory(currentUserId);

        boolean userHasEnoughResourcesToStartAction = userInventory.getCoins() - Math.abs(entryFee.getCoins()) >= 0;
        userHasEnoughResourcesToStartAction &= userInventory.getDiamond() - Math.abs(entryFee.getDiamond()) >= 0;

        return userHasEnoughResourcesToStartAction || entryFee.isZeroAmount();
    }
}
