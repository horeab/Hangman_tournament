package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.labelimage.LabelImageConfigBuilder;
import libgdx.resources.FontManager;
import libgdx.resources.MainResource;
import libgdx.resources.Res;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.SubmitQuestionsGameLabel;
import libgdx.ui.screens.game.creator.GameControlsCreatorService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;

public class SubmitQuestionsGameControlsCreatorService {

    public static final String SUBMIT_ANSWER_BUTTON_NAME = "SUBMIT_ANSWER_BUTTON_NAME";
    public static final String ANSWER_LATER_BUTTON_NAME = "ANSWER_LATER_BUTTON_NAME";
    private static final String DELETE_ANSWERS_BUTTON_NAME = "DELETE_ANSWERS_BUTTON_NAME";

    private AbstractGameScreen abstractGameScreen;
    private SubmitQuestionsGameService gameService;
    private List<MyButton> originalOptionButtons;
    private List<MyButton> dummyOptionButtons;
    private ButtonSkin defaultButtonSkin;

    public SubmitQuestionsGameControlsCreatorService(AbstractGameScreen abstractGameScreen,
                                                     SubmitQuestionsGameService gameService,
                                                     List<MyButton> originalOptionButtons,
                                                     ButtonSkin defaultButtonSkin) {
        this.abstractGameScreen = abstractGameScreen;
        this.gameService = gameService;
        this.originalOptionButtons = originalOptionButtons;
        this.defaultButtonSkin = defaultButtonSkin;
    }

    public Table createSubmitAnswersTable(List<MyButton> dummyOptionButtons,
                                          Set<MyButton> pressedDummyOptions) {
        this.dummyOptionButtons = dummyOptionButtons;
        Table allTable = new Table();
        float marginDimen = MainDimen.vertical_general_margin.getDimen() ;
        allTable.add(new GameControlsCreatorService().createLongAnswerOptionsTable(dummyOptionButtons)).row();
        allTable.add(createAnswerExtraTable(dummyOptionButtons, pressedDummyOptions)).padBottom(marginDimen);
        return allTable;
    }

    private Table createAnswerExtraTable(List<MyButton> dummyOptionButtons,
                                         Set<MyButton> pressedDummyOptions) {
        float marginDimen = MainDimen.horizontal_general_margin.getDimen();
        Table answerTable = new Table();
        MyButton submitAnswers = createSubmitAnswersButton();
        MyButton deleteAnswers = createDeleteAnswersButton(dummyOptionButtons, pressedDummyOptions, submitAnswers);
        MyButton answerLater = createAnswerLaterButton();
        answerTable.add(deleteAnswers).width(deleteAnswers.getWidth()).height(deleteAnswers.getHeight()).padLeft(marginDimen).padRight(marginDimen);
        answerTable.add(answerLater).width(answerLater.getWidth()).height(answerLater.getHeight()).padRight(marginDimen);
        answerTable.add(submitAnswers).width(submitAnswers.getWidth()).height(submitAnswers.getHeight()).padRight(marginDimen);

        addSubmitAnswersOnClickListener(pressedDummyOptions, submitAnswers);

        return answerTable;
    }

