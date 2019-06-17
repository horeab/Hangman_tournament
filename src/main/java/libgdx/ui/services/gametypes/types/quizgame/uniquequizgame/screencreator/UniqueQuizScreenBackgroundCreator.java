package libgdx.ui.services.gametypes.types.quizgame.uniquequizgame.screencreator;

import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;

public class UniqueQuizScreenBackgroundCreator extends AbstractGameScreenBackgroundCreator {

    public UniqueQuizScreenBackgroundCreator(AbstractGameScreen abstractGameScreen, GameUser gameUser) {
        super(abstractGameScreen, gameUser);
    }

    @Override
    public void createBackground() {
    }

    @Override
    public void refreshBackground(int nrOfWrongAnswersPressed) {
    }

}
