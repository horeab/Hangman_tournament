package libgdx.ui.model.inventory;

import java.util.Objects;

import libgdx.game.model.BaseUserInfo;

public class ExperienceWithUser {

    private BaseUserInfo user;
    private int userId;
    private int experience;
    private int rank;

    public BaseUserInfo getUser() {
        return user;
    }

    public int getUserId() {
        return userId;
    }

    public int getExperience() {
        return experience;
    }

    public int getRank() {
        return rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceWithUser that = (ExperienceWithUser) o;
        return userId == that.userId &&
                experience == that.experience &&
                rank == that.rank &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, userId, experience, rank);
    }
}
