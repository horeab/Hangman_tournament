package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.gametypes.types.hangmangame.screencreator.HangmanQuestionContainerCreatorService;

public class SubmitQuestionsScreenBackgroundCreator extends AbstractGameScreenBackgroundCreator {

    public SubmitQuestionsScreenBackgroundCreator(AbstractGameScreen abstractGameScreen, GameUser gameUser) {
        super(abstractGameScreen, gameUser);
    }

    @Override
    public void createBackground() {
    }

    @Override
    public void refreshBackground(int nrOfWrongAnswersPressed) {
        for (int i = nrOfWrongAnswersPressed - 1; i >= 0; i--) {
            Image image = getAbstractGameScreen().getRoot().findActor(SubmitQuestionsWithHeaderAndTimerContainerCreatorService.AVAILABLE_TRIES_IMAGE_CELL_NAME + i);
            if (image != null) {
                image.addAction(Actions.fadeOut(0.5f));
            }
        }
    }

}
