package libgdx.ui.implementations.games.flags.questionconfig;

import libgdx.ui.constants.game.campaign.CampaignLevel;

public enum FlagsCampaignLevelEnum implements CampaignLevel {

    //This order is displayed on the campaign screen
    LEVEL_0_0,
    LEVEL_0_2,
    LEVEL_0_3,
    LEVEL_0_1,
    LEVEL_0_4,
    LEVEL_0,
    ////
    LEVEL_1_0,
    LEVEL_1_2,
    LEVEL_1_3,
    LEVEL_1_1,
    LEVEL_1_4,
    LEVEL_1,
    ////
    LEVEL_2_0,
    LEVEL_2_2,
    LEVEL_2_3,
    LEVEL_2_1,
    LEVEL_2_4,
    LEVEL_2,
    //
    ;

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }
}
