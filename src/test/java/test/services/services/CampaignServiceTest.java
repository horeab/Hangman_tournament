package test.services.services;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaCampaignLevelEnum;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;
import libgdx.ui.model.game.CampaignLevelDb;
import libgdx.ui.services.game.campaign.CampaignService;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CampaignServiceTest extends TournamentGameTestDbApiService {

    private CampaignService campaignService;

    @Before
    public void setup() throws Exception {
        super.setup();
        campaignService = new CampaignService();
    }


    @Test
    public void createLevel0_onInit() {
        assertEquals(0, campaignDbApiService.getAllCampaignLevels(currentUserId).size());
        List<CampaignLevelDb> allCampaignLevelDbs = campaignService.processAndGetAllLevels(currentUserId);
        assertEquals(1, allCampaignLevelDbs.size());
        assertEquals(1, campaignDbApiService.getAllCampaignLevels(currentUserId).size());
        assertEquals(currentUserId, allCampaignLevelDbs.get(0).getUserId());
        assertEquals(0, allCampaignLevelDbs.get(0).getLevel());
        assertEquals(0, allCampaignLevelDbs.get(0).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), allCampaignLevelDbs.get(0).getStatus());
    }

    @Test
    public void newLevelsWereAddedAndUserFinishedGame() {
        campaignService = new CampaignService();
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        campaignService.levelFinished(currentUserId, campaignService.processAndGetAllLevels(currentUserId).get(0).getQuestionAnswered(), HangmanArenaCampaignLevelEnum.values()[0]);
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[2].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        campaignService.levelFinished(currentUserId, campaignService.processAndGetAllLevels(currentUserId).get(1).getQuestionAnswered(), HangmanArenaCampaignLevelEnum.values()[1]);
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[2].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        assertNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[3].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));

        //Level LEVEL_0_2 is the last level the user finished, and new levels were added ex: LEVEL_0_3
        campaignDbApiService.updateStatus(currentUserId, HangmanArenaCampaignLevelEnum.values()[2].getIndex(), CampaignLevelStatusEnum.FINISHED);
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), campaignDbApiService.getAllCampaignLevels(currentUserId)));
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), campaignDbApiService.getAllCampaignLevels(currentUserId)));
        CampaignLevelDb level2 = campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[2].getIndex(), campaignDbApiService.getAllCampaignLevels(currentUserId));
        assertEquals(CampaignLevelStatusEnum.FINISHED.getStatus(), level2.getStatus());
        assertNotNull(level2);
        assertNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[3].getIndex(), campaignDbApiService.getAllCampaignLevels(currentUserId)));
        //prepare new levels
        campaignService.processAndGetAllLevels(currentUserId);
        assertNotNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[3].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
        CampaignLevelDb level3 = campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[3].getIndex(), campaignService.processAndGetAllLevels(currentUserId));
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), level3.getStatus());
        assertNotNull(level3);
        assertNull(campaignService.getCampaignLevel(HangmanArenaCampaignLevelEnum.values()[4].getIndex(), campaignService.processAndGetAllLevels(currentUserId)));
    }

    @Test
    public void levelFinished() {
        campaignService = new CampaignService();
        assertEquals(1, campaignService.processAndGetAllLevels(currentUserId).size());

        campaignService.levelFinished(currentUserId, 5, HangmanArenaCampaignLevelEnum.values()[0]);
        TestUtils.waitt();
        List<CampaignLevelDb> allCampaignLevelDbs = campaignService.processAndGetAllLevels(currentUserId);
        assertEquals(2, allCampaignLevelDbs.size());
        assertEquals(currentUserId, allCampaignLevelDbs.get(0).getUserId());
        assertEquals(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), allCampaignLevelDbs.get(0).getLevel());
        assertEquals(5, allCampaignLevelDbs.get(0).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.FINISHED.getStatus(), allCampaignLevelDbs.get(0).getStatus());
        assertEquals(currentUserId, allCampaignLevelDbs.get(1).getUserId());
        assertEquals(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), allCampaignLevelDbs.get(1).getLevel());
        assertEquals(0, allCampaignLevelDbs.get(1).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), allCampaignLevelDbs.get(1).getStatus());

        campaignService.levelFinished(currentUserId, 3, HangmanArenaCampaignLevelEnum.values()[1]);
        allCampaignLevelDbs = campaignDbApiService.getAllCampaignLevels(currentUserId);
        assertEquals(3, allCampaignLevelDbs.size());
        assertEquals(currentUserId, allCampaignLevelDbs.get(0).getUserId());
        assertEquals(HangmanArenaCampaignLevelEnum.values()[0].getIndex(), allCampaignLevelDbs.get(0).getLevel());
        assertEquals(5, allCampaignLevelDbs.get(0).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.FINISHED.getStatus(), allCampaignLevelDbs.get(0).getStatus());
        assertEquals(currentUserId, allCampaignLevelDbs.get(1).getUserId());
        assertEquals(HangmanArenaCampaignLevelEnum.values()[1].getIndex(), allCampaignLevelDbs.get(1).getLevel());
        assertEquals(3, allCampaignLevelDbs.get(1).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.FINISHED.getStatus(), allCampaignLevelDbs.get(1).getStatus());
        assertEquals(currentUserId, allCampaignLevelDbs.get(2).getUserId());
        assertEquals(HangmanArenaCampaignLevelEnum.values()[2].getIndex(), allCampaignLevelDbs.get(2).getLevel());
        assertEquals(0, allCampaignLevelDbs.get(2).getQuestionAnswered());
        assertEquals(CampaignLevelStatusEnum.IN_PROGRESS.getStatus(), allCampaignLevelDbs.get(2).getStatus());

        //not update if questions answered are smaller than the current
        campaignService.levelFinished(currentUserId, 2, HangmanArenaCampaignLevelEnum.values()[1]);
        allCampaignLevelDbs = campaignDbApiService.getAllCampaignLevels(currentUserId);
        assertEquals(3, allCampaignLevelDbs.get(1).getQuestionAnswered());

        campaignService.levelFinished(currentUserId, 4, HangmanArenaCampaignLevelEnum.values()[1]);
        allCampaignLevelDbs = campaignDbApiService.getAllCampaignLevels(currentUserId);
        assertEquals(4, allCampaignLevelDbs.get(1).getQuestionAnswered());

        //should not break
        campaignService.levelFinished(currentUserId, 2, HangmanArenaCampaignLevelEnum.values()[HangmanArenaCampaignLevelEnum.values().length - 1]);
        TestUtils.waitt();
    }
}
