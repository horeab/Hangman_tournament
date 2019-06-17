package test.services.services;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.game.creator.utils.headeranimation.HeaderAnimationService;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;
import test.services.LiveGameTest;
import test.services.TestUtils;

public class OpponentHeaderAnimationServiceTest extends LiveGameTest {

    private static final int WAIT_MILLIS = 1000;
    private GameUser opponentGameUser;
    private HeaderAnimationService opponentHeaderAnimationClickService;

    @Mock
    private PlayerHeader playerHeader;

    @Before
    public void setup() throws Exception {
        super.setup();
        TestUtils.waitt(WAIT_MILLIS);
        PlayerHeaderContainer playerHeaderContainer = mock(PlayerHeaderContainer.class);
        when(playerHeaderContainer.getPlayerHeader(opponent)).thenReturn(playerHeader);
        GameConfig gameConfig = new GameConfig(new QuestionConfig(TestDataCreator.QUESTION_0.getQuestionCategory()), GameTypeStage.practice_1, DateUtils.getNowDateString());
        GameContext gameContext = TestDataCreator.createEmptyGameContext(gameConfig, new ArrayList<>(), TestDataCreator.QUESTION_0);
        opponentGameUser = gameContext.getOpponentGameUser();
        opponentHeaderAnimationClickService = HeaderAnimationService.createInstance(gameContext.getOpponentGameUser(), playerHeader);
    }

    //Word: gnocchi
    @Test
    public void processOpponentAnswers() {
        opponentHeaderAnimationClickService.gameInfoChanged();
        TestUtils.waitt(WAIT_MILLIS);
        verify(playerHeader, times(0)).increaseFillCorrectAnswersTable(anyFloat());
        addAnswer("g",
                1, 16.666668f,
                0
        );
        addAnswer("n",
                1, 33.333336f,
                0
        );
        addAnswer("x",
                1, 33.333336f,
                1
        );
        addAnswer("o",
                1, 50f,
                1
        );
        addAnswer("g",
                2, 50f,
                1
        );
        addAnswer("y",
                2, 50f,
                2
        );
        addAnswer("c",
                1, 66.66667f,
                2
        );
        addAnswer("h",
                1, 83.33333f,
                2
        );
        addAnswer("i",
                1, 100f,
                2
        );
        verify(playerHeader, times(7)).increaseFillCorrectAnswersTable(anyFloat());
        verify(playerHeader, times(2)).animatePulseWrongAnswer();

    }

    private void addAnswer(String answerId,
                           int expectedIncreaseFill, float expectedPercentToFill,
                           int expectedAnimatePulseWrongAnswer) {
        opponentGameUser.addAnswerToGameQuestionInfo(answerId, 1L);
        opponentHeaderAnimationClickService.gameInfoChanged();
        TestUtils.waitt(WAIT_MILLIS);
//        verify(playerHeader, times(expectedIncreaseFill)).increaseFillCorrectAnswersTable(expectedPercentToFill);
//        verify(playerHeader, times(expectedAnimatePulseWrongAnswer)).animatePulseWrongAnswer();
    }
}
