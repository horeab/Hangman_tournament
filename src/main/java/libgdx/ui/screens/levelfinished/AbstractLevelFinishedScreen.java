package libgdx.ui.screens.levelfinished;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.button.builders.FacebookShareButtonBuilder;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.model.game.GameContext;
import libgdx.utils.ActorPositionManager;
import libgdx.resources.FontManager;
import libgdx.utils.ScreenDimensionsManager;

public abstract class AbstractLevelFinishedScreen extends AbstractScreen implements ScreenWithGameContext {

    protected LevelFinishedContainersCreator levelFinishedContainersCreator;
    private Table gameFinishedInfoMessagesTable;
    private Table generalInfoTable;
    private UsersWithLevelFinished usersWithLevelFinished;
    private GameContext gameContext;

    public AbstractLevelFinishedScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext) {
        this.gameContext = gameContext;
        this.usersWithLevelFinished = usersWithLevelFinished;
        this.levelFinishedContainersCreator = new LevelFinishedContainersCreator(usersWithLevelFinished);
    }

    @Override
    protected void initFields() {
        super.initFields();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        Table table = new Table();
        table.add(new UserStatusBar(this).createTable()).padBottom(MainDimen.horizontal_general_margin.getDimen()).top().row();
        table.add(createGeneralInfoTable()).row();
        table.add(createGameFinishedInfoMessagesTable()).expandY();
        table.setWidth(ScreenDimensionsManager.getScreenWidth());
        table.setHeight(ScreenDimensionsManager.getScreenHeightValue(99));
        ActorPositionManager.setActorCenterScreen(table);
        addActor(table);
    }

    public UsersWithLevelFinished getUsersWithLevelFinished() {
        return usersWithLevelFinished;
    }

    protected Table getGameFinishedInfoMessagesTable() {
        return gameFinishedInfoMessagesTable;
    }

    protected Table getGeneralInfoTable() {
        return generalInfoTable;
    }

    @Override
    public GameContext getGameContext() {
        return gameContext;
    }

    private Table createGameFinishedInfoMessagesTable() {
        gameFinishedInfoMessagesTable = new Table();
        gameFinishedInfoMessagesTable.setHeight(ScreenDimensionsManager.getScreenHeightValue(85));
        gameFinishedInfoMessagesTable.setWidth(ScreenDimensionsManager.getScreenWidth());

        MyWrappedLabel titleMsg = new MyWrappedLabel(getTitleMsg());
        titleMsg.setFontScale(FontManager.getBigFontDim());
        titleMsg.setAlignment(Align.center);
        titleMsg.setStyleDependingOnContrast();
        gameFinishedInfoMessagesTable.add(titleMsg).padTop(MainDimen.vertical_general_margin.getDimen() * 2).padBottom(MainDimen.vertical_general_margin.getDimen() * 2).row();

        MyWrappedLabel infoMsg = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setStyleDependingOnContrast().setText(getInfoMsg()).build());
        gameFinishedInfoMessagesTable.add(infoMsg).width(infoMsg.getWidth()).padBottom(MainDimen.vertical_general_margin.getDimen()).row();

        return gameFinishedInfoMessagesTable;
    }

    protected void addShareButton() {
        final MyButton shareBtn = new FacebookShareButtonBuilder(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshScreen();
            }
        }).setShareButton(getCurrentUser(), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shareContent();
            }
        }, getGameContext().getGameConfig().getUniqueId()).build();
        shareBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shareContent();
            }
        });
        gameFinishedInfoMessagesTable.add(shareBtn).width(shareBtn.getWidth()).height(shareBtn.getHeight()).padTop(MainDimen.vertical_general_margin.getDimen() * 3).row();
    }

    protected void addPlayAgainButton() {
        MyButton playAgain = createPlayAgainButton();
        gameFinishedInfoMessagesTable.add(playAgain).width(playAgain.getWidth()).height(playAgain.getHeight()).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        playAgain.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onPlayAgainButtonClick();
            }
        });
    }

    protected void shareContent() {
        TournamentGame.getInstance().getFacebookService().shareApp();
    }

    private Table createGeneralInfoTable() {
        generalInfoTable = new Table();
        return generalInfoTable;
    }

    protected abstract String getTitleMsg();

    protected abstract String getInfoMsg();

    protected abstract void refreshScreen();

    protected abstract MyButton createPlayAgainButton();

    protected abstract void onPlayAgainButtonClick();

}
