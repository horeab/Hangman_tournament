package libgdx.ui.screens.tournament.creator;

import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.util.TournamentStage;

public class TournamentInfoWithUserInfo {

    private UserInfo userInfo;
    private TournamentStage tournamentStage;
    private TournamentUser tournamentUser;

    public TournamentInfoWithUserInfo(UserInfo userInfo, TournamentStage tournamentStage, TournamentUser tournamentUser) {
        this.userInfo = userInfo;
        this.tournamentStage = tournamentStage;
        this.tournamentUser = tournamentUser;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public TournamentStage getTournamentStage() {
        return tournamentStage;
    }

    public TournamentUser getTournamentUser() {
        return tournamentUser;
    }
}
