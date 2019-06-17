package libgdx.ui.screens.levelfinished.tournament;

import java.util.List;

import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.model.RGBColor;

public class LevelFinishedTournamentOpponentWinScreen extends AbstractLevelFinishedTournamentScreen {

    public LevelFinishedTournamentOpponentWinScreen(TournamentContext tournamentContext, UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(tournamentContext, usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        addPlayAgainButton();
        setBackgroundColor(RGBColor.LIGHT_RED1);
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_fail_msg2.getText();
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_fail_msg1.getText();
    }

    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        return levelFinishedContainersCreator.create2PlayersOpponentWinUserInfoContainerConfig();
    }

}
