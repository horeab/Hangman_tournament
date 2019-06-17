package libgdx.ui.screens.levelfinished.campaign;

import java.util.List;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.levelfinished.AbstractLevelFinishedScreen;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.utils.DateUtils;

public abstract class LevelFinishedCampaignScreen extends AbstractLevelFinishedScreen {

    protected CampaignLevel campaignLevelEnum;

    public LevelFinishedCampaignScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext, CampaignLevel campaignLevelEnum) {
        super(usersWithLevelFinished, gameContext);
        this.campaignLevelEnum = campaignLevelEnum;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        getGameFinishedInfoMessagesTable().add(new UserInfoContainerCreator().createUserInfoContainer(createUserInfoContainerConfig())).row();
        addShareButton();
    }

    protected abstract List<UserInfoContainerConfig> createUserInfoContainerConfig();

    @Override
    public MyButton createPlayAgainButton() {
        return new ButtonBuilder(TournamentGameLabel.level_finished_play_again.getText()).setDefaultButton().build();
    }

    @Override
    public void onPlayAgainButtonClick() {
        new StartScreenWithCoinsService(getCurrentUser(), new GameConfig(getGameContext().getGameConfig().getQuestionConfig(), getGameContext().getGameConfig().getGameTypeStage(), DateUtils.getNowDateString())).startCampaign(campaignLevelEnum);
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showCampaignScreen();
    }

    @Override
    protected void refreshScreen() {
        GameUser gameUser = getUsersWithLevelFinished().getGameUserThatWon() != null ? getUsersWithLevelFinished().getGameUserThatWon() : getUsersWithLevelFinished().getGameUserThatLost();
        screenManager.showLevelFinishedCampaignScreen(getGameContext(), campaignLevelEnum);
    }

}
