package libgdx.ui.screens.levelfinished.challenge;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.List;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.controls.popup.notificationpopup.MyNotificationPopup;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.levelfinished.AbstractLevelFinishedScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameRematchService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.listeners.livegame.aftergame.OpponentRematchStatusChangeListener;
import libgdx.ui.services.listeners.livegame.aftergame.RematchGameListener;
import libgdx.utils.DateUtils;

public abstract class AbstractLevelFinishedChallengeScreen extends AbstractLevelFinishedScreen {

    private int liveGameId;
    private LiveGameService liveGameService = new LiveGameService();
    private LiveGameRematchService liveGameRematchService = new LiveGameRematchService();

    public AbstractLevelFinishedChallengeScreen(UsersWithLevelFinished usersWithLevelFinished, int liveGameId, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
        this.liveGameId = liveGameId;
    }

    @Override
    public void addListenersToScreen() {
        super.addListenersToScreen();
        addListenerToScreen(new RematchGameListener<>(liveGameId, this));
        addListenerToScreen(new OpponentRematchStatusChangeListener<>(liveGameId, this));
    }

    @Override
    public void buildStage() {
        super.buildStage();
        addExtraInfo();
        Table userInfoContainer = new UserInfoContainerCreator().createUserInfoContainer(createUserInfoContainerConfig());
        getGameFinishedInfoMessagesTable().add(userInfoContainer).height(userInfoContainer.getPrefHeight()).width(userInfoContainer.getPrefWidth()).row();
        addShareButton();
        addPlayAgainButton();
    }

    @Override
    public MyButton createPlayAgainButton() {
        MyButton playAgainButton = new ButtonBuilder(TournamentGameLabel.level_finished_play_again.getText()).setButtonName(TournamentGameLabel.level_finished_play_again.getText()).setDefaultButton().build();
        if (displayCurrentUserRematchPopup(liveGameService.getCurrentUserStatus(liveGameId)) != null) {
            playAgainButton.setDisabled(true);
        }
        return playAgainButton;
    }

    @Override
    protected void displayNotifications() {
        super.displayNotifications();
        displayCurrentUserRematchPopup(liveGameService.getCurrentUserStatus(liveGameId));
        liveGameRematchService.displayRematchNotificationPopup(
                liveGameService.getOpponentStatus(liveGameId),
                (Table) getRoot().findActor(UserInfoContainerCreator.getOpponentContainerName()));
    }

    @Override
    public void onPlayAgainButtonClick() {
        liveGameService.wantRematch(liveGameId);
        if (liveGameService.isUser1LoggedIn(liveGameId)) {
            GameConfig oldGameConfig = getGameContext().getGameConfig();
            liveGameService.createLiveGame(
                    getCurrentUser().getId(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2ID, Integer.class), new GameConfig(oldGameConfig.getQuestionConfig(), oldGameConfig.getGameTypeStage(), DateUtils.getNowDateString()));
        }
        MyButton playAgainButton = getRoot().findActor(TournamentGameLabel.level_finished_play_again.getText());
        playAgainButton.setDisabled(true);
        displayCurrentUserRematchPopup(LiveGameStatusEnum.WANT_MATCH.getStatus());
    }

    private MyNotificationPopup displayCurrentUserRematchPopup(int currentUserStatus) {
        return liveGameRematchService.displayRematchNotificationPopup(currentUserStatus,
                (Table) getRoot().findActor(UserInfoContainerCreator.getCurrentUserContainerName()));
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_challenge_info_msg,
                new Runnable() {
                    @Override
                    public void run() {
                        liveGameService.abandonMatch(liveGameId);
                        screenManager.showMainScreen();
                    }
                }).addToPopupManager();
    }

    @Override
    protected void refreshScreen() {
        GameUser currentUser;
        GameUser opponent;
        if (getUsersWithLevelFinished().getUserThatWon().equals(getCurrentUser())) {
            currentUser = getUsersWithLevelFinished().getGameUserThatWon();
            opponent = getUsersWithLevelFinished().getGameUserThatLost();
        } else {
            currentUser = getUsersWithLevelFinished().getGameUserThatLost();
            opponent = getUsersWithLevelFinished().getGameUserThatWon();
        }
        screenManager.showLevelFinishedChallengeScreen(getGameContext(), liveGameId);
    }

    //TO BE OVERRIDDEN
    protected void addExtraInfo() {
    }

    protected abstract List<UserInfoContainerConfig> createUserInfoContainerConfig();

}
