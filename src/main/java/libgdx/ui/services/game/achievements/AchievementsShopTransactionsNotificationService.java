package libgdx.ui.services.game.achievements;

import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;

public class AchievementsShopTransactionsNotificationService extends AchievementsNotificationService {

    private ShopTransactionsDbApiService shopTransactionsDbApiService = new ShopTransactionsDbApiService();
    private ShopTransactionTypeEnum shopTransactionTypeEnum;

    public AchievementsShopTransactionsNotificationService(int userId, ShopTransactionTypeEnum shopTransactionTypeEnum) {
        super(userId);
        this.shopTransactionTypeEnum = shopTransactionTypeEnum;
    }

    @Override
    AchievementEnum getAchievementEnum() {
        return AchievementEnum.getAchievementEnum(shopTransactionTypeEnum);
    }

    @Override
    int getTotalSteps() {
        return shopTransactionsDbApiService.selectTotalAmountShopTransactionType(userId, shopTransactionTypeEnum);
    }
}
