package libgdx.ui.screens.game.creator.service.graphicschange;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.game.creator.service.GameControlsService;
import libgdx.ui.screens.game.creator.utils.headeranimation.HeaderAnimationService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;

public class GraphicsChangeService {

    private GameControlsService gameControlsService;
    private RefreshQuestionDisplayService refreshQuestionDisplayService;
    private HeaderAnimationService opponentHeaderAnimationService;
    private HeaderAnimationService currentUserHeaderAnimationService;
    private PlayerHeaderContainer playerHeaderContainer;
    private AbstractGameScreen gameScreen;
    private GameContext gameContext;

    public GraphicsChangeService(AbstractGameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.gameContext = gameScreen.getGameContext();
    }


}
