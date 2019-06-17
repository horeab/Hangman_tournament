package libgdx.ui.controls.labelimage.prize;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.controls.labelimage.InventoryLabelImageBuilder;
import libgdx.controls.labelimage.LabelImage;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilder;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.resources.FontManager;

public abstract class LevelUpPrizeLabelImage {

    int userId;
    GameTypeStage gameTypeStage;
    private LabelImage coinsPrizeLabelImage;

    LevelUpPrizeLabelImage(int userId, GameTypeStage gameTypeStage) {
        this.userId = userId;
        this.gameTypeStage = gameTypeStage;
    }

    public Table create() {
        Table table = new Table();
        TransactionAmountEnum transactionAmountEnum = getPrizeTransaction();
        LabelImage coins = getInventoryLabelImageBuilder(transactionAmountEnum).build();
        coinsPrizeLabelImage = coins;
        table.add(coins);
        int diamond = transactionAmountEnum.getDiamond();
        if (displayDiamondReward()) {
            diamond++;
        }
        if (diamond > 0) {
            table.row();
            table.add(new TournamentInventoryTableBuilder(new TournamentTransactionAmount(0, diamond)).setPositiveAmountPlusPrefix().buildTransactionTable());
        }
        return table;
    }

    protected InventoryLabelImageBuilder getInventoryLabelImageBuilder(TransactionAmountEnum transactionAmountEnum) {
        return new InventoryLabelImageBuilder()
                .withFontScale(FontManager.getBigFontDim())
                .withFrontLabel(TournamentGameLabel.resources_prize)
                .setAmount(transactionAmountEnum.getCoins())
                .setResource(Resource.coins)
                .setPositiveAmountPlusPrefix(true);
    }

    TransactionAmountEnum getPrizeTransaction() {
        return gameTypeStage.getPrizeTransaction().getTransactionAmountEnum();
    }

    public LabelImage getCoinsPrizeLabelImage() {
        return coinsPrizeLabelImage;
    }

    abstract boolean displayDiamondReward();
}
