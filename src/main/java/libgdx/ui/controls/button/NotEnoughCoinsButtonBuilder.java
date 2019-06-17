package libgdx.ui.controls.button;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MainButtonSize;
import libgdx.controls.button.MyButton;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.labelimage.LabelImage;
import libgdx.controls.labelimage.LabelImageConfigBuilder;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.services.ScreenManager;
import libgdx.resources.FontManager;

public class NotEnoughCoinsButtonBuilder extends ButtonBuilder {

    private int coins;
    private int diamond;
    private TransactionAmountEnum notEnoughCoinsForTransaction;

    public NotEnoughCoinsButtonBuilder(UserInventory userInventory, TransactionAmountEnum notEnoughCoinsForTransaction) {
        this(userInventory.getCoins(), userInventory.getDiamond(), notEnoughCoinsForTransaction);
    }

    private NotEnoughCoinsButtonBuilder(int coins, int diamond, TransactionAmountEnum notEnoughCoinsForTransaction) {
        setButtonSkin(ButtonSkin.NORMAL_ORANGE);
        setFixedButtonSize(MainButtonSize.STANDARD_IMAGE);
        this.coins = coins;
        this.diamond = diamond;
        this.notEnoughCoinsForTransaction = notEnoughCoinsForTransaction;
    }


    private AccessConfig getAccessConfig() {
        final ScreenManager screenManager = TournamentGame.getInstance().getScreenManager();
        AccessConfig practiceWithCoinsAccessConfing = new AccessConfig(Resource.coins, TournamentGameLabel.mainmenu_button_practice, new Runnable() {
            @Override
            public void run() {
                screenManager.showPracticeOnlineOptionsScreen();
            }
        });
        if (coins < Math.abs(notEnoughCoinsForTransaction.getCoins())) {
            return practiceWithCoinsAccessConfing;
        } else if (diamond < Math.abs(notEnoughCoinsForTransaction.getDiamond())) {
            return new AccessConfig(Resource.diamond, TournamentGameLabel.mainmenu_button_tournament, new Runnable() {
                @Override
                public void run() {
                    screenManager.showTournamentOnlineOptionsScreen();
                }
            });
        }
        return practiceWithCoinsAccessConfing;
    }

    @Override
    public MyButton build() {
        final AccessConfig accessConfig = getAccessConfig();
        LabelImage gameInfoTable = new LabelImage(new LabelImageConfigBuilder()
                .setImage(accessConfig.resource)
                .setMarginBetweenLabelImage(MainDimen.horizontal_general_margin.getDimen())
                .setText(accessConfig.gameLabel.getText())
                .setAlignTextRight(true)
                .build());
        addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                accessConfig.run.run();
            }
        });
        Table plusTable = new Table();
        MyWrappedLabel myLabel = new MyWrappedLabel("+");
        myLabel.setFontScale(FontManager.getBigFontDim());
        plusTable.add(myLabel);
        Table table = new Table();
        table.add(plusTable);
        table.add(gameInfoTable);
        addCenterTextImageColumn(table);
        setFixedButtonSize(ButtonSize.ONE_ROW_BUTTON_SIZE);
        return super.build();
    }

    private static class AccessConfig {
        private Resource resource;
        private TournamentGameLabel gameLabel;
        private Runnable run;

        AccessConfig(Resource resource, TournamentGameLabel gameLabel, Runnable run) {
            this.resource = resource;
            this.gameLabel = gameLabel;
            this.run = run;
        }
    }
}
