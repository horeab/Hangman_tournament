package libgdx.ui.constants.game;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.resources.Resource;

public enum  DiamondEnum {

    DIAMOND(Resource.diamond,Resource.levelup_fill_red, ShopTransactionTypeEnum.WIN_DIAMOND, ShopTransactionTypeEnum.PAY_DIAMOND),;

    private Resource resource;
    private Resource levelUpFillResource;
    private ShopTransactionTypeEnum winTransaction;
    private ShopTransactionTypeEnum payTransaction;

    DiamondEnum(Resource resource, Resource levelUpFillResource, ShopTransactionTypeEnum winTransaction, ShopTransactionTypeEnum payTransaction) {
        this.resource = resource;
        this.levelUpFillResource = levelUpFillResource;
        this.winTransaction = winTransaction;
        this.payTransaction = payTransaction;
    }

    public Resource getResource() {
        return resource;
    }

    public Resource getLevelUpFillResource() {
        return levelUpFillResource;
    }

    public ShopTransactionTypeEnum getWinTransaction() {
        return winTransaction;
    }

    public ShopTransactionTypeEnum getPayTransaction() {
        return payTransaction;
    }
}
