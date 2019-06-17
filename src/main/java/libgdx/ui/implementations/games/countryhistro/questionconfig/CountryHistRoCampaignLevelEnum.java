package libgdx.ui.implementations.games.countryhistro.questionconfig;

import libgdx.ui.constants.game.campaign.CampaignLevel;

public enum CountryHistRoCampaignLevelEnum implements CampaignLevel {

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
    LEVEL_1,;

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }

}
