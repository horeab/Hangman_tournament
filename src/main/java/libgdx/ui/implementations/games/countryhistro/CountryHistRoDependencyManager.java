package libgdx.ui.implementations.games.countryhistro;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.countryhistro.questionconfig.CountryHistRoCampaignLevelEnum;
import libgdx.ui.implementations.games.countryhistro.questionconfig.CountryHistRoQuestionCategoryEnum;
import libgdx.ui.implementations.games.countryhistro.questionconfig.CountryHistRoQuestionConfigService;
import libgdx.ui.implementations.games.countryhistro.questionconfig.CountryHistRoQuestionDifficultyLevel;
import libgdx.ui.implementations.games.countryhistro.resources.CountryHistRoSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class CountryHistRoDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        return list;
    }

    @Override
    public Class<CountryHistRoCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return CountryHistRoCampaignLevelEnum.class;
    }

    @Override
    public Class<CountryHistRoQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return CountryHistRoQuestionCategoryEnum.class;
    }

    @Override
    public Class<CountryHistRoQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return CountryHistRoQuestionDifficultyLevel.class;
    }

    @Override
    public Class<CountryHistRoSpecificResource> getSpecificResourceTypeEnum() {
        return CountryHistRoSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new CountryHistRoQuestionConfigService();
    }

}
