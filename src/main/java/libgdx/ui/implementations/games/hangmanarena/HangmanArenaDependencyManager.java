package libgdx.ui.implementations.games.hangmanarena;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.geoquiz.questionconfig.GeoQuizQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaCampaignLevelEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionConfigService;
import libgdx.ui.implementations.games.hangmanarena.resources.HangmanArenaSpecificResource;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public class HangmanArenaDependencyManager extends TournamentGameDependencyManager {


    @Override
    public List<ImageCategIncrementRes> getImageCategIncrementResList() {
        return new ArrayList<>();
    }

    @Override
    public Class<HangmanArenaCampaignLevelEnum> getCampaignLevelTypeEnum() {
        return HangmanArenaCampaignLevelEnum.class;
    }

    @Override
    public Class<HangmanArenaQuestionCategoryEnum> getQuestionCategoryTypeEnum() {
        return HangmanArenaQuestionCategoryEnum.class;
    }

    @Override
    public Class<HangmanArenaQuestionDifficultyLevel> getQuestionDifficultyTypeEnum() {
        return HangmanArenaQuestionDifficultyLevel.class;
    }

    @Override
    public Class<HangmanArenaSpecificResource> getSpecificResourceTypeEnum() {
        return HangmanArenaSpecificResource.class;
    }

    @Override
    public QuestionConfigService getQuestionConfigService() {
        return new HangmanArenaQuestionConfigService();
    }

}
