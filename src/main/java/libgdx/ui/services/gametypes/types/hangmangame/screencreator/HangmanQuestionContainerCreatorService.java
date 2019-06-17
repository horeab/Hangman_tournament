package libgdx.ui.services.gametypes.types.hangmangame.screencreator;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import libgdx.controls.button.ButtonBuilder;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.game.creator.GameControlsCreatorService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanGameService;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class HangmanQuestionContainerCreatorService extends QuestionContainerCreatorService<HangmanGameService> {

    public static String AVAILABLE_TRIES_IMAGE_CELL_NAME = "AVAILABLE_TRIES_IMAGE_CELL_NAME";

    public HangmanQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        super(gameUser, abstractGameScreen);
    }

    @Override
    public Table createAnswerOptionsTable() {
        return new GameControlsCreatorService().createSquareAnswerOptionsTable(getNrOfAnswersOnRow(), getNrOfAnswerRows(), new ArrayList<>(getAllAnswerButtons().values()));
    }

    @Override
    public Table createQuestionTable() {
        Table questionTable = super.createQuestionTable();
        String questionToBeDisplayed = gameService.getQuestionToBeDisplayed();
        Image questionImage = gameService.getQuestionImage();
        if (StringUtils.isNotBlank(questionToBeDisplayed)) {
            MyWrappedLabel questionLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(questionToBeDisplayed).build());
            questionContainer.add(questionLabel).padBottom(MainDimen.vertical_general_margin.getDimen() * 16).row();
        } else if (questionImage != null) {
            Table imageTable = createQuestionImage(questionImage, ScreenDimensionsManager.getScreenWidthValue(80), ScreenDimensionsManager.getScreenHeightValue(25));
            questionContainer.add(imageTable);
            questionContainer.add(createAvailableTriesTableForQuestionWithImage()).padLeft(MainDimen.horizontal_general_margin.getDimen());
        }
        return questionTable;
    }

    private Table createAvailableTriesTableForQuestionWithImage() {
        Table table = new Table();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen() / 3;
        for (int i = 0; i < HangmanGameService.GAME_OVER_WRONG_LETTERS; i++) {
            Image image = GraphicUtils.getImage(Resource.heart_full);
            image.setName(AVAILABLE_TRIES_IMAGE_CELL_NAME + i);
            table.add(image).padBottom(verticalGeneralMarginDimen).row();
        }
        return table;
    }


    @Override
    public int getNrOfAnswerRows() {
        return nrOfAnswerRows();
    }

    public static int nrOfAnswerRows() {
        return 5;
    }

    @Override
    public int getNrOfAnswersOnRow() {
        return (int) Math.ceil(gameService.getAllAnswerOptions().size() / Float.valueOf(getNrOfAnswerRows()));
    }

    @Override
    public ButtonSkin correctAnswerSkin() {
        return ButtonSkin.SQUARE_CORRECT;
    }

    @Override
    public ButtonSkin wrongAnswerSkin() {
        return ButtonSkin.SQUARE_WRONG;
    }

    @Override
    protected MyButton createAnswerButton(final String answer) {
//        no Uppercase for ß, if uppercase its displayed ass SS
        return new ButtonBuilder(answer)
                .setFixedButtonSize(getNrOfAnswersOnRow() >= 7 ? ButtonSize.HANGMAN_SMALL_BUTTON : ButtonSize.HANGMAN_BUTTON)
                .setButtonSkin(ButtonSkin.SQUARE).build();

    }

}
