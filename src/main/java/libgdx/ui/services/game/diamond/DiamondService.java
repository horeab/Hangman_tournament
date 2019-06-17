package libgdx.ui.services.game.diamond;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.DiamondEnum;
import libgdx.ui.constants.game.GameType;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.services.game.achievements.AchievementsService;

public class DiamondService {

    private ShopTransactionsDbApiService shopTransactionsDbApiService = new ShopTransactionsDbApiService();
    private UserInventoryDbApiService userInventoryDbApiService = new UserInventoryDbApiService();

    private int userId;

    public DiamondService(int userId) {
        this.userId = userId;
    }

    public int getTotalDiamonds() {
        return userInventoryDbApiService.getTotalDiamonds(userId);
    }

    public void winDiamond(String uniqueTransactionId) {
        new MyNotificationPopupCreator(new MyNotificationPopupConfigBuilder()
                .setText(TournamentGameLabel.achievement_diamond_win.getText())
                .setResource(DiamondEnum.DIAMOND.getResource())
                .setImageDimen(MainDimen.side_btn_image.getDimen() / 2).build())
                .shortNotificationPopup().addToPopupManager();
        shopTransactionsDbApiService.createShopTransaction(userId, DiamondEnum.DIAMOND.getWinTransaction(), uniqueTransactionId);
    }

    public void payDiamond(String uniqueTransactionId) {
        shopTransactionsDbApiService.createShopTransaction(userId, DiamondEnum.DIAMOND.getPayTransaction(), uniqueTransactionId);
    }

    public boolean diamondRewardIfGameWin(GameType gameType) {
        return gameType.getGameTypeDiamondConfiguration() != null && new AchievementsService().nextStepIsLevelUp(new GameTransactionsService(userId).getTotalWinTransactionsForGameTypeDiamondConfiguration(gameType), gameType.getGameTypeDiamondConfiguration().getStepsForDiamondReward());
    }

    public boolean diamondWon(GameType gameType) {
        return gameType.getGameTypeDiamondConfiguration() != null && new AchievementsService().currentStepIsLevelUp(new GameTransactionsService(userId).getTotalWinTransactionsForGameTypeDiamondConfiguration(gameType), gameType.getGameTypeDiamondConfiguration().getStepsForDiamondReward());
    }
}
