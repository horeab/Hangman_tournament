package libgdx.ui.screens.mainmenu.popup.rewards;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;

public class RewardConfig {

    private int daysUntilNewReward;
    private ShopTransactionTypeEnum rewardShopTransaction;

    public RewardConfig(int daysUntilNewReward, ShopTransactionTypeEnum rewardShopTransaction) {
        this.daysUntilNewReward = daysUntilNewReward;
        this.rewardShopTransaction = rewardShopTransaction;
    }

    public int getDaysUntilNewReward() {
        return daysUntilNewReward;
    }

    public ShopTransactionTypeEnum getRewardShopTransaction() {
        return rewardShopTransaction;
    }

    public String getRewardName() {
        return rewardShopTransaction.name();
    }
}
