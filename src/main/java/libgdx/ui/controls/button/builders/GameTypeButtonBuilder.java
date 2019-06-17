package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyLabel;
import libgdx.controls.labelimage.LabelImage;
import libgdx.controls.labelimage.LabelImageConfigBuilder;
import libgdx.ui.controls.labelimage.price.PriceLabelImage;
import libgdx.ui.controls.labelimage.prize.IfLevelUpPrizeLabelImage;
import libgdx.resources.FontManager;

public class GameTypeButtonBuilder extends ButtonBuilder {

    private GameTypeStage gameTypeStage;
    private int userId;

    public GameTypeButtonBuilder(int userId, GameTypeStage gameTypeStage) {
        this.gameTypeStage = gameTypeStage;
        this.userId = userId;
    }

    @Override
    public MyButton build() {
        setFixedButtonSize(getButtonSize());

        Table table = new Table();
        table.add(createCoinsInfoTables());

        addCenterTextImageColumn(table);
        return super.build();
    }

    private Table createCoinsInfoTables() {
        Table table = new Table();
        table.add(new LabelImage(new LabelImageConfigBuilder().setText(gameTypeStage.getGameTypeLabelText()).setSingleLineLabel().build())).row();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        IfLevelUpPrizeLabelImage ifLevelUpPrizeLabelImage = new IfLevelUpPrizeLabelImage(userId, gameTypeStage);
        Table prize = ifLevelUpPrizeLabelImage.create();
        MyLabel myLabel = ifLevelUpPrizeLabelImage.getCoinsPrizeLabelImage().getLabels().get(0);
        if (new GlyphLayout(FontManager.getFont(), myLabel.getText()).width + getButtonSize().getWidth() / 3 > getButtonSize().getWidth()) {
            myLabel.setFontScale(myLabel.getFontScaleX() * 0.91f);
        }
        table.add(prize)
                .padTop(verticalGeneralMarginDimen)
                .padBottom(verticalGeneralMarginDimen)
                .row();
        table.add(new PriceLabelImage(gameTypeStage).create());
        return table;
    }

    private ButtonSize getButtonSize() {
        return ButtonSize.START_GAME_OPTION_SIZE;
    }

}
