package libgdx.ui.services.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.model.user.TournamentUser;

public class CreateTournamentService {

    static final List<Integer> STAGE0_LEFT_SIDE_POSITION = Arrays.asList(2, 3);
    static final List<Integer> STAGE0_RIGHT_SIDE_POSITIONS = Arrays.asList(4, 5, 6, 7);

    private List<BaseUserInfo> allUsers;
    private BaseUserInfo currentUser;

    public CreateTournamentService(List<BaseUserInfo> allUsers) {
        this.currentUser = TournamentGame.getInstance().getCurrentUser();
        allUsers = new ArrayList<>(allUsers);
        allUsers.remove(currentUser);
        this.allUsers = allUsers;
    }

    public List<TournamentUser> createNewTournamentUsers() {
        List<TournamentUser> tournamentUsers = new LinkedList<>();
        List<Integer> positionToAdd = getPositionsForTournamentUsers();
        tournamentUsers.add(new TournamentUser(currentUser, 0));
        for (int i = 0; i < 7; i++) {
            BaseUserInfo baseUserInfo = allUsers.get(i);
            tournamentUsers.add(createTournamentUser(baseUserInfo, positionToAdd.get(i)));
        }
        return tournamentUsers;
    }

    TournamentUser createTournamentUser(BaseUserInfo baseUserInfo, int initialPositionForStage0) {
        return new TournamentUser(baseUserInfo, initialPositionForStage0);
    }

    List<Integer> getPositionsForTournamentUsers() {
        List<Integer> randomPositionsToSetLeft = new ArrayList<>(STAGE0_LEFT_SIDE_POSITION);
        List<Integer> randomPositionsToSetRight = new ArrayList<>(STAGE0_RIGHT_SIDE_POSITIONS);
        Collections.shuffle(randomPositionsToSetLeft);
        Collections.shuffle(randomPositionsToSetRight);
        return Arrays.asList(1, STAGE0_LEFT_SIDE_POSITION.get(0), STAGE0_LEFT_SIDE_POSITION.get(1), STAGE0_RIGHT_SIDE_POSITIONS.get(0), STAGE0_RIGHT_SIDE_POSITIONS.get(1), STAGE0_RIGHT_SIDE_POSITIONS.get(2), STAGE0_RIGHT_SIDE_POSITIONS.get(3));
    }
}
