package libgdx.ui.services.game.achievements;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.ui.resources.Resource;

public abstract class AchievementsNotificationService {

    private AchievementsService achievementsService = new AchievementsService();
    protected int userId;

    AchievementsNotificationService(int userId) {
        this.userId = userId;
    }

    public void processDisplayNotification() {
        AchievementEnum achievementEnum = getAchievementEnum();
        if (TournamentGame.getInstance().hasInternet()
                && achievementEnum != null
                && achievementsService.nextStepIsLevelUp(getTotalSteps(), achievementEnum.getLevelUpAmountArray())
                && !(achievementEnum.getLevelUpAmountArray().length == 1 && getTotalSteps() >= achievementEnum.getLevelUpAmountArray()[0])) {
            new MyNotificationPopupCreator(new MyNotificationPopupConfigBuilder()
                    .setText(achievementEnum.getGameLabel().getText())
                    .setResource(Resource.achievement)
                    .build())
                    .shortNotificationPopup()
                    .addToPopupManager();
        }
    }

    abstract AchievementEnum getAchievementEnum();

    abstract int getTotalSteps();
}
