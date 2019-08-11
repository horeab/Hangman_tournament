package libgdx.ui.screens.livegame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.labelimage.InventoryLabelImageBuilder;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.controls.CountDownCounter;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.ui.controls.labelimage.prize.IfLevelUpPrizeLabelImage;
import libgdx.ui.controls.popup.ExitGamePopup;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.controls.user.UserInfoContainerCreator;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.resources.ResourcesManager;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.ActorPositionManager;
import libgdx.resources.FontManager;
import libgdx.utils.ScreenDimensionsManager;

public class PlayerVersusPlayerScreen extends AbstractScreen implements ScreenWithGameContext {

    public static final int COUNTDOWN_AMOUNT_MILLIS = 5000;

    private int liveGameId;
    private GameContext gameContext;

    public PlayerVersusPlayerScreen(GameContext gameContext, int liveGameId) {
        this.liveGameId = liveGameId;
        this.gameContext = gameContext;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        Table contentTable = new Table();
        addActor(contentTable);
        contentTable.setHeight(ScreenDimensionsManager.getScreenHeightValue(85));
        contentTable.setWidth(ScreenDimensionsManager.getScreenWidth());
        contentTable.setY(ScreenDimensionsManager.getScreenHeightValue(20));

        contentTable.add(createTitleLabel()).padBottom(MainDimen.horizontal_general_margin.getDimen()).padTop(MainDimen.horizontal_general_margin.getDimen() * 3).row();
        contentTable.add(createPrizeLabel()).row();
        contentTable.add(createCountDownCounter()).height(ScreenDimensionsManager.getScreenHeightValue(15)).row();
        contentTable.add(createPlayerContainer()).row();
    }

    @Override
    public GameContext getGameContext() {
        return gameContext;
    }

    private Stack createPlayerContainer() {
        List<UserInfoContainerConfig> userInfoContainerConfigs = new ArrayList<>();
        UserInfoContainerConfig config1 = new UserInfoContainerConfig(gameContext.getCurrentUserGameUser(), UserInfoContainerConfig.ContainerColor.GREEN);
        config1.setWithScoreLabel();
        userInfoContainerConfigs.add(config1);
        UserInfoContainerConfig config2 = new UserInfoContainerConfig(gameContext.getOpponentGameUser(), UserInfoContainerConfig.ContainerColor.GREEN);
        config2.setWithScoreLabel();
        userInfoContainerConfigs.add(config2);

        MyWrappedLabel vsLabel = new MyWrappedLabel("VS");
        vsLabel.setAlignment(Align.center);
        vsLabel.setFontScale(FontManager.getBigFontDim() * 1.2f);
        vsLabel.setTextColor(Color.RED);
        ActorPositionManager.setActorCenterScreen(vsLabel);

        Stack stack = new Stack();
        stack.add(new UserInfoContainerCreator().createUserInfoContainer(userInfoContainerConfigs));
        stack.add(vsLabel);

        return stack;
    }

    private MyWrappedLabel createCountDownCounter() {
        CountDownCounter countDownCounter = new CountDownCounter(COUNTDOWN_AMOUNT_MILLIS, TournamentGameLabel.tournament_go_label.getText(), this) {
            @Override
            public void executeAfterCountDownCounter() {
                screenManager.startGameChallengeScreen(gameContext, liveGameId);
            }
        };
        countDownCounter.start();
        return countDownCounter.getCountdownCounterLabel();
    }

    private MyWrappedLabel createTitleLabel() {
        MyWrappedLabel title = new MyWrappedLabel(getGameContext().getGameConfig().getGameTypeStage().getGameTypeLabelText());
        title.setStyleDependingOnContrast();
        title.setFontScale(FontManager.getBigFontDim());
        return title;
    }

    private Table createPrizeLabel() {
        Table prizeMoney = new IfLevelUpPrizeLabelImage(getCurrentUser().getId(), getGameContext().getGameConfig().getGameTypeStage()) {
            @Override
            protected InventoryLabelImageBuilder getInventoryLabelImageBuilder(TransactionAmountEnum transactionAmountEnum) {
                return super.getInventoryLabelImageBuilder(transactionAmountEnum).setTextColor(MyWrappedLabelConfigBuilder.getScreenContrastStyle());
            }
        }.create();
        prizeMoney.setPosition(ScreenDimensionsManager.getScreenWidth() / 2 - prizeMoney.getWidth() / 2, ScreenDimensionsManager.getScreenHeightValue(92));
        return prizeMoney;
    }

    @Override
    public void onBackKeyPress() {
        new ExitGamePopup(this, TournamentGameLabel.exit_challenge_info_msg).addToPopupManager();
    }

}
