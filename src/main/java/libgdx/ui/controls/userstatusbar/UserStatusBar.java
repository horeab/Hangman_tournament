package libgdx.ui.controls.userstatusbar;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.controls.popup.notificationpopup.PositionNotificationPopup;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.AbstractScreen;
import libgdx.utils.ActorPositionManager;
import libgdx.utils.ScreenDimensionsManager;

public class UserStatusBar extends Table {

    private AbstractScreen screen;

    public UserStatusBar(AbstractScreen screen) {
        this.screen = screen;
    }

    protected UserStatusBarBuilder createUserStatusBarBuilder() {
        return new UserStatusBarBuilder(screen);
    }

    public void addToScreen() {
        UserStatusBar userStatusBar = createUserStatusBarBuilder().build();
        userStatusBar.setY(ScreenDimensionsManager.getScreenHeightValue(95.5f));
        ActorPositionManager.setActorCenterHorizontalOnScreen(userStatusBar);
        displayPopupLogin(userStatusBar);
        screen.addActor(userStatusBar);
    }

    public Table createTable() {
        UserStatusBar userStatusBar = createUserStatusBarBuilder().build();
        displayPopupLogin(userStatusBar);
        return userStatusBar;
    }

    private void displayPopupLogin(UserStatusBar userStatusBar) {
        final Image loginOnClickActor = userStatusBar.findActor(UserStatusBarBuilder.ACTOR_NAME_PROFILE_PICTURE);
        if (loginOnClickActor != null && !TournamentGame.getInstance().getLoginService().isUserLoggedIn() && TournamentGame.getInstance().getAppInfoService().googleFacebookLoginEnabled()) {
            final PositionNotificationPopup positionNotificationPopup = new MyNotificationPopupCreator(
                    new MyNotificationPopupConfigBuilder()
                            .setText(TournamentGameLabel.login_login_btn_text.getText()).build())
                    .positionNotificationPopup(
                            MainDimen.horizontal_general_margin.getDimen(),
                            ScreenDimensionsManager.getScreenHeight() - MainDimen.vertical_general_margin.getDimen() * 7);
            positionNotificationPopup.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    InputEvent event1 = new InputEvent();
                    event1.setType(InputEvent.Type.touchDown);
                    loginOnClickActor.fire(event1);
                    InputEvent event2 = new InputEvent();
                    event2.setType(InputEvent.Type.touchUp);
                    loginOnClickActor.fire(event2);
                }
            });
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    positionNotificationPopup.addToPopupManager();
                }
            }, 1f);
        }
    }

}
