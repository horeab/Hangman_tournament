package libgdx.ui.screens.levelfinished.tournament;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.util.TournamentStage;
import libgdx.utils.model.RGBColor;

public class LevelFinishedTournamentCurrentUserWinScreen extends AbstractLevelFinishedTournamentScreen {

    public LevelFinishedTournamentCurrentUserWinScreen(TournamentContext tournamentContext, UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        super(tournamentContext, usersWithLevelFinished, gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        addContinueButton();
        setBackgroundColor(RGBColor.LIGHT_BLUE);
        modifyStats();
    }

    private void modifyStats() {
        if (getTournamentContext().getTournamentStage() == TournamentStage.STAGE_2) {
            gameStatsDbApiService.incrementGameStatsTournamentsWon(getCurrentUser().getId(), getGameContext().getGameConfig().getUniqueId());
        }
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_success_msg2.getText();
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.tournamentWin(getTournamentContext().getTournamentStage(), getGameContext().getGameConfig());
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_tournament_info_msg).addToPopupManager();
    }

    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_success_msg1.getText();
    }

    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        return levelFinishedContainersCreator.create2PlayersCurrentUserWinUserInfoContainerConfig();
    }

    private void addContinueButton() {
        MyButton continueBtn = new ButtonBuilder(TournamentGameLabel.level_finished_continue.getText()).setDefaultButton().build();
        getGameFinishedInfoMessagesTable().add(continueBtn).width(Dimen.width_btn_menu_normal.getDimen()).height(Dimen.height_btn_menu_normal.getDimen()).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (getTournamentContext().getTournamentStage() != TournamentStage.STAGE_2) {
                    getTournamentContext().setAvailableHints(getGameContext().getAmountAvailableHints());
                    screenManager.goToNextStageTournamentScreen(getTournamentContext(), getGameContext().getGameConfig());
                } else {
                    screenManager.showLevelFinishedTournamentWinScreen(getUsersWithLevelFinished(), getGameContext());
                }
            }
        });
    }
}
