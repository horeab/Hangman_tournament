package libgdx.ui.implementations.games.anatomy.questionconfig;

import libgdx.ui.constants.game.campaign.CampaignLevel;

public enum AnatomyCampaignLevelEnum implements CampaignLevel {

    //This order is displayed on the campaign screen
    LEVEL_0_0,
    LEVEL_0_2,
    LEVEL_0_1,
    LEVEL_0_3,
    LEVEL_0_4,
    LEVEL_0,;

    @Override
    public int getIndex() {
        return ordinal();
    }

    @Override
    public String getName() {
        return name();
    }

}
