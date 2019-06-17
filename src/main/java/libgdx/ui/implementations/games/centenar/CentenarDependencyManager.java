package libgdx.ui.implementations.games.centenar;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarCampaignLevelEnum;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionCategoryEnum;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionConfigService;
import libgdx.ui.implementations.games.centenar.questionconfig.CentenarQuestionDifficultyLevel;
import libgdx.ui.implementations.games.centenar.resources.CentenarSpecificResource;
import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class CentenarDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 4, CentenarQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(6, 9, CentenarQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<CentenarCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return CentenarCampaignLevelEnum.class;
    }

    @Override
    public Class<CentenarQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return CentenarQuestionCategoryEnum.class;
    }

    @Override
    public Class<CentenarQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return CentenarQuestionDifficultyLevel.class;
    }

    @Override
    public Class<CentenarSpecificResource> getSpecificResourceTypeEnum() {
        return CentenarSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new CentenarQuestionConfigService();
    }

    @Override
    public TournamentGameScreenCreator getTournamentGameScreenCreator() {
        return new CentenarTournamentGameScreenCreator();
    }
}
