package test.controls;

import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import static org.junit.Assert.*;

import org.apache.commons.lang3.mutable.MutableBoolean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.game.screens.PracticeGameScreen;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;

@RunWith(PowerMockRunner.class)
public class ScreenRunnabelTest extends TournamentGameTestDbApiService {

    @Test
    public void testWithCorrectScreen() throws Exception {
        screenManager.startGamePracticeScreen(TestDataCreator.createGameContext(new GameConfig(getQuestionConfig(), GameTypeStage.practice_3, DateUtils.getNowDateString())));
        Thread.sleep(1000);
        PracticeGameScreen practiceGameScreen = (PracticeGameScreen) TournamentGame.getInstance().getScreen();
        RunnableAction action = new RunnableAction();
        MutableBoolean enteredRunnable = new MutableBoolean(false);
        action.setRunnable(new ScreenRunnable(practiceGameScreen) {
            @Override
            public void executeOperations() {
                assertTrue(true);
                enteredRunnable.setTrue();
            }

            @Override
            public void executeOperationsAfterScreenChanged() {
                fail();
                enteredRunnable.setTrue();
            }
        });
        action.run();
        assertTrue(enteredRunnable.booleanValue());
    }

    private QuestionConfig getQuestionConfig() {
        return new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat3, 5);
    }

    @Test
    public void testWithWrongScreen() throws Exception {
        screenManager.startGamePracticeScreen(TestDataCreator.createGameContext(new GameConfig(getQuestionConfig(), GameTypeStage.practice_3, DateUtils.getNowDateString())));
        Thread.sleep(1000);
        PracticeGameScreen practiceGameScreen = (PracticeGameScreen) TournamentGame.getInstance().getScreen();
        RunnableAction action = new RunnableAction();
        MutableBoolean enteredRunnable = new MutableBoolean(false);
        action.setRunnable(new ScreenRunnable(practiceGameScreen) {
            @Override
            public void executeOperations() {
                fail();
                enteredRunnable.setTrue();
            }

            @Override
            public void executeOperationsAfterScreenChanged() {
                assertTrue(true);
                enteredRunnable.setTrue();
            }
        });

        screenManager.showMainScreen();
        Thread.sleep(1000);

        action.run();
        assertTrue(enteredRunnable.booleanValue());
    }
}
