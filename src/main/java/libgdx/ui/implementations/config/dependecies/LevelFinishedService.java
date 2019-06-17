package libgdx.ui.implementations.config.dependecies;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;

public abstract class LevelFinishedService {

    public abstract boolean isGameFinished(GameContext gameContext);

    public abstract UsersWithLevelFinished createUsersWithGameFinished(GameContext gameContext);

    public static int correctAnsweredQuestionsForGameSuccess(int totalNrOfQuestions) {
        return Double.valueOf(Math.ceil(totalNrOfQuestions / Float.valueOf(2))).intValue();
    }

    public static float getPercentageOfFinishedQuestions(GameUser gameUser) {
        return gameUser.getFinishedQuestions() / Float.valueOf(gameUser.getTotalNrOfQuestions() ) * 100;
    }

    public static float getPercentageOfWonQuestions(GameUser gameUser) {
        return gameUser.getWonQuestions() / Float.valueOf(gameUser.getTotalNrOfQuestions() ) * 100;
    }

    UsersWithLevelFinished createUsersWithLevelFinished(GameUser userThatWon, GameUser
            userThatLost) {
        UsersWithLevelFinished usersWithLevelFinished = new UsersWithLevelFinished();
        usersWithLevelFinished.addUserThatWon(userThatWon);
        usersWithLevelFinished.addUserThatLost(userThatLost);
        return usersWithLevelFinished;
    }

}
