package libgdx.ui.implementations.games.flags;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsCampaignLevelEnum;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsQuestionCategoryEnum;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsQuestionConfigService;
import libgdx.ui.implementations.games.flags.questionconfig.FlagsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.flags.resources.FlagsSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class FlagsDependencyManager extends TournamentGameDependencyManager {


    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 41, FlagsQuestionCategoryEnum.cat0, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 32, FlagsQuestionCategoryEnum.cat1, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 63, FlagsQuestionCategoryEnum.cat2, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 15, FlagsQuestionCategoryEnum.cat3, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 52, FlagsQuestionCategoryEnum.cat4, ImageCategIncrementRes.PNG));
        return list;
    }


    @Override
    public Class<FlagsCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return FlagsCampaignLevelEnum.class;
    }

    @Override
    public Class<FlagsQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return FlagsQuestionCategoryEnum.class;
    }

    @Override
    public Class<FlagsQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return FlagsQuestionDifficultyLevel.class;
    }

    @Override
    public Class<FlagsSpecificResource> getSpecificResourceTypeEnum() {
        return FlagsSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new FlagsQuestionConfigService();
    }



}
