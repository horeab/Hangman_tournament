package libgdx.ui.implementations.games.countrygeo;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoCampaignLevelEnum;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoQuestionCategoryEnum;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoQuestionConfigService;
import libgdx.ui.implementations.games.countrygeo.questionconfig.CountryGeoQuestionDifficultyLevel;
import libgdx.ui.implementations.games.countrygeo.resources.CountryGeoSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class CountryGeoDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        return list;
    }

    @Override
    public Class<CountryGeoCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return CountryGeoCampaignLevelEnum.class;
    }

    @Override
    public Class<CountryGeoQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return CountryGeoQuestionCategoryEnum.class;
    }

    @Override
    public Class<CountryGeoQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return CountryGeoQuestionDifficultyLevel.class;
    }

    @Override
    public Class<CountryGeoSpecificResource> getSpecificResourceTypeEnum() {
        return CountryGeoSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new CountryGeoQuestionConfigService();
    }

}
