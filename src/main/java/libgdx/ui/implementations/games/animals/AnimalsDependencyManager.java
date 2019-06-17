package libgdx.ui.implementations.games.animals;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.animals.questionconfig.AnimalsCampaignLevelEnum;
import libgdx.ui.implementations.games.animals.questionconfig.AnimalsQuestionCategoryEnum;
import libgdx.ui.implementations.games.animals.questionconfig.AnimalsQuestionConfigService;
import libgdx.ui.implementations.games.animals.questionconfig.AnimalsQuestionDifficultyLevel;
import libgdx.ui.implementations.games.animals.resources.AnimalsSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;
import libgdx.utils.model.RGBColor;

public class AnimalsDependencyManager extends TournamentGameDependencyManager {

    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        List<ImageCategIncrementRes> list = new ArrayList<>();
        list.add(new ImageCategIncrementRes(0, 43, AnimalsQuestionCategoryEnum.cat0, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 11, AnimalsQuestionCategoryEnum.cat1, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 6, AnimalsQuestionCategoryEnum.cat2, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 9, AnimalsQuestionCategoryEnum.cat3, ImageCategIncrementRes.JPG));
        list.add(new ImageCategIncrementRes(0, 8, AnimalsQuestionCategoryEnum.cat4, ImageCategIncrementRes.JPG));
        return list;
    }

    @Override
    public RGBColor getScreenBackgroundColor() {
        return new RGBColor(1, 0, 183, 0);
    }

    @Override
    public Class<AnimalsCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return AnimalsCampaignLevelEnum.class;
    }

    @Override
    public Class<AnimalsQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return AnimalsQuestionCategoryEnum.class;
    }

    @Override
    public Class<AnimalsQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return AnimalsQuestionDifficultyLevel.class;
    }

    @Override
    public Class<AnimalsSpecificResource> getSpecificResourceTypeEnum() {
        return AnimalsSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new AnimalsQuestionConfigService();
    }

}
