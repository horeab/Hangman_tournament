package libgdx.ui.screens.levelfinished.tournament;

import java.util.List;

import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.AbstractLevelFinishedScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.controls.user.UserInfoContainerCreator;

public abstract class AbstractLevelFinishedTournamentScreen extends AbstractLevelFinishedScreen {

    private TournamentContext tournamentContext;

    AbstractLevelFinishedTournamentScreen(TournamentContext tournamentContext, UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
        this.tournamentContext = tournamentContext;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        getGameFinishedInfoMessagesTable().add(new UserInfoContainerCreator().createUserInfoContainer(createUserInfoContainerConfig())).row();
        addShareButton();
    }

    @Override
    public MyButton createPlayAgainButton() {
        return new ButtonBuilder(TournamentGameLabel.level_finished_play_again.getText()).setDefaultButton().build();
    }

    @Override
    public void onPlayAgainButtonClick() {
        screenManager.showTournamentOnlineOptionsScreen();
    }

    @Override
    protected void refreshScreen() {
        boolean currentUserWon = getUsersWithLevelFinished().getUserThatWon().equals(getCurrentUser());
        GameUser currentUser = currentUserWon ? getUsersWithLevelFinished().getGameUserThatWon() : getUsersWithLevelFinished().getGameUserThatLost();
        GameUser opponent = currentUserWon ? getUsersWithLevelFinished().getGameUserThatLost() : getUsersWithLevelFinished().getGameUserThatWon();
        screenManager.showLevelFinishedTournamentScreen(tournamentContext, getGameContext());
    }

    public TournamentContext getTournamentContext() {
        return tournamentContext;
    }

    protected abstract List<UserInfoContainerConfig> createUserInfoContainerConfig();

}
