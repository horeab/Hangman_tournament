package libgdx.ui.controls.popup.notificationpopup;

import java.util.Objects;

import libgdx.controls.popup.NotTransferableToOtherScreenPopup;
import libgdx.controls.popup.notificationpopup.MyNotificationPopup;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfig;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.screens.AbstractScreen;

public class ChallengeNotificationPopup extends MyNotificationPopup<AbstractScreen> implements NotTransferableToOtherScreenPopup {

    private LiveGameInvite liveGameInvite;

    ChallengeNotificationPopup(LiveGameInvite liveGameInvite, MyNotificationPopupConfig config) {
        super(config);
        this.liveGameInvite = liveGameInvite;
    }

    @Override
    public ChallengeNotificationPopup createNewInstance() {
        return new ChallengeNotificationPopupCreator(liveGameInvite).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ChallengeNotificationPopup that = (ChallengeNotificationPopup) o;
        return Objects.equals(liveGameInvite, that.liveGameInvite);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), liveGameInvite);
    }
}