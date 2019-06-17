package libgdx.ui.screens.levelfinished.practice;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.List;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.labelimage.prize.WinLevelUpPrizeLabelImage;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.model.RGBColor;

public class LevelFinishedPracticeCurrentUserWinScreen extends LevelFinishedPracticeScreen {

    public LevelFinishedPracticeCurrentUserWinScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        setBackgroundColor(RGBColor.LIGHT_BLUE);
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gameWin(getGameContext().getGameConfig());
    }

    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        return levelFinishedContainersCreator.create1PlayersCurrentUserWinUserInfoContainerConfig();
    }

    @Override
    protected void addExtraInfo() {
        Table prizeTable = new WinLevelUpPrizeLabelImage(getCurrentUser().getId(), getGameContext().getGameConfig().getGameTypeStage(), getGameContext().getGameConfig().getUniqueId()).create();
        getGameFinishedInfoMessagesTable().add(prizeTable).expandX().padBottom(MainDimen.vertical_general_margin.getDimen()).row();
    }

    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_success_msg1.getText();
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_success_msg2.getText();
    }

}
