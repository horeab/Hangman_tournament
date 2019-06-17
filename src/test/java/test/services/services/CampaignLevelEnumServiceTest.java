package test.services.services;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferCampaignLevelEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionCategoryEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionDifficultyLevel;
import libgdx.ui.implementations.games.scoalasofer.resources.ScoalaSoferSpecificResource;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.ui.resources.Resource;
import libgdx.ui.services.CampaignLevelEnumService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CampaignLevelEnumServiceTest extends TournamentGameTestDbApiService {

    private CampaignLevelEnumService campaignLevelEnumService;

    @Test
    public void test_Level_0_2() {
        campaignLevelEnumService = new CampaignLevelEnumService(ScoalaSoferCampaignLevelEnum.LEVEL_0_2);
        assertEquals(Integer.valueOf(2), campaignLevelEnumService.getCategory());
        assertEquals(GameTypeStage.campaign_level_0_2, campaignLevelEnumService.getGameTypeStage());
        assertEquals(ScoalaSoferSpecificResource.campaign_level_0_2, campaignLevelEnumService.getIcon());
        assertEquals(Resource.campaign_level_0_background, campaignLevelEnumService.getBackgroundTexture());
        assertEquals("Semnale luminoase", campaignLevelEnumService.getLabelText());
        assertEquals(ButtonSkin.CAMPAIGN_LEVEL_0, campaignLevelEnumService.getButtonSkin());
        for (int tries = 0; tries < 20; tries++) {
            RandomCategoryAndDifficulty randomCategoryAndDifficulty = campaignLevelEnumService.getQuestionConfig().getRandomCategoryAndDifficulty();
            assertEquals(ScoalaSoferQuestionCategoryEnum.cat2, randomCategoryAndDifficulty.getQuestionCategory());
            assertEquals(ScoalaSoferQuestionDifficultyLevel._0, randomCategoryAndDifficulty.getQuestionDifficulty());
        }
    }

    @Test
    public void test_Level_2_13() {
        campaignLevelEnumService = new CampaignLevelEnumService(ScoalaSoferCampaignLevelEnum.LEVEL_2_13);
        assertEquals(Integer.valueOf(13), campaignLevelEnumService.getCategory());
        assertEquals(GameTypeStage.campaign_level_2_3, campaignLevelEnumService.getGameTypeStage());
        assertEquals(ScoalaSoferSpecificResource.campaign_level_2_13, campaignLevelEnumService.getIcon());
        assertEquals(Resource.campaign_level_2_background, campaignLevelEnumService.getBackgroundTexture());
        assertEquals("Noțiuni de mecanică", campaignLevelEnumService.getLabelText());
        assertEquals(ButtonSkin.CAMPAIGN_LEVEL_2, campaignLevelEnumService.getButtonSkin());
        for (int tries = 0; tries < 20; tries++) {
            RandomCategoryAndDifficulty randomCategoryAndDifficulty = campaignLevelEnumService.getQuestionConfig().getRandomCategoryAndDifficulty();
            assertEquals(ScoalaSoferQuestionCategoryEnum.cat13, randomCategoryAndDifficulty.getQuestionCategory());
            assertEquals(ScoalaSoferQuestionDifficultyLevel._2, randomCategoryAndDifficulty.getQuestionDifficulty());
        }
    }


    @Test
    public void test_Level_2() {
        campaignLevelEnumService = new CampaignLevelEnumService(ScoalaSoferCampaignLevelEnum.LEVEL_2);
        assertNull(campaignLevelEnumService.getCategory());
        assertEquals(GameTypeStage.campaign_level_2, campaignLevelEnumService.getGameTypeStage());
        assertEquals(Resource.bomb, campaignLevelEnumService.getIcon());
        assertEquals(Resource.campaign_level_2_background, campaignLevelEnumService.getBackgroundTexture());
        assertNull(campaignLevelEnumService.getLabelText());
        assertEquals(ButtonSkin.CAMPAIGN_LEVEL_WALL, campaignLevelEnumService.getButtonSkin());
        Set<ScoalaSoferQuestionCategoryEnum> categoriesToBeFound = new HashSet<>( Arrays.asList(
                ScoalaSoferQuestionCategoryEnum.cat10,
                ScoalaSoferQuestionCategoryEnum.cat11,
                ScoalaSoferQuestionCategoryEnum.cat12,
                ScoalaSoferQuestionCategoryEnum.cat13,
                ScoalaSoferQuestionCategoryEnum.cat14));
        Set<ScoalaSoferQuestionCategoryEnum> foundCategs = new HashSet<>();
        for (int tries = 0; tries < 20; tries++) {
            RandomCategoryAndDifficulty randomCategoryAndDifficulty = campaignLevelEnumService.getQuestionConfig().getRandomCategoryAndDifficulty();
            foundCategs.add((ScoalaSoferQuestionCategoryEnum) randomCategoryAndDifficulty.getQuestionCategory());
            assertEquals(ScoalaSoferQuestionDifficultyLevel._2, randomCategoryAndDifficulty.getQuestionDifficulty());
        }
        assertEquals(categoriesToBeFound.size(), foundCategs.size());
        assertTrue(foundCategs.containsAll(categoriesToBeFound));
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.scoalasofer.name();
            }
        };
    }

}
