package test.services.services;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import test.TournamentGameTestDbApiService;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.services.tournament.CreateTournamentService;
import libgdx.ui.services.tournament.TournamentService;
import libgdx.ui.util.TestDataCreator;
import libgdx.ui.util.TournamentStage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TournamentServiceTest extends TournamentGameTestDbApiService {


    @Test
    public void terminateCurrentStageAndGoToNextStage()  {
        CreateTournamentService tournamentAnonBotService = new CreateTournamentService(TestDataCreator.createUsers());
        TournamentService tournamentBotService = new TournamentService();
        final List<TournamentUser> tournamentUsers = tournamentAnonBotService.createNewTournamentUsers();
        assertTournamentUsersStage0(tournamentUsers);
        assertTournamentUsersAreNotSame(tournamentUsers);
        tournamentBotService.terminateCurrentStageAndGoToNextStage(tournamentUsers, TournamentStage.STAGE_0);
        assertTournamentUsersStage1(tournamentUsers);
        tournamentBotService.terminateCurrentStageAndGoToNextStage(tournamentUsers, TournamentStage.STAGE_1);
        assertTournamentUsersStage2(tournamentUsers);
        tournamentBotService.terminateCurrentStageAndGoToNextStage(tournamentUsers, TournamentStage.STAGE_2);
        assertTournamentUsersGameFinish(tournamentUsers);
    }


    private void assertTournamentUsersAreNotSame(List<TournamentUser> tournamentUsers) {
        Set<String> keys = new HashSet<>();
        for (TournamentUser user : tournamentUsers) {
            keys.add(user.getName());
        }
        assertEquals(tournamentUsers.size(), keys.size());
    }

    private void assertTournamentUsersGameFinish(List<TournamentUser> tournamentUsers) {
        TournamentUser currentUserFromTournament = tournamentUsers.get(0);//Current user
        TournamentUser opponentUser = tournamentUsers.get(1);//Opponent user
        TournamentUser user2 = getTournamentUserForPosition(tournamentUsers, 2, TournamentStage.STAGE_0);
        TournamentUser user3 = getTournamentUserForPosition(tournamentUsers, 3, TournamentStage.STAGE_0);
        TournamentUser user4 = getTournamentUserForPosition(tournamentUsers, 4, TournamentStage.STAGE_0);
        TournamentUser user5 = getTournamentUserForPosition(tournamentUsers, 5, TournamentStage.STAGE_0);
        TournamentUser user6 = getTournamentUserForPosition(tournamentUsers, 6, TournamentStage.STAGE_0);
        TournamentUser user7 = getTournamentUserForPosition(tournamentUsers, 7, TournamentStage.STAGE_0);

        //RIGHT SIDE
        assertTournamentUser(currentUserFromTournament, TournamentStage.STAGE_2);
        assertEquals("528701832", currentUserFromTournament.getBaseUserInfo().getExternalId());
        assertEquals(Integer.valueOf(0), currentUserFromTournament.getPositionForStage2());
        assertTournamentUser(opponentUser, TournamentStage.STAGE_2);
        assertTournamentUser(user2, TournamentStage.STAGE_2);
        assertTournamentUser(user3, TournamentStage.STAGE_2);
        assertNrOfGameOverUsersAndExpectedPosUsers(3, new ArrayList<Integer>(), TournamentStage.GAME_FINISH, opponentUser, user2, user3);

        //LEFT SIDE
        assertTournamentUser(user4, TournamentStage.STAGE_2);
        assertTournamentUser(user5, TournamentStage.STAGE_2);
        assertTournamentUser(user6, TournamentStage.STAGE_2);
        assertTournamentUser(user7, TournamentStage.STAGE_2);
        assertNrOfGameOverUsersAndExpectedPosUsers(4, new ArrayList<Integer>(), TournamentStage.GAME_FINISH, user4, user5, user6, user7);
    }

    private void assertTournamentUsersStage2(List<TournamentUser> tournamentUsers) {
        TournamentUser currentUserFromTournament = tournamentUsers.get(0);//Current user
        TournamentUser user1 = tournamentUsers.get(1);//Opponent user
        TournamentUser user2 = getTournamentUserForPosition(tournamentUsers, 2, TournamentStage.STAGE_0);
        TournamentUser user3 = getTournamentUserForPosition(tournamentUsers, 3, TournamentStage.STAGE_0);
        TournamentUser user4 = getTournamentUserForPosition(tournamentUsers, 4, TournamentStage.STAGE_0);
        TournamentUser user5 = getTournamentUserForPosition(tournamentUsers, 5, TournamentStage.STAGE_0);
        TournamentUser user6 = getTournamentUserForPosition(tournamentUsers, 6, TournamentStage.STAGE_0);
        TournamentUser user7 = getTournamentUserForPosition(tournamentUsers, 7, TournamentStage.STAGE_0);

        //RIGHT SIDE
        assertEquals(Integer.valueOf(0), currentUserFromTournament.getPositionForStage2());
        assertNrOfGameOverUsersAndExpectedPosUsers(3, new ArrayList<Integer>(), TournamentStage.STAGE_2, user1, user2, user3);
        //LEFT SIDE
        assertNrOfGameOverUsersAndExpectedPosUsers(3, new ArrayList<Integer>(Arrays.asList(1)), TournamentStage.STAGE_2, user4, user5, user6, user7);
    }

    private void assertTournamentUsersStage1(List<TournamentUser> tournamentUsers) {
        TournamentUser currentUserFromTournament = tournamentUsers.get(0);//Current user
        TournamentUser user1 = tournamentUsers.get(1);//Opponent user
        TournamentUser user2 = getTournamentUserForPosition(tournamentUsers, 2, TournamentStage.STAGE_0);
        TournamentUser user3 = getTournamentUserForPosition(tournamentUsers, 3, TournamentStage.STAGE_0);
        TournamentUser user4 = getTournamentUserForPosition(tournamentUsers, 4, TournamentStage.STAGE_0);
        TournamentUser user5 = getTournamentUserForPosition(tournamentUsers, 5, TournamentStage.STAGE_0);
        TournamentUser user6 = getTournamentUserForPosition(tournamentUsers, 6, TournamentStage.STAGE_0);
        TournamentUser user7 = getTournamentUserForPosition(tournamentUsers, 7, TournamentStage.STAGE_0);
        //RIGHT SIDE
        assertEquals(Integer.valueOf(0), currentUserFromTournament.getPositionForStage1());
        assertEquals(Integer.valueOf(-1), user1.getPositionForStage1());
        assertNrOfGameOverUsersAndExpectedPosUsers(1, new ArrayList<Integer>(Arrays.asList(1)), TournamentStage.STAGE_1, user2, user3);
        //LEFT SIDE
        assertNrOfGameOverUsersAndExpectedPosUsers(2, new ArrayList<Integer>(Arrays.asList(2, 3)), TournamentStage.STAGE_1, user4, user5, user6, user7);
    }

    private void assertTournamentUsersStage0(List<TournamentUser> tournamentUsers) {
        TournamentUser currentUserFromTournament = tournamentUsers.get(0);//Current user
        TournamentUser opponentUser = tournamentUsers.get(1);//Opponent user
        TournamentUser user2 = getTournamentUserForPosition(tournamentUsers, 2, TournamentStage.STAGE_0);
        TournamentUser user3 = getTournamentUserForPosition(tournamentUsers, 3, TournamentStage.STAGE_0);
        TournamentUser user4 = getTournamentUserForPosition(tournamentUsers, 4, TournamentStage.STAGE_0);
        TournamentUser user5 = getTournamentUserForPosition(tournamentUsers, 5, TournamentStage.STAGE_0);
        TournamentUser user6 = getTournamentUserForPosition(tournamentUsers, 6, TournamentStage.STAGE_0);
        TournamentUser user7 = getTournamentUserForPosition(tournamentUsers, 7, TournamentStage.STAGE_0);

        //RIGHT SIDE
        assertTournamentUserStage0(currentUserFromTournament, 0);
        assertEquals("528701832", currentUserFromTournament.getBaseUserInfo().getExternalId());
        assertTournamentUserStage0(opponentUser, 1);
        assertTournamentUserStage0(user2, 2);
        assertTournamentUserStage0(user3, 3);

        //LEFT SIDE
        assertTournamentUserStage0(user4, 4);
        assertTournamentUserStage0(user5, 5);
        assertTournamentUserStage0(user6, 6);
        assertTournamentUserStage0(user7, 7);
    }

    private void assertNrOfGameOverUsersAndExpectedPosUsers(int expectedNrOfGameOverUsers, List<Integer> positionThatShouldBeFound, TournamentStage tournamentStage, TournamentUser... users) {
        List<Integer> foundPos = new ArrayList<>();
        for (TournamentUser user : users) {
            Integer positionForStage = user.getPositionForStage(tournamentStage);
            if (positionForStage == null || positionForStage == -1) {
                expectedNrOfGameOverUsers = expectedNrOfGameOverUsers - 1;
            } else {
                foundPos.add(positionForStage);
            }
        }
        positionThatShouldBeFound.removeAll(foundPos);
        assertEquals(0, positionThatShouldBeFound.size());
        assertEquals(0, expectedNrOfGameOverUsers);
    }

    private void assertTournamentUserStage0(TournamentUser toAssert, int expectedPosForStage) {
        assertTournamentUser(toAssert, TournamentStage.STAGE_1);
        assertEquals(Integer.valueOf(expectedPosForStage), toAssert.getPositionForStage0());
    }

    private void assertTournamentUser(TournamentUser toAssert, TournamentStage tournamentStage) {
        if (toAssert.getName().equals("Horea Bucerzan")) {
            assertEquals("Horea Bucerzan", toAssert.getName());
        } else {
            assertTrue(toAssert.getName().length() > 5);
        }
    }

    private TournamentUser getTournamentUserForPosition(List<TournamentUser> users, int position, TournamentStage tournamentStage) {
        TournamentUser result = null;
        for (TournamentUser user : users) {
            if (user.getPositionForStage(tournamentStage) == position) {
                result = user;
            }
        }
        return result;
    }

}
