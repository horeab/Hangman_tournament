package libgdx.ui.implementations.config.dependecies;

import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;

public class SinglePlayerLevelFinishedService extends LevelFinishedService {

    protected SinglePlayerLevelFinishedService() {
    }

    @Override
    public UsersWithLevelFinished createUsersWithGameFinished(GameContext gameContext) {
        GameUser gameUser = gameContext.getCurrentUserGameUser();
        UsersWithLevelFinished usersWithLevelFinished = new UsersWithLevelFinished();
        if (isGameFinished(gameContext)) {
            if (isGameWon(gameUser)) {
                usersWithLevelFinished.addUserThatWon(gameUser);
            } else {
                usersWithLevelFinished.addUserThatLost(gameUser);
            }
        }
        return usersWithLevelFinished;
    }

    protected boolean isGameWon(GameUser gameUser) {
        return gameUser.getWonQuestions() >= correctAnsweredQuestionsForGameSuccess(gameUser.getTotalNrOfQuestions());
    }

    @Override
    public boolean isGameFinished(GameContext gameContext) {
        GameUser gameUser = gameContext.getCurrentUserGameUser();
        return gameUser.getFinishedQuestions() == gameUser.getTotalNrOfQuestions();
    }


}
