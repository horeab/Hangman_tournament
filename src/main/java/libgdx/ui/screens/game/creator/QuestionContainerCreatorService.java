package libgdx.ui.screens.game.creator;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import libgdx.graphics.GraphicUtils;
import libgdx.resources.MainResource;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.controls.button.HintButton;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.button.builders.HintButtonBuilder;
import libgdx.ui.controls.game.GameInfoHeaderBuilder;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.utils.ScreenDimensionsManager;
import sun.applet.Main;

public abstract class QuestionContainerCreatorService<TGameService extends GameService> {

    private Map<String, MyButton> allAnswerButtons;
    private List<HintButton> hintButtons;
    private GameQuestionInfo gameQuestionInfo;
    protected TGameService gameService;
    protected Table questionContainer;
    protected GameContext gameContext;
    protected GameUser gameUser;
    protected AbstractGameScreen abstractGameScreen;

    public QuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        this.gameQuestionInfo = gameUser.getGameQuestionInfo();
        this.gameService = (TGameService) GameServiceContainer.getGameService(gameUser.getBaseUserInfo().getId(), gameUser.getGameQuestionInfo());
        this.questionContainer = new Table();
        this.gameContext = abstractGameScreen.getGameContext();
        this.abstractGameScreen = abstractGameScreen;
        this.gameUser = gameUser;
        this.allAnswerButtons = createAnswerOptionsButtons(gameService.getAllAnswerOptions());
        this.hintButtons = createHintButtons(abstractGameScreen);
    }

    public AbstractGameScreen getAbstractGameScreen() {
        return abstractGameScreen;
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    protected abstract MyButton createAnswerButton(String answer);

    public abstract ButtonSkin correctAnswerSkin();

    public abstract ButtonSkin wrongAnswerSkin();

    public abstract int getNrOfAnswerRows();

    public abstract int getNrOfAnswersOnRow();

    public abstract Table createAnswerOptionsTable();

    public GameUser getGameUser() {
        return gameUser;
    }

    public Table createQuestionTable() {
        Table table = new Table();
        table.add(new GameInfoHeaderBuilder(gameContext.getCurrentUserGameUser()).addItemToAlignedRightTable(createHintButtonsTable()).build()).growX().row();
        table.add(questionContainer).pad(MainDimen.horizontal_general_margin.getDimen()).growY();
        setContainerBackground();
        return table;
    }

    protected void setContainerBackground() {
        questionContainer.setBackground(GraphicUtils.getNinePatch(MainResource.popup_background));
    }

    public Map<String, MyButton> getAllAnswerButtons() {
        return allAnswerButtons;
    }

    public List<HintButton> getHintButtons() {
        return hintButtons;
    }

    private List<HintButton> createHintButtons(AbstractGameScreen abstractGameScreen) {
        List<HintButton> hintButtons = new ArrayList<>();
        for (HintButtonType hintButtonType : abstractGameScreen.getGameContext().getAvailableHints()) {
            hintButtons.add(new HintButtonBuilder(hintButtonType, abstractGameScreen).build());
        }
        return hintButtons;
    }

    private Map<String, MyButton> createAnswerOptionsButtons(List<String> allAnswerOptions) {
        Map<String, MyButton> allAnswerButtons = new LinkedHashMap<>();
        for (String answer : allAnswerOptions) {
            MyButton button = createAnswerButton(answer);
            allAnswerButtons.put(answer, button);
        }
        return allAnswerButtons;
    }

    protected void addQuestionImage(Image questionImage) {
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        float imgHeight = questionImage.getHeight();
        float imgWidth = questionImage.getWidth();
        float imgHeightToBeDisplayed = 9999;
        float newWidth = 9999;
        int imgHeightMultiplier = 18;
        int screenWidth = ScreenDimensionsManager.getScreenWidth();
        while (newWidth > screenWidth - verticalGeneralMarginDimen * 2) {
            imgHeightMultiplier--;
            imgHeightToBeDisplayed = verticalGeneralMarginDimen * imgHeightMultiplier;
            newWidth = ScreenDimensionsManager.getNewWidthForNewHeight(imgHeightToBeDisplayed, imgWidth, imgHeight);
        }
        questionImage.addListener(new ImageZoomControlCreator().createZoomListener(getAbstractGameScreen(),
                createImageTableForMaxHeightMaxWidth(gameService.getQuestionImage(), ScreenDimensionsManager.getScreenWidthValue(90), ScreenDimensionsManager.getScreenHeightValue(35))));
        questionContainer.add(questionImage).height(imgHeightToBeDisplayed).width(newWidth).center();
    }

    protected Table createQuestionImage(Image questionImage, float maxWidth, float maxHeight) {
        Table imageTable = createImageTableForMaxHeightMaxWidth(questionImage, maxWidth, maxHeight);
        imageTable.addListener(new ImageZoomControlCreator().createZoomListener(getAbstractGameScreen(),
                createImageTableForMaxHeightMaxWidth(gameService.getQuestionImage(), ScreenDimensionsManager.getScreenWidthValue(90), ScreenDimensionsManager.getScreenHeightValue(35))));
        return imageTable;
    }

    private Table createImageTableForMaxHeightMaxWidth(Image questionImage, float maxWidth, float maxHeight) {
        float origImgHeight = questionImage.getHeight();
        float origImgWidth = questionImage.getWidth();
        float newHeight = ScreenDimensionsManager.getNewHeightForNewWidth(maxWidth, origImgWidth, origImgHeight);
        while (newHeight > maxHeight) {
            maxWidth--;
            newHeight = ScreenDimensionsManager.getNewHeightForNewWidth(maxWidth, origImgWidth, origImgHeight);
        }
        Table imageTable = new Table();
        imageTable.add(questionImage).height(newHeight).width(maxWidth).center();
        return imageTable;
    }

    public Table createHintButtonsTable() {
        Table table = new Table();
        float padSide = MainDimen.horizontal_general_margin.getDimen() * 2;
        for (HintButton button : hintButtons) {
            table.add(button.getMyButton()).height(button.getMyButton().getHeight()).width(button.getMyButton().getWidth()).padRight(padSide);
        }
        return table;
    }

    public GameQuestionInfo getGameQuestionInfo() {
        return gameQuestionInfo;
    }

}
