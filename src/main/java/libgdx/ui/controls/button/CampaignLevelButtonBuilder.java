package libgdx.ui.controls.button;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.apache.commons.lang3.StringUtils;


import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.resources.MainResource;
import libgdx.resources.Res;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.controls.StarsBarCreator;
import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.labelimage.LabelImage;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilder;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.CampaignLevelDb;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.game.GameTransactionsService;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.services.CampaignLevelEnumService;
import libgdx.utils.DateUtils;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;

public class CampaignLevelButtonBuilder extends ButtonBuilder {

    private CampaignLevelDb level;
    private CampaignLevel levelEnum;
    private AbstractScreen screen;
    private float fontDimen = FontManager.calculateMultiplierStandardFontSize(0.8f);
    private boolean levelLocked;
    private CampaignLevelEnumService campaignLevelEnumService;

    public CampaignLevelButtonBuilder(AbstractScreen screen, CampaignLevel levelEnum, CampaignLevelDb level) {
        this.level = level;
        this.levelEnum = levelEnum;
        this.screen = screen;
        this.campaignLevelEnumService = new CampaignLevelEnumService(levelEnum);
    }

    public CampaignLevelButtonBuilder setLevelLocked(boolean levelLocked) {
        this.levelLocked = levelLocked;
        return this;
    }

    @Override
    public MyButton build() {
        setFixedButtonSize(ButtonSize.CAMPAIGN_LEVEL_ROUND_IMAGE);
        setButtonSkin(campaignLevelEnumService.getButtonSkin());
        addLevelInfo();
        addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new StartScreenWithCoinsService(screen.getCurrentUser(), new GameConfig(campaignLevelEnumService.getQuestionConfig(), campaignLevelEnumService.getGameTypeStage(), DateUtils.getNowDateString())).startCampaign(levelEnum);
            }
        });
        MyButton myButton = super.build();
        myButton.setDisabled(levelLocked);
        return myButton;
    }

    private void addLevelInfo() {
        Table table = new Table();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        if (level != null && level.getStatus() == CampaignLevelStatusEnum.FINISHED.getStatus()) {
            Table starsBar = createStarsBar();
            table.add(starsBar).height(starsBar.getHeight()).width(starsBar.getWidth()).padBottom(verticalGeneralMarginDimen / 2).row();
        }
        Table iconTable = createIconTable();
        table.add(iconTable).row();
        if (!levelLocked && StringUtils.isNotBlank(campaignLevelEnumService.getLabelText())) {
            table.add(createTextTable()).padTop(verticalGeneralMarginDimen / 2).row();
        }
        ShopTransactionTypeEnum entryFeeTransaction = campaignLevelEnumService.getGameTypeStage().getEntryFeeTransaction();
        if (!levelLocked
                && !entryFeeTransaction.getTransactionAmountEnum().isZeroAmount()
                && !(new GameTransactionsService(screen.getCurrentUser().getId()).isTransactionUniqueAndAlreadyExecuted(entryFeeTransaction, campaignLevelEnumService.getGameTypeStage().isUniqueTransaction()))) {
            table.add(createPayTransactionTable()).padTop(verticalGeneralMarginDimen / 2).row();
        }
        addCenterTextImageColumn(table);
    }

    private float getIconDimen() {
        return MainDimen.horizontal_general_margin.getDimen() * 10;
    }

    private Table createPayTransactionTable() {
        Table payTable = new TournamentInventoryTableBuilder(new TournamentTransactionAmount(campaignLevelEnumService.getGameTypeStage().getEntryFeeTransaction().getTransactionAmountEnum()))
                .setRationFontImage(0.8f).setNegativeAmountMinusPrefix().buildTransactionTable();
        payTable.setBackground(GraphicUtils.getNinePatch(Resource.pay_transaction));
        return payTable;
    }

    private LabelImage createTextTable() {
        LabelImage textTable = createTextTable(campaignLevelEnumService.getLabelText(), MainDimen.horizontal_general_margin.getDimen() * 17, fontDimen);
        textTable.setBackground(GraphicUtils.getNinePatch(MainResource.popup_background));
        return textTable;
    }

    private Table createStarsBar() {
        Table table = new StarsBarCreator(TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(campaignLevelEnumService.getQuestionConfig().getAmountOfQuestions()).getStarsWon(level.getQuestionAnswered())).createHorizontalStarsBar();
        table.setBackground(GraphicUtils.getNinePatch(Resource.stars_table_background));
        return table;
    }

    private Table createIconTable() {
        Table table = new Table();
        Table iconTable = new Table();
        float iconDimen = getIconDimen();
        Res icon = campaignLevelEnumService.getIcon();
        if (icon.getPath().equals(Resource.bomb.getPath())) {
            Stack bombWithFire = new Stack();
            bombWithFire.addActor(GraphicUtils.getImage(icon));
            Image image = GraphicUtils.getImage(Resource.fire);
            Table animTable = new Table();
            float animDimen = iconDimen / 2.3f;
            new ActorAnimation(image, screen).animateZoomInZoomOut(0.3f);
            animTable.add(image).padLeft(MainDimen.horizontal_general_margin.getDimen() * 4).padBottom(MainDimen.vertical_general_margin.getDimen() * 5).width(animDimen).height(animDimen);
            bombWithFire.addActor(animTable);
            iconTable.add(bombWithFire).width(iconDimen / 1.5f).height(iconDimen / 1.5f);
        } else {
            iconTable.add(GraphicUtils.getImage(icon)).width(iconDimen).height(iconDimen);
        }
        if (levelLocked) {
            Stack stack = new Stack();
            Image image = GraphicUtils.getImage(Resource.lock_icon);
            stack.addActor(image);
            table.add(stack).width(iconDimen).height(iconDimen);
        } else {
            table.add(iconTable);
        }
        return table;
    }

}
