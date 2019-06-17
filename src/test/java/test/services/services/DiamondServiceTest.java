package test.services.services;

import org.junit.Before;
import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.screens.actionoptions.gamescreens.PracticeOptionsScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.diamond.DiamondService;
import libgdx.ui.services.game.GameTransactionsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class DiamondServiceTest extends TournamentGameTestDbApiService {

    private DiamondService diamondService1;
    private DiamondService diamondService2;

    @Before
    public void setup() throws Exception {
        super.setup();
        diamondService1 = new DiamondService(opponent.getId());
        diamondService2 = new DiamondService(randomUser.getId());
    }

    @Test
    public void diamondRewardIfGameWin() {
        GameTransactionsService gameTransactionsService = new GameTransactionsService(randomUser.getId());

        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x1"));
        assertTrue(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));

        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x2"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.PRACTICE));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x3"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x33"));
        assertTrue(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));

        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x4"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x5"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x6"));
        assertTrue(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));

        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x8"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
        gameTransactionsService.gameWin(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.tournament_1, "x9"));
        assertFalse(diamondService2.diamondRewardIfGameWin(GameType.TOURNAMENT));
    }

    @Test
    public void test() {
        assertEquals(0, diamondService1.getTotalDiamonds());
        assertEquals(0, diamondService2.getTotalDiamonds());
        diamondService1.winDiamond("1");
        assertEquals(1, diamondService1.getTotalDiamonds());
        assertEquals(0, diamondService2.getTotalDiamonds());
        diamondService1.winDiamond("2");
        diamondService2.winDiamond("22");
        assertEquals(2, diamondService1.getTotalDiamonds());
        assertEquals(1, diamondService2.getTotalDiamonds());
        diamondService1.winDiamond("3");
        diamondService1.winDiamond("3");
        diamondService1.winDiamond("3");
        assertEquals(3, diamondService1.getTotalDiamonds());
        assertEquals(1, diamondService2.getTotalDiamonds());
    }
}
