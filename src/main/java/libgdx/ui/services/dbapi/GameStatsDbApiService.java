package libgdx.ui.services.dbapi;

import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.ui.services.game.achievements.AchievementsOtherNotificationService;

public class GameStatsDbApiService extends libgdx.dbapi.GameStatsDbApiService {

    @DbApiServiceSubmit
    public void incrementGameStatsQuestionsWon(int userId, String uniqueOperationId) {
        super.incrementGameStatsQuestionsWon(userId, uniqueOperationId);
        new AchievementsOtherNotificationService(userId, AchievementEnum.QUESTIONS_WIN).processDisplayNotification();
    }

}
