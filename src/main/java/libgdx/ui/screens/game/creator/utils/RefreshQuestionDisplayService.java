package libgdx.ui.screens.game.creator.utils;


import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;

public abstract class RefreshQuestionDisplayService<TGameService extends GameService> {

    protected TGameService gameService;
    protected AbstractGameScreen abstractGameScreen;
    protected Map<String, MyButton> allAnswerButtons;

    public RefreshQuestionDisplayService(AbstractGameScreen abstractGameScreen, Map<String, MyButton> allAnswerButtons) {
        GameContext gameContext = abstractGameScreen.getGameContext();
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        gameService = (TGameService) gameContext.getCurrentUserCreatorDependencies().getGameService(currentUserGameUser.getBaseUserInfo().getId(), currentUserGameUser.getGameQuestionInfo().getQuestion());
        this.abstractGameScreen = abstractGameScreen;
        this.allAnswerButtons = allAnswerButtons;
    }

    public AbstractGameScreen getAbstractGameScreen() {
        return abstractGameScreen;
    }

    public abstract void refreshQuestion(GameQuestionInfo gameQuestionInfo);

    public abstract void gameOverQuestion(GameQuestionInfo gameQuestionInfo);
}
