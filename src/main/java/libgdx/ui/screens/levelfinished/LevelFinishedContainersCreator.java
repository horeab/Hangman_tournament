package libgdx.ui.screens.levelfinished;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.controls.user.UserInfoContainerConfig;

public class LevelFinishedContainersCreator {

    private UsersWithLevelFinished usersWithLevelFinished;

    public LevelFinishedContainersCreator(UsersWithLevelFinished usersWithLevelFinished) {
        this.usersWithLevelFinished = usersWithLevelFinished;
    }

    public List<UserInfoContainerConfig> create1PlayersCurrentUserWinUserInfoContainerConfig() {
        List<UserInfoContainerConfig> userInfoContainerConfigs = new ArrayList<>();
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatWon(), UserInfoContainerConfig.ContainerColor.GREEN));
        return userInfoContainerConfigs;
    }

    public List<UserInfoContainerConfig> create1PlayersCurrentUserLoseUserInfoContainerConfig() {
        List<UserInfoContainerConfig> userInfoContainerConfigs = new ArrayList<>();
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatLost(), UserInfoContainerConfig.ContainerColor.RED));
        return userInfoContainerConfigs;
    }

    public List<UserInfoContainerConfig> create2PlayersOpponentWinUserInfoContainerConfig() {
        List<UserInfoContainerConfig> userInfoContainerConfigs = new ArrayList<>();
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatLost(), UserInfoContainerConfig.ContainerColor.RED));
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatWon(), UserInfoContainerConfig.ContainerColor.GREEN));
        return userInfoContainerConfigs;
    }

    public List<UserInfoContainerConfig> create2PlayersCurrentUserWinUserInfoContainerConfig() {
        List<UserInfoContainerConfig> userInfoContainerConfigs = new ArrayList<>();
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatWon(), UserInfoContainerConfig.ContainerColor.GREEN));
        userInfoContainerConfigs.add(new UserInfoContainerConfig(usersWithLevelFinished.getGameUserThatLost(), UserInfoContainerConfig.ContainerColor.RED));
        return userInfoContainerConfigs;
    }
}
