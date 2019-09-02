package libgdx.ui.model.stats;


import java.util.Objects;

import libgdx.game.model.BaseUserInfo;

public class GameStatsWithUser extends GameStats {

    private BaseUserInfo user;
    private int rank;

    public BaseUserInfo getUser() {
        return user;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStatsWithUser gameStats = (GameStatsWithUser) o;
        return Objects.equals(user, gameStats.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user);
    }
}
