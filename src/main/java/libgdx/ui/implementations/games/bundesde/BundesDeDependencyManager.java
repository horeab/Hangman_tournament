package libgdx.ui.implementations.games.bundesde;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeCampaignLevelEnum;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionCategoryEnum;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionConfigService;
import libgdx.ui.implementations.games.bundesde.questionconfig.BundesDeQuestionDifficultyLevel;
import libgdx.ui.implementations.games.bundesde.resources.BundesDeSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class BundesDeDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 15, BundesDeQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 15, BundesDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<BundesDeCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return BundesDeCampaignLevelEnum.class;
    }

    @Override
    public Class<BundesDeQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return BundesDeQuestionCategoryEnum.class;
    }

    @Override
    public Class<BundesDeQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return BundesDeQuestionDifficultyLevel.class;
    }

    @Override
    public Class<BundesDeSpecificResource> getSpecificResourceTypeEnum() {
        return BundesDeSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new BundesDeQuestionConfigService();
    }

    @Override
    public BundesDeTournamentGameScreenCreator getTournamentGameScreenCreator() {
        return new BundesDeTournamentGameScreenCreator();
    }
}
