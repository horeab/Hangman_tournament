package libgdx.ui.implementations.games.scoalasofer;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.StarsService;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.judetelerom.questionconfig.JudeteleRomQuestionCategoryEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferCampaignLevelEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionCategoryEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionConfigService;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionDifficultyLevel;
import libgdx.ui.implementations.games.scoalasofer.resources.ScoalaSoferSpecificResource;
import libgdx.ui.implementations.config.dependecies.SinglePlayerLevelFinishedService;
import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;
import libgdx.utils.model.RGBColor;

public class ScoalaSoferDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        ///////////////
        list.add(new ImageCategIncrementRes(17, 28, ScoalaSoferQuestionCategoryEnum.cat0, ImageCategIncrementRes.JPG));
        ///////////////
        list.add(new ImageCategIncrementRes(27, 46, ScoalaSoferQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(34, 53, ScoalaSoferQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(0, 2, ScoalaSoferQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(21, 86, ScoalaSoferQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(8, 19, ScoalaSoferQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(41, 123, ScoalaSoferQuestionCategoryEnum.cat5, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(1, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(9, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(11, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(14, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(19, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(21, 22, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(24, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(29, ScoalaSoferQuestionCategoryEnum.cat6, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(131, 164, ScoalaSoferQuestionCategoryEnum.cat7, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(55, 60, ScoalaSoferQuestionCategoryEnum.cat8, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(145, 155, ScoalaSoferQuestionCategoryEnum.cat10, ImageCategIncrementRes.JPG));
        /////////////////
        list.add(new ImageCategIncrementRes(59, 64, ScoalaSoferQuestionCategoryEnum.cat12, ImageCategIncrementRes.JPG));
        ///////////////
        return list;
    }

    @Override
    public RGBColor getScreenBackgroundColor() {
        return new RGBColor(185,183,232);
    }

    @Override
    public Class<ScoalaSoferCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return ScoalaSoferCampaignLevelEnum.class;
    }

    @Override
    public Class<ScoalaSoferQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return ScoalaSoferQuestionCategoryEnum.class;
    }

    @Override
    public Class<ScoalaSoferQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return ScoalaSoferQuestionDifficultyLevel.class;
    }

    @Override
    public Class<ScoalaSoferSpecificResource> getSpecificResourceTypeEnum() {
        return ScoalaSoferSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new ScoalaSoferQuestionConfigService();
    }

    @Override
    public SinglePlayerLevelFinishedService getSinglePlayerLevelFinishedService() {
        return new ScoalaSoferSinglePlayerLevelFinishedService();
    }

    @Override
    public StarsService getStarsService(int gameTotalNrOfQuestions) {
        return gameTotalNrOfQuestions == ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS ? new ScoalaSoferStarsService(gameTotalNrOfQuestions) : super.getStarsService(gameTotalNrOfQuestions);
    }

    @Override
    public TournamentGameScreenCreator getTournamentGameScreenCreator() {
        return new ScoalaSoferTournamentGameScreenCreator();
    }
}
