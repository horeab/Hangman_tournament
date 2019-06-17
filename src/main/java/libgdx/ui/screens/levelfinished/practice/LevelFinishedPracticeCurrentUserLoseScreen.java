package libgdx.ui.screens.levelfinished.practice;

import java.util.List;

import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.model.RGBColor;

public class LevelFinishedPracticeCurrentUserLoseScreen extends LevelFinishedPracticeScreen {

    public LevelFinishedPracticeCurrentUserLoseScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        setBackgroundColor(RGBColor.LIGHT_RED1);
    }


    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        return levelFinishedContainersCreator.create1PlayersCurrentUserLoseUserInfoContainerConfig();
    }

    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_fail_msg1.getText();
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_fail_msg2.getText();
    }

}
