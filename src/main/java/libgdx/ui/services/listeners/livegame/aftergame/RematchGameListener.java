package libgdx.ui.services.listeners.livegame.aftergame;

import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueMet;
import libgdx.ui.services.listeners.ListenerValueMetConfig;

public class RematchGameListener<TScreen extends AbstractLevelFinishedChallengeScreen> extends ListenerValueMet<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();

    public RematchGameListener(int finishedLiveGameId, TScreen screen) {
        super(finishedLiveGameId, screen,
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER1STATUS, LiveGameStatusEnum.WANT_MATCH.getStatus(), Integer.class),
                new ListenerValueMetConfig(LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.WANT_MATCH.getStatus(), Integer.class));
    }

    @Override
    protected void executeOperations() {
        LiveGame newLiveGame = getDbApiService().getLiveGame(
                getDbApiService().getColumnValue(getEntityId(), LiveGameDbApiService.COLUMN_NAME_USER1ID, Integer.class),
                getDbApiService().getColumnValue(getEntityId(), LiveGameDbApiService.COLUMN_NAME_USER2ID, Integer.class));
        if (newLiveGame != null && newLiveGame.getUser1Status() != null) {
            int opponentId = getScreen().getCurrentUser().getId() == newLiveGame.getUser1Id() ? newLiveGame.getUser2Id() : newLiveGame.getUser1Id();
            liveGameActionsService.startChallengeWithCoins(new UsersDbApiService().getUser(opponentId), newLiveGame.getQuestionsArray(), newLiveGame.getId(), newLiveGame.getGameConfigObject(), getScreen());
        }
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
