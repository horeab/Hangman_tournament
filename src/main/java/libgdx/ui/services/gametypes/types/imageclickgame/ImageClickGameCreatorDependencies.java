package libgdx.ui.services.gametypes.types.imageclickgame;

import java.util.Map;

import libgdx.controls.button.MyButton;
import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.imageclickgame.screencreator.ImageClickQuestionContainerCreatorService;
import libgdx.ui.services.gametypes.types.imageclickgame.screencreator.ImageClickScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.imageclickgame.service.ImageClickGameService;
import libgdx.ui.services.gametypes.types.imageclickgame.service.ImageClickRefreshQuestionDisplayService;
import libgdx.ui.services.questions.QuestionCreator;

public class ImageClickGameCreatorDependencies extends CreatorDependencies {

    @Override
    public Class<? extends GameService> getGameServiceClass() {
        return ImageClickGameService.class;
    }

    @Override
    public RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons) {
        return new ImageClickRefreshQuestionDisplayService(screen, allAnswerButtons);
    }

    @Override
    public QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen) {
        return new ImageClickQuestionContainerCreatorService(gameUser, screen);
    }

    @Override
    public AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser) {
        return new ImageClickScreenBackgroundCreator(screen, gameUser);
    }

    @Override
    public HintButtonType getHintButtonType() {
        return HintButtonType.HINT_DISABLE_TWO_ANSWERS;
    }

}
