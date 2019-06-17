package libgdx.ui.implementations.games.centenar.questionconfig;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.question.QuestionCategory;

public enum CentenarCampaignLevelEnum implements CampaignLevel {

    //This order is displayed on the campaign screen
    LEVEL_0_2,
    LEVEL_0_4,
    LEVEL_0_1,
    LEVEL_0_3,
    LEVEL_0_0,
    LEVEL_0,
    ////
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
