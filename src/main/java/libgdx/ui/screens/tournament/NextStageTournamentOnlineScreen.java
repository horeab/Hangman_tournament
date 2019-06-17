package libgdx.ui.screens.tournament;

import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.services.tournament.TournamentService;
import libgdx.ui.util.TournamentStage;

public class NextStageTournamentOnlineScreen extends AbstractTournamentScreen {

    public NextStageTournamentOnlineScreen(TournamentContext tournamentContext, GameConfig gameConfig) {
        super(tournamentContext, gameConfig);
    }

    @Override
    public void buildStage() {
        goToNextTournamentStage(getTournamentContext());
        super.buildStage();
    }

    private void goToNextTournamentStage(TournamentContext tournamentContext) {
        new TournamentService().terminateCurrentStageAndGoToNextStage(tournamentContext.getTournamentUsers(), tournamentContext.getTournamentStage());
        setTournamentStage(TournamentStage.getNextStage(tournamentContext.getTournamentStage()));
        QuestionConfig questionConfig = getGameConfig().getQuestionConfig();
        QuestionConfig newQuestionConfig = new QuestionConfig(tournamentContext.getTournamentStage().getRandomQuestionDifficultyLevel(), questionConfig.getRandomCategoryAndDifficulty().getQuestionCategory(), questionConfig.getAmountOfQuestions());
        getGameConfig().setQuestionConfig(newQuestionConfig);
    }
}