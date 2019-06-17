package libgdx.ui.services.gametypes.types.imageclickgame.screencreator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Map;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.animations.ActorAnimation;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.builders.ImageButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.imageclickgame.service.ImageClickGameService;
import libgdx.resources.FontManager;
import libgdx.utils.ScreenDimensionsManager;

public class ImageClickQuestionContainerCreatorService extends QuestionContainerCreatorService<ImageClickGameService> {

    public ImageClickQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen abstractGameScreen) {
        super(gameUser, abstractGameScreen);
    }


    @Override
    public Table createAnswerOptionsTable() {
        return new Table();
    }

    @Override
    public Table createQuestionTable() {
        Table questionTable = super.createQuestionTable();
        MyWrappedLabel questionLabel = new MyWrappedLabel(new MyWrappedLabelConfigBuilder()
                .setFontScale(FontManager.getNormalBigFontDim())
                .setText(gameService.getQuestionToBeDisplayed()).build());
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        questionContainer.add(questionLabel).padBottom(verticalGeneralMarginDimen).row();
        Group group = createGroupWithImageAndAnswerOptions();
        questionContainer.add(group).height(group.getHeight()).width(group.getWidth()).padBottom(verticalGeneralMarginDimen * 10).center();
        return questionTable;
    }

    private Group createGroupWithImageAndAnswerOptions() {
        Question question = getGameQuestionInfo().getQuestion();
        final Image image = gameService.getQuestionImage();
        Group grp = new Group();
        float imgHeight = image.getHeight();
        float imgWidth = image.getWidth();
        if (imgWidth > imgHeight) {
            image.setHeight(MainDimen.vertical_general_margin.getDimen() * 18);
            image.setWidth(ScreenDimensionsManager.getNewWidthForNewHeight(image.getHeight(), imgWidth, imgHeight));
        } else {
            image.setWidth(MainDimen.horizontal_general_margin.getDimen() * 40);
            image.setHeight(ScreenDimensionsManager.getNewHeightForNewWidth(image.getWidth(), imgWidth, imgHeight));
        }
        grp.addActor(image);
        grp.setHeight(image.getHeight());
        grp.setWidth(image.getWidth());
        Map<MyButton, Pair<Integer, Integer>> buttonWithCoordinates = gameService.getAnswerOptionsCoordinates(new ArrayList<>(getAllAnswerButtons().values()), question.getQuestionDifficultyLevel(), question.getQuestionCategory());
        for (Map.Entry<MyButton, Pair<Integer, Integer>> entry : buttonWithCoordinates.entrySet()) {
            MyButton button = entry.getKey();
            grp.addActor(button);
            Pair<Integer, Integer> coord = entry.getValue();
            button.setTransform(true);
            button.setPosition(image.getWidth() / 100 * coord.getKey(), image.getHeight() / 100 * coord.getValue(), Align.center);
            new ActorAnimation(button, TournamentGame.getInstance().getAbstractScreen()).animateZoomInZoomOut();
        }
        image.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.print((x / image.getWidth()) * 100 + "," + (y / image.getHeight()) * 100 + "\n");
            }
        });
        return grp;
    }

    @Override
    public ButtonSkin correctAnswerSkin() {
        return ButtonSkin.ANSWER_IMAGE_CLICK_CORRECT;
    }

    @Override
    public ButtonSkin wrongAnswerSkin() {
        return ButtonSkin.ANSWER_IMAGE_CLICK_WRONG;
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
        final MyButton button = new ImageButtonBuilder(ButtonSkin.ANSWER_IMAGE_CLICK, TournamentGame.getInstance().getAbstractScreen())
                .setText(answer)
                .setFixedButtonSize(ButtonSize.IMAGE_CLICK_ANSWER_OPTION)
                .build();
        button.getCenterRow().setVisible(false);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                button.toFront();
                button.getCenterRow().setVisible(true);
            }
        });
        return button;
    }

}
