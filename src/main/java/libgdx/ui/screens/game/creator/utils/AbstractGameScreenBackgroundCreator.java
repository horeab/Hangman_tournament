package libgdx.ui.screens.game.creator.utils;

import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.game.screens.AbstractGameScreen;

public abstract class AbstractGameScreenBackgroundCreator {

    private AbstractGameScreen abstractGameScreen;
    private GameQuestionInfo gameQuestionInfo;

    public AbstractGameScreenBackgroundCreator(AbstractGameScreen abstractGameScreen, GameUser gameUser) {
        this.abstractGameScreen = abstractGameScreen;
        this.gameQuestionInfo = gameUser.getGameQuestionInfo();
    }

    public GameQuestionInfo getGameQuestionInfo() {
        return gameQuestionInfo;
    }

    public AbstractGameScreen getAbstractGameScreen() {
        return abstractGameScreen;
    }

    public abstract void createBackground();

    public abstract void refreshBackground(int nrOfWrongLettersPressed);

}
