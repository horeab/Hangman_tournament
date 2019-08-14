package libgdx.ui.controls.labelimage.inventory;

import libgdx.controls.labelimage.InventoryTableBuilder;
import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.utils.model.FontColor;

public class TournamentInventoryTableBuilderCreator extends InventoryTableBuilderCreator<TournamentTransactionAmount> {

    @Override
    public InventoryTableBuilder create(TournamentTransactionAmount transactionAmount) {
        return new TournamentInventoryTableBuilder(transactionAmount)
                .setPositiveAmountPlusPrefix()
                .setRationFontImage(1.1f)
                .setCoinsFontColor(FontColor.GREEN)
                .setDiamondFontColor(FontColor.GREEN);
    }
}
