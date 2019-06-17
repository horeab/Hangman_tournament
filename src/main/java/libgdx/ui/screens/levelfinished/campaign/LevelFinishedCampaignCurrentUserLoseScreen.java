package libgdx.ui.screens.levelfinished.campaign;

import java.util.List;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.model.RGBColor;

public class LevelFinishedCampaignCurrentUserLoseScreen extends LevelFinishedCampaignScreen {

    public LevelFinishedCampaignCurrentUserLoseScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext, CampaignLevel campaignLevelEnum) {
        super(usersWithLevelFinished, gameContext, campaignLevelEnum);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        setBackgroundColor(RGBColor.LIGHT_RED1);
        addPlayAgainButton();
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
