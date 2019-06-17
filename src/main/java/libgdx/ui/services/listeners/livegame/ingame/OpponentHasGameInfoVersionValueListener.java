package libgdx.ui.services.listeners.livegame.ingame;

import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.ChallengeGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.listeners.ListenerValueMet;
import libgdx.ui.services.listeners.ListenerValueMetConfig;

public class OpponentHasGameInfoVersionValueListener<TScreen extends AbstractGameScreen & ChallengeGameScreen> extends ListenerValueMet<TScreen, LiveGameDbApiService> {

    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();
    private GameAnswerInfo lastAnswerPressedByCurrentUser;

    public OpponentHasGameInfoVersionValueListener(int liveGameId, GameAnswerInfo lastAnswerPressedByCurrentUser, int versionOpponentShouldHave, String opponentHasCurrentUserGameInfoVersionColumnName, TScreen screen) {
        super(liveGameId, screen, new ListenerValueMetConfig(opponentHasCurrentUserGameInfoVersionColumnName, versionOpponentShouldHave, Integer.class));
        this.lastAnswerPressedByCurrentUser = lastAnswerPressedByCurrentUser;
    }

    @Override
    protected void executeOperations() {
        liveGameActionsService.updateCurrentUserPressedAnswerToDb(getScreen(), lastAnswerPressedByCurrentUser);
    }

    @Override
    protected LiveGameDbApiService createDbApiService() {
        return new LiveGameDbApiService();
    }
}
