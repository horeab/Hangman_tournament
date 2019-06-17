package libgdx.ui.implementations.games.kennstde;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.bundesde.BundesDeTournamentGameScreenCreator;
import libgdx.ui.implementations.games.kennstde.questionconfig.KennstDeCampaignLevelEnum;
import libgdx.ui.implementations.games.kennstde.questionconfig.KennstDeQuestionCategoryEnum;
import libgdx.ui.implementations.games.kennstde.questionconfig.KennstDeQuestionConfigService;
import libgdx.ui.implementations.games.kennstde.questionconfig.KennstDeQuestionDifficultyLevel;
import libgdx.ui.implementations.games.kennstde.resources.KennstDeSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class KennstDeDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 1, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(3, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(6, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(8, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(13, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(15, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(17, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));

        list.add(new ImageCategIncrementRes(0, 14,KennstDeQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));

        list.add(new ImageCategIncrementRes(0, 19,KennstDeQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<KennstDeCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return KennstDeCampaignLevelEnum.class;
    }

    @Override
    public Class<KennstDeQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return KennstDeQuestionCategoryEnum.class;
    }

    @Override
    public Class<KennstDeQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return KennstDeQuestionDifficultyLevel.class;
    }

    @Override
    public Class<KennstDeSpecificResource> getSpecificResourceTypeEnum() {
        return KennstDeSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new KennstDeQuestionConfigService();
    }

}
