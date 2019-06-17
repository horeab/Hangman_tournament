package libgdx.ui.services.game.campaign;

import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;
import libgdx.ui.model.game.CampaignLevelDb;
import libgdx.ui.services.dbapi.CampaignDbApiService;
import libgdx.utils.EnumUtils;

public class CampaignService {

    private CampaignDbApiService campaignDbApiService = new CampaignDbApiService();

    public List<CampaignLevelDb> processAndGetAllLevels(int userId) {
        List<CampaignLevelDb> allPlayedLevels = campaignDbApiService.getAllCampaignLevels(userId);
        CampaignLevel[] values = (CampaignLevel[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getCampaignLevelTypeEnum());
        if (getCampaignLevel(values[0].getIndex(), allPlayedLevels) == null) {
            campaignDbApiService.createCampaignLevel(userId, values[0]);
            allPlayedLevels.add(new CampaignLevelDb(userId, values[0]));
        } else {
            //In case new levels were added and the user already finished the game
            CampaignLevelDb maxFinishedLevel = getMaxFinishedLevel(allPlayedLevels);
            if (maxFinishedLevel != null && maxFinishedLevel.getLevel() < values.length - 1
                    &&
                    getCampaignLevel(maxFinishedLevel.getLevel() + 1, allPlayedLevels) == null) {
                levelFinished(userId, maxFinishedLevel.getQuestionAnswered(), getCampaignLevelEnum(maxFinishedLevel.getLevel()));
                CampaignLevel campaignLevelEnum = getCampaignLevelEnum(maxFinishedLevel.getLevel() + 1);
                if (campaignLevelEnum != null) {
                    allPlayedLevels.add(new CampaignLevelDb(userId, campaignLevelEnum));
                }
            }
        }
        return allPlayedLevels;
    }

    public void levelFinished(int userId, int questionsAnswered, CampaignLevel level) {
        Integer dbQuestionsAnswered = campaignDbApiService.getQuestionsAnswered(userId, level.getIndex());
        if (dbQuestionsAnswered == null || dbQuestionsAnswered < questionsAnswered) {
            campaignDbApiService.updateQuestionsAnswered(userId, level.getIndex(), questionsAnswered);
            campaignDbApiService.updateStatus(userId, level.getIndex(), CampaignLevelStatusEnum.FINISHED);
        }
        CampaignLevel nextLevel = getNextLevel(level);
        if (nextLevel != null) {
            campaignDbApiService.createCampaignLevel(userId, nextLevel);
        }
    }

    public CampaignLevelDb getMaxOpenedLevel(List<CampaignLevelDb> allPlayedLevels) {
        CampaignLevelDb maxFinishedLevel = null;
        for (CampaignLevelDb campaignLevelDb : allPlayedLevels) {
            if (campaignLevelDb.getStatus() == CampaignLevelStatusEnum.IN_PROGRESS.getStatus() && (maxFinishedLevel == null || maxFinishedLevel.getLevel() < campaignLevelDb.getLevel())) {
                maxFinishedLevel = campaignLevelDb;
            }
        }
        return maxFinishedLevel;
    }

    private CampaignLevelDb getMaxFinishedLevel(List<CampaignLevelDb> allPlayedLevels) {
        CampaignLevelDb maxFinishedLevel = null;
        for (CampaignLevelDb campaignLevelDb : allPlayedLevels) {
            if (campaignLevelDb.getStatus() == CampaignLevelStatusEnum.FINISHED.getStatus() && (maxFinishedLevel == null || maxFinishedLevel.getLevel() < campaignLevelDb.getLevel())) {
                maxFinishedLevel = campaignLevelDb;
            }
        }
        return maxFinishedLevel;
    }

    private CampaignLevel getCampaignLevelEnum(int level) {
        CampaignLevel[] values = (CampaignLevel[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getCampaignLevelTypeEnum());
        for (CampaignLevel campaignLevelEnum : values) {
            if (campaignLevelEnum.getIndex() == level) {
                return campaignLevelEnum;
            }
        }
        return null;
    }

    private CampaignLevel getNextLevel(CampaignLevel currentCampaignLevelEnum) {
        CampaignLevel[] values = (CampaignLevel[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getCampaignLevelTypeEnum());
        for (CampaignLevel campaignLevelEnum : values) {
            if (currentCampaignLevelEnum != null && campaignLevelEnum.getIndex() == currentCampaignLevelEnum.getIndex() + 1) {
                return campaignLevelEnum;
            }
        }
        return null;
    }

    public CampaignLevelDb getCampaignLevel(int level, List<CampaignLevelDb> allPlayedLevels) {
        for (CampaignLevelDb campaignLevelDb : allPlayedLevels) {
            if (campaignLevelDb.getLevel() == level) {
                return campaignLevelDb;
            }
        }
        return null;
    }
}
