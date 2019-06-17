package libgdx.ui.constants.game;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.resources.TournamentGameLabel;

public enum GameTypeStage {

    tournament_1(ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_1, ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_1, false, GameType.TOURNAMENT),
    tournament_2(ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_2, ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_2, false, GameType.TOURNAMENT),
    tournament_3(ShopTransactionTypeEnum.PAY_TOURNAMENT_OPT_3, ShopTransactionTypeEnum.WIN_TOURNAMENT_OPT_3, false, GameType.TOURNAMENT),

    practice_1(ShopTransactionTypeEnum.PAY_PRACTICE_OPT_1, ShopTransactionTypeEnum.WIN_PRACTICE_OPT_1, false, GameType.PRACTICE),
    practice_2(ShopTransactionTypeEnum.PAY_PRACTICE_OPT_2, ShopTransactionTypeEnum.WIN_PRACTICE_OPT_2, false, GameType.PRACTICE),
    practice_3(ShopTransactionTypeEnum.PAY_PRACTICE_OPT_3, ShopTransactionTypeEnum.WIN_PRACTICE_OPT_3, false, GameType.PRACTICE),

    challenge_1(ShopTransactionTypeEnum.PAY_CHALLENGE_OPT_1, ShopTransactionTypeEnum.WIN_CHALLENGE_OPT_1, false, GameType.CHALLENGE),
    challenge_2(ShopTransactionTypeEnum.PAY_CHALLENGE_OPT_2, ShopTransactionTypeEnum.WIN_CHALLENGE_OPT_2, false, GameType.CHALLENGE),
    challenge_3(ShopTransactionTypeEnum.PAY_CHALLENGE_OPT_3, ShopTransactionTypeEnum.WIN_CHALLENGE_OPT_3, false, GameType.CHALLENGE),

    campaign_level_0_0(ShopTransactionTypeEnum.PAY_LEVEL_0_0, ShopTransactionTypeEnum.WIN_LEVEL_0_0, true, GameType.CAMPAIGN),
    campaign_level_0_1(ShopTransactionTypeEnum.PAY_LEVEL_0_1, ShopTransactionTypeEnum.WIN_LEVEL_0_1, true, GameType.CAMPAIGN),
    campaign_level_0_2(ShopTransactionTypeEnum.PAY_LEVEL_0_2, ShopTransactionTypeEnum.WIN_LEVEL_0_2, true, GameType.CAMPAIGN),
    campaign_level_0_3(ShopTransactionTypeEnum.PAY_LEVEL_0_3, ShopTransactionTypeEnum.WIN_LEVEL_0_3, true, GameType.CAMPAIGN),
    campaign_level_0_4(ShopTransactionTypeEnum.PAY_LEVEL_0_4, ShopTransactionTypeEnum.WIN_LEVEL_0_4, true, GameType.CAMPAIGN),
    campaign_level_0(ShopTransactionTypeEnum.PAY_LEVEL_0, ShopTransactionTypeEnum.WIN_LEVEL_0, true, GameType.CAMPAIGN),
    ////
    campaign_level_1_0(ShopTransactionTypeEnum.PAY_LEVEL_1_0, ShopTransactionTypeEnum.WIN_LEVEL_1_0, true, GameType.CAMPAIGN),
    campaign_level_1_1(ShopTransactionTypeEnum.PAY_LEVEL_1_1, ShopTransactionTypeEnum.WIN_LEVEL_1_1, true, GameType.CAMPAIGN),
    campaign_level_1_2(ShopTransactionTypeEnum.PAY_LEVEL_1_2, ShopTransactionTypeEnum.WIN_LEVEL_1_2, true, GameType.CAMPAIGN),
    campaign_level_1_3(ShopTransactionTypeEnum.PAY_LEVEL_1_3, ShopTransactionTypeEnum.WIN_LEVEL_1_3, true, GameType.CAMPAIGN),
    campaign_level_1_4(ShopTransactionTypeEnum.PAY_LEVEL_1_4, ShopTransactionTypeEnum.WIN_LEVEL_1_4, true, GameType.CAMPAIGN),
    campaign_level_1(ShopTransactionTypeEnum.PAY_LEVEL_1, ShopTransactionTypeEnum.WIN_LEVEL_1, true, GameType.CAMPAIGN),
    ////
    campaign_level_2_0(ShopTransactionTypeEnum.PAY_LEVEL_2_0, ShopTransactionTypeEnum.WIN_LEVEL_2_0, true, GameType.CAMPAIGN),
    campaign_level_2_1(ShopTransactionTypeEnum.PAY_LEVEL_2_1, ShopTransactionTypeEnum.WIN_LEVEL_2_1, true, GameType.CAMPAIGN),
    campaign_level_2_2(ShopTransactionTypeEnum.PAY_LEVEL_2_2, ShopTransactionTypeEnum.WIN_LEVEL_2_2, true, GameType.CAMPAIGN),
    campaign_level_2_3(ShopTransactionTypeEnum.PAY_LEVEL_2_3, ShopTransactionTypeEnum.WIN_LEVEL_2_3, true, GameType.CAMPAIGN),
    campaign_level_2_4(ShopTransactionTypeEnum.PAY_LEVEL_2_4, ShopTransactionTypeEnum.WIN_LEVEL_2_4, true, GameType.CAMPAIGN),
    campaign_level_2(ShopTransactionTypeEnum.PAY_LEVEL_2, ShopTransactionTypeEnum.WIN_LEVEL_2, true, GameType.CAMPAIGN),
    ////
    campaign_level_3_0(ShopTransactionTypeEnum.PAY_LEVEL_3_0, ShopTransactionTypeEnum.WIN_LEVEL_3_0, true, GameType.CAMPAIGN),
    campaign_level_3_1(ShopTransactionTypeEnum.PAY_LEVEL_3_1, ShopTransactionTypeEnum.WIN_LEVEL_3_1, true, GameType.CAMPAIGN),
    campaign_level_3_2(ShopTransactionTypeEnum.PAY_LEVEL_3_2, ShopTransactionTypeEnum.WIN_LEVEL_3_2, true, GameType.CAMPAIGN),
    campaign_level_3_3(ShopTransactionTypeEnum.PAY_LEVEL_3_3, ShopTransactionTypeEnum.WIN_LEVEL_3_3, true, GameType.CAMPAIGN),
    campaign_level_3_4(ShopTransactionTypeEnum.PAY_LEVEL_3_4, ShopTransactionTypeEnum.WIN_LEVEL_3_4, true, GameType.CAMPAIGN),
    campaign_level_3(ShopTransactionTypeEnum.PAY_LEVEL_3, ShopTransactionTypeEnum.WIN_LEVEL_3, true, GameType.CAMPAIGN),
    ////
    campaign_level_4_0(ShopTransactionTypeEnum.PAY_LEVEL_4_0, ShopTransactionTypeEnum.WIN_LEVEL_4_0, true, GameType.CAMPAIGN),
    campaign_level_4_1(ShopTransactionTypeEnum.PAY_LEVEL_4_1, ShopTransactionTypeEnum.WIN_LEVEL_4_1, true, GameType.CAMPAIGN),
    campaign_level_4_2(ShopTransactionTypeEnum.PAY_LEVEL_4_2, ShopTransactionTypeEnum.WIN_LEVEL_4_2, true, GameType.CAMPAIGN),
    campaign_level_4_3(ShopTransactionTypeEnum.PAY_LEVEL_4_3, ShopTransactionTypeEnum.WIN_LEVEL_4_3, true, GameType.CAMPAIGN),
    campaign_level_4_4(ShopTransactionTypeEnum.PAY_LEVEL_4_4, ShopTransactionTypeEnum.WIN_LEVEL_4_4, true, GameType.CAMPAIGN),
    campaign_level_4(ShopTransactionTypeEnum.PAY_LEVEL_4, ShopTransactionTypeEnum.WIN_LEVEL_4, true, GameType.CAMPAIGN),
    ////
    campaign_level_5_0(ShopTransactionTypeEnum.PAY_LEVEL_5_0, ShopTransactionTypeEnum.WIN_LEVEL_5_0, true, GameType.CAMPAIGN),
    campaign_level_5_1(ShopTransactionTypeEnum.PAY_LEVEL_5_1, ShopTransactionTypeEnum.WIN_LEVEL_5_1, true, GameType.CAMPAIGN),
    campaign_level_5_2(ShopTransactionTypeEnum.PAY_LEVEL_5_2, ShopTransactionTypeEnum.WIN_LEVEL_5_2, true, GameType.CAMPAIGN),
    campaign_level_5_3(ShopTransactionTypeEnum.PAY_LEVEL_5_3, ShopTransactionTypeEnum.WIN_LEVEL_5_3, true, GameType.CAMPAIGN),
    campaign_level_5_4(ShopTransactionTypeEnum.PAY_LEVEL_5_4, ShopTransactionTypeEnum.WIN_LEVEL_5_4, true, GameType.CAMPAIGN),
    campaign_level_5(ShopTransactionTypeEnum.PAY_LEVEL_5, ShopTransactionTypeEnum.WIN_LEVEL_5, true, GameType.CAMPAIGN),;

