package test.services.services;

import java.util.Collections;

import test.TournamentGameTestDbApiService;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.util.TestDataCreator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class GameContextServiceTest extends TournamentGameTestDbApiService {

    private GameContextService gameContextService;

    @Override
    public void setup() throws Exception {
        super.setup();
        gameContextService = new GameContextService();
    }

    private static GameContext createEmptyGameUsers() {
        return new GameContextService().createGameContext(TestDataCreator.createCurrentUser(), Collections.singletonList(TestDataCreator.getUserList().get(0)), LevelFinishedServiceTest.GAME_CONFIG, TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2, TestDataCreator.QUESTION_3, TestDataCreator.QUESTION_4);
    }
}
