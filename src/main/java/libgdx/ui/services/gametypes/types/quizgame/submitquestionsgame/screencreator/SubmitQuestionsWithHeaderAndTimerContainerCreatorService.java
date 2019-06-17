package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import libgdx.ui.controls.CountDownCounter;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.FontManager;
import libgdx.resources.MainResource;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferSinglePlayerLevelFinishedService;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.game.creator.service.graphicschange.QuestionFinishedOperationsService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.SubmitQuestionsGameLabel;
import libgdx.utils.ScreenDimensionsManager;

public class SubmitQuestionsWithHeaderAndTimerContainerCreatorService extends SubmitQuestionsContainerCreatorService {

    public static String AVAILABLE_TRIES_IMAGE_CELL_NAME = "SUBMITQUESTIONS_AVAILABLE_TRIES_IMAGE_CELL_NAME";

    public SubmitQuestionsWithHeaderAndTimerContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        super(gameUser, abstractGameScreen);
    }

    @Override
    protected void addQuestionHeaderTable() {
        questionContainer.add(createQuestionHeaderTable(getGameContext().getCurrentUserGameUser().getTotalNrOfQuestions())).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
    }

    private Table createTimerTable() {
        Table timerTable = new Table();
        float counterFontScale = FontManager.calculateMultiplierStandardFontSize(1f);
        MyWrappedLabel countdownCounterLabel = createCountDownCounter(counterFontScale).getCountdownCounterLabel();
        countdownCounterLabel.setAlignment(Align.right);
        float screenWidthValue = ScreenDimensionsManager.getScreenWidthValue(50);
        timerTable.add().growX();
        timerTable.add(countdownCounterLabel).padRight(MainDimen.horizontal_general_margin.getDimen()).right();
        timerTable.setWidth(screenWidthValue);
        return timerTable;
    }

    @Override
    protected float getButtonHeightPercent() {
        return super.getButtonHeightPercent() - 4f;
    }

    private Table createAvailableTries() {
        Table table = new Table();
        float sideDimen = MainDimen.vertical_general_margin.getDimen() * 2.5f;
        for (int i = 0; i < ScoalaSoferSinglePlayerLevelFinishedService.WRONG_ANSWERS_FOR_GAME_OVER - getGameUser().getLostQuestions(); i++) {
            Image image = GraphicUtils.getImage(MainResource.btn_settings_up);
            image.setName(AVAILABLE_TRIES_IMAGE_CELL_NAME + i);
            table.add(image).padRight(MainDimen.horizontal_general_margin.getDimen()).width(sideDimen).height(sideDimen);
        }
        return table;
    }

    private Table createQuestionHeaderTable(int totalNrOfQuestions) {
        Table questionsInfoTable = new Table();
        questionsInfoTable.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_background_default));
        String text = SubmitQuestionsGameLabel.question_from_question.getText(getGameContext().getCurrentUserGameUser().getFinishedQuestions() + 1, totalNrOfQuestions);
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        MyWrappedLabel label = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setFontScale(FontManager.calculateMultiplierStandardFontSize(0.9f)).setWidth(ScreenDimensionsManager.getScreenWidthValue(50)).setText(text).build());
        label.setAlignment(Align.left);
        questionsInfoTable.add(label).padLeft(MainDimen.horizontal_general_margin.getDimen());
        Table timerTable = createTimerTable();
        questionsInfoTable.add(timerTable).width(timerTable.getWidth()).row();
        questionsInfoTable.add(createAvailableTries()).padTop(verticalGeneralMarginDimen / 3).padBottom(verticalGeneralMarginDimen / 5).colspan(2).row();
        return questionsInfoTable;
    }

    private CountDownCounter createCountDownCounter(float fontScale) {
        long millisLeft = 1800000 - gameContext.getMillisGameInProgress();
        CountDownCounter countDownCounter = new CountDownCounter(millisLeft, "", getAbstractGameScreen()) {
            @Override
            public void executeAfterCountDownCounter() {
                while (getGameUser().getGameQuestionInfo() != null) {
                    getGameUser().setLostQuestion(getGameUser().getGameQuestionInfo());
                }
                new QuestionFinishedOperationsService(getAbstractGameScreen()).executeFinishedQuestionOperations();
            }

            @Override
            public void executeOnZeroSeconds() {
                super.executeOnZeroSeconds();
                gameControlsCreatorService.disableAllControls();
            }
        };
        countDownCounter.setFontScale(fontScale);
        countDownCounter.start();
        return countDownCounter;
    }
}
