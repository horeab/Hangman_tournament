package libgdx.ui.model.user;


import java.util.HashMap;
import java.util.Map;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.util.TournamentStage;

public class TournamentUser {


    private Map<TournamentStage, Integer> tournamentStageAndPosition = new HashMap<>();
    private BaseUserInfo baseUserInfo;

    public TournamentUser(BaseUserInfo baseUserInfo, int initialPositonForStage0) {
        this.baseUserInfo = baseUserInfo;
        addPositionForStage0(initialPositonForStage0);
    }

    public int getId() {
        return baseUserInfo.getId();
    }

    public Map<TournamentStage, Integer> getTournamentStageAndPosition() {
        return tournamentStageAndPosition;
    }

    public boolean isUserGameOver() {
        return tournamentStageAndPosition.values().contains(-1);
    }

    public void addPositionForStage0(int position) {
        tournamentStageAndPosition.put(TournamentStage.STAGE_0, position);
    }

    public void addPositionForStage1(int position) {
        tournamentStageAndPosition.put(TournamentStage.STAGE_1, position);
    }

    public void addPositionForStage2(int position) {
        tournamentStageAndPosition.put(TournamentStage.STAGE_2, position);
    }

    public void addPositionForGameFinish(int position) {
        tournamentStageAndPosition.put(TournamentStage.GAME_FINISH, position);
    }

    public void addPositionForStage(TournamentStage stage, int position) {
        tournamentStageAndPosition.put(stage, position);
    }

    public Integer getPositionForStage(TournamentStage stage) {
        return tournamentStageAndPosition.get(stage);
    }

    public Integer getPositionForStage0() {
        return tournamentStageAndPosition.get(TournamentStage.STAGE_0);
    }

    public Integer getPositionForStage1() {
        return tournamentStageAndPosition.get(TournamentStage.STAGE_1);
    }

    public Integer getPositionForStage2() {
        return tournamentStageAndPosition.get(TournamentStage.STAGE_2);
    }

    public String getName() {
        return baseUserInfo.getFullName();
    }

    public void setName(String name) {
        baseUserInfo.setFullName(name);
    }

    public BaseUserInfo getBaseUserInfo() {
        return baseUserInfo;
    }

    public void setBaseUserInfo(BaseUserInfo baseUserInfo) {
        this.baseUserInfo = baseUserInfo;
    }

    public boolean isGameOver() {
        return tournamentStageAndPosition.values().contains(-1) || tournamentStageAndPosition.values().contains(null);
    }

    public TournamentStage getCurrentTournamentStage() {
        if (getPositionForStage2() != null) {
            return TournamentStage.STAGE_2;
        } else if (getPositionForStage1() != null) {
            return TournamentStage.STAGE_1;
        } else {
            return TournamentStage.STAGE_0;
        }
    }
}
