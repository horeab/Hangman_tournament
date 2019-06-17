package libgdx.ui.implementations.games.hangmanarena.questionconfig;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.services.questions.QuestionConfigService;
import libgdx.ui.util.TournamentStage;

public class HangmanArenaQuestionConfigService implements QuestionConfigService {

    @Override
    public QuestionConfig getTournamentQuestionConfig() {
        return new QuestionConfig(TournamentStage.STAGE_0.getRandomQuestionDifficultyLevel(), HangmanArenaQuestionCategoryEnum.cat5, 1);
    }

    @Override
    public QuestionConfig getPracticeQuestionConfig() {
        return new QuestionConfig(HangmanArenaQuestionCategoryEnum.cat5, 1);
    }

    @Override
    public QuestionConfig getChallengeQuestionConfig() {
        return new QuestionConfig(HangmanArenaQuestionCategoryEnum.cat5, 1);
    }

    @Override
    public int getTournamentAmountAvailableHints() {
        return 3;
    }

    @Override
    public int getCampaignAmountAvailableHints() {
        return 2;
    }

    @Override
    public int getChallengeAmountAvailableHints() {
        return getCampaignAmountAvailableHints();
    }

    @Override
    public int getPracticeAmountAvailableHints() {
        return getCampaignAmountAvailableHints();
    }

    @Override
    public int getCampaignLevelAmountQuestions() {
        return 3;
    }

    @Override
    public int getStageFinishedCampaignLevelAmountQuestions() {
        return getCampaignLevelAmountQuestions();
    }

    @Override
    public List<QuestionDifficulty> getEasyDiff() {
        return Arrays.<QuestionDifficulty>asList(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionDifficultyLevel._1);
    }

    @Override
    public List<QuestionDifficulty> getMediumDiff() {
        return Arrays.<QuestionDifficulty>asList(HangmanArenaQuestionDifficultyLevel._2, HangmanArenaQuestionDifficultyLevel._3);
    }

    @Override
    public List<QuestionDifficulty> getHardDiff() {
        return Arrays.<QuestionDifficulty>asList(HangmanArenaQuestionDifficultyLevel._4, HangmanArenaQuestionDifficultyLevel._5);
    }
}
