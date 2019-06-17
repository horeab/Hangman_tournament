package libgdx.ui.services.game.diamond;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.GameType;
import libgdx.ui.controls.IncrementBar;
import libgdx.ui.resources.Dimen;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.services.game.achievements.AchievementsService;
import libgdx.graphics.GraphicUtils;

public class DiamondControlsService {

    private GameType gameType;
    private int totalDiamondWinTransaction;

    public DiamondControlsService(GameType gameType, int userId) {
        totalDiamondWinTransaction = new GameTransactionsService(userId).getTotalWinTransactionsForGameTypeDiamondConfiguration(gameType);
        this.gameType = gameType;
    }

    public Table createDiamondLevelUpTable() {
        AchievementsService achievementsService = new AchievementsService();
        float diamondDimen = Dimen.side_diamond_level_up.getDimen();
        Table diamondTable = new Table();
        diamondTable.add(new IncrementBar(
                achievementsService.getTotalSteps(totalDiamondWinTransaction, gameType.getGameTypeDiamondConfiguration().getStepsForDiamondReward()),
                achievementsService.getCurrentSteps(totalDiamondWinTransaction, gameType.getGameTypeDiamondConfiguration().getStepsForDiamondReward()))
                .createHorizontalBar(gameType.getGameTypeDiamondConfiguration().getDiamondReward().getLevelUpFillResource())).align(Align.right);
        diamondTable.add(GraphicUtils.getImage(gameType.getGameTypeDiamondConfiguration().getDiamondReward().getResource())).padLeft(MainDimen.horizontal_general_margin.getDimen()).height(diamondDimen).width(diamondDimen).align(Align.left);
        return diamondTable;
    }

}
