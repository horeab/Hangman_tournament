package test.services.services;

import org.junit.Before;
import org.junit.Test;

import test.TournamentGameTestDbApiService;
import libgdx.ui.services.game.achievements.AchievementsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AchievementsServiceTest extends TournamentGameTestDbApiService {

    private AchievementsService achievementsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        achievementsService = new AchievementsService();
    }

    @Test
    public void getTotalSteps() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertEquals(3, achievementsService.getTotalSteps(0, steps));
        assertEquals(3, achievementsService.getTotalSteps(1, steps));
        assertEquals(3, achievementsService.getTotalSteps(2, steps));

        //9
        assertEquals(6, achievementsService.getTotalSteps(3, steps));
        assertEquals(6, achievementsService.getTotalSteps(8, steps));

        //20
        assertEquals(11, achievementsService.getTotalSteps(9, steps));
        assertEquals(11, achievementsService.getTotalSteps(18, steps));
        assertEquals(11, achievementsService.getTotalSteps(19, steps));

        //35
        assertEquals(15, achievementsService.getTotalSteps(20, steps));
        assertEquals(15, achievementsService.getTotalSteps(34, steps));
        assertEquals(15, achievementsService.getTotalSteps(35, steps));
        assertEquals(15, achievementsService.getTotalSteps(36, steps));
        assertEquals(15, achievementsService.getTotalSteps(999, steps));

        Integer[] steps2 = {1};
        assertEquals(1, achievementsService.getTotalSteps(0, steps2));
        assertEquals(1, achievementsService.getTotalSteps(1, steps2));
        assertEquals(1, achievementsService.getTotalSteps(2, steps2));
    }

    @Test
    public void getCurrentSteps() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertEquals(0, achievementsService.getCurrentSteps(0, steps));
        assertEquals(1, achievementsService.getCurrentSteps(1, steps));
        assertEquals(2, achievementsService.getCurrentSteps(2, steps));

        //9
        assertEquals(0, achievementsService.getCurrentSteps(3, steps));
        assertEquals(5, achievementsService.getCurrentSteps(8, steps));

        //20
        assertEquals(0, achievementsService.getCurrentSteps(9, steps));
        assertEquals(9, achievementsService.getCurrentSteps(18, steps));
        assertEquals(10, achievementsService.getCurrentSteps(19, steps));

        //35
        assertEquals(0, achievementsService.getCurrentSteps(20, steps));
        assertEquals(10, achievementsService.getCurrentSteps(30, steps));
        assertEquals(14, achievementsService.getCurrentSteps(34, steps));
        assertEquals(0, achievementsService.getCurrentSteps(35, steps));
        assertEquals(1, achievementsService.getCurrentSteps(36, steps));
        assertEquals(2, achievementsService.getCurrentSteps(37, steps));
        assertEquals(14, achievementsService.getCurrentSteps(49, steps));
        assertEquals(0, achievementsService.getCurrentSteps(50, steps));
        assertEquals(1, achievementsService.getCurrentSteps(51, steps));
        assertEquals(14, achievementsService.getCurrentSteps(64, steps));
        assertEquals(0, achievementsService.getCurrentSteps(65, steps));
    }


    @Test
    public void getCurrentStepsWithMaxLevel() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertEquals(0, achievementsService.getCurrentStepsWithMaxLevel(0, steps));
        assertEquals(1, achievementsService.getCurrentStepsWithMaxLevel(1, steps));
        assertEquals(2, achievementsService.getCurrentStepsWithMaxLevel(2, steps));

        //9
        assertEquals(0, achievementsService.getCurrentStepsWithMaxLevel(3, steps));
        assertEquals(5, achievementsService.getCurrentStepsWithMaxLevel(8, steps));

        //20
        assertEquals(0, achievementsService.getCurrentStepsWithMaxLevel(9, steps));
        assertEquals(9, achievementsService.getCurrentStepsWithMaxLevel(18, steps));
        assertEquals(10, achievementsService.getCurrentStepsWithMaxLevel(19, steps));

        //35
        assertEquals(0, achievementsService.getCurrentStepsWithMaxLevel(20, steps));
        assertEquals(10, achievementsService.getCurrentStepsWithMaxLevel(30, steps));
        assertEquals(14, achievementsService.getCurrentStepsWithMaxLevel(34, steps));
        assertEquals(15, achievementsService.getCurrentStepsWithMaxLevel(35, steps));
        assertEquals(15, achievementsService.getCurrentStepsWithMaxLevel(36, steps));
        assertEquals(15, achievementsService.getCurrentStepsWithMaxLevel(37, steps));
        assertEquals(15, achievementsService.getCurrentStepsWithMaxLevel(65, steps));
    }


    @Test
    public void getFinishedSteps() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertEquals(0, achievementsService.getFinishedSteps(0, steps));
        assertEquals(0, achievementsService.getFinishedSteps(1, steps));
        assertEquals(0, achievementsService.getFinishedSteps(2, steps));

        //9
        assertEquals(1, achievementsService.getFinishedSteps(3, steps));
        assertEquals(1, achievementsService.getFinishedSteps(8, steps));

        //20
        assertEquals(2, achievementsService.getFinishedSteps(9, steps));
        assertEquals(2, achievementsService.getFinishedSteps(18, steps));
        assertEquals(2, achievementsService.getFinishedSteps(19, steps));

        //35
        assertEquals(3, achievementsService.getFinishedSteps(20, steps));
        assertEquals(3, achievementsService.getFinishedSteps(30, steps));
        assertEquals(3, achievementsService.getFinishedSteps(34, steps));
        assertEquals(4, achievementsService.getFinishedSteps(35, steps));
        assertEquals(4, achievementsService.getFinishedSteps(36, steps));
        assertEquals(4, achievementsService.getFinishedSteps(37, steps));
        assertEquals(4, achievementsService.getFinishedSteps(49, steps));
        assertEquals(5, achievementsService.getFinishedSteps(50, steps));
        assertEquals(5, achievementsService.getFinishedSteps(51, steps));
        assertEquals(5, achievementsService.getFinishedSteps(64, steps));
        assertEquals(6, achievementsService.getFinishedSteps(65, steps));

        Integer[] steps2 = {1};
        assertEquals(0, achievementsService.getFinishedSteps(0, steps2));
        assertEquals(1, achievementsService.getFinishedSteps(1, steps2));
        assertEquals(2, achievementsService.getFinishedSteps(2, steps2));
        assertEquals(10, achievementsService.getFinishedSteps(10, steps2));
        assertEquals(15, achievementsService.getFinishedSteps(15, steps2));
    }

    @Test
    public void getFinishedStepsWithMaxLevel() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertEquals(0, achievementsService.getFinishedStepsWithMaxLevel(0, steps));
        assertEquals(0, achievementsService.getFinishedStepsWithMaxLevel(1, steps));
        assertEquals(0, achievementsService.getFinishedStepsWithMaxLevel(2, steps));

        //9
        assertEquals(1, achievementsService.getFinishedStepsWithMaxLevel(3, steps));
        assertEquals(1, achievementsService.getFinishedStepsWithMaxLevel(8, steps));

        //20
        assertEquals(2, achievementsService.getFinishedStepsWithMaxLevel(9, steps));
        assertEquals(2, achievementsService.getFinishedStepsWithMaxLevel(18, steps));
        assertEquals(2, achievementsService.getFinishedStepsWithMaxLevel(19, steps));

        //35
        assertEquals(3, achievementsService.getFinishedStepsWithMaxLevel(20, steps));
        assertEquals(3, achievementsService.getFinishedStepsWithMaxLevel(30, steps));
        assertEquals(3, achievementsService.getFinishedStepsWithMaxLevel(34, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(35, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(36, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(37, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(49, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(50, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(51, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(64, steps));
        assertEquals(4, achievementsService.getFinishedStepsWithMaxLevel(65, steps));

        Integer[] steps2 = {1};
        assertEquals(0, achievementsService.getFinishedStepsWithMaxLevel(0, steps2));
        assertEquals(1, achievementsService.getFinishedStepsWithMaxLevel(1, steps2));
        assertEquals(1, achievementsService.getFinishedStepsWithMaxLevel(2, steps2));
    }

    @Test
    public void nextStepIsLevelUp() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertFalse(achievementsService.nextStepIsLevelUp(0, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(1, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(2, steps));

        //9
        assertFalse(achievementsService.nextStepIsLevelUp(3, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(8, steps));

        //20
        assertFalse(achievementsService.nextStepIsLevelUp(9, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(18, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(19, steps));

        //35
        assertFalse(achievementsService.nextStepIsLevelUp(20, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(30, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(34, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(35, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(36, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(37, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(49, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(50, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(51, steps));
        assertTrue(achievementsService.nextStepIsLevelUp(64, steps));
        assertFalse(achievementsService.nextStepIsLevelUp(65, steps));

        Integer[] steps2 = {1};
        assertTrue(achievementsService.nextStepIsLevelUp(0, steps2));
        assertTrue(achievementsService.nextStepIsLevelUp(1, steps2));
        assertTrue(achievementsService.nextStepIsLevelUp(2, steps2));
    }

    @Test
    public void currentStepIsLevelUp() {
        Integer[] steps = {3, 6, 11, 15};
        //3
        assertFalse(achievementsService.currentStepIsLevelUp(0, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(1, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(2, steps));

        //9
        assertTrue(achievementsService.currentStepIsLevelUp(3, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(8, steps));

        //20
        assertTrue(achievementsService.currentStepIsLevelUp(9, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(18, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(19, steps));

        //35
        assertTrue(achievementsService.currentStepIsLevelUp(20, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(30, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(34, steps));
        assertTrue(achievementsService.currentStepIsLevelUp(35, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(36, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(37, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(49, steps));
        assertTrue(achievementsService.currentStepIsLevelUp(50, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(51, steps));
        assertFalse(achievementsService.currentStepIsLevelUp(64, steps));
        assertTrue(achievementsService.currentStepIsLevelUp(65, steps));

        Integer[] steps2 = {1};
        assertFalse(achievementsService.currentStepIsLevelUp(0, steps2));
        assertTrue(achievementsService.currentStepIsLevelUp(1, steps2));
        assertTrue(achievementsService.currentStepIsLevelUp(2, steps2));
    }
}
