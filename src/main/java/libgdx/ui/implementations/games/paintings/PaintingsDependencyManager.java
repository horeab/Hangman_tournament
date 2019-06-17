package libgdx.ui.implementations.games.paintings;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.kennstde.questionconfig.KennstDeQuestionCategoryEnum;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsCampaignLevelEnum;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsQuestionCategoryEnum;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsQuestionConfigService;
import libgdx.ui.implementations.games.paintings.questionconfig.PaintingsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.paintings.resources.PaintingsSpecificResource;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferTournamentGameScreenCreator;
import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class PaintingsDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 42, KennstDeQuestionCategoryEnum.cat0, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 15, KennstDeQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 36, KennstDeQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 33, KennstDeQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 13, KennstDeQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public Class<PaintingsCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return PaintingsCampaignLevelEnum.class;
    }

    @Override
    public Class<PaintingsQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return PaintingsQuestionCategoryEnum.class;
    }

    @Override
    public Class<PaintingsQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return PaintingsQuestionDifficultyLevel.class;
    }

    @Override
    public Class<PaintingsSpecificResource> getSpecificResourceTypeEnum() {
        return PaintingsSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new PaintingsQuestionConfigService();
    }

    @Override
    public TournamentGameScreenCreator getTournamentGameScreenCreator() {
        return new PaintingsTournamentGameScreenCreator();
    }
}
