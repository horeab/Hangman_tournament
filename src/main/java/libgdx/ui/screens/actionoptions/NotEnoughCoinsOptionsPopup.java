package libgdx.ui.screens.actionoptions;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import libgdx.controls.button.builders.BuyOfferButtonBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.game.external.BillingService;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.controls.button.NotEnoughCoinsButtonBuilder;
import libgdx.ui.controls.button.builders.WatchVideoAdButtonBuilder;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilder;
import libgdx.ui.controls.labelimage.price.PriceLabelImage;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.utils.ScreenDimensionsManager;

public class NotEnoughCoinsOptionsPopup extends MyPopup<AbstractScreen, ScreenManager> {

    private GameConfig gameConfig;
    private UserInventory userInventory;
    private TransactionAmountEnum notEnoughCoinsForTransaction;

    public NotEnoughCoinsOptionsPopup(AbstractScreen abstractScreen, GameConfig gameConfig, TransactionAmountEnum notEnoughCoinsForTransaction) {
        super(abstractScreen);
        this.gameConfig = gameConfig;
        this.notEnoughCoinsForTransaction = notEnoughCoinsForTransaction;
        userInventory = new UserInventoryDbApiService().getUserInventory(getScreen().getCurrentUser().getId());
    }

    @Override
    public void addButtons() {
        addButton(new WatchVideoAdButtonBuilder().build());
        BillingService billingService = TournamentGame.getInstance().getBillingService();
        if (!billingService.removeAdsAlreadyBought() && Gdx.app.getType() == Application.ApplicationType.Android) {
            addButton(BuyOfferButtonBuilder.removeAdsButton().build());
        }
        addButton(new NotEnoughCoinsButtonBuilder(userInventory, notEnoughCoinsForTransaction).build());
    }

    @Override
    protected void addText() {
        super.addText();
        getContentTable()
                .add(new PriceLabelImage(gameConfig.getGameTypeStage()).create())
                .width(getPrefWidth() - ScreenDimensionsManager.getScreenWidthValue(15)).padBottom(MainDimen.vertical_general_margin.getDimen() * 2).row();

        Color coinsFontColor = Color.BLACK;
        Color diamondFontColor = Color.BLACK;
        if (userInventory.getCoins() < Math.abs(notEnoughCoinsForTransaction.getCoins())) {
            coinsFontColor = Color.RED;
        }
        if (userInventory.getDiamond() < Math.abs(notEnoughCoinsForTransaction.getDiamond())) {
            diamondFontColor = Color.RED;
        }

        getContentTable()
                .add(new TournamentInventoryTableBuilder(new TournamentTransactionAmount(userInventory))
                        .setRationFontImage(1.1f)
                        .setCoinsFontColor(coinsFontColor)
                        .setDiamondFontColor(diamondFontColor)
                        .setNegativeAmountMinusPrefix()
                        .buildDetailed())
                .width(getPrefWidth() - ScreenDimensionsManager.getScreenWidthValue(5)).row();
        addEmptyRowWithMargin(getContentTable());
    }


    @Override
    protected String getLabelText() {
        return TournamentGameLabel.resources_not_enough.getText(TournamentGame.getInstance().getAppInfoService().getAppName());
    }
}
