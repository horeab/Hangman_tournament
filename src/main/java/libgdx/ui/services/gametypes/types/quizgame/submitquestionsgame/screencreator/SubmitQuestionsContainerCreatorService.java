package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import libgdx.controls.MyWrappedLabelResizeService;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.labelimage.LabelImageConfigBuilder;
import libgdx.resources.FontManager;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.ImageZoomControlCreator;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameControlsCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameService;
import libgdx.utils.ScreenDimensionsManager;

public class SubmitQuestionsContainerCreatorService extends QuestionContainerCreatorService<SubmitQuestionsGameService> {

    private Set<MyButton> pressedOptions = new HashSet<>();
    SubmitQuestionsGameControlsCreatorService gameControlsCreatorService;

    public SubmitQuestionsContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        super(gameUser, abstractGameScreen);
        gameControlsCreatorService = new SubmitQuestionsGameControlsCreatorService(abstractGameScreen, gameService, new ArrayList<MyButton>(getAllAnswerButtons().values()), getDefaultButtonSkin());
    }

    @Override
    public Table createAnswerOptionsTable() {
        Map<String, MyButton> dummyButtons = new HashMap<>();
        for (Map.Entry<String, MyButton> entry : getAllAnswerButtons().entrySet()) {
            final MyButton dummyButton = createAnswerButton(entry.getValue().getText());
            dummyButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    pressedOptions.add(dummyButton);
                    dummyButton.setButtonSkin(ButtonSkin.LONG_ANSWER_SUBMIT_QUESTIONS_OPTION_SELECTED);
                    MyButton submitButton = (MyButton) getAbstractGameScreen().getRoot().findActor(SubmitQuestionsGameControlsCreatorService.SUBMIT_ANSWER_BUTTON_NAME);
                    submitButton.setDisabled(false);
                    submitButton.setTouchable(Touchable.enabled);
                }
            });
            dummyButtons.put(entry.getKey(), dummyButton);
        }
        return gameControlsCreatorService.createSubmitAnswersTable(new ArrayList<MyButton>(dummyButtons.values()), pressedOptions);
    }

    @Override
    public Table createQuestionTable() {
        float questionTableMaxHeight = ScreenDimensionsManager.getScreenHeightValue(45);
        float questionLabelMaxHeightPercent = gameService.getQuestionImage() == null ? 40 : 32;
        float imageMaxHeightPercent = 23;
        Table questionTable = processQuestionTable(questionLabelMaxHeightPercent, imageMaxHeightPercent);
        while (questionTable.getPrefHeight() > questionTableMaxHeight) {
            questionLabelMaxHeightPercent = questionLabelMaxHeightPercent - 0.5f;
            imageMaxHeightPercent = imageMaxHeightPercent - 0.5f;
            questionTable = processQuestionTable(questionLabelMaxHeightPercent, imageMaxHeightPercent);
        }
        addQuestionHeaderTable();
        questionContainer.add(questionTable).growY();
        Table allTable = new Table();
        allTable.add(questionContainer).growY();
        return allTable;
    }

    private Table processQuestionTable(float questionLabelMaxHeightPercent, float imageMaxHeightPercent) {
        Table questionTable = new Table();
        MyWrappedLabel questionLabel = new MyWrappedLabelResizeService(new MyWrappedLabelConfigBuilder().setText(gameService.getQuestionToBeDisplayed()))
                .resizeMaxHeight(ScreenDimensionsManager.getScreenHeightValue(questionLabelMaxHeightPercent));
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        questionTable.add().growY().row();
        questionTable.add(questionLabel).height(questionLabel.getPrefHeight()).width(questionLabel.getPrefWidth()).padBottom(verticalGeneralMarginDimen).row();
        Image questionImage = gameService.getQuestionImage();
        if (questionImage != null) {
            Table imageTable = createQuestionImage(questionImage, ScreenDimensionsManager.getScreenWidthValue(80), ScreenDimensionsManager.getScreenHeightValue(imageMaxHeightPercent));
            imageTable.add(new ImageZoomControlCreator().createZoomTable()).height(imageTable.getPrefHeight());
            questionTable.add(imageTable).height(imageTable.getPrefHeight()).width(imageTable.getPrefWidth()).padBottom(verticalGeneralMarginDimen / 2).row();
        }
        questionTable.add().growY();
        return questionTable;
    }

    protected void addQuestionHeaderTable() {
        new Table();
    }

    @Override
    public ButtonSkin correctAnswerSkin() {
        return ButtonSkin.LONG_ANSWER_OPTION_CORRECT;
    }

    @Override
    public ButtonSkin wrongAnswerSkin() {
        return ButtonSkin.LONG_ANSWER_OPTION_WRONG;
    }

    @Override
    public int getNrOfAnswerRows() {
        return 2;
    }

    @Override
    public int getNrOfAnswersOnRow() {
        return 2;
    }

    @Override
    protected MyButton createAnswerButton(final String answer) {
        float percent = getButtonHeightPercent();
        float buttonMaxHeight = ScreenDimensionsManager.getScreenHeightValue(percent);
        float fontScaleMultiplier = 1f;
        MyButton myButton = createButton(answer, fontScaleMultiplier);
        while (myButton.getHeight() > buttonMaxHeight) {
            fontScaleMultiplier = fontScaleMultiplier - 0.05f;
            myButton = createButton(answer, fontScaleMultiplier);
        }
        return myButton;
    }

    protected float getButtonHeightPercent() {
        return gameService.getQuestionImage() == null ? 18f : 12f;
    }

    private MyButton createButton(String answer, float fontScaleMultiplier) {
        ButtonSize buttonSize = ButtonSize.LONG_QUIZ_OPTION_ANSWER_THIN;
        LabelImageConfigBuilder builder = new LabelImageConfigBuilder().setFontScale(FontManager.calculateMultiplierStandardFontSize(fontScaleMultiplier)).setText(answer).setWrappedLineLabel(buttonSize.getWidth() / 1.1f);
        ButtonBuilder buttonBuilder = new ButtonBuilder().setWrappedText(builder).setFixedButtonSize(buttonSize).setButtonSkin(getDefaultButtonSkin());
        return buttonBuilder.build();
    }

    private ButtonSkin getDefaultButtonSkin() {
        return ButtonSkin.LONG_ANSWER_SUBMIT_QUESTIONS_OPTION;
    }

}
