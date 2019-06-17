package libgdx.ui.screens.achievements;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.constants.game.achievements.AchievementEnum;
import libgdx.controls.button.MyButton;
import libgdx.ui.controls.button.builders.AchievementItemBuilder;
import libgdx.ui.controls.userstatusbar.UserStatusBar;
import libgdx.ui.screens.AbstractScreen;

public class AchievementsScreen extends AbstractScreen {

    @Override
    public void buildStage() {
        super.buildStage();
        Table table = new Table();
        table.setFillParent(true);
        ScrollPane scrollPane = new ScrollPane(createAchievementsTable());
        scrollPane.setScrollingDisabled(true, false);
        float dimen = MainDimen.horizontal_general_margin.getDimen();
        table.add(new UserStatusBar(this).createTable()).padBottom(dimen).padTop(dimen).top().row();
        table.add(scrollPane).expand();
        addActor(table);
    }

    private Table createAchievementsTable() {
        Table table = new Table();
        table.add(createAchievementsListTable());
        return table;
    }

    private Table createAchievementsListTable() {
        Table table = new Table();
        float margin = MainDimen.vertical_general_margin.getDimen();
        for (AchievementEnum item : AchievementEnum.values()) {
            MyButton button = new AchievementItemBuilder(getCurrentUser().getId(), item).build();
            table.add(button).height(button.getHeight()).width(button.getWidth()).padTop(margin).padBottom(margin).row();
        }
        return table;
    }
    @Override
    public void onBackKeyPress() {
        screenManager.showMainScreen();
    }

}
