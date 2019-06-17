package libgdx.ui.model.transactions;

import libgdx.transactions.TransactionAmount;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.model.inventory.UserInventory;

public class TournamentTransactionAmount implements TransactionAmount {

    private int coins;
    private int diamonds;

    public TournamentTransactionAmount(int coins, int diamonds) {
        this.coins = coins;
        this.diamonds = diamonds;
    }

    public TournamentTransactionAmount(TransactionAmountEnum transactionAmountEnum) {
        this(transactionAmountEnum.getCoins(), transactionAmountEnum.getDiamond());
    }

    public TournamentTransactionAmount(UserInventory userInventory) {
        this(userInventory.getCoins(), userInventory.getDiamond());
    }

    public int getCoins() {
        return coins;
    }

    public int getDiamonds() {
        return diamonds;
    }

    public boolean isZeroAmount() {
        return coins == 0 && diamonds == 0;
    }
}
