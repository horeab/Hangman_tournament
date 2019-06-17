package libgdx.ui.screens.actionoptions.gamescreens;



import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.actionoptions.OptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.utils.DateUtils;

public class ChallengeOptionsScreen extends OptionsScreen {

    private BaseUserInfo opponent;

    public ChallengeOptionsScreen(BaseUserInfo opponent) {
        super(GameType.CHALLENGE);
        this.opponent = opponent;
    }

    @Override
    public void executeOption(GameTypeStage gameTypeStage) {
        GameConfig gameConfig = new GameConfig(getDefaultQuestionConfig(), gameTypeStage, DateUtils.getNowDateString());
        BaseUserInfo currentUser = getCurrentUser();
        LiveGame liveGame = new LiveGameService().createLiveGameWithLiveGameInvite(currentUser, opponent.getId(), gameConfig);
        new StartScreenWithCoinsService(getCurrentUser(), gameConfig).startChallenge(opponent, liveGame.getQuestionsArray(), liveGame.getId());
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showSearchUserScreen();
    }

    @Override
    public void executeOptionAnon() {
    }

    private static QuestionConfig getDefaultQuestionConfig() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getChallengeQuestionConfig();
    }

}