    private ShopTransactionTypeEnum entryFeeTransaction;
    private ShopTransactionTypeEnum prizeTransaction;
    private boolean uniqueTransaction;
    private GameType gameType;

    GameTypeStage(ShopTransactionTypeEnum entryFeeTransaction, ShopTransactionTypeEnum prizeTransaction, boolean uniqueTransaction, GameType gameType) {
        this.entryFeeTransaction = entryFeeTransaction;
        this.prizeTransaction = prizeTransaction;
        this.uniqueTransaction = uniqueTransaction;
        this.gameType = gameType;
    }

    public GameType getGameType() {
        return gameType;
    }

    public boolean isUniqueTransaction() {
        return uniqueTransaction;
    }

    public ShopTransactionTypeEnum getEntryFeeTransaction() {
        return entryFeeTransaction;
    }

    public ShopTransactionTypeEnum getPrizeTransaction() {
        return prizeTransaction;
    }

    public String getGameTypeLabelText() {
        return TournamentGameLabel.getByName(name()).getText();
    }

    public static List<GameTypeStage> getGameTypeStages(GameType gameType) {
        List<GameTypeStage> stages = new ArrayList<>();
        for (GameTypeStage stage : values()) {
            if (stage.getGameType() == gameType) {
                stages.add(stage);
            }
        }
        return stages;
    }

    public static ShopTransactionTypeEnum[] getWinTransactions(GameType gameType) {
        List<GameTypeStage> gameTypeLevels = getGameTypeStages(gameType);
        ShopTransactionTypeEnum[] transactionTypeEnums = new ShopTransactionTypeEnum[gameTypeLevels.size()];
        for (int i = 0; i < gameTypeLevels.size(); i++) {
            transactionTypeEnums[i] = gameTypeLevels.get(i).getPrizeTransaction();
        }
        return transactionTypeEnums;
    }
}
