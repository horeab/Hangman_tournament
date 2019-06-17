package libgdx.ui.implementations.games.geoquiz;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.cunro.questionconfig.CunRoQuestionCategoryEnum;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizCampaignLevelEnum;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionCategoryEnum;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionConfigService;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionDifficultyLevel;
import libgdx.ui.implementations.games.geoquiz.resources.GeoQuizSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class GeoQuizDependencyManager extends TournamentGameDependencyManager {


    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 34, GeoQuizQuestionCategoryEnum.cat0, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 49, GeoQuizQuestionCategoryEnum.cat1, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 49, GeoQuizQuestionCategoryEnum.cat2, ImageCategIncrementRes.PNG));
        list.add(new ImageCategIncrementRes(0, 49, GeoQuizQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 35, GeoQuizQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        return list;
    }


    @Override
    public Class<GeoQuizCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return GeoQuizCampaignLevelEnum.class;
    }

    @Override
    public Class<GeoQuizQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return GeoQuizQuestionCategoryEnum.class;
    }

    @Override
    public Class<GeoQuizQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return GeoQuizQuestionDifficultyLevel.class;
    }

    @Override
    public Class<GeoQuizSpecificResource> getSpecificResourceTypeEnum() {
        return GeoQuizSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new GeoQuizQuestionConfigService();
    }



}
