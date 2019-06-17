package libgdx.ui.services.questions;

import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.util.TournamentStage;

public abstract class StandardQuestionConfigService implements QuestionConfigService {

    @Override
    public QuestionConfig getTournamentQuestionConfig() {
        return new QuestionConfig(TournamentStage.STAGE_0.getRandomQuestionDifficultyLevel(), 5);
    }

    @Override
    public QuestionConfig getPracticeQuestionConfig() {
        return new QuestionConfig(5);
    }

    @Override
    public QuestionConfig getChallengeQuestionConfig() {
        return new QuestionConfig(5);
    }

    @Override
    public int getCampaignLevelAmountQuestions() {
        return 5;
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
    public int getStageFinishedCampaignLevelAmountQuestions() {
        return getCampaignLevelAmountQuestions();
    }

}
