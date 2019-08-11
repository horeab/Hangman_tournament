package libgdx.ui.controls.labelimage.prize;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.labelimage.InventoryLabelImageBuilder;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.services.dbapi.UniqueDbOperationContainer;
import libgdx.ui.services.game.diamond.DiamondService;

public class WinLevelUpPrizeLabelImage extends LevelUpPrizeLabelImage {

    private String uniqueId;

    public WinLevelUpPrizeLabelImage(int userId, GameTypeStage gameTypeStage, String uniqueId) {
        super(userId, gameTypeStage);
        this.uniqueId = uniqueId;
    }

    @Override
    public Table create() {
        //create the prize Table only if the Win transaction was actually executed
        if (UniqueDbOperationContainer.getInstance().isTransactionExecuted(getPrizeTransaction().name(), uniqueId)) {
            return super.create();
        } else {
            return new Table();
        }
    }

    @Override
    protected InventoryLabelImageBuilder getInventoryLabelImageBuilder(TransactionAmountEnum transactionAmountEnum) {
        return super.getInventoryLabelImageBuilder(transactionAmountEnum).setTextColor(MyWrappedLabelConfigBuilder.getScreenContrastStyle());
    }

    @Override
    boolean displayDiamondReward() {
        return new DiamondService(userId).diamondWon(gameTypeStage.getGameType());
    }
}
