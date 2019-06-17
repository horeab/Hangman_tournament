package libgdx.ui.screens.game.creator.utils.headeranimation;

import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.utils.Utils;

public class SingleQuestionHeaderAnimationService extends HeaderAnimationService {

    private GameQuestionInfo gameQuestionInfo;

    SingleQuestionHeaderAnimationService(GameQuestionInfo gameQuestionInfo, PlayerHeader playerHeader) {
        super(playerHeader);
        this.gameQuestionInfo = gameQuestionInfo;
    }

    @Override
    public void gameInfoChanged() {
        if (!gameQuestionInfo.getAnswerIds().isEmpty()) {
            GameAnswerInfo gameAnswerInfo = (GameAnswerInfo) Utils.getLastElement(gameQuestionInfo.getAnswers());
            GameService gameService = GameServiceContainer.getGameService(playerHeader.getPlayer().getId(), gameQuestionInfo);
            Float percentOfCorrectLettersPressed = null;
            if (gameService.isAnswerCorrectInQuestion(gameAnswerInfo.getAnswer())) {
                percentOfCorrectLettersPressed = gameService.getPercentOfCorrectAnswersPressed(gameQuestionInfo.getAnswerIds());
            }
            executeAnimation(percentOfCorrectLettersPressed);
        }
    }
}
