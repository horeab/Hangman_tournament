package libgdx.ui.constants.overridable;

import libgdx.transactions.TransactionAmount;

public enum TransactionAmountEnum {

    WIN_PLAYER_INITIAL_BUDGET(200, 0),
    WIN_CONNECT_FACEBOOK(150, 0),
    WIN_SHARE_GAME(150, 0),
    WIN_WATCH_VIDEO_AD(50, 1),
    WIN_BUY_REMOVE_ADS(1000, 10),

    WIN_TOURNAMENT_OPT_1(75, 0),
    WIN_TOURNAMENT_OPT_2(150, 0),
    WIN_TOURNAMENT_OPT_3(300, 0),
    PAY_TOURNAMENT_OPT_1(-25, 0),
    PAY_TOURNAMENT_OPT_2(-50, 0),
    PAY_TOURNAMENT_OPT_3(-100, 0),

    WIN_PRACTICE_OPT_1(5, 0),
    WIN_PRACTICE_OPT_2(10, 0),
    WIN_PRACTICE_OPT_3(20, 0),
    PAY_PRACTICE_OPT_1(-0, 0),
    PAY_PRACTICE_OPT_2(-5, 0),
    PAY_PRACTICE_OPT_3(-10, 0),

    WIN_CHALLENGE_OPT_1(10, 0),
    WIN_CHALLENGE_OPT_2(40, 0),
    WIN_CHALLENGE_OPT_3(200, 0),
    PAY_CHALLENGE_OPT_1(-5, 0),
    PAY_CHALLENGE_OPT_2(-20, 0),
    PAY_CHALLENGE_OPT_3(-100, 0),

    WIN_DIAMOND(0, 0),
    PAY_DIAMOND(0, 0),

    //CAMPAIGN
    PAY_LEVEL_0_0(0, 0),
    WIN_LEVEL_0_0(10, 0),
    PAY_LEVEL_0_1(0, 0),
    WIN_LEVEL_0_1(10, 0),
    PAY_LEVEL_0_2(0, 0),
    WIN_LEVEL_0_2(10, 0),
    PAY_LEVEL_0_3(0, 0),
    WIN_LEVEL_0_3(10, 0),
    PAY_LEVEL_0_4(0, 0),
    WIN_LEVEL_0_4(10, 0),
    PAY_LEVEL_0(-200, -1),
    WIN_LEVEL_0(100, 0),
    ////////////////////////////
    PAY_LEVEL_1_0(0, 0),
    WIN_LEVEL_1_0(30, 0),
    PAY_LEVEL_1_1(0, 0),
    WIN_LEVEL_1_1(30, 0),
    PAY_LEVEL_1_2(0, 0),
    WIN_LEVEL_1_2(30, 0),
    PAY_LEVEL_1_3(0, 0),
    WIN_LEVEL_1_3(30, 0),
    PAY_LEVEL_1_4(0, 0),
    WIN_LEVEL_1_4(30, 0),
    PAY_LEVEL_1(-400, -1),
    WIN_LEVEL_1(200, 0),
    ////////////////////////////
    PAY_LEVEL_2_0(0, 0),
    WIN_LEVEL_2_0(50, 0),
    PAY_LEVEL_2_1(0, 0),
    WIN_LEVEL_2_1(50, 0),
    PAY_LEVEL_2_2(0, 0),
    WIN_LEVEL_2_2(50, 0),
    PAY_LEVEL_2_3(0, 0),
    WIN_LEVEL_2_3(50, 0),
    PAY_LEVEL_2_4(0, 0),
    WIN_LEVEL_2_4(50, 0),
    PAY_LEVEL_2(-600, -1),
    WIN_LEVEL_2(300, 0),
    ////////////////////////////
    PAY_LEVEL_3_0(0, 0),
    WIN_LEVEL_3_0(70, 0),
    PAY_LEVEL_3_1(0, 0),
    WIN_LEVEL_3_1(70, 0),
    PAY_LEVEL_3_2(0, 0),
    WIN_LEVEL_3_2(70, 0),
    PAY_LEVEL_3_3(0, 0),
    WIN_LEVEL_3_3(70, 0),
    PAY_LEVEL_3_4(0, 0),
    WIN_LEVEL_3_4(70, 0),
    PAY_LEVEL_3(-800, -1),
    WIN_LEVEL_3(500, 0),
    ////////////////////////////
    PAY_LEVEL_4_0(0, 0),
    WIN_LEVEL_4_0(100, 0),
    PAY_LEVEL_4_1(0, 0),
    WIN_LEVEL_4_1(100, 0),
    PAY_LEVEL_4_2(0, 0),
    WIN_LEVEL_4_2(100, 0),
    PAY_LEVEL_4_3(0, 0),
    WIN_LEVEL_4_3(100, 0),
    PAY_LEVEL_4_4(0, 0),
    WIN_LEVEL_4_4(100, 0),
    PAY_LEVEL_4(-1000, -1),
    WIN_LEVEL_4(700, 0),
    ////////////////////////////
    PAY_LEVEL_5_0(0, 0),
    WIN_LEVEL_5_0(150, 0),
    PAY_LEVEL_5_1(0, 0),
    WIN_LEVEL_5_1(150, 0),
    PAY_LEVEL_5_2(0, 0),
    WIN_LEVEL_5_2(150, 0),
    PAY_LEVEL_5_3(0, 0),
    WIN_LEVEL_5_3(150, 0),
    PAY_LEVEL_5_4(0, 0),
    WIN_LEVEL_5_4(150, 0),
    PAY_LEVEL_5(-1500, -2),
    WIN_LEVEL_5(1000, 5),;

    private int coins;
    private int diamond;

    TransactionAmountEnum(int coins, int diamond) {
        this.coins = coins;
        this.diamond = diamond;
    }

    public boolean isZeroAmount() {
        return coins == 0 && diamond == 0;
    }

    public int getCoins() {
        return coins;
    }

    public int getDiamond() {
        return diamond;
    }

}
