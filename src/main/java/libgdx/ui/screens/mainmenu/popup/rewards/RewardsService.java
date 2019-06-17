package libgdx.ui.screens.mainmenu.popup.rewards;


import java.util.Date;

import libgdx.preferences.PreferencesService;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.utils.DateUtils;

public class RewardsService {

    private PreferencesService preferencesService;

    private RewardConfig rewardConfig;

    public RewardsService(RewardConfig rewardConfig) {
        this.rewardConfig = rewardConfig;
        preferencesService = new PreferencesService("RewardsService" + rewardConfig.getRewardName());
    }

    public boolean isValidGiveRewardForItem() {
        long dateShareWasMade = preferencesService.getPreferences().getLong(getDateLastRewardMadeKey(), 0);
//TODO  ---VALUE CHANGED--- Should not be true
//        return true;
        return new Date().after(DateUtils.plusDays(new Date(dateShareWasMade), rewardConfig.getDaysUntilNewReward())) || dateShareWasMade == 0;
    }


    public void giveReward(int userId, String uniqueTransactionId) {
        if (isValidGiveRewardForItem()) {
            preferencesService.putLong(getDateLastRewardMadeKey(), DateUtils.getNowMillis());
            new ShopTransactionsDbApiService().createShopTransaction(userId, rewardConfig.getRewardShopTransaction(), uniqueTransactionId);
        }
    }

    public RewardConfig getRewardConfig() {
        return rewardConfig;
    }

    private String getDateLastRewardMadeKey() {
        return "dateLastRewardMade" + rewardConfig.getRewardName();
    }
}
