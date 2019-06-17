package libgdx.ui.services.gametypes.types.quizgame.dependentquizgame.screencreator;

import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;

public class DependentQuizScreenBackgroundCreator extends AbstractGameScreenBackgroundCreator {

    public DependentQuizScreenBackgroundCreator(AbstractGameScreen abstractGameScreen, GameUser gameUser) {
        super(abstractGameScreen, gameUser);
    }

    @Override
    public void createBackground() {
    }

    @Override
    public void refreshBackground(int nrOfWrongAnswersPressed) {
    }

}
