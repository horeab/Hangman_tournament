package libgdx.ui.controls.game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.resources.dimen.MainDimen;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;
import libgdx.ui.model.game.GameUser;
import libgdx.resources.FontManager;
import libgdx.utils.ScreenDimensionsManager;

public class GameInfoHeaderBuilder {

    private GameUser gameUser;
    private Table alignedRightTable;

    public GameInfoHeaderBuilder(GameUser gameUser) {
        this.gameUser = gameUser;
        this.alignedRightTable = new Table();
    }

    public GameInfoHeaderBuilder addItemToAlignedRightTable(Table table) {
        alignedRightTable.add(table);
        return this;
    }

    public GameInfoHeader build() {
        GameInfoHeader gameInfoHeader = new GameInfoHeader();
        gameInfoHeader.setWidth(ScreenDimensionsManager.getScreenWidthValue(95));
        MyWrappedLabel categoryLabel = new MyWrappedLabel(new SpecificPropertiesUtils().getQuestionCategoryLabel(gameUser.getGameQuestionInfo().getQuestion().getQuestionCategory().getIndex()));
        float multiplier = 1f;
        categoryLabel.setStyleDependingOnContrast();
        categoryLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(multiplier));
        while (alignedRightTable.getPrefWidth() + categoryLabel.getPrefWidth() > gameInfoHeader.getWidth()) {
            multiplier = multiplier - 0.1f;
            categoryLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(multiplier));
        }
        gameInfoHeader.add(categoryLabel).padRight(MainDimen.horizontal_general_margin.getDimen()).top();
        gameInfoHeader.add().growX();
        gameInfoHeader.add(alignedRightTable);
        return gameInfoHeader;
    }
}
