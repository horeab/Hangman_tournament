package libgdx.ui.services.game.achievements;

import libgdx.game.model.GameStats;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.ui.services.dbapi.GameStatsDbApiService;

public class AchievementsOtherNotificationService extends AchievementsNotificationService {

    private GameStatsDbApiService gameStatsDbApiService = new GameStatsDbApiService();
    private AchievementEnum achievementEnum;

    public AchievementsOtherNotificationService(int userId, AchievementEnum achievementEnum) {
        super(userId);
        this.achievementEnum = achievementEnum;
    }

    @Override
    int getTotalSteps() {
        GameStats gameStats = gameStatsDbApiService.getGameStats(userId);
        if (gameStats != null) {
            return gameStats.getQuestionsWon();
        } else {
            return 9999;
        }
    }

    @Override
    AchievementEnum getAchievementEnum() {
        return achievementEnum;
    }
}
