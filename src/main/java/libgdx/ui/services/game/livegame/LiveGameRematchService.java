package libgdx.ui.services.game.livegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.apache.commons.lang3.StringUtils;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.controls.popup.notificationpopup.PositionNotificationPopup;
import libgdx.ui.resources.TournamentGameLabel;

public class LiveGameRematchService {

    public PositionNotificationPopup displayRematchNotificationPopup(int userStatus, Table userInfoContainer) {
        PositionNotificationPopup positionNotificationPopup = null;
        String message = getMessage(userStatus);
        if (StringUtils.isNotBlank(message)) {
            Vector2 stageLoc = userInfoContainer.localToStageCoordinates(new Vector2(userInfoContainer.getX(), userInfoContainer.getY()));
            positionNotificationPopup = new MyNotificationPopupCreator(
                    new MyNotificationPopupConfigBuilder().setText(message).build())
                    .positionNotificationPopup(
                            userInfoContainer.getX() + MainDimen.horizontal_general_margin.getDimen() * 4,
                            stageLoc.y - MainDimen.vertical_general_margin.getDimen() * 2);
            positionNotificationPopup.addToPopupManager();

        }
        return positionNotificationPopup;
    }

    private String getMessage(int userStatus) {
        String message = null;
        if (userStatus == LiveGameStatusEnum.WANT_MATCH.getStatus()) {
            message = TournamentGameLabel.live_game_want_rematch.getText();
        } else if (userStatus == LiveGameStatusEnum.REJECT_MATCH.getStatus()) {
            message = TournamentGameLabel.live_game_reject_rematch.getText();
        }
        return message;
    }
}
