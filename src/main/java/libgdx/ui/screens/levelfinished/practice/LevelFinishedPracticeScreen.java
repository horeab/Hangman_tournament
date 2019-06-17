package libgdx.ui.screens.levelfinished.practice;



import java.util.List;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.levelfinished.AbstractLevelFinishedScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.utils.DateUtils;

public abstract class LevelFinishedPracticeScreen extends AbstractLevelFinishedScreen {

    public LevelFinishedPracticeScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        addExtraInfo();
        getGameFinishedInfoMessagesTable().add(new UserInfoContainerCreator().createUserInfoContainer(createUserInfoContainerConfig())).row();
        addShareButton();
        addPlayAgainButton();
    }

    protected abstract List<UserInfoContainerConfig> createUserInfoContainerConfig();

    @Override
    public MyButton createPlayAgainButton() {
        return new ButtonBuilder(TournamentGameLabel.level_finished_play_again.getText()).setDefaultButton().build();
    }

    @Override
    public void onPlayAgainButtonClick() {
        new StartScreenWithCoinsService(getCurrentUser(), new GameConfig(getGameContext().getGameConfig().getQuestionConfig(), getGameContext().getGameConfig().getGameTypeStage(), DateUtils.getNowDateString())).startPractice();
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

    @Override
    protected void refreshScreen() {
        GameUser gameUser = getUsersWithLevelFinished().getGameUserThatWon() != null ? getUsersWithLevelFinished().getGameUserThatWon() : getUsersWithLevelFinished().getGameUserThatLost();
        screenManager.showLevelFinishedPracticeScreen(getGameContext());
    }

    //TO BE OVERRIDEN
    protected void addExtraInfo() {
    }

}
