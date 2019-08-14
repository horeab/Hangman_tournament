package libgdx.ui.controls.animation;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import libgdx.controls.ScreenRunnable;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.FontManager;
import libgdx.resources.Res;
import libgdx.ui.resources.Resource;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.utils.SoundUtils;
import libgdx.utils.model.FontColor;

public class ImageAnimation {

    private libgdx.screen.AbstractScreen screen;

    public ImageAnimation(libgdx.screen.AbstractScreen screen) {
        this.screen = screen;
    }

    public Table animateWinTrophy() {
        return createTable(Resource.trophy, TournamentGameLabel.level_finished_tournamnet_success_msg1.getText());
    }

    public Table animateUnlockNextStage() {
        return createTable(Resource.campaign_wall_explosion, "");
    }

    private Table createTable(Res resource, String text) {
        Table table = new Table();
        table.setWidth(ScreenDimensionsManager.getScreenWidth());
        table.setHeight(ScreenDimensionsManager.getScreenHeight());
        table.add(GraphicUtils.getImage(resource)).row();
        table.setBackground(GraphicUtils.getNinePatch(Resource.userstatusbar_background_default));
        MyWrappedLabel myLabel = new MyWrappedLabel(text);
        myLabel.setFontScale(FontManager.getBigFontDim());
        myLabel.setTextColor(FontColor.RED);
        table.add(myLabel);
        table.setTransform(true);
        animateZoomInZoomOut(table);
        return table;
    }

    private void animateZoomInZoomOut(Actor actor) {
        float duration = 0.6f;
        float zoomAmount = 1.5f;
        actor.setOrigin(Align.center);
        actor.addAction(Actions.sequence(
                Actions.scaleBy(zoomAmount, zoomAmount, duration),
                Actions.scaleBy(-zoomAmount, -zoomAmount, duration / 2f),
                createSoundRunnableAction(),
                Actions.delay(0.3f),
                Actions.fadeOut(0.5f)
        ));
    }

    private RunnableAction createSoundRunnableAction() {
        RunnableAction run = new RunnableAction();
        run.setRunnable(new ScreenRunnable(screen) {
            @Override
            public void executeOperations() {
                SoundUtils.playSound(Resource.sound_image_bang);
            }
        });
        return run;
    }

}
