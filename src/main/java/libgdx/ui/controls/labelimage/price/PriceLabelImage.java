package libgdx.ui.controls.labelimage.price;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.labelimage.LabelImage;
import libgdx.controls.labelimage.LabelImageConfigBuilder;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilder;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.resources.FontManager;

public class PriceLabelImage {

    private GameTypeStage gameTypeStage;

    public PriceLabelImage(GameTypeStage gameTypeStage) {
        this.gameTypeStage = gameTypeStage;
    }

    public Table create() {
        Table table = new Table();
        if (gameTypeStage.getEntryFeeTransaction().getTransactionAmountEnum().isZeroAmount()) {
            table.add(new LabelImage(new LabelImageConfigBuilder().setSingleLineLabel().setText(TournamentGameLabel.resources_free.getText()).build()));
        } else {
            MyWrappedLabel label = new MyWrappedLabel(TournamentGameLabel.resources_entry_fee.getText());
            label.setFontScale(FontManager.getSmallFontDim());
            table.add(label).padRight(MainDimen.horizontal_general_margin.getDimen());
            table.add(new TournamentInventoryTableBuilder(new TournamentTransactionAmount(gameTypeStage.getEntryFeeTransaction().getTransactionAmountEnum())).buildTransactionTable());
        }

        return table;
    }
}
