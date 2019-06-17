package libgdx.ui.services.game;

import libgdx.transactions.TransactionAmount;
import libgdx.transactions.TransactionsService;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.model.transactions.TournamentTransactionAmount;

public class TournamentTransactionsService implements TransactionsService {

    @Override
    public TransactionAmount getBuyRemoveAdsTransactionAmount() {
        return new TournamentTransactionAmount(TransactionAmountEnum.WIN_BUY_REMOVE_ADS);
    }

}
