package libgdx.ui.services.game.userexperience;

public class UserExperienceService {

    public int getUserExperienceLevel(int totalExperience) {
        float experienceMultiplier = 1f;
        int[] levelChangeVals = getLevelChangeVals();
        for (int i = levelChangeVals.length - 1; i >= 0; i--) {
            if (Float.valueOf(totalExperience) >= Float.valueOf(levelChangeVals[i] * experienceMultiplier)) {
                return i + 1;
            }
        }
        return 1;
    }

    public float getPercentFillUntilNextLevel(int totalExperience) {
        int experienceNeededForNextLevel = getLevelChangeVals()[getUserExperienceLevel(totalExperience)];
        int currentLevelExperience = getLevelChangeVals()[getUserExperienceLevel(totalExperience) - 1];
        float percentToFill = (totalExperience - currentLevelExperience) / (Float.valueOf(experienceNeededForNextLevel) - currentLevelExperience);
        percentToFill = percentToFill < 0.02f ? 0 : percentToFill;
        return percentToFill;
    }

    private int[] getLevelChangeVals() {
        return new int[]{0, 5, 20, 50, 90, 150, 250, 400, 1000, 1700, 2500, 3500, 5000, 7000, 10000, 15000, 25000, 50000, 100000, 200000, 400000, 700000, 1100000};
    }

}
