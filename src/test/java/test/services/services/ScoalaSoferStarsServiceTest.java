package test.services.services;

import org.junit.Test;

import java.util.Arrays;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.config.dependecies.StarsService;

import static org.junit.Assert.assertEquals;

public class ScoalaSoferStarsServiceTest extends TournamentGameTestDbApiService {


    @Test
    public void getQuestionsToAddFinishIconAndStarIcon() {
        //first element is the finishIcon, the next three elements are the star icons to be displayed
        assertEquals(Arrays.asList(22, 23, 25, 26), TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(26).getQuestionsToAddFinishIconAndStarIcon());
    }

    @Test
    public void getStarsWon() {
        StarsService starsService = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(26);
        assertEquals(0, starsService.getStarsWon(0));
        assertEquals(0, starsService.getStarsWon(13));
        assertEquals(0, starsService.getStarsWon(16));
        assertEquals(0, starsService.getStarsWon(22));
        assertEquals(1, starsService.getStarsWon(23));
        assertEquals(1, starsService.getStarsWon(24));
        assertEquals(2, starsService.getStarsWon(25));
        assertEquals(3, starsService.getStarsWon(26));
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.scoalasofer.name();
            }
        };
    }
}
