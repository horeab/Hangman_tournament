package libgdx.ui.controls.userstatusbar;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.MyButton;
import libgdx.controls.button.builders.BackButtonBuilder;
import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.ScreenRunnable;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.ui.controls.labelimage.inventory.TournamentInventoryTableBuilder;
import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.mainmenu.popup.LoginPopup;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.ui.services.game.userexperience.UserExperienceService;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class UserStatusBarBuilder {

    public static final String ACTOR_NAME_PROFILE_PICTURE = "USERSTATUSBAR_ACTOR_NAME_PROFILE_PICTURE";

    private static final Dimen PROFILE_PICTURE_SIDE_DIMEN = Dimen.side_userstatus_profile_picture;
    private float USERSTATUSBAR_WIDTH = ScreenDimensionsManager.getScreenWidthValue(95);

    private UserInventoryDbApiService userInventoryDbApiService = new UserInventoryDbApiService();

    private UserInfo userInfo;
    private AbstractScreen screen;
    private boolean withLoginButton;

    public UserStatusBarBuilder(AbstractScreen screen) {
        this.userInfo = new UserInfo(screen.getCurrentUser(), PROFILE_PICTURE_SIDE_DIMEN);
        this.screen = screen;
    }

    public UserStatusBarBuilder withLoginButton() {
        withLoginButton = true;
        return this;
    }

    public UserStatusBar build() {
        UserStatusBar userStatusBar = new UserStatusBar(screen);
        Stack stack = new Stack();
        stack.setWidth(USERSTATUSBAR_WIDTH);
        stack.add(createBackgroundTable());
        stack.add(createUserInfoTable());
        userStatusBar.add(stack).width(USERSTATUSBAR_WIDTH).row();
        Table experienceTable = createExperienceTable();
        userStatusBar.add(experienceTable).height(experienceTable.getPrefHeight()).width(USERSTATUSBAR_WIDTH);
        return userStatusBar;
    }


    private Table createBackgroundTable() {
        Table table = new Table();
        table.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_background_default));
        return table;
    }

    private Table createUserInfoTable() {
        Image profilePictureImage = userInfo.getProfilePictureImage();
        profilePictureImage.setName(ACTOR_NAME_PROFILE_PICTURE);
        Table infoTable = new Table();
        infoTable.add(createUsernameTable(userInfo.getUserNameLabel())).padLeft(MainDimen.horizontal_general_margin.getDimen());
        infoTable.add().growX();
        if (TournamentGame.getInstance().getLoginService().isUserLoggedIn()) {
            infoTable.add(createInventoryTable());
        }
        if (withLoginButton) {
            profilePictureImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    new LoginPopup(screen).addToPopupManager();
                }
            });
        }
        userInfo.getProfilePictureContainer().toFront();

        Table table = new Table();
        if (Gdx.app.getType() == Application.ApplicationType.iOS) {
            MyButton backBtn = new BackButtonBuilder().createScreenBackButton(screen);
            table.add(backBtn).height(backBtn.getHeight()).width(backBtn.getWidth());
        }
        table.add(userInfo.getProfilePictureContainer()).padLeft(ScreenDimensionsManager.getScreenWidthValue(0.5f));
        table.add(infoTable).growX();
        return table;
    }

    private Table createUsernameTable(MyWrappedLabel userNameLabel) {
        Table table = new Table();
        userNameLabel.setWidth(ScreenDimensionsManager.getScreenWidthValue(80));
        userNameLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(0.8f));
        table.add(userNameLabel);
        return table;
    }

    private Table createExperienceTable() {
        Table fullTable = new Table();
        fullTable.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_experience_full));
        float percentFillUntilNextLevel = new UserExperienceService().getPercentFillUntilNextLevel(userInfo.getExperience());
        float widthToFill = percentFillUntilNextLevel * USERSTATUSBAR_WIDTH;
        if (percentFillUntilNextLevel == 0) {
            fullTable.setVisible(false);
        }
        fullTable.setWidth(widthToFill);
        Table emptyTable = new Table();
        emptyTable.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_experience_empty));
        emptyTable.setWidth(USERSTATUSBAR_WIDTH - widthToFill);
        Table table = new Table();
        float height = MainDimen.vertical_general_margin.getDimen() / 2;
        table.add(fullTable).height(height).width(fullTable.getWidth());
        table.add(emptyTable).height(height).width(emptyTable.getWidth());
        return table;
    }

    private Table createInventoryTable() {
        final Table inventoryTable = new Table();
        new Thread(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                final UserInventory userInventory = userInventoryDbApiService.getUserInventory(userInfo.getBaseUserInfo().getId());
                Gdx.app.postRunnable(new ScreenRunnable(screen) {
                    @Override
                    public void executeOperations() {
                        inventoryTable.add(new TournamentInventoryTableBuilder(new TournamentTransactionAmount(userInventory))
                                .setCoinsFontColor(MyWrappedLabelConfigBuilder.getScreenContrastStyle())
                                .setDiamondFontColor(MyWrappedLabelConfigBuilder.getScreenContrastStyle())
                                .setRationFontImage(0.9f)
                                .setNegativeAmountMinusPrefix()
                                .buildHorizontalInventory());
                    }
                });
            }
        }).start();
        return inventoryTable;
    }

}
