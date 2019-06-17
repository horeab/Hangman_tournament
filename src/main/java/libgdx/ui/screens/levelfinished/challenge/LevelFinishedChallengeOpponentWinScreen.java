package libgdx.ui.screens.levelfinished.challenge;

import java.util.List;

import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.model.RGBColor;

public class LevelFinishedChallengeOpponentWinScreen extends AbstractLevelFinishedChallengeScreen {

    public LevelFinishedChallengeOpponentWinScreen(UsersWithLevelFinished usersWithLevelFinished, int liveGameId, GameContext gameContext) {
        super(usersWithLevelFinished, liveGameId, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        setBackgroundColor(RGBColor.LIGHT_RED1);
    }


    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_fail_msg1.getText();
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_fail_msg2.getText();
    }

    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        List<UserInfoContainerConfig> configs = levelFinishedContainersCreator.create2PlayersOpponentWinUserInfoContainerConfig();
        for (UserInfoContainerConfig config : configs) {
            config.setWithScoreLabel();
        }
        return configs;
    }

}
