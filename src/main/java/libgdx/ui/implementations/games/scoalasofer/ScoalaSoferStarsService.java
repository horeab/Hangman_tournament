package libgdx.ui.implementations.games.scoalasofer;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.implementations.config.dependecies.StarsService;

public class ScoalaSoferStarsService extends StarsService {


    public ScoalaSoferStarsService(int gameTotalNrOfQuestions) {
        super(gameTotalNrOfQuestions);
    }

    @Override
    public int getStarsWon(int currentNrSuccessQuestions) {
        int nrOfStars = 0;
        List<Integer> starPositions = getQuestionsToAddFinishIconAndStarIcon();
        for (int i = starPositions.size() - 1; i >= 0; i--) {
            if (currentNrSuccessQuestions >= starPositions.get(i) && i > 0) {
                nrOfStars = i;
                break;
            }
        }
        return nrOfStars;
    }

    @Override
    public List<Integer> getQuestionsToAddFinishIconAndStarIcon() {
        List<Integer> questionsToAddStar = new ArrayList<Integer>();
        questionsToAddStar.add(ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS - ScoalaSoferSinglePlayerLevelFinishedService.WRONG_ANSWERS_FOR_GAME_OVER);
        for (int i = 1; i <= StarsService.NR_OF_STARS_TO_DISPLAY; i++) {
            questionsToAddStar.add(getQuestionForStar(i));
        }
        return questionsToAddStar;
    }

    @Override
    protected int getQuestionForStar(int star) {
        int questionForStar = 0;
        int scoalaSoferTotalQuestions = ScoalaSoferSinglePlayerLevelFinishedService.TOTAL_QUESTIONS;
        if (gameTotalNrOfQuestions == scoalaSoferTotalQuestions) {
            switch (star) {
                case 1:
                    questionForStar = scoalaSoferTotalQuestions - 3;
                    break;
                case 2:
                    questionForStar = scoalaSoferTotalQuestions - 1;
                    break;
                case 3:
                    questionForStar = scoalaSoferTotalQuestions;
                    break;
            }
        }
        return questionForStar;
    }
}
