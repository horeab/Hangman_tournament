package test.services.dbapi;

import org.junit.Test;

import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaCampaignLevelEnum;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;
import libgdx.ui.model.game.CampaignLevelDb;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class CampaignDbApiServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void createCampaignLevel() {
        assertEquals(0, campaignDbApiService.getAllCampaignLevels(currentUserId).size());
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_3);
        List<CampaignLevelDb> allCampaignLevelDbs = campaignDbApiService.getAllCampaignLevels(currentUserId);
        assertEquals(1, allCampaignLevelDbs.size());
        assertEquals(currentUserId, allCampaignLevelDbs.get(0).getUserId());
        assertEquals(3, allCampaignLevelDbs.get(0).getLevel());
        assertEquals(0, allCampaignLevelDbs.get(0).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), allCampaignLevelDbs.get(0).getStatus());
    }

    @Test
    public void getAllCampaignLevels() {
        assertEquals(0, campaignDbApiService.getAllCampaignLevels(currentUserId).size());
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1);
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_0);
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1);
        List<CampaignLevelDb> allCampaignLevelDbs = campaignDbApiService.getAllCampaignLevels(currentUserId);
        assertEquals(2, allCampaignLevelDbs.size());
    }

    @Test
    public void updateQuestionsAnswered() {
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1);
        assertEquals(0, campaignDbApiService.getAllCampaignLevels(currentUserId).get(0).getQuestionAnswered());
        campaignDbApiService.updateQuestionsAnswered(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1.getIndex(), 5);
        TestUtils.waitt();
        assertEquals(5, campaignDbApiService.getAllCampaignLevels(currentUserId).get(0).getQuestionAnswered());
    }


    @Test
    public void updateStatus() {
        campaignDbApiService.createCampaignLevel(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1);
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), campaignDbApiService.getAllCampaignLevels(currentUserId).get(0).getStatus());
        campaignDbApiService.updateStatus(currentUserId, HangmanArenaCampaignLevelEnum.LEVEL_0_1.getIndex(), CampaignLevelStatusEnum.FINISHED);
        TestUtils.waitt();
        assertEquals(CampaignLevelStatusEnum.FINISHED.getStatus(), campaignDbApiService.getAllCampaignLevels(currentUserId).get(0).getStatus());
    }
}
