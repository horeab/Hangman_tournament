package libgdx.ui.game;

import libgdx.controls.labelimage.InventoryTableBuilderCreator;
import libgdx.controls.popup.RatingService;
import libgdx.game.MainDependencyManager;
import libgdx.resources.ResourceService;
import libgdx.transactions.TransactionsService;
import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilderCreator;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.services.TournamentRatingService;
import libgdx.ui.services.game.TournamentTransactionsService;
import libgdx.ui.services.TournamentGameResourceService;

public class TournamentMainDependencyManager extends MainDependencyManager<ScreenManager, AbstractScreen, TournamentGameLabel, Resource, GameIdEnum> {

    @Override
    public Class<Resource> getMainResourcesClass() {
        return Resource.class;
    }

    @Override
    public Class<GameIdEnum> getGameIdClass() {
        return GameIdEnum.class;
    }

    @Override
    public Class<TournamentGameLabel> getGameLabelClass() {
        return TournamentGameLabel.class;
    }

    @Override
    public ResourceService createResourceService() {
        return new TournamentGameResourceService();
    }

    @Override
    public RatingService createRatingService(AbstractScreen abstractScreen) {
        return new TournamentRatingService(abstractScreen);
    }

    @Override
    public ScreenManager createScreenManager() {
        return new ScreenManager();
    }

    @Override
    public InventoryTableBuilderCreator createInventoryTableBuilderCreator() {
        return new TournamentInventoryTableBuilderCreator();
    }

    @Override
    public TransactionsService getTransactionsService() {
        return new TournamentTransactionsService();
    }
}
