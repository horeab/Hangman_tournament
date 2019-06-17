package libgdx.ui.implementations.games.anatomy;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyCampaignLevelEnum;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyQuestionCategoryEnum;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyQuestionConfigService;
import libgdx.ui.implementations.games.anatomy.questionconfig.AnatomyQuestionDifficultyLevel;
import libgdx.ui.implementations.games.anatomy.resources.AnatomySpecificResource;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionCategoryEnum;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class AnatomyDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0,  AnatomyQuestionCategoryEnum.cat2, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 1, AnatomyQuestionCategoryEnum.cat3, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0,  AnatomyQuestionCategoryEnum.cat4, ImageCategIncrementRes.PNG));
        return list;
    }

    @Override
    public Class<AnatomyCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return AnatomyCampaignLevelEnum.class;
    }

    @Override
    public Class<AnatomyQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return AnatomyQuestionCategoryEnum.class;
    }

    @Override
    public Class<AnatomyQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return AnatomyQuestionDifficultyLevel.class;
    }

    @Override
    public Class<AnatomySpecificResource> getSpecificResourceTypeEnum() {
        return AnatomySpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new AnatomyQuestionConfigService();
    }

}
