package libgdx.ui.implementations.games.scoalasofer.questionconfig;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferSinglePlayerLevelFinishedService;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.services.questions.StandardQuestionConfigService;

public class ScoalaSoferQuestionConfigService extends StandardQuestionConfigService {

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Arrays.<QuestionDifficulty>asList(ScoalaSoferQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Arrays.<QuestionDifficulty>asList(ScoalaSoferQuestionDifficultyLevel._0);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(ScoalaSoferQuestionDifficultyLevel._1);
    }

    @Override
    public QuestionConfig getPracticeQuestionConfig() {
        return new QuestionConfig(ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS);
    }

    @Override
    public int getStageFinishedCampaignLevelAmountQuestions() {
        return ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS;
    }

    @Override
    public int getPracticeAmountAvailableHints() {
        return 0;
    }

    @Override
    public int getCampaignAmountAvailableHints() {
        return 0;
    }

    @Override
    public int getTournamentAmountAvailableHints() {
        return 0;
    }

    @Override
    public int getChallengeAmountAvailableHints() {
        return 0;
    }
}
