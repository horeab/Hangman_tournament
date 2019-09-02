package libgdx.ui.services.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.util.TournamentStage;

public class TournamentService {

    public void terminateCurrentStageAndGoToNextStage(List<TournamentUser> tournamentUsers, TournamentStage currentStage) {
        switch (currentStage) {
            case STAGE_0:
                terminateStage0(tournamentUsers);
                break;
            case STAGE_1:
                terminateStage1(tournamentUsers);
                break;
            case STAGE_2:
                terminateStage2(tournamentUsers);
                break;
        }
    }

    private void terminateStage2(List<TournamentUser> tournamentUsers) {
        //LEFT SIDE
        TournamentUser currentUser = tournamentUsers.get(0);//Current user
        currentUser.addPositionForGameFinish(0);
        //RIGHT SIDE
        TournamentUser rightSideOpponentUser = getTournamentUserForPosition(tournamentUsers, 1, TournamentStage.STAGE_2);
        rightSideOpponentUser.addPositionForStage2(-1);
    }

    private void terminateStage1(List<TournamentUser> tournamentUsers) {
        //LEFT SIDE
        TournamentUser currentUser = tournamentUsers.get(0);//Current user
        TournamentUser user1 = getTournamentUserForPosition(tournamentUsers, 1, TournamentStage.STAGE_1);
        currentUser.addPositionForStage2(0);
        user1.addPositionForStage2(-1);

        //RIGHT SIDE
        TournamentUser user2 = getTournamentUserForPosition(tournamentUsers, 2, TournamentStage.STAGE_1);
        TournamentUser user3 = getTournamentUserForPosition(tournamentUsers, 3, TournamentStage.STAGE_1);
        terminateRandomUser(user2, user3, 1, TournamentStage.STAGE_2);
    }

    private void terminateStage0(List<TournamentUser> tournamentUsers) {
        //LEFT SIDE
        TournamentUser currentUserForTournament = tournamentUsers.get(0);//Current user
        currentUserForTournament.addPositionForStage1(0);
        TournamentUser user1 = tournamentUsers.get(1);
        TournamentUser user2 = tournamentUsers.get(2);
        TournamentUser user3 = tournamentUsers.get(3);
        user1.addPositionForStage1(-1);
        terminateRandomUser(user2, user3, 1, TournamentStage.STAGE_1);

        //RIGHT SIDE
        TournamentUser user4 = getTournamentUserForPosition(tournamentUsers, 4, TournamentStage.STAGE_0);
        TournamentUser user5 = getTournamentUserForPosition(tournamentUsers, 5, TournamentStage.STAGE_0);
        TournamentUser user6 = getTournamentUserForPosition(tournamentUsers, 6, TournamentStage.STAGE_0);
        TournamentUser user7 = getTournamentUserForPosition(tournamentUsers, 7, TournamentStage.STAGE_0);
        terminateRandomUser(user4, user5, 2, TournamentStage.STAGE_1);
        terminateRandomUser(user6, user7, 3, TournamentStage.STAGE_1);
    }

    private void terminateRandomUser(TournamentUser user1, TournamentUser user2, int positionForSuccessUser, TournamentStage stage) {
        List<TournamentUser> list = new ArrayList<>(Arrays.asList(user1, user2));
        Collections.shuffle(list);
        list.get(0).addPositionForStage(stage, -1);
        list.get(1).addPositionForStage(stage, positionForSuccessUser);
    }

    public BaseUserInfo getCurrentOpponentForTournamentStage(List<TournamentUser> tournamentUsers, TournamentStage tournamentStage) {
        TournamentUser opponent = getTournamentUserForPosition(tournamentUsers, 1, tournamentStage);
        return opponent.getBaseUserInfo();
    }

    private TournamentUser getTournamentUserForPosition(List<TournamentUser> users, int position, TournamentStage tournamentStage) {
        TournamentUser result = null;
        for (TournamentUser user : users) {
            if (user.getPositionForStage(tournamentStage) != null && user.getPositionForStage(tournamentStage) == position) {
                result = user;
            }
        }
        return result;
    }
}
