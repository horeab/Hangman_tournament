package libgdx.ui.implementations.games.astronomy;

import java.util.ArrayList;
import java.util.List;

import libgdx.constants.ScreenContrast;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyCampaignLevelEnum;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyQuestionCategoryEnum;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyQuestionConfigService;
import libgdx.ui.implementations.games.astronomy.questionconfig.AstronomyQuestionDifficultyLevel;
import libgdx.ui.implementations.games.astronomy.resources.AstronomySpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class AstronomyDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        return list;
    }

    @Override
    public Class<AstronomyCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return AstronomyCampaignLevelEnum.class;
    }

    @Override
    public Class<AstronomyQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return AstronomyQuestionCategoryEnum.class;
    }

    @Override
    public Class<AstronomyQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return AstronomyQuestionDifficultyLevel.class;
    }

    @Override
    public Class<AstronomySpecificResource> getSpecificResourceTypeEnum() {
        return AstronomySpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new AstronomyQuestionConfigService();
    }

    @Override
    public ScreenContrast getScreenContrast() {
        return ScreenContrast.DARK;
    }
}
