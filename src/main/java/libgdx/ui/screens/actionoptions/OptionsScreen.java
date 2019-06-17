package libgdx.ui.screens.actionoptions;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.game.GameTypeButtonEnum;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.button.builders.GameTypeButtonBuilder;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.game.diamond.DiamondControlsService;
import libgdx.utils.ActorPositionManager;
import libgdx.utils.ScreenDimensionsManager;

public abstract class OptionsScreen extends AbstractScreen {

    private GameType gameType;

    public OptionsScreen(GameType gameType) {
        this.gameType = gameType;
    }

    @Override
    public void buildStage() {
        super.buildStage();
        new UserStatusBar(this).addToScreen();
        if (!TournamentGame.getInstance().getLoginService().isUserLoggedIn()) {
            executeOptionAnon();
        } else {
            ScrollPane scrollPane = new ScrollPane(createOptionsTable());
            scrollPane.setScrollingDisabled(true, true);

            Table table = new Table();
            table.setWidth(ScreenDimensionsManager.getScreenWidth());
            table.setHeight(ScreenDimensionsManager.getScreenHeight());
            ActorPositionManager.setActorCenterScreen(table);

            float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
            if (gameType.getGameTypeDiamondConfiguration() != null) {
                table.add(new DiamondControlsService(gameType, getCurrentUser().getId()).createDiamondLevelUpTable())
                        .padBottom(verticalGeneralMarginDimen).padTop(verticalGeneralMarginDimen * 4).row();
            }
            table.add(scrollPane);

            addActor(table);
        }
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

    private Table createOptionsTable() {
        Table optionsTable = new Table();
        for (final GameTypeStage gameTypeStage : GameTypeStage.getGameTypeStages(gameType)) {
            MyButton option = new GameTypeButtonBuilder(getCurrentUser().getId(), gameTypeStage)
                    .setButtonSkin(GameTypeButtonEnum.getGameTypeButtonEnum(gameTypeStage).getButtonSkin())
                    .setFixedButtonSize(ButtonSize.START_GAME_OPTION_SIZE)
                    .build();
            option.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    executeOption(gameTypeStage);
                }
            });
            optionsTable.add(option).width(option.getWidth()).height(option.getHeight()).center().row();
        }
        return optionsTable;
    }

    public abstract void executeOption(GameTypeStage gameTypeStage);

    public abstract void executeOptionAnon();

}
