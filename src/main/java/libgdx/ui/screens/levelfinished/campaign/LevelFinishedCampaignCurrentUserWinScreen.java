package libgdx.ui.screens.levelfinished.campaign;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.controls.StarsBarCreator;
import libgdx.ui.controls.animation.ImageAnimation;
import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.labelimage.prize.WinLevelUpPrizeLabelImage;
import libgdx.ui.controls.user.UserInfoContainerConfig;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.screens.levelfinished.UsersWithLevelFinished;
import libgdx.ui.services.game.campaign.CampaignService;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.services.CampaignLevelEnumService;
import libgdx.utils.model.RGBColor;

public class LevelFinishedCampaignCurrentUserWinScreen extends LevelFinishedCampaignScreen {

    private int totalWonQuestions;

    public LevelFinishedCampaignCurrentUserWinScreen(UsersWithLevelFinished usersWithLevelFinished, GameContext gameContext, CampaignLevel campaignLevelEnum) {
        super(usersWithLevelFinished, gameContext, campaignLevelEnum);
    }

    @Override
    protected void initFields() {
        super.initFields();
        this.totalWonQuestions = getUsersWithLevelFinished().getGameUserThatWon().getWonQuestions();
    }

    @Override
    public void buildStage() {
        super.buildStage();
        CampaignLevelEnumService campaignLevelEnumService = new CampaignLevelEnumService(campaignLevelEnum);
        Table horizontalStarsBar = new StarsBarCreator(TournamentGame.getInstance().getSubGameDependencyManager()
                .getStarsService(campaignLevelEnumService.getQuestionConfig().getAmountOfQuestions()).getStarsWon(totalWonQuestions))
                .createHorizontalStarsBar();
        Table prizeTable = new WinLevelUpPrizeLabelImage(getCurrentUser().getId(), getGameContext().getGameConfig().getGameTypeStage(), getGameContext().getGameConfig().getUniqueId()).create();
        getGeneralInfoTable().add(horizontalStarsBar).height(horizontalStarsBar.getHeight()).width(horizontalStarsBar.getWidth()).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
        getGeneralInfoTable().add(prizeTable).expandX().row();
        setBackgroundColor(RGBColor.LIGHT_BLUE);
        addContinueButton();

        if (campaignLevelEnumService.getCategory() == null && prizeTable.hasChildren()) {
            addActor(new ImageAnimation(this).animateUnlockNextStage());
        }
    }

    @Override
    protected void executeShopTransactions() {
        super.executeShopTransactions();
        gameTransactionsService.gameWin(getGameContext().getGameConfig());
        new CampaignService().levelFinished(getCurrentUser().getId(), totalWonQuestions, campaignLevelEnum);
    }

    @Override
    protected List<UserInfoContainerConfig> createUserInfoContainerConfig() {
        return levelFinishedContainersCreator.create1PlayersCurrentUserWinUserInfoContainerConfig();
    }

    @Override
    protected String getTitleMsg() {
        return TournamentGameLabel.level_finished_success_msg1.getText();
    }

    @Override
    protected String getInfoMsg() {
        return TournamentGameLabel.level_finished_success_msg2.getText();
    }

    private void addContinueButton() {
        MyButton continueBtn = new ButtonBuilder(TournamentGameLabel.level_finished_continue.getText()).setDefaultButton().build();
        getGameFinishedInfoMessagesTable().add(continueBtn).width(Dimen.width_btn_menu_normal.getDimen()).height(Dimen.height_btn_menu_normal.getDimen()).padTop(MainDimen.vertical_general_margin.getDimen()).row();
        continueBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenManager.showCampaignScreen();
            }
        });
    }

}
