package libgdx.ui.screens.levelfinished.tournament;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Arrays;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.controls.animation.ImageAnimation;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.labelimage.prize.WinLevelUpPrizeLabelImage;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.levelfinished.AbstractLevelFinishedScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.ActorPositionManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class LevelFinishedTournamentWinScreen extends AbstractLevelFinishedScreen {

    public LevelFinishedTournamentWinScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        addTrophyBackgroundPicture();
        super.buildStage();
        addPrizeInfo();
        getGameFinishedInfoMessagesTable().add(new UserInfoContainerCreator().createUserInfoContainer(Arrays.asList(new UserInfoContainerConfig(getUsersWithLevelFinished().getGameUserThatWon(), UserInfoContainerConfig.ContainerColor.WHITE)))).row();
        addShareButton();
        addPlayAgainButton();
        addActor(new ImageAnimation(this).animateWinTrophy());
    }

    private void addPrizeInfo() {
        Table prizeTable = new WinLevelUpPrizeLabelImage(getCurrentUser().getId(), getGameContext().getGameConfig().getGameTypeStage(), getGameContext().getGameConfig().getUniqueId()).create();
        getGameFinishedInfoMessagesTable().add(prizeTable).expandX()
                .padBottom(MainDimen.vertical_general_margin.getDimen())
                .width(ScreenDimensionsManager.getScreenWidthValue(80))
                .row();
    }

    private void addTrophyBackgroundPicture() {
        Image trophyBig = GraphicUtils.getImage(Resource.trophy_big);
        ActorPositionManager.setActorCenterScreen(trophyBig);
        trophyBig.setX(trophyBig.getX() + ScreenDimensionsManager.getScreenWidthValue(10));
        trophyBig.rotateBy(20);
        addActor(trophyBig);
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
        screenManager.showLevelFinishedTournamentWinScreen(getUsersWithLevelFinished(), getGameContext());
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }


    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_tournamnet_success_msg1.getText();
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_tournamnet_success_msg2.getText();
    }

    @Override
    protected void shareContent() {
        TournamentGame.getInstance().getFacebookService().shareApp();
    }

}
