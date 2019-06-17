package test.services.listeners;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.extrainfo.ExtraInfoItem;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.game.UserExtraInfoService;
import libgdx.ui.services.listeners.user.UserExtraInfoListener;
import libgdx.utils.DateUtils;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserExtraInfoListenerTest extends LiveGameTest {

    private LiveGameActionsService liveGameActionsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        liveGameActionsService = Mockito.mock(LiveGameActionsService.class);
        PowerMockito.whenNew(LiveGameActionsService.class).withAnyArguments().thenReturn(liveGameActionsService);
    }

    @Test
    public void test() {
        usersDbApiService.updateColumnValue(opponentId, UsersDbApiService.COLUMN_NAME_EXTRAINFO, "");
        TestUtils.waitt();
        UserExtraInfoService userExtraInfoService = new UserExtraInfoService();
        UserExtraInfoListener userExtraInfoListener = new UserExtraInfoListener(opponentId, (AbstractScreen) TournamentGame.getInstance().getScreen());
        userExtraInfoListener.start();

        float waitMultiplier = 1.5f;
        int defaultQueryPeriodMillis = userExtraInfoListener.getQueryPeriodMillis();
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(0)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), new Object(), true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(0)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(1)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(2)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(2)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(3)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(3)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(4)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(4)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        //Item is to late created, so it's not executed
        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.minusSeconds(20).getTime(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(4)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(4)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        userExtraInfoService.addExtraInfoItem(opponentId, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis * waitMultiplier));
        verify(liveGameActionsService, times(5)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(5)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));

        Assert.assertEquals("{\"items\":[]}", usersDbApiService.getColumnValue(opponentId, UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
    }
}
