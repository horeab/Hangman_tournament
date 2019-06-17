package libgdx.ui.implementations.config.dependecies;

import java.util.ArrayList;
import java.util.List;

public class StarsService {

    public static final int NR_OF_STARS_TO_DISPLAY = 3;

    protected int gameTotalNrOfQuestions;

    protected StarsService(int gameTotalNrOfQuestions) {
        this.gameTotalNrOfQuestions = gameTotalNrOfQuestions;
    }

    public int getStarsWon(int currentNrSuccessQuestions) {
        int correctAnsweredQuestionsForGameSuccess = gameTotalNrOfQuestions - LevelFinishedService.correctAnsweredQuestionsForGameSuccess(gameTotalNrOfQuestions);
        float intervalsForStar = (gameTotalNrOfQuestions - correctAnsweredQuestionsForGameSuccess) / Float.valueOf(StarsService.NR_OF_STARS_TO_DISPLAY);
        float percentOfQuestionsAnswered = currentNrSuccessQuestions / Float.valueOf(gameTotalNrOfQuestions);
        int nrOfStars = 0;
        for (int i = 0; i < StarsService.NR_OF_STARS_TO_DISPLAY; i++) {
            if (percentOfQuestionsAnswered * gameTotalNrOfQuestions >= gameTotalNrOfQuestions - intervalsForStar * i) {
                nrOfStars++;
            }
        }
        return nrOfStars;
    }

    /***
     * The first element is the finish flag questionNr .
     * The next three elements are the three star icons.
     */
    public List<Integer> getQuestionsToAddFinishIconAndStarIcon() {
        List<Integer> questionsToAddStar = new ArrayList<Integer>();
        questionsToAddStar.add(LevelFinishedService.correctAnsweredQuestionsForGameSuccess(gameTotalNrOfQuestions));
        for (int i = 1; i <= StarsService.NR_OF_STARS_TO_DISPLAY; i++) {
            questionsToAddStar.add(getQuestionForStar(i));
        }
        return questionsToAddStar;
    }


    protected int getQuestionForStar(int star) {
        int previousStarsWon = 0;
        for (int i = 1; i <= gameTotalNrOfQuestions; i++) {
            int starsWon = getStarsWon(i);
            if (starsWon == star || starsWon > star && previousStarsWon < star) {
                return i;
            }
            previousStarsWon = starsWon;
        }
        return 0;
    }

}
