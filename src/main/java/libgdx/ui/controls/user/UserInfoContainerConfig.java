package libgdx.ui.controls.user;

import libgdx.ui.resources.Resource;
import libgdx.ui.model.game.GameUser;

public class UserInfoContainerConfig {

    public enum ContainerColor {
        GREEN(Resource.levelfinished_user_success_background),
        WHITE(Resource.levelfinished_tournament_win_user_success_background),
        RED(Resource.levelfinished_user_fail_background),;

        private Resource background;

        ContainerColor(Resource background) {
            this.background = background;
        }

        public Resource getBackground() {
            return background;
        }
    }

    private GameUser gameUser;
    private ContainerColor containerColor;
    private boolean withScoreLabel;

    public UserInfoContainerConfig(GameUser gameUser, ContainerColor containerColor) {
        this.gameUser = gameUser;
        this.containerColor = containerColor;
    }

    public void setWithScoreLabel() {
        this.withScoreLabel = true;
    }

    public boolean isWithScoreLabel() {
        return withScoreLabel;
    }

    public GameUser getGameUser() {
        return gameUser;
    }

    public ContainerColor getContainerColor() {
        return containerColor;
    }
}
