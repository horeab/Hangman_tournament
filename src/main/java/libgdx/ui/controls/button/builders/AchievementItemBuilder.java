package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.button.ButtonBuilder;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.ui.controls.IncrementBar;
import libgdx.ui.controls.button.ButtonSize;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.ui.resources.Resource;
import libgdx.resources.ResourcesManager;
import libgdx.ui.services.dbapi.GameStatsDbApiService;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.game.achievements.AchievementsService;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;

public class AchievementItemBuilder extends ButtonBuilder {

    private final static float GENERAL_MARGIN = MainDimen.horizontal_general_margin.getDimen();

    private AchievementsService achievementsService = new AchievementsService();
    private ShopTransactionsDbApiService shopTransactionsDbApiService = new ShopTransactionsDbApiService();
    private GameStatsDbApiService gameStatsDbApiService = new GameStatsDbApiService();

    private int userId;
    private AchievementEnum achievement;

    public AchievementItemBuilder(int userId, AchievementEnum achievement) {
        this.userId = userId;
        this.achievement = achievement;
        setButtonSkin(ButtonSkin.ACHIEVEMENT_ITEM);
        setFixedButtonSize(ButtonSize.ACHIEVEMENT);
    }

    @Override
    public MyButton build() {
        int totalAmount;
        if (achievement == AchievementEnum.QUESTIONS_WIN) {
            totalAmount = gameStatsDbApiService.getGameStats(userId).getQuestionsWon();
        } else {
            totalAmount = shopTransactionsDbApiService.selectTotalAmountShopTransactionType(userId, achievement.getShopTransactionTypeEnumsArray());
        }
        return prepareButton(achievement, totalAmount);
    }

    private MyButton prepareButton(AchievementEnum item, int totalAmount) {
        float buttonWidth = ButtonSize.ACHIEVEMENT.getWidth();
        float labelImageWidth = buttonWidth / 1.4f;

        Table table = new Table();
        MyWrappedLabel label = new MyWrappedLabel(new MyWrappedLabelConfigBuilder().setText(item.getGameLabel().getText()).setWidth(labelImageWidth / 1.5f).setFontScale(FontManager.getSmallFontDim()).build());

        Table labelImageTable = new Table();
        float iconDimen = GENERAL_MARGIN * 9;
        labelImageTable.add(label).padBottom(GENERAL_MARGIN * 2).row();
        labelImageTable.add(GraphicUtils.getImage(item.getAchievementType().getResource())).height(iconDimen).width(iconDimen);

        table.add(labelImageTable).width(labelImageWidth);
        table.add(createLevelUpTable(totalAmount, item.getLevelUpAmountArray())).fill();
        table.setWidth(buttonWidth);
        table.setHeight(ButtonSize.ACHIEVEMENT.getHeight());
        addCenterTextImageColumn(table);
        MyButton button = super.build();
        button.setTouchable(Touchable.disabled);
        return button;
    }

    private Table createLevelUpTable(int totalAmount, Integer[] levelUpAmountArray) {
        int currentLevelAmount;
        int finishedLevels;
        if (levelUpAmountArray.length == 1) {
            currentLevelAmount = achievementsService.getCurrentStepsWithMaxLevel(totalAmount, levelUpAmountArray);
            finishedLevels = achievementsService.getFinishedStepsWithMaxLevel(totalAmount, levelUpAmountArray);
        } else {
            currentLevelAmount = achievementsService.getCurrentSteps(totalAmount, levelUpAmountArray);
            finishedLevels = achievementsService.getFinishedSteps(totalAmount, levelUpAmountArray);
        }

        float heightLevelUpGraphic = ButtonSize.START_GAME_OPTION_SIZE.getHeight() / 1.5f;
        Table table = new Table();
        MyWrappedLabel label = new MyWrappedLabel(finishedLevels + " x ");
        if (finishedLevels > 0) {
            label.setStyle(ResourcesManager.getLabelGreen());
        }
        table.add(label);
        table.add(GraphicUtils.getImage(Resource.achievement))
                .width(GENERAL_MARGIN * 5)
                .height(GENERAL_MARGIN * 5)
                .row();
        table.add(new IncrementBar(achievementsService.getTotalSteps(totalAmount, levelUpAmountArray), currentLevelAmount).createVerticalBar())
                .colspan(2).padTop(GENERAL_MARGIN).height(heightLevelUpGraphic);
        return table;
    }


}
