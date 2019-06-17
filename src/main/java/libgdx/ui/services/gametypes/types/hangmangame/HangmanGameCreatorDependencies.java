package libgdx.ui.services.gametypes.types.hangmangame;

import java.util.Map;

import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.RefreshQuestionDisplayService;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.screencreator.HangmanQuestionContainerCreatorService;
import libgdx.ui.services.gametypes.types.hangmangame.screencreator.HangmanScreenBackgroundCreator;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanGameService;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanQuestionCreator;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanRefreshQuestionDisplayService;
import libgdx.ui.services.questions.QuestionCreator;

public class HangmanGameCreatorDependencies extends CreatorDependencies {

    @Override
    public Class<? extends GameService> getGameServiceClass() {
        return HangmanGameService.class;
    }

    @Override
    public QuestionCreator getQuestionCreator(QuestionDifficulty questionDifficulty, QuestionCategory questionCategory) {
        return new HangmanQuestionCreator(questionDifficulty, questionCategory);
    }

    @Override
    public RefreshQuestionDisplayService getRefreshQuestionDisplayService(AbstractGameScreen screen, Map<String, MyButton> allAnswerButtons) {
        return new HangmanRefreshQuestionDisplayService(screen, allAnswerButtons);
    }

    @Override
    public QuestionContainerCreatorService getQuestionContainerCreatorService(GameUser gameUser, AbstractGameScreen screen) {
        return new HangmanQuestionContainerCreatorService(gameUser, screen);
    }

    @Override
    public AbstractGameScreenBackgroundCreator getGameScreenBackgroundCreator(AbstractGameScreen screen, GameUser gameUser) {
        return new HangmanScreenBackgroundCreator(screen, gameUser);
    }

    @Override
    public HintButtonType getHintButtonType() {
        return HintButtonType.HINT_PRESS_RANDOM_ANSWER;
    }
}
