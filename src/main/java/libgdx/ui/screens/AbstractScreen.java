package libgdx.ui.screens;

import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.services.dbapi.GameStatsDbApiService;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.dbapi.UserGamesDbApiService;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.services.listeners.ListenerAll;
import libgdx.ui.services.listeners.user.UserExtraInfoListener;

public abstract class AbstractScreen extends libgdx.screen.AbstractScreen<ScreenManager> {

    private List<ListenerAll> screenListeners = new ArrayList<>();
    protected UsersDbApiService usersDbApiService;
    protected LiveGameDbApiService liveGameDbApiService;
    protected GameStatsDbApiService gameStatsDbApiService;
    protected ShopTransactionsDbApiService shopTransactionsDbApiService;
    protected UserGamesDbApiService userGamesDbApiService;
    protected GameTransactionsService gameTransactionsService;
    protected UserInventoryDbApiService userInventoryDbApiService;

    private BaseUserInfo currentUser;

    protected AbstractScreen() {
        this.currentUser = TournamentGame.getInstance().getCurrentUser();
        this.usersDbApiService = new UsersDbApiService();
        this.gameStatsDbApiService = new GameStatsDbApiService();
        this.liveGameDbApiService = new LiveGameDbApiService();
        this.shopTransactionsDbApiService = new ShopTransactionsDbApiService();
        this.userGamesDbApiService = new UserGamesDbApiService();
        this.userInventoryDbApiService = new UserInventoryDbApiService();
        this.gameTransactionsService = new GameTransactionsService(getCurrentUser().getId());
        usersDbApiService.updateLastTimeActiveDateForUser(currentUser.getId());
    }

    public BaseUserInfo getCurrentUser() {
        return currentUser;
    }

    private void delayDisplayNotifications() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (isScreenValid()) {
                    displayNotifications();
                }
            }
        }, 0.1f);
    }

    public void addListenersToScreen() {
        addListenerToScreen(new UserExtraInfoListener(currentUser.getId(), this));
    }

    protected void addListenerToScreen(ListenerAll listener) {
        screenListeners.add(listener);
    }

    private void startListeners() {
        if (TournamentGame.getInstance().hasInternet()) {
            for (ListenerAll listener : screenListeners) {
                listener.start();
            }
        }
    }

    public GameStatsDbApiService getGameStatsDbApiService() {
        return gameStatsDbApiService;
    }

    @Override
    public void buildStage() {
        initFields();
        executeShopTransactions();
        addListenersToScreen();
        delayDisplayNotifications();
    }

    @Override
    public void afterBuildStage() {
        startListeners();
    }

}