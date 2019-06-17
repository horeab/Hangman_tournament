package libgdx.ui.implementations.games.cunro;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionCategoryEnum;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoCampaignLevelEnum;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionCategoryEnum;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionConfigService;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionDifficultyLevel;
import libgdx.ui.implementations.games.cunro.resources.CunRoSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class CunRoDependencyManager extends TournamentGameDependencyManager {


    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(1, 1, CunRoQuestionCategoryEnum.cat0, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 9, CunRoQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 11, CunRoQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<CunRoCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return CunRoCampaignLevelEnum.class;
    }

    @Override
    public Class<CunRoQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return CunRoQuestionCategoryEnum.class;
    }

    @Override
    public Class<CunRoQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return CunRoQuestionDifficultyLevel.class;
    }

    @Override
    public Class<CunRoSpecificResource> getSpecificResourceTypeEnum() {
        return CunRoSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new CunRoQuestionConfigService();
    }

}
