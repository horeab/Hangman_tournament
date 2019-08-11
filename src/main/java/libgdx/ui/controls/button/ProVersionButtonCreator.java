package libgdx.ui.controls.button;

import org.apache.commons.lang3.StringUtils;

import libgdx.controls.button.MyButton;
import libgdx.ui.game.TournamentGame;

public class ProVersionButtonCreator {

    public MyButton createMainMenuButton() {
        return hasProVersion() ?
                new ProVersionButtonBuilder().build() :
                new LeaderboardButtonBuilder().build();
    }

    public MyButton createSettingButton() {
        return hasProVersion() ? new LeaderboardButtonBuilder().build() : null;
    }

    private boolean hasProVersion() {
        return StringUtils.isNotBlank(TournamentGame.getInstance().getAppInfoService().getProVersionStoreAppId());
    }
}
