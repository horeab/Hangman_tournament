package libgdx.ui.controls.labelimage.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.labelimage.InventoryTableBuilder;
import libgdx.controls.labelimage.LabelImage;
import libgdx.game.Game;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;

public class TournamentInventoryTableBuilder extends InventoryTableBuilder<TournamentTransactionAmount, TournamentInventoryTableBuilder> {

    private int coins;
    private int diamond;
    private Color coinsFontColor = Color.BLACK;
    private Color diamondFontColor = Color.BLACK;


    public TournamentInventoryTableBuilder(TournamentTransactionAmount transactionAmount) {
        super(transactionAmount);
        coins = transactionAmount.getCoins();
        diamond = transactionAmount.getDiamonds();
    }


    public TournamentInventoryTableBuilder setCoinsFontColor(Color fontColor) {
        this.coinsFontColor = fontColor;
        return this;
    }

    public TournamentInventoryTableBuilder setDiamondFontColor(Color fontColor) {
        this.diamondFontColor = fontColor;
        return this;
    }

    public Table buildDetailed() {
        Table table = new Table();
        MyWrappedLabel label = new MyWrappedLabel(TournamentGameLabel.resources_current_amount.getText());
        label.setTextColor(Color.RED);
        table.add(label).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
        table.add(buildHorizontalInventory());
        table.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_background_default));
        return table;
    }

    public Table buildHorizontalInventory() {
        Table table = new Table();
        LabelImage coinsInventoryLabelImage = createCoinsInventoryLabelImage();
        LabelImage diamondInventoryLabelImage = createDiamondInventoryLabelImage();
        table.add(coinsInventoryLabelImage).uniformX();
        table.add(diamondInventoryLabelImage).uniformX();
        return table;
    }

    @Override
    public Table buildTransactionTable() {
        Table table = new Table();
        boolean coinsHasValue = coins != 0;
        boolean diamondHasValue = diamond != 0;

        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        if (coinsHasValue) {
            addLabelImageToTable(table, verticalGeneralMarginDimen, createCoinsInventoryLabelImage());
        }
        if (diamondHasValue) {
            addLabelImageToTable(table, verticalGeneralMarginDimen, createDiamondInventoryLabelImage());
        }
        return table;
    }

    private void addLabelImageToTable(Table table, float verticalGeneralMarginDimen, LabelImage diamondWhiteInventoryLabelImage) {
        table.add(diamondWhiteInventoryLabelImage).padLeft(verticalGeneralMarginDimen / 4).padRight(verticalGeneralMarginDimen / 4).uniformX();
    }

    private LabelImage createCoinsInventoryLabelImage() {
        LabelImage labelImage = createInventoryLabelImageBuilder()
                .setResource(Resource.coins)
                .setAmount(coins)
                .setTextColor(coinsFontColor)
                .build();
        animateRedLabelInventory(coinsFontColor, labelImage);
        return labelImage;
    }

    private LabelImage createDiamondInventoryLabelImage() {
        LabelImage labelImage = createInventoryLabelImageBuilder()
                .setResource(Resource.diamond)
                .setAmount(diamond)
                .setTextColor(diamondFontColor)
                .build();
        animateRedLabelInventory(diamondFontColor, labelImage);
        return labelImage;
    }

    private void animateRedLabelInventory(Color fontColor, LabelImage labelImage) {
        if (fontColor.equals(Color.RED)) {
            new ActorAnimation(labelImage, Game.getInstance().getAbstractScreen()).animateFastFadeInFadeOutWithTotalFadeOut();
        }
    }
}