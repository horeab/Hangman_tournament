package test.controls;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.creator.simulatedopponent.SimulatedOpponentScreenCreator;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.util.TestDataCreator;
import libgdx.ui.util.TournamentStage;
import test.services.TestUtils;

@RunWith(PowerMockRunner.class)
public class SimulatedOpponentQuestionContainerCreatorTest extends TournamentGameTestDbApiService {

    private SimulatedOpponentQuestionContainerCreator creator;
    private GameContext gameContext;

    @Before
    public void setup() throws Exception {
        super.setup();
        gameContext = new GameContextService().createGameContext(this.currentUser, Arrays.asList(this.randomUser), getGameConfig(),
                TestDataCreator.QUESTION_0, TestDataCreator.QUESTION_1, TestDataCreator.QUESTION_2);

        TournamentGame.getInstance().getScreenManager().startGameTournamentScreen(gameContext, new TournamentContext(TournamentStage.STAGE_0, new ArrayList<>()));
        TestUtils.waitt(1500);
        AbstractGameScreen screen = (AbstractGameScreen) TournamentGame.getInstance().getScreen();
        SimulatedOpponentScreenCreator simulatedOpponentScreenCreator = new SimulatedOpponentScreenCreator(screen, screen.getGameContext());
        simulatedOpponentScreenCreator.create();
        creator = simulatedOpponentScreenCreator.getGameScreenQuestionContainerCreator();
    }


    @Test
    public void processOpponentAnswerPressCounter() {
        creator.processOpponentAnswerPressCounter(gameContext.getOpponentGameUser().getGameQuestionInfo(),new GameAnswerInfo("g", 1L));
    }
}
