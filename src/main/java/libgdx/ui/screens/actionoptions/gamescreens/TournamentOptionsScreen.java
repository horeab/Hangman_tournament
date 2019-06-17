package libgdx.ui.screens.actionoptions.gamescreens;



import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.screens.actionoptions.OptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;

public class TournamentOptionsScreen extends OptionsScreen {

    public TournamentOptionsScreen() {
        super(GameType.TOURNAMENT);
    }

    @Override
    public void executeOption(GameTypeStage gameTypeStage) {
        new StartScreenWithCoinsService(getCurrentUser(), new GameConfig(getDefaultQuestionConfig(), gameTypeStage, DateUtils.getNowDateString())).startTournament();
    }

    @Override
    public void executeOptionAnon() {
        screenManager.startNewTournamentOnlineScreen(TestDataCreator.createAnonUsers(), new GameConfig(getDefaultQuestionConfig(), GameTypeStage.tournament_1, DateUtils.getNowDateString()));
    }

    private static QuestionConfig getDefaultQuestionConfig() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getTournamentQuestionConfig();
    }
}
