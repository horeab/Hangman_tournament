package libgdx.ui.controls.labelimage.inventory;

import libgdx.transactions.TransactionAmount;
import libgdx.controls.labelimage.InventoryTableBuilder;
import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.resources.ResourcesManager;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.model.transactions.TournamentTransactionAmount;

public class TournamentInventoryTableBuilderCreator extends InventoryTableBuilderCreator<TournamentTransactionAmount> {

    @Override
    public InventoryTableBuilder create(TournamentTransactionAmount transactionAmount) {
        return new TournamentInventoryTableBuilder(transactionAmount)
                .setPositiveAmountPlusPrefix()
                .setRationFontImage(1.1f)
                .setCoinsFontStyle(ResourcesManager.getLabelMoreDarkGreen())
                .setDiamondFontStyle(ResourcesManager.getLabelMoreDarkGreen());
    }
}
