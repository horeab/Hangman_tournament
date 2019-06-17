package libgdx.ui.implementations.games.capitals;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsCampaignLevelEnum;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsQuestionCategoryEnum;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsQuestionConfigService;
import libgdx.ui.implementations.games.capitals.questionconfig.CapitalsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.capitals.resources.CapitalsSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class CapitalsDependencyManager extends TournamentGameDependencyManager {


    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 41, CapitalsQuestionCategoryEnum.cat0, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 32, CapitalsQuestionCategoryEnum.cat1, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 63, CapitalsQuestionCategoryEnum.cat2, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 14, CapitalsQuestionCategoryEnum.cat3, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 52, CapitalsQuestionCategoryEnum.cat4, ImageCategIncrementRes.PNG));
        return list;
    }


    @Override
    public Class<CapitalsCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return CapitalsCampaignLevelEnum.class;
    }

    @Override
    public Class<CapitalsQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return CapitalsQuestionCategoryEnum.class;
    }

    @Override
    public Class<CapitalsQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return CapitalsQuestionDifficultyLevel.class;
    }

    @Override
    public Class<CapitalsSpecificResource> getSpecificResourceTypeEnum() {
        return CapitalsSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new CapitalsQuestionConfigService();
    }



}
