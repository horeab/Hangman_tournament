package libgdx.ui.services.listeners.user;

import java.util.ArrayList;

import libgdx.ui.model.game.extrainfo.ExtraInfo;
import libgdx.ui.model.game.extrainfo.ExtraInfoItem;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.game.UserExtraInfoService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueChange;
import libgdx.ui.services.listeners.ListenerConfig;

public class UserExtraInfoListener extends ListenerValueChange<AbstractScreen, UsersDbApiService> {

    private static final int QUERY_PERIOD_MILLIS = 4000;
    private UserExtraInfoService userExtraInfoService = new UserExtraInfoService();

    public UserExtraInfoListener(int userId, AbstractScreen screen) {
        super(userId, screen, new ListenerConfig(UsersDbApiService.COLUMN_NAME_EXTRAINFOCHANGE, Integer.class));
        setQueryPeriodMillis(QUERY_PERIOD_MILLIS);
    }

    @Override
    public synchronized void executeOperations(Object param) {
        ExtraInfo extraInfo = userExtraInfoService.getExtraInfoFromDb(getEntityId());
        for (ExtraInfoItem item : new ArrayList<>(extraInfo.getItems())) {
            if (!userExtraInfoService.itemCreationDateIsExpired(item.getCreationDate())) {
                processItem(item);
            }
            userExtraInfoService.removeExtraInfoItem(getEntityId(), extraInfo, item);
        }
    }

    private void processItem(ExtraInfoItem item) {
        LiveGameInvite liveGameInvite = userExtraInfoService.getExtraInfoItem(item, LiveGameInvite.class);
        if (liveGameInvite != null) {
            new LiveGameActionsService().displayLiveGameChallenge(liveGameInvite, getScreen());
        }
    }


    @Override
    protected UsersDbApiService createDbApiService() {
        return new UsersDbApiService();
    }
}
