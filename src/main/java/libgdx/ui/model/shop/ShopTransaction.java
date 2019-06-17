package libgdx.ui.model.shop;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;

public class ShopTransaction {

    private int coinsAmount;
    private int experienceGain;
    private ShopTransactionTypeEnum transactionType;
    private String entityCreationDate;

    public int getCoinsAmount() {
        return coinsAmount;
    }

    public ShopTransactionTypeEnum getTransactionType() {
        return transactionType;
    }

    public String getEntityCreationDate() {
        return entityCreationDate;
    }

    public int getExperienceGain() {
        return experienceGain;
    }

}
