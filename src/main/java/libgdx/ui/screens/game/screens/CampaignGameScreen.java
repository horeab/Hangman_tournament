package libgdx.ui.screens.game.screens;

import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentScreenCreator;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;

public class CampaignGameScreen extends AbstractGameScreen<SimulatedOpponentScreenCreator> implements ScreenWithGameContext {

    private CampaignLevel campaignLevelEnum;

    public CampaignGameScreen(GameContext gameContext, CampaignLevel campaignLevelEnum) {
        super(gameContext);
        this.campaignLevelEnum = campaignLevelEnum;
    }

    @Override
    public void afterBuildStage() {
        super.afterBuildStage();
        getScreenCreator().getGameScreenQuestionContainerCreator().getCurrentUserHeaderAnimationService().gameInfoChanged();
    }

    @Override
    protected SimulatedOpponentScreenCreator initScreenCreator(GameContext gameContext) {
        return new SimulatedOpponentScreenCreator(this, gameContext);
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gamePay(getGameConfig());
    }

    @Override
    public void executeLevelFinished() {
        screenManager.showLevelFinishedCampaignScreen(getGameContext(), campaignLevelEnum);
    }

    @Override
    public void goToNextQuestionScreen() {
        screenManager.startGameCampaignScreen(getGameContext(), campaignLevelEnum);
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showCampaignScreen();
    }

    @Override
    public LevelFinishedService getLevelFinishedService() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getSinglePlayerLevelFinishedService();
    }
}