    private void addSubmitAnswersOnClickListener(final Set<MyButton> pressedDummyOptions,
                                                 final MyButton submitAnswers) {
        submitAnswers.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameService.setInitialAnswerOptionsPressedToVerify(pressedDummyOptions.size());
                for (MyButton originalBtn : getOriginalButtonsForClickedDummies(pressedDummyOptions)) {
                    pressListenersForButton(originalBtn);
                }
                disableAllControls();
            }
        });
    }

    public void disableAllControls() {
        if (dummyOptionButtons != null) {
            for (MyButton button : dummyOptionButtons) {
                button.setTouchable(Touchable.disabled);
            }
        }
        disableButtonName(SubmitQuestionsGameControlsCreatorService.SUBMIT_ANSWER_BUTTON_NAME);
        disableButtonName(SubmitQuestionsGameControlsCreatorService.DELETE_ANSWERS_BUTTON_NAME);
        disableButtonName(SubmitQuestionsGameControlsCreatorService.ANSWER_LATER_BUTTON_NAME);
    }

    private void disableButtonName(String buttonName) {
        MyButton button = (MyButton) abstractGameScreen.getRoot().findActor(buttonName);
        if (button != null) {
            button.setDisabled(true);
        }
    }

    private ButtonSize getAnswerExtraButtonSize() {
        return ButtonSize.SMALL_SIZE;
    }

    private float getAnswerExtraButtonTextWidth() {
        return getAnswerExtraButtonSize().getWidth() / 1.1f;
    }

    private MyButton createSubmitAnswersButton() {
        final MyButton submitAnswers = new ButtonBuilder().setButtonSkin(ButtonSkin.GREEN).setFixedButtonSize(getAnswerExtraButtonSize())
                .setWrappedText(createLabelImageConfigBuilder(MainResource.play, getAnswerExtraButtonTextWidth(), SubmitQuestionsGameLabel.submit_answer.getText())).build();
        submitAnswers.setName(SUBMIT_ANSWER_BUTTON_NAME);
        submitAnswers.setDisabled(true);
        return submitAnswers;
    }

    private void pressListenersForButton(MyButton originalBtn) {
        Array<EventListener> listeners = originalBtn.getListeners();
        for (int i = 0; i < listeners.size; i++) {
            if (listeners.get(i) instanceof ClickListener) {
                ((ClickListener) listeners.get(i)).clicked(null, 0, 0);
            }
        }
    }

    private List<MyButton> getOriginalButtonsForClickedDummies(Set<MyButton> pressedDummyOptions) {
        List<MyButton> originalButtons = new ArrayList<>();
        for (MyButton dummyBtn : pressedDummyOptions) {
            for (MyButton originalBtn : originalOptionButtons) {
                if (dummyBtn.getText().equals(originalBtn.getText())) {
                    originalButtons.add(originalBtn);
                }
            }
        }
        return originalButtons;
    }

    private MyButton createDeleteAnswersButton(final List<MyButton> dummyOptionButtons, final Set<MyButton> pressedDummyOptions, final MyButton submitAnswer) {
        MyButton deleteAnswer = new ButtonBuilder().setButtonSkin(ButtonSkin.RED).setFixedButtonSize(getAnswerExtraButtonSize())
                .setWrappedText(createLabelImageConfigBuilder(MainResource.error, getAnswerExtraButtonTextWidth(), SubmitQuestionsGameLabel.delete_answers.getText())).build();
        deleteAnswer.setName(DELETE_ANSWERS_BUTTON_NAME);
        deleteAnswer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                for (MyButton myButton : dummyOptionButtons) {
                    myButton.setButtonSkin(defaultButtonSkin);
                }
                pressedDummyOptions.clear();
                submitAnswer.setDisabled(true);
            }
        });
        return deleteAnswer;
    }

    private MyButton createAnswerLaterButton() {
        MyButton answerLater = new ButtonBuilder().setButtonSkin(ButtonSkin.BLUE).setFixedButtonSize(getAnswerExtraButtonSize())
                .setWrappedText(createLabelImageConfigBuilder(MainResource.skip, getAnswerExtraButtonTextWidth(), SubmitQuestionsGameLabel.answer_later.getText())).build();
        answerLater.setName(ANSWER_LATER_BUTTON_NAME);
        answerLater.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Collections.rotate(abstractGameScreen.getCurrentUserGameUser().getOpenQuestionInfos(), -1);
                abstractGameScreen.goToNextQuestionScreen();
            }
        });
        return answerLater;
    }

    private LabelImageConfigBuilder createLabelImageConfigBuilder(Res image, float width, String text) {
        float defaultImageSideDimension = LabelImageConfigBuilder.DEFAULT_IMAGE_SIDE_DIMENSION / 1.2f;
        float fontScale = FontManager.calculateMultiplierStandardFontSize(0.75f);
        return new LabelImageConfigBuilder()
                .setText(text)
                .setFontScale(fontScale)
                .setWrappedLineLabel(width - defaultImageSideDimension).setImageSideDimension(defaultImageSideDimension)
                .setAlignTextRight(true)
                .setImage(image);
    }
}
