package libgdx.ui.screens.actionoptions.gamescreens;



import java.util.ArrayList;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.actionoptions.OptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.utils.DateUtils;

public class PracticeOptionsScreen extends OptionsScreen {

    public PracticeOptionsScreen() {
        super(GameType.PRACTICE);
    }

    @Override
    public void executeOption(GameTypeStage gameTypeStage) {
        new StartScreenWithCoinsService(getCurrentUser(), new GameConfig(getDefaultQuestionConfig(), gameTypeStage, DateUtils.getNowDateString())).startPractice();
    }

    @Override
    public void executeOptionAnon() {
        QuestionConfig questionConfig = getDefaultQuestionConfig();
        screenManager.startGamePracticeScreen(
                new GameContextService().createGameContext(getCurrentUser(), new ArrayList<BaseUserInfo>(),
                        new GameConfig(questionConfig, GameTypeStage.practice_1, DateUtils.getNowDateString())));
    }

    public static QuestionConfig getDefaultQuestionConfig() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getPracticeQuestionConfig();
    }
}
