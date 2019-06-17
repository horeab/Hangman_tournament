package libgdx.ui.screens.game.creator;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.popup.ImagePopup;
import libgdx.graphics.GraphicUtils;
import libgdx.resources.MainResource;
import libgdx.resources.dimen.MainDimen;
import libgdx.ui.screens.AbstractScreen;

public class ImageZoomControlCreator {

    public Table createZoomTable() {
        Table table = new Table();
        Image zoom = GraphicUtils.getImage(MainResource.magnify_glass);
        float sideDimen = MainDimen.horizontal_general_margin.getDimen() * 5;
        table.add().growY().row();
        Table zoomTable = new Table();
        zoomTable.setBackground(GraphicUtils.getNinePatch(MainResource.popup_background));
        zoomTable.add(zoom);
        table.add(zoomTable).height(sideDimen).width(sideDimen);
        return table;
    }

    public ClickListener createZoomListener(final AbstractScreen screen,final Actor questionImage) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new ImagePopup(screen, questionImage).addToPopupManager();
            }

        };
    }
}
