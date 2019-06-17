package libgdx.ui.services.game.achievements;

public class AchievementsService {

    public int getTotalSteps(Integer totalSteps, Integer[] steps) {
        int totalSumOfSteps = 0;
        for (int step : steps) {
            totalSumOfSteps = totalSumOfSteps + step;
            if (totalSteps < totalSumOfSteps) {
                return step;
            }
        }
        return steps[steps.length - 1];
    }

    public int getFinishedStepsWithMaxLevel(Integer totalSteps, Integer[] steps) {
        int totalSumOfSteps = 0;
        int finishedSteps = 0;
        for (int step : steps) {
            totalSumOfSteps = totalSumOfSteps + step;
            if (totalSteps >= totalSumOfSteps) {
                finishedSteps++;
            }
        }
        return finishedSteps;
    }

    public int getFinishedSteps(Integer totalSteps, Integer[] steps) {
        int totalSumOfSteps = 0;
        int finishedSteps = 0;
        int i = 0;
        while (totalSteps >= totalSumOfSteps) {
            int step;
            if (i < steps.length - 1) {
                step = steps[i];
            } else {
                step = steps[steps.length - 1];
            }
            totalSumOfSteps = totalSumOfSteps + step;
            if (totalSteps >= totalSumOfSteps) {
                finishedSteps++;
            }
            i++;
        }
        return finishedSteps;
    }

    public int getCurrentSteps(Integer totalSteps, Integer[] steps) {
        int totalSumOfSteps = 0;
        for (int step : steps) {
            totalSumOfSteps = totalSumOfSteps + step;
            if (totalSumOfSteps - totalSteps > 0) {
                return Math.abs(totalSumOfSteps - step - totalSteps);
            }
        }
        int lastStep = steps[steps.length - 1];
        while (totalSumOfSteps - totalSteps <= 0) {
            totalSumOfSteps = totalSumOfSteps + lastStep;
        }
        return Math.abs(totalSumOfSteps - lastStep - totalSteps);
    }


    public int getCurrentStepsWithMaxLevel(Integer totalSteps, Integer[] steps) {
        int totalSumOfSteps = 0;
        for (int step : steps) {
            totalSumOfSteps = totalSumOfSteps + step;
            if (totalSumOfSteps - totalSteps > 0) {
                return Math.abs(totalSumOfSteps - step - totalSteps);
            }
        }
        return steps[steps.length - 1];
    }

    public boolean nextStepIsLevelUp(Integer totalSteps, Integer[] steps) {
        return getTotalSteps(totalSteps, steps) - getCurrentSteps(totalSteps, steps) == 1;
    }

    public boolean currentStepIsLevelUp(Integer totalSteps, Integer[] steps) {
        return nextStepIsLevelUp(totalSteps - 1, steps);
    }
}
