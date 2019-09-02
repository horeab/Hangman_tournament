package libgdx.ui.services.dbapi;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.dbapi.UniqueDbOperationContainer;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.ui.model.stats.GameStats;
import libgdx.ui.services.game.achievements.AchievementsOtherNotificationService;

public class GameStatsDbApiService extends libgdx.dbapi.GameStatsDbApiService {

    @DbApiServiceSubmit
    public void incrementGameStatsQuestionsWon(int userId, String uniqueOperationId) {
        super.incrementGameStatsQuestionsWon(userId, uniqueOperationId);
        new AchievementsOtherNotificationService(userId, AchievementEnum.QUESTIONS_WIN).processDisplayNotification();
    }

}
