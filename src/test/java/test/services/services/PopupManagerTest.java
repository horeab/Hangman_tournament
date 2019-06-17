package test.services.services;

import org.junit.Before;
import org.junit.Test;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupManager;
import libgdx.controls.popup.MyPopupManager;
import libgdx.ui.controls.popup.notificationpopup.ChallengeNotificationPopup;
import libgdx.ui.controls.popup.notificationpopup.ChallengeNotificationPopupCreator;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfig;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupConfigBuilder;
import libgdx.controls.popup.notificationpopup.MyNotificationPopupCreator;
import libgdx.controls.popup.notificationpopup.PositionNotificationPopup;
import libgdx.controls.popup.notificationpopup.ShortNotificationPopup;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.screens.AbstractScreen;
import libgdx.controls.popup.RatingPopup;
import libgdx.ui.screens.mainmenu.popup.RewardsPopup;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.util.TestDataCreator;
import test.services.LiveGameTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PopupManagerTest extends LiveGameTest {

    private MyPopupManager myPopupManager;
    private MyNotificationPopupManager myNotificationPopupManager;

    @Before
    public void setup() throws Exception {
        super.setup();
        myPopupManager = ((AbstractScreen) TournamentGame.getInstance().getScreen()).getMyPopupManager();
        myNotificationPopupManager = ((AbstractScreen) TournamentGame.getInstance().getScreen()).getMyNotificationPopupManager();
    }

    @Test
    public void testMyPopupManager() {
        RewardsPopup myPopup1 = new RewardsPopup((AbstractScreen) TournamentGame.getInstance().getScreen());
        RatingPopup myPopup2 = new RatingPopup((AbstractScreen) TournamentGame.getInstance().getScreen()) {
            @Override
            protected void addExtraButtons() {
            }
        };

        myPopup1.addToPopupManager();
        myPopup2.addToPopupManager();
        assertFalse(myPopupManager.isPopupDisplayed());
        assertFalse(myPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myPopupManager.isPopupDisplayed(myPopup2));
        assertFalse(myPopupManager.isPopupDisplayed(RewardsPopup.class));
        assertFalse(myPopupManager.isPopupDisplayed(RatingPopup.class));

        renderScreen();

        assertTrue(myPopupManager.isPopupDisplayed());
        assertTrue(myPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myPopupManager.isPopupDisplayed(myPopup2));
        assertTrue(myPopupManager.isPopupDisplayed(RewardsPopup.class));
        assertFalse(myPopupManager.isPopupDisplayed(RatingPopup.class));

        myPopupManager.hideAllDisplayedPopups();
        renderScreen();

        assertTrue(myPopupManager.isPopupDisplayed());
        assertFalse(myPopupManager.isPopupDisplayed(myPopup1));
        assertTrue(myPopupManager.isPopupDisplayed(myPopup2));
        assertFalse(myPopupManager.isPopupDisplayed(RewardsPopup.class));
        assertTrue(myPopupManager.isPopupDisplayed(RatingPopup.class));

        myPopup2.hide();
        renderScreen();

        assertFalse(myPopupManager.isPopupDisplayed());
        assertFalse(myPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myPopupManager.isPopupDisplayed(myPopup2));
        assertFalse(myPopupManager.isPopupDisplayed(RewardsPopup.class));
        assertFalse(myPopupManager.isPopupDisplayed(RatingPopup.class));
    }

    @Test
    public void testMyNotificationPopupManager() throws Exception {
        setup();
        setup();
        MyNotificationPopupConfig config = new MyNotificationPopupConfigBuilder().setText("x").build();
        ChallengeNotificationPopup myPopup1 = new ChallengeNotificationPopupCreator(new LiveGameInvite(new Question[]{TestDataCreator.QUESTION_1}, 1, randomUser, getGameConfig())).build();
        ChallengeNotificationPopup myPopup2 = new ChallengeNotificationPopupCreator(new LiveGameInvite(new Question[]{TestDataCreator.QUESTION_1}, 2, randomUser, getGameConfig())).build();
        PositionNotificationPopup myMultipleShowsOnScreenPopup1 = new MyNotificationPopupCreator(config).positionNotificationPopup(0, 0);
        PositionNotificationPopup myMultipleShowsOnScreenPopup2 = new MyNotificationPopupCreator(config).positionNotificationPopup(1, 0);
        ShortNotificationPopup myPopup3 = new MyNotificationPopupCreator(config).shortNotificationPopup();

        myPopup1.addToPopupManager();
        myPopup2.addToPopupManager();
        myMultipleShowsOnScreenPopup1.addToPopupManager();
        myMultipleShowsOnScreenPopup2.addToPopupManager();
        myPopup3.addToPopupManager();
        assertFalse(myNotificationPopupManager.isPopupDisplayed());
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup2));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup3));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup2));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(ChallengeNotificationPopup.class));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(PositionNotificationPopup.class));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(ShortNotificationPopup.class));

        renderScreen();

        assertTrue(myNotificationPopupManager.isPopupDisplayed(myPopup3));
        assertTrue(myNotificationPopupManager.isPopupDisplayed());
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup2));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup1));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup2));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(ChallengeNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(PositionNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(ShortNotificationPopup.class));

        ShortNotificationPopup myPopup4 = new MyNotificationPopupCreator(new MyNotificationPopupConfigBuilder().setText("xx").build()).shortNotificationPopup();
        myPopup4.addToPopupManager();

        myPopup1.hide();
        myMultipleShowsOnScreenPopup2.hide();
        renderScreen();

        assertTrue(myNotificationPopupManager.isPopupDisplayed());
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup1));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myPopup2));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myPopup3));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup4));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup2));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(ChallengeNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(PositionNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(ShortNotificationPopup.class));

        myPopup2.hide();
        renderScreen();

        assertTrue(myNotificationPopupManager.isPopupDisplayed());
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup2));

        //myPopup3 disappears from the screen because its displayed only for 3 seconds
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup3));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(myPopup4));

        assertTrue(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup2));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(ChallengeNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(PositionNotificationPopup.class));
        assertTrue(myNotificationPopupManager.isPopupDisplayed(ShortNotificationPopup.class));

        myPopup4.hide();
        myMultipleShowsOnScreenPopup1.hide();
        renderScreen();

        assertFalse(myNotificationPopupManager.isPopupDisplayed());
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup2));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup3));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myPopup4));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup1));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(myMultipleShowsOnScreenPopup2));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(ChallengeNotificationPopup.class));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(PositionNotificationPopup.class));
        assertFalse(myNotificationPopupManager.isPopupDisplayed(ShortNotificationPopup.class));
    }

    private void renderScreen() {
        for (int i = 0; i < 3; i++) {
            TournamentGame.getInstance().getScreen().render(1f);
        }
    }

}
