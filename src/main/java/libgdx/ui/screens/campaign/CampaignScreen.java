package libgdx.ui.screens.campaign;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.List;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.controls.button.CampaignLevelButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.model.game.CampaignLevelDb;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.game.campaign.CampaignService;
import libgdx.ui.services.CampaignLevelEnumService;
import libgdx.utils.EnumUtils;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class CampaignScreen extends AbstractScreen {

    private int scrollPanePositionInit = 0;
    private CampaignService campaignService = new CampaignService();
    private List<CampaignLevelDb> allCampaignLevelDbs;
    private ScrollPane scrollPane;
    private Integer scrollToLevel;
    private int nrOfLevels;
    private float levelHeight;

    @Override
    protected void initFields() {
        super.initFields();
        allCampaignLevelDbs = campaignService.processAndGetAllLevels(getCurrentUser().getId());
        nrOfLevels = ((CampaignLevel[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getCampaignLevelTypeEnum())).length;
        CampaignLevelDb maxOpenedLevel = campaignService.getMaxOpenedLevel(allCampaignLevelDbs);
        scrollToLevel = maxOpenedLevel == null ? nrOfLevels : maxOpenedLevel.getLevel();
        levelHeight = MainDimen.vertical_general_margin.getDimen() * 18;
        scrollPane = new ScrollPane(createAllScroll());
    }

    @Override
    public void buildStage() {
        super.buildStage();
        Table table = new Table();
        table.setFillParent(true);
        scrollPane.setScrollingDisabled(true, false);
        table.add(new UserStatusBar(this).createTable()).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        table.add(scrollPane).expand();
        scrollPane.scrollTo(0, 2210, 0, 0);
        addActor(table);
    }

    private Stack createAllScroll() {
        Table backgroundsTable = new Table();
        Table dottedLinesTable = new Table();
        Table levelIconsTable = new Table();
        CampaignLevel[] levels = (CampaignLevel[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getCampaignLevelTypeEnum());
        boolean leftToRight = true;
        int i = 0;
        float itemTableWidth = ScreenDimensionsManager.getScreenWidthValue(95);
        while (i < nrOfLevels) {
            Table backgroundTable = new Table();
            Table dottedLineTable = new Table();
            Table levelIconTable = new Table();
            Resource dottedLineResource;
            Resource backgroundTexture = new CampaignLevelEnumService(levels[i]).getBackgroundTexture();
            if (i == 0) {
                addLevelButtons(levelIconTable, 1, levels[i], levels[i + 1]);
                dottedLineResource = Resource.table_down_center_left_dotted_line;
                i += 2;
            } else if (i + 1 > nrOfLevels - 1) {
                addLevelButtons(levelIconTable, -1, levels[i], null);
                dottedLineResource = (nrOfLevels - 1) % 2 == 0 ? Resource.table_up_center_left_dotted_line : Resource.table_center_right_dotted_line;
                i += 1;
            } else {
                dottedLineResource = leftToRight ? Resource.table_left_right_dotted_line : Resource.table_right_left_dotted_line;
                leftToRight = !leftToRight;
                addLevelButtons(levelIconTable, leftToRight ? 1 : -1, levels[i], levels[i + 1]);
                i += 2;
            }
            backgroundTable.setBackground(GraphicUtils.getNinePatch(backgroundTexture));
            dottedLineTable.setBackground(GraphicUtils.getNinePatch(dottedLineResource));
            backgroundsTable.add(backgroundTable).height(levelHeight).width(itemTableWidth).row();
            dottedLinesTable.add(dottedLineTable).height(levelHeight).width(itemTableWidth).row();
            levelIconsTable.add(levelIconTable).height(levelHeight).width(itemTableWidth).row();
        }
        Table backgroundTable = new Table();
        backgroundTable.setBackground(GraphicUtils.getNinePatch(new CampaignLevelEnumService(levels[nrOfLevels - 1]).getBackgroundTexture()));
        backgroundsTable.add(backgroundTable).height(levelHeight).width(itemTableWidth).row();
        dottedLinesTable.add().height(levelHeight).width(itemTableWidth).row();
        levelIconsTable.add().height(levelHeight).width(itemTableWidth).row();
        Stack stack = new Stack();
        stack.addActor(backgroundsTable);
        stack.addActor(dottedLinesTable);
        stack.addActor(levelIconsTable);
        return stack;
    }

    private void addLevelButtons(Table lineTable, int sidePadDirection, CampaignLevel level1, CampaignLevel level2) {
        Table levelsTable = new Table();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        float padTopAllTable = verticalGeneralMarginDimen;
        if (level1 != null) {
            levelsTable.add(createButtonTable(level1)).row();
        }
        if (level2 != null) {
            levelsTable.add(createButtonTable(level2)).padTop(verticalGeneralMarginDimen).padRight(sidePadDirection * MainDimen.horizontal_general_margin.getDimen() * 26).row();
            padTopAllTable = verticalGeneralMarginDimen * 9;
        }
        lineTable.add(levelsTable).padTop(padTopAllTable).fillX();
    }

    private Table createButtonTable(CampaignLevel levelEnum) {
        Table levelTable = new Table();
        CampaignLevelDb campaignLevelDb = campaignService.getCampaignLevel(levelEnum.getIndex(), allCampaignLevelDbs);
        MyButton levelBtn = new CampaignLevelButtonBuilder(this, levelEnum, campaignLevelDb).setLevelLocked(campaignLevelDb == null && !TournamentGame.getInstance().getAppInfoService().screenShotMode()).build();
        levelTable.add(levelBtn).width(levelBtn.getWidth()).height(levelBtn.getHeight()).row();
        levelTable.setWidth(levelBtn.getWidth());
        levelTable.setHeight(levelBtn.getHeight());
        return levelTable;
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

    @Override
    public void render(float dt) {
        super.render(dt);
        // scrollPanePositionInit needs to be used otherwise the scrollTo wont work
        if (scrollPane != null && scrollPanePositionInit < 2) {
            scrollPane.setScrollY((scrollToLevel / 2) * levelHeight);
            scrollPanePositionInit++;
        }
    }

}
