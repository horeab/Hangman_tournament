package libgdx.ui.implementations.games.scoalasofer;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.implementations.config.dependecies.SinglePlayerLevelFinishedService;

public class ScoalaSoferSinglePlayerLevelFinishedService extends SinglePlayerLevelFinishedService {

    public static final int TOTAL_QUESTIONS = 26;
    public static final int WRONG_ANSWERS_FOR_GAME_OVER = 4;

    @Override
    public boolean isGameFinished(GameContext gameContext) {
        GameUser gameUser = gameContext.getCurrentUserGameUser();
        return isGameLost(gameUser) || isGameWon(gameUser);
    }

    @Override
    protected boolean isGameWon(GameUser gameUser) {
        return gameUser.getFinishedQuestions() == gameUser.getTotalNrOfQuestions() && gameUser.getLostQuestions() < WRONG_ANSWERS_FOR_GAME_OVER;
    }

    private boolean isGameLost(GameUser gameUser) {
        return gameUser.getLostQuestions() >= WRONG_ANSWERS_FOR_GAME_OVER;
    }

}
