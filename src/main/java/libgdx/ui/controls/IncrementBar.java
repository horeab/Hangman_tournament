package libgdx.ui.controls;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.game.TournamentGame;
import libgdx.controls.animations.ActorAnimation;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.graphics.GraphicUtils;

public class IncrementBar {

    private int totalSteps;
    private int currentSteps;

    public IncrementBar(int totalSteps, int currentSteps) {
        this.totalSteps = totalSteps;
        this.currentSteps = currentSteps;
    }

    public Table createHorizontalBar(Resource levelUpFillResource) {
        Table table = new Table();
        for (int i = 0; i < totalSteps; i++) {
            Table row = createRow(i, levelUpFillResource);
            table.add(row).width(row.getWidth()).height(row.getHeight()).fill().padRight(MainDimen.vertical_general_margin.getDimen() / 2).expand();
        }
        return table;
    }

    public Table createVerticalBar() {
        Table table = new Table();
        for (int i = totalSteps - 1; i >= 0; i--) {
            Table row = createRow(i, Resource.levelup_fill_green);
            table.add(row).width(row.getWidth()).height(row.getHeight()).fill().expand().row();
        }
        return table;
    }

    private Table createRow(int i, Resource levelUpFillResource) {
        Table tableLevel = new Table();
        Resource background = i < currentSteps ? levelUpFillResource : Resource.levelup_background;
        if (currentSteps == totalSteps) {
            background = Resource.levelup_fill_yellow;
        } else if (i == currentSteps ) {
            new ActorAnimation(tableLevel, (AbstractScreen) TournamentGame.getInstance().getScreen()).animateFastFadeInFadeOut();
        }
        tableLevel.setBackground(GraphicUtils.getNinePatch(background));
        float sideDim = MainDimen.horizontal_general_margin.getDimen() * 2.5f   ;
        tableLevel.setHeight(sideDim);
        tableLevel.setWidth(sideDim);
        return tableLevel;
    }
}
