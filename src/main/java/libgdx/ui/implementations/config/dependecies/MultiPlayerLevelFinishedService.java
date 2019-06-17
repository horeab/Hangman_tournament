package libgdx.ui.implementations.config.dependecies;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;

public class MultiPlayerLevelFinishedService extends LevelFinishedService {

    MultiPlayerLevelFinishedService() {
    }

    @Override
    public UsersWithLevelFinished createUsersWithGameFinished(GameContext gameContext) {
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        UsersWithLevelFinished usersWithLevelFinished = new UsersWithLevelFinished();
        if (isGameFinished(gameContext)) {
            if (gameUser1.getWonQuestions() > gameUser2.getWonQuestions()) {
                usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
            } else if (gameUser1.getWonQuestions() < gameUser2.getWonQuestions()) {
                usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
            } else if (gameUser1.userHasMultipleQuestions() && gameUser1.getWonQuestions() == gameUser2.getWonQuestions() && gameUser1.getLostQuestions() == gameUser2.getLostQuestions()) {
                if (gameUser1.getTotalAnswerMillisForGameQuestionInfoList() < gameUser2.getTotalAnswerMillisForGameQuestionInfoList()) {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
                } else {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
                }
            } else if (gameUser1.getTotalNrOfQuestions() == 1 && gameUser1.getWonQuestions() == gameUser2.getWonQuestions() && gameUser1.getWonQuestions() == 1) {
                if (gameUser1.getTotalAnswerMillisForGameQuestionInfoList() < gameUser2.getTotalAnswerMillisForGameQuestionInfoList()) {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
                } else {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
                }
            } else if (gameUser1.getTotalNrOfQuestions() == 1 && gameUser1.getLostQuestions() == gameUser2.getLostQuestions() && gameUser1.getLostQuestions() == 1) {
                if (gameUser1.getTotalAnswerMillisForGameQuestionInfoList() > gameUser2.getTotalAnswerMillisForGameQuestionInfoList()) {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
                } else {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
                }
            } else if (gameUser1.getLostQuestions() > gameUser2.getLostQuestions()) {
                usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
            } else if (gameUser1.getLostQuestions() < gameUser2.getLostQuestions()) {
                usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
            } else if (gameUser1.getLostQuestions() == gameUser2.getLostQuestions()) {
                if (gameUser1.getTotalAnswerMillisForGameQuestionInfoList() > gameUser2.getTotalAnswerMillisForGameQuestionInfoList()) {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser1, gameUser2);
                } else {
                    usersWithLevelFinished = createUsersWithLevelFinished(gameUser2, gameUser1);
                }
            }
        }
        return usersWithLevelFinished;
    }

    @Override
    public boolean isGameFinished(GameContext gameContext) {
        GameUser gameUser1 = gameContext.getCurrentUserGameUser();
        GameUser gameUser2 = gameContext.getOpponentGameUser();
        int totalFinishedQuestionsUser1 = gameUser1.getFinishedQuestions();
        int totalFinishedQuestionsUser2 = gameUser2.getFinishedQuestions();
        int totalQuestions = gameUser1.getTotalNrOfQuestions();
        if ((totalQuestions == 1 && (totalQuestions == totalFinishedQuestionsUser1 || totalQuestions == totalFinishedQuestionsUser2))
                ||
                (totalQuestions > 1 && (totalQuestions == totalFinishedQuestionsUser1 && totalQuestions == totalFinishedQuestionsUser2))) {
            return true;
        } else {
            int totalWonQuestionsUser1 = gameUser1.getWonQuestions();
            int totalWonQuestionsUser2 = gameUser2.getWonQuestions();
            int totalRemainingQuestionsUser1 = totalQuestions - totalFinishedQuestionsUser1;
            int totalRemainingQuestionsUser2 = totalQuestions - totalFinishedQuestionsUser2;

            return totalWonQuestionsUser1 >= totalRemainingQuestionsUser2 + totalWonQuestionsUser2 && totalQuestions == totalFinishedQuestionsUser1
                    ||
                    totalWonQuestionsUser2 >= totalRemainingQuestionsUser1 + totalWonQuestionsUser1 && totalQuestions == totalFinishedQuestionsUser2
                    ||
                    totalRemainingQuestionsUser1 < totalWonQuestionsUser2 - totalWonQuestionsUser1
                    ||
                    totalRemainingQuestionsUser2 < totalWonQuestionsUser1 - totalWonQuestionsUser2;
        }
    }

}
