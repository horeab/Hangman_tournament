package libgdx.ui.services.questions;


import java.util.List;

import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.question.QuestionConfig;

public interface QuestionConfigService {

    QuestionConfig getTournamentQuestionConfig();

    QuestionConfig getPracticeQuestionConfig();

    QuestionConfig getChallengeQuestionConfig();

    int getCampaignLevelAmountQuestions();

    int getStageFinishedCampaignLevelAmountQuestions();

    int getTournamentAmountAvailableHints();

    int getPracticeAmountAvailableHints();

    int getChallengeAmountAvailableHints();

    int getCampaignAmountAvailableHints();

    List<QuestionDifficulty> getEasyDiff();

    List<QuestionDifficulty> getMediumDiff();

    List<QuestionDifficulty> getHardDiff();

}
