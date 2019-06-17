package libgdx.ui.controls.labelimage.prize;

import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.services.game.diamond.DiamondService;

public class IfLevelUpPrizeLabelImage extends LevelUpPrizeLabelImage {

    public IfLevelUpPrizeLabelImage(int userId, GameTypeStage gameTypeStage) {
        super(userId, gameTypeStage);
    }

    @Override
    boolean displayDiamondReward() {
        return new DiamondService(userId).diamondRewardIfGameWin(gameTypeStage.getGameType());
    }
}
