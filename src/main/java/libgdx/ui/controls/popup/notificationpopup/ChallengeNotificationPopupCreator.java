package libgdx.ui.controls.popup.notificationpopup;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.popup.notificationpopup.MyNotificationPopup;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.services.game.livegame.LiveGameService;

public class ChallengeNotificationPopupCreator extends MyNotificationPopupCreator {

    private LiveGameInvite liveGameInvite;

    public ChallengeNotificationPopupCreator(LiveGameInvite liveGameInvite) {
        super(new MyNotificationPopupConfigBuilder().build());
        this.liveGameInvite = liveGameInvite;
    }

    public ChallengeNotificationPopup build() {
        ChallengeNotificationPopup popup = new ChallengeNotificationPopup(liveGameInvite, config);
        config.setText(TournamentGameLabel.live_game_friend_challenge.getText(liveGameInvite.getCreatorUser().getFullName()));
        setAttributes(popup, config);
        popup.add().row();
        Table buttonsTable = new Table();
        MyButton acceptBtn = createChallengeNotificationPopupAcceptButton(liveGameInvite, popup);
        MyButton declineBtn = createChallengeNotificationPopupDeclineButton(popup);
        buttonsTable.add(acceptBtn).padTop(MainDimen.vertical_general_margin.getDimen()).width(acceptBtn.getWidth()).height(acceptBtn.getHeight()).row();
        buttonsTable.add(declineBtn).padTop(MainDimen.vertical_general_margin.getDimen()).padBottom(MainDimen.vertical_general_margin.getDimen()).width(declineBtn.getWidth()).height(declineBtn.getHeight());
        popup.add(buttonsTable);
        popup.pack();
        return popup;
    }

    private <TPopupType extends ChallengeNotificationPopup> MyButton createChallengeNotificationPopupAcceptButton(final LiveGameInvite liveGameInvite, final TPopupType popup) {
        MyButton acceptBtn = new ButtonBuilder(TournamentGameLabel.accept.getText()).setFixedButtonSize(ButtonSize.NORMAL_SIZE).setButtonSkin(ButtonSkin.GREEN).build();
        acceptBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popup.hide();
                new LiveGameService().acceptChallenge(liveGameInvite, popup.getScreen());
            }
        });
        return acceptBtn;
    }

    private MyButton createChallengeNotificationPopupDeclineButton(final MyNotificationPopup popup) {
        MyButton declineBtn = new ButtonBuilder(TournamentGameLabel.decline.getText()).setFixedButtonSize(ButtonSize.NORMAL_SIZE).setButtonSkin(ButtonSkin.RED).build();
        declineBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                popup.hide();
            }
        });
        return declineBtn;
    }
}
