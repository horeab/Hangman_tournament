package libgdx.ui.screens.levelfinished.service.challenge;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.screens.levelfinished.challenge.LevelFinishedChallengeCurrentUserWinScreen;
import libgdx.ui.screens.levelfinished.challenge.LevelFinishedChallengeOpponentWinScreen;
import libgdx.ui.screens.levelfinished.service.LevelFinishedScreenService;

public abstract class ChallengeLevelFinishedScreenService extends LevelFinishedScreenService {

    private int liveGameId;
    GameContext gameContext;

    ChallengeLevelFinishedScreenService(GameContext gameContext, int liveGameId) {
        this.liveGameId = liveGameId;
        this.gameContext = gameContext;
    }

    public AbstractLevelFinishedChallengeScreen getGameFinishedChallengeScreen() {
        UsersWithLevelFinished usersWithLevelFinished = TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService()
                .createUsersWithGameFinished(gameContext);
        //this means that the match has been abandoned and the current user should win the game
        if (usersWithLevelFinished.getUserThatWon() == null) {
            usersWithLevelFinished.addUserThatWon(gameContext.getCurrentUserGameUser());
            usersWithLevelFinished.addUserThatLost(gameContext.getOpponentGameUser());
        }
        if (gameContext.getCurrentUserGameUser().getBaseUserInfo().equals(usersWithLevelFinished.getUserThatWon())) {
            return new LevelFinishedChallengeCurrentUserWinScreen(usersWithLevelFinished, liveGameId, gameContext);
        } else {
            return new LevelFinishedChallengeOpponentWinScreen(usersWithLevelFinished, liveGameId, gameContext);
        }
    }

    public abstract GameUser getUser1();

    public abstract GameUser getUser2();
}
