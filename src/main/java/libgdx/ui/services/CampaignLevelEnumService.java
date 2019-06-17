package libgdx.ui.services;

import libgdx.resources.Res;
import libgdx.resources.SpecificResource;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.resources.Resource;
import libgdx.utils.EnumUtils;


public class CampaignLevelEnumService {

    private CampaignLevel campaignLevel;

    public CampaignLevelEnumService(CampaignLevel campaignLevel) {
        this.campaignLevel = campaignLevel;
    }

    public String getLabelText() {
        return getCategory() != null ? new SpecificPropertiesUtils().getQuestionCategoryLabel(getCategory()) : null;
    }

    public Resource getBackgroundTexture() {
        return EnumUtils.getEnumValue(Resource.class, "campaign_level_" + getDifficulty() + "_background");
    }

    public ButtonSkin getButtonSkin() {
        return getCategory() != null ? EnumUtils.getEnumValue(ButtonSkin.class, "CAMPAIGN_LEVEL_" + getDifficulty()) : ButtonSkin.CAMPAIGN_LEVEL_WALL;
    }

    public Res getIcon() {
        Res res = Resource.bomb;
        if (getCategory() != null) {
            res = (SpecificResource) EnumUtils.getEnumValue(TournamentGame.getInstance().getSubGameDependencyManager().getSpecificResourceTypeEnum(), "campaign_level_" + getDifficulty() + "_" + getCategory());
        }
        return res;
    }

    public QuestionConfig getQuestionConfig() {
        QuestionDifficulty difficulty = (QuestionDifficulty) EnumUtils.getEnumValue(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum(), "_" + getDifficulty());
        int campaignLevelAmountQuestions;
        QuestionConfig questionConfig;
        if (getCategory() != null) {
            campaignLevelAmountQuestions = TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getCampaignLevelAmountQuestions();
            QuestionCategory category = (QuestionCategory) EnumUtils.getEnumValue(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum(), "cat" + getCategory());
            questionConfig = new QuestionConfig(difficulty, category, campaignLevelAmountQuestions);
        } else {
            campaignLevelAmountQuestions = TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getStageFinishedCampaignLevelAmountQuestions();
            questionConfig = new QuestionConfig(difficulty, campaignLevelAmountQuestions);
        }


        return questionConfig;
    }

    public GameTypeStage getGameTypeStage() {
        Integer category = getCategory();
        if (category != null && category > 4) {
            category = category - 5 * getDifficulty();
        }
        return EnumUtils.getEnumValue(GameTypeStage.class, "campaign_level_" + getDifficulty() + (category != null ? "_" + category : ""));
    }

    private int getDifficulty() {
        return Integer.valueOf(campaignLevel.getName().split("_")[1]);
    }

    public Integer getCategory() {
        return getCategory(campaignLevel);
    }

    private static String[] getSplit(CampaignLevel campaignLevel) {
        return campaignLevel.getName().split("_");
    }

    private static Integer getCategory(CampaignLevel campaignLevel) {
        String[] split = getSplit(campaignLevel);
        return split.length == 3 ? Integer.valueOf(split[2]) : null;
    }

}
