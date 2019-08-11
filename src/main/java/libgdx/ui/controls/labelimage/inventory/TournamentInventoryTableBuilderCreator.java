package libgdx.ui.controls.labelimage.inventory;

import com.badlogic.gdx.graphics.Color;
import libgdx.controls.labelimage.InventoryTableBuilder;
import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.resources.ResourcesManager;
import libgdx.ui.model.transactions.TournamentTransactionAmount;

public class TournamentInventoryTableBuilderCreator extends InventoryTableBuilderCreator<TournamentTransactionAmount> {

    @Override
    public InventoryTableBuilder create(TournamentTransactionAmount transactionAmount) {
        return new TournamentInventoryTableBuilder(transactionAmount)
                .setPositiveAmountPlusPrefix()
                .setRationFontImage(1.1f)
                .setCoinsFontColor(Color.GREEN)
                .setDiamondFontColor(Color.GREEN);
    }
}
