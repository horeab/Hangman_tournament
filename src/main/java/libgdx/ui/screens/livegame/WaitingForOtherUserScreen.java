package libgdx.ui.screens.livegame;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.CountDownCounter;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.model.game.LiveGame;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.listeners.livegame.beforegame.AcceptLiveGameListener;
import libgdx.utils.ScreenDimensionsManager;

public class WaitingForOtherUserScreen extends AbstractScreen implements ScreenWithGameContext {

    private static final int COUNTDOWN_AMOUNT_MILLIS = 15000;

    private LiveGameService liveGameService = new LiveGameService();

    private GameContext gameContext;
    private int liveGameId;

    public WaitingForOtherUserScreen(GameContext gameContext, int liveGameId) {
        this.liveGameId = liveGameId;
        this.gameContext = gameContext;
    }

    @Override
    public void addListenersToScreen() {
        super.addListenersToScreen();
        addListenerToScreen(new AcceptLiveGameListener(gameContext, liveGameId, this));
    }

    @Override
    public GameContext getGameContext() {
        return gameContext;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        Table allTable = new Table();
        final Table infoTable = new Table();
        final Table playerNotJoinTable = createPlayerNotJoinTable();
        CountDownCounter countDownCounter = new CountDownCounter(COUNTDOWN_AMOUNT_MILLIS, String.valueOf(0), this) {
            @Override
            public void executeAfterCountDownCounter() {
                getCountdownCounterLabel().remove();
                liveGameService.deleteLiveGame(getCurrentUser().getId(), gameContext.getOpponentGameUser().getBaseUserInfo().getId());
                infoTable.clearChildren();
                infoTable.add(playerNotJoinTable);
            }
        };
        countDownCounter.start();
        Table waitingTable = createWaitingTable(countDownCounter.getCountdownCounterLabel());
        infoTable.add(waitingTable);
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        allTable.add(new UserStatusBar(this).createTable()).padBottom(dimen).padTop(dimen).top().row();
        allTable.add(infoTable).expand();
        allTable.setFillParent(true);
        addActor(allTable);
    }

    private Table createWaitingTable(MyWrappedLabel countdownCounterLabel) {
        Table table = new Table();
        MyWrappedLabel waitingLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setStyleDependingOnContrast().setText(TournamentGameLabel.live_game_waiting_player.getText
                (gameContext.getOpponentGameUser().getBaseUserInfo().getFullName())).build());
        table.add(waitingLabel).align(Align.center).width(ScreenDimensionsManager.getScreenWidthValue(90)).row();
        table.add(countdownCounterLabel).height(ScreenDimensionsManager.getScreenHeightValue(10)).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        return table;
    }

    private Table createPlayerNotJoinTable() {
        Table table = new Table();
        final BaseUserInfo opponent = gameContext.getOpponentGameUser().getBaseUserInfo();
        MyButton resendChallengeButton = new ButtonBuilder(TournamentGameLabel.live_game_resend_challenge.getText()).setFixedButtonSize(ButtonSize.NORMAL_SIZE).setButtonSkin(ButtonSkin.GREEN).build();
        resendChallengeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                LiveGame liveGame = liveGameService.createLiveGameWithLiveGameInvite(getCurrentUser(), opponent.getId(), getGameContext().getGameConfig());
                new StartScreenWithCoinsService(getCurrentUser(), getGameContext().getGameConfig()).startChallenge(opponent, liveGame.getQuestionsArray(), liveGame.getId());
            }
        });
        MyWrappedLabel playerNotJoin = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setStyleDependingOnContrast().setText(TournamentGameLabel.live_game_other_player_not_join.getText(opponent.getFullName())).build());
        table.add(playerNotJoin).align(Align.center).width(ScreenDimensionsManager.getScreenWidthValue(90)).row();
        float horizontalMargin = MainDimen.horizontal_general_margin.getDimen();
        resendChallengeButton.setWidth(resendChallengeButton.getPrefWidth() + horizontalMargin * 2);
        table.add(resendChallengeButton).height(resendChallengeButton.getHeight()).width(resendChallengeButton.getWidth()).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        return table;
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_challenge_info_msg,
                new Runnable() {
                    @Override
                    public void run() {
                        liveGameService.deleteLiveGame(getCurrentUser().getId(), gameContext.getOpponentGameUser().getBaseUserInfo().getId());
                        screenManager.showSearchUserScreen();
                    }
                }).addToPopupManager();
    }

}
