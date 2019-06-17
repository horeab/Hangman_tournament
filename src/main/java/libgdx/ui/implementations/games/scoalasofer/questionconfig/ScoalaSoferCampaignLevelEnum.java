package libgdx.ui.implementations.games.scoalasofer.questionconfig;

import libgdx.ui.constants.game.campaign.CampaignLevel;

public enum ScoalaSoferCampaignLevelEnum implements CampaignLevel {

    //This order is displayed on the campaign screen
    LEVEL_0_0,
    LEVEL_0_1,
    LEVEL_0_2,
    LEVEL_0_3,
    LEVEL_0_4,
    LEVEL_0,

    LEVEL_1_5,
    LEVEL_1_6,
    LEVEL_1_7,
    LEVEL_1_8,
    LEVEL_1_9,
    LEVEL_1,

    LEVEL_2_10,
    LEVEL_2_11,
    LEVEL_2_12,
    LEVEL_2_13,
    LEVEL_2_14,
    LEVEL_2,;

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }

}
