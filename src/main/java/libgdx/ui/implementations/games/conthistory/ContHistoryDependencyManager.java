package libgdx.ui.implementations.games.conthistory;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.conthistory.questionconfig.ContHistoryCampaignLevelEnum;
import libgdx.ui.implementations.games.conthistory.questionconfig.ContHistoryQuestionCategoryEnum;
import libgdx.ui.implementations.games.conthistory.questionconfig.ContHistoryQuestionConfigService;
import libgdx.ui.implementations.games.conthistory.questionconfig.ContHistoryQuestionDifficultyLevel;
import libgdx.ui.implementations.games.conthistory.resources.ContHistorySpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class ContHistoryDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        return list;
    }

    @Override
    public Class<ContHistoryCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return ContHistoryCampaignLevelEnum.class;
    }

    @Override
    public Class<ContHistoryQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return ContHistoryQuestionCategoryEnum.class;
    }

    @Override
    public Class<ContHistoryQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return ContHistoryQuestionDifficultyLevel.class;
    }

    @Override
    public Class<ContHistorySpecificResource> getSpecificResourceTypeEnum() {
        return ContHistorySpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new ContHistoryQuestionConfigService();
    }

}
