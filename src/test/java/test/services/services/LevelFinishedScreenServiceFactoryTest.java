package test.services.services;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.screens.levelfinished.practice.LevelFinishedPracticeScreen;
import libgdx.ui.screens.levelfinished.service.LevelFinishedScreenServiceFactory;
import libgdx.ui.screens.levelfinished.tournament.AbstractLevelFinishedTournamentScreen;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.util.TestDataCreator;
import libgdx.ui.util.TournamentStage;
import test.services.LiveGameTest;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LevelFinishedScreenServiceFactoryTest extends LiveGameTest {

    private LevelFinishedScreenServiceFactory levelFinishedScreenServiceFactory;

    @Before
    public void setup() throws Exception {
        super.setup();
        levelFinishedScreenServiceFactory = new LevelFinishedScreenServiceFactory();
    }

    @Test
    public void getLevelFinishedChallengeScreen() {
        TestUtils.waitt();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.opponent), getGameConfig(), TestDataCreator.QUESTION_0);
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        GameUser opponentGameUser = gameContext.getOpponentGameUser();
        TestDataCreator.resolveWord(currentUserGameUser);
        TestDataCreator.onlyOneLetterPressed(opponentGameUser);
        AbstractLevelFinishedChallengeScreen screen = levelFinishedScreenServiceFactory
                .createChallengeLevelFinishedScreenService(gameContext, liveGameId)
                .getGameFinishedChallengeScreen();
        UsersWithLevelFinished usersWithLevelFinished = screen.getUsersWithLevelFinished();
        assertEquals(currentUser, usersWithLevelFinished.getUserThatWon());
        assertEquals(opponent, usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void getLevelFinishedChallengeScreenWithUserAbandonGame() {
        TestUtils.waitt();
        GameContext gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.opponent), getGameConfig(), TestDataCreator.QUESTION_0);
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        GameUser opponentGameUser = gameContext.getOpponentGameUser();
        TestDataCreator.onlyOneLetterPressed(currentUserGameUser);
        TestDataCreator.onlyOneLetterPressed(opponentGameUser);
        AbstractLevelFinishedChallengeScreen screen = levelFinishedScreenServiceFactory
                .createChallengeLevelFinishedScreenService(gameContext, liveGameId)
                .getGameFinishedChallengeScreen();
        UsersWithLevelFinished usersWithLevelFinished = screen.getUsersWithLevelFinished();
        assertEquals(currentUser, usersWithLevelFinished.getUserThatWon());
        assertEquals(opponent, usersWithLevelFinished.getUserThatLost());
    }

    @Test
    public void getLevelFinishedPractice() {
        LevelFinishedPracticeScreen screen = levelFinishedScreenServiceFactory.createStandardLevelFinishedScreenService()
                .getLevelFinishedPracticeScreen(TestDataCreator.createGameContext(getGameConfig(), TestDataCreator.QUESTION_0));
        UsersWithLevelFinished usersWithLevelFinished = screen.getUsersWithLevelFinished();
        assertEquals(currentUser, usersWithLevelFinished.getUserThatWon());
        assertNull(usersWithLevelFinished.getUserThatLost());
    }


    @Test
    public void getLevelFinishedTournamentScreen() {
        GameContext gameContext = TestDataCreator.createGameContext(getGameConfig(), TestDataCreator.QUESTION_0);
        AbstractLevelFinishedTournamentScreen screen = levelFinishedScreenServiceFactory.createStandardLevelFinishedScreenService().getLevelFinishedTournamentScreen(
                new TournamentContext(TournamentStage.STAGE_0, new ArrayList<TournamentUser>()),
                gameContext);
        UsersWithLevelFinished usersWithLevelFinished = screen.getUsersWithLevelFinished();
        assertEquals(gameContext.getCurrentUserGameUser().getBaseUserInfo(), usersWithLevelFinished.getUserThatWon());
        assertEquals(gameContext.getOpponentGameUser().getBaseUserInfo(), usersWithLevelFinished.getUserThatLost());
    }

}
