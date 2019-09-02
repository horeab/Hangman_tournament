package libgdx.ui.screens.levelfinished;

import java.util.ArrayList;
import java.util.List;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.model.game.GameUser;

public class UsersWithLevelFinished {

    private List<GameUser> usersThatWon;
    private List<GameUser> usersThatLost;

    public UsersWithLevelFinished() {
        this.usersThatWon = new ArrayList<>();
        this.usersThatLost = new ArrayList<>();
    }

    public GameUser getGameUserThatWon() {
        return usersThatWon.isEmpty() ? null : usersThatWon.get(0);
    }

    public GameUser getGameUserThatLost() {
        return usersThatLost.isEmpty() ? null : usersThatLost.get(0);
    }

    public BaseUserInfo getUserThatWon() {
        return getGameUserThatWon() == null ? null : getGameUserThatWon().getBaseUserInfo();
    }

    public BaseUserInfo getUserThatLost() {
        return getGameUserThatLost() == null ? null : getGameUserThatLost().getBaseUserInfo();
    }

    public void addUserThatWon(GameUser gameUser) {
        usersThatWon.add(gameUser);
    }

    public void addUserThatLost(GameUser gameUser) {
        usersThatLost.add(gameUser);
    }
}