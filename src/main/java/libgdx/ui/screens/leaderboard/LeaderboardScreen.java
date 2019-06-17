package libgdx.ui.screens.leaderboard;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.LinkedHashSet;

import libgdx.controls.label.MyWrappedLabel;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.model.inventory.ExperienceWithUser;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.resources.ResourcesManager;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class LeaderboardScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        super.buildStage();
        Table table = new Table();
        table.setFillParent(true);
        ScrollPane scrollPane = new ScrollPane(createLeaderboardTable());
        scrollPane.setScrollingDisabled(true, true);
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        table.add(new UserStatusBar(this).createTable()).padBottom(dimen).padTop(dimen).top().row();
        table.add(scrollPane).expand();
        addActor(table);
    }

    private Table createLeaderboardTable() {
        final Table table = new Table();
        table.padTop(MainDimen.vertical_general_margin.getDimen());
        final LinkedHashSet<ExperienceWithUser> list = userInventoryDbApiService.selectLeaderboardExperience(getCurrentUser().getId());
        Integer previousRank = -1;
        for (ExperienceWithUser item : list) {
            //if key is null, it means that its an dots dots item ...
            if (previousRank != -1 && item.getRank() - previousRank > 1) {
                Table infoTable = new Table();
                infoTable.setBackground(ResourcesManager.getTableBackgroundDefault());
                infoTable.add(new MyWrappedLabel(".........")).padBottom(MainDimen.vertical_general_margin.getDimen() / 2);
                table.add(infoTable).width(ScreenDimensionsManager.getScreenWidthValue(95)).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
            }
            Table infoTable = new Table();
            BaseUserInfo baseUserInfo = item.getUser();
            UserInfo userInfo = new UserInfo(baseUserInfo, Dimen.side_leaderboard_userinfo_profile_picture);
            infoTable.add(createRankLabel(item.getRank())).padLeft(MainDimen.horizontal_general_margin.getDimen()).padRight(MainDimen.horizontal_general_margin.getDimen() * 3);
            infoTable.add(userInfo.getProfilePictureContainer());
            infoTable.add(createLeaderboardUserInfo(userInfo)).padTop(MainDimen.vertical_general_margin.getDimen() / 2).padBottom(MainDimen.vertical_general_margin.getDimen() / 2).padRight(MainDimen.horizontal_general_margin.getDimen() * 3).padLeft(MainDimen.horizontal_general_margin.getDimen() * 3).row();
            if (baseUserInfo.getId() == getCurrentUser().getId()) {
                infoTable.setBackground(GraphicUtils.getNinePatch(Resource.leaderboard_currentuser_background));
            } else {
                infoTable.setBackground(GraphicUtils.getNinePatch(Resource.leaderboard_otheruser_background));
            }
            table.add(infoTable).width(ScreenDimensionsManager.getScreenWidthValue(95)).padBottom(MainDimen.vertical_general_margin.getDimen()).row();
            previousRank = item.getRank();
        }
        return table;
    }

    private MyWrappedLabel createRankLabel(int rank) {
        MyWrappedLabel rankLabel = new MyWrappedLabel(Integer.toString(rank));
        rankLabel.setAlignment(Align.center);
        rankLabel.setFontScale(FontManager.calculateMultiplierStandardFontSize(1f));
        return rankLabel;
    }

    private Table createLeaderboardUserInfo(UserInfo userInfo) {
        Table table = new Table();
        float labelWidth = ScreenDimensionsManager.getScreenWidthValue(40);
        userInfo.getUserNameLabel().setEllipsis(labelWidth);
        userInfo.getUserNameLabel().setAlignment(Align.left);
        userInfo.getUserNameLabel().setFontScale(FontManager.getNormalFontDim());
        table.add(userInfo.getUserNameLabel()).align(Align.left).width(labelWidth).row();
        return table;
    }

    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

}
