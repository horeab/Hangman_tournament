package libgdx.ui.services.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.NotEnoughCoinsOptionsPopup;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.ScreenManager;
import libgdx.ui.model.game.question.Question;

public class StartScreenWithCoinsService {

    private ScreenManager screenManager = TournamentGame.getInstance().getScreenManager();
    private LiveGameService liveGameService = new LiveGameService();
    private GameConfig gameConfig;
    private BaseUserInfo currentUser;

    public StartScreenWithCoinsService(BaseUserInfo currentUser, GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.currentUser = currentUser;
    }


    public void startCampaign(final CampaignLevel campaignLevelEnum) {
        run(new Runnable() {
            @Override
            public void run() {
                screenManager.startGameCampaignScreen(new GameContextService().createGameContext(currentUser, new ArrayList<BaseUserInfo>(), gameConfig), campaignLevelEnum);
            }
        });
    }

    public void startPractice() {
        run(new Runnable() {
            @Override
            public void run() {
                screenManager.startGamePracticeScreen(new GameContextService().createGameContext(currentUser, new ArrayList<BaseUserInfo>(), gameConfig));
            }
        });
    }

    public void startChallenge(final BaseUserInfo opponent, final Question[] questions, final int liveGameId) {
        run(new Runnable() {
            @Override
            public void run() {
                waitGameStart(liveGameId, opponent, questions);
            }
        });
    }

    private void waitGameStart(int liveGameId, BaseUserInfo opponent, Question[] questions) {
        liveGameService.waitingGameStart(liveGameId);
        screenManager.showWaitingForUserScreen(
                new GameContextService().createGameContext(currentUser, Collections.singletonList(opponent), gameConfig, questions), liveGameId);
    }

    public void startTournament() {
        final AbstractScreen abstractScreen = TournamentGame.getInstance().getAbstractScreen();
        run(new Runnable() {
            @Override
            public void run() {
                new Thread(new ScreenRunnable(abstractScreen) {
                    @Override
                    public void executeOperations() {
                        final List<BaseUserInfo> usersToChooseForTournament = new UsersDbApiService().selectUsersForTournament();
                        Gdx.app.postRunnable(new ScreenRunnable(abstractScreen) {
                            @Override
                            public void executeOperations() {
                                screenManager.startNewTournamentOnlineScreen(usersToChooseForTournament, gameConfig);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void run(Runnable runnableIfEnoughCoins) {
        ShopTransactionTypeEnum entryFeeTransaction = gameConfig.getGameTypeStage().getEntryFeeTransaction();
        if (new GameTransactionsService(currentUser.getId()).userHasEnoughResourcesToStartAction(entryFeeTransaction.getTransactionAmountEnum())
                ||
                new GameTransactionsService(currentUser.getId()).isTransactionUniqueAndAlreadyExecuted(entryFeeTransaction, gameConfig.getGameTypeStage().isUniqueTransaction())
                ||
                !TournamentGame.getInstance().hasInternet()) {
            runnableIfEnoughCoins.run();
        } else {
            new NotEnoughCoinsOptionsPopup(TournamentGame.getInstance().getAbstractScreen(), gameConfig, entryFeeTransaction.getTransactionAmountEnum()).addToPopupManager();
        }
    }
}
