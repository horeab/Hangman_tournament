package test.services.services;

import org.junit.Test;

import java.util.Arrays;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.config.dependecies.StarsService;

import static org.junit.Assert.assertEquals;

public class StarsServiceTest extends TournamentGameTestDbApiService {

    @Test
    public void getQuestionsToAddFinishIconAndStarIcon() {
        //first element is the finishIcon, the next three elements are the star icons to be displayed
        assertEquals(Arrays.asList(2, 2, 3, 3), TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(3).getQuestionsToAddFinishIconAndStarIcon());
        assertEquals(Arrays.asList(13, 17, 21,25), TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(25).getQuestionsToAddFinishIconAndStarIcon());
    }

    @Test
    public void getStarsWon() {
        StarsService starsService = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(3);
        assertEquals(0, starsService.getStarsWon(0));
        assertEquals(0, starsService.getStarsWon(1));
        assertEquals(1, starsService.getStarsWon(2));
        assertEquals(3, starsService.getStarsWon(3));

        starsService = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(5);
        assertEquals(0, starsService.getStarsWon(0));
        assertEquals(0, starsService.getStarsWon(1));
        assertEquals(0, starsService.getStarsWon(2));
        assertEquals(1, starsService.getStarsWon(3));
        assertEquals(2, starsService.getStarsWon(4));
        assertEquals(3, starsService.getStarsWon(5));


        starsService = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(7);
        assertEquals(0, starsService.getStarsWon(0));
        assertEquals(0, starsService.getStarsWon(3));
        assertEquals(0, starsService.getStarsWon(4));
        assertEquals(1, starsService.getStarsWon(5));
        assertEquals(2, starsService.getStarsWon(6));
        assertEquals(3, starsService.getStarsWon(7));


        starsService = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(25);
        assertEquals(0, starsService.getStarsWon(0));
        assertEquals(0, starsService.getStarsWon(13));
        assertEquals(0, starsService.getStarsWon(16));
        assertEquals(1, starsService.getStarsWon(17));
        assertEquals(1, starsService.getStarsWon(20));
        assertEquals(2, starsService.getStarsWon(21));
        assertEquals(2, starsService.getStarsWon(24));
        assertEquals(3, starsService.getStarsWon(25));
    }
}
