package libgdx.ui.services.game.userexperience;

import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;

public class UserExperienceChangeNotificationService {

    private int levelBeforeChange;

    public void beforeExperienceChange(int totalExperience) {
        levelBeforeChange = new UserExperienceService().getUserExperienceLevel(totalExperience);
    }

    public void afterExperienceChange(int totalExperience) {
        int levelAfterChange = new UserExperienceService().getUserExperienceLevel(totalExperience);
        if (levelAfterChange > levelBeforeChange) {
            new MyNotificationPopupCreator(
                    new MyNotificationPopupConfigBuilder()
                            .setText(TournamentGameLabel.achievement_level_up.getText(levelAfterChange))
                            .setResource(Resource.btn_leaderboard_up).build())
                    .shortNotificationPopup().addToPopupManager();
        }
    }
}
