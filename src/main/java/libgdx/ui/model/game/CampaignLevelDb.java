package libgdx.ui.model.game;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;

public class CampaignLevelDb {

    public CampaignLevelDb(int userId, CampaignLevel campaignLevel) {
        this.userId = userId;
        level = campaignLevel.getIndex();
        questionAnswered = 0;
        status = CampaignLevelStatusEnum.IN_PROGRESS.getStatus();
    }

    private int userId;
    private int level;
    private int questionAnswered;
    private int status;

    public int getUserId() {
        return userId;
    }

    public int getLevel() {
        return level;
    }

    public int getQuestionAnswered() {
        return questionAnswered;
    }

    public int getStatus() {
        return status;
    }
}
