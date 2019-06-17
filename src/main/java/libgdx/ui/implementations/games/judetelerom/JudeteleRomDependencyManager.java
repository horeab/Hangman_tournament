package libgdx.ui.implementations.games.judetelerom;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomCampaignLevelEnum;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionCategoryEnum;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionConfigService;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionDifficultyLevel;
import libgdx.ui.implementations.games.judetelerom.resources.JudeteleRomSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class JudeteleRomDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 40, JudeteleRomQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 40, JudeteleRomQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<JudeteleRomCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return JudeteleRomCampaignLevelEnum.class;
    }

    @Override
    public Class<JudeteleRomQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return JudeteleRomQuestionCategoryEnum.class;
    }

    @Override
    public Class<JudeteleRomQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return JudeteleRomQuestionDifficultyLevel.class;
    }

    @Override
    public Class<JudeteleRomSpecificResource> getSpecificResourceTypeEnum() {
        return JudeteleRomSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new JudeteleRomQuestionConfigService();
    }

}
