package libgdx.ui.services.game;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.achievements.AchievementsShopTransactionsNotificationService;
import libgdx.ui.services.game.userexperience.UserExperienceChangeNotificationService;

public class ShopTransactionNotificationService {

    private UserExperienceChangeNotificationService userExperienceChangeNotification = new UserExperienceChangeNotificationService();
    private UserInventoryDbApiService userInventoryDbApiService = new UserInventoryDbApiService();
    private int userId;
    private ShopTransactionTypeEnum shopTransactionTypeEnum;

    public ShopTransactionNotificationService(int userId, ShopTransactionTypeEnum shopTransactionTypeEnum) {
        this.userId = userId;
        this.shopTransactionTypeEnum = shopTransactionTypeEnum;
    }

    public void beforeShopTransaction() {
        new AchievementsShopTransactionsNotificationService(userId, shopTransactionTypeEnum).processDisplayNotification();
        userExperienceChangeNotification.beforeExperienceChange(userInventoryDbApiService.getExperience(userId));
    }

    public void afterShopTransaction() {
        userExperienceChangeNotification.afterExperienceChange(userInventoryDbApiService.getExperience(userId));
    }
}
