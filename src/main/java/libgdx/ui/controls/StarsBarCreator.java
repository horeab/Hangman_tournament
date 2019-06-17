package libgdx.ui.controls;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.resources.Resource;
import libgdx.ui.implementations.config.dependecies.StarsService;
import libgdx.graphics.GraphicUtils;

public class StarsBarCreator {

    private int wonStars;

    public StarsBarCreator(int wonStars) {
        this.wonStars = wonStars;
    }

    public Table createHorizontalStarsBar() {
        Table table = new Table();
        float verticalGeneralMarginDimen = MainDimen.vertical_general_margin.getDimen();
        float rightMargin = verticalGeneralMarginDimen / 2;
        float sideDimen = verticalGeneralMarginDimen * 1.5f;
        for (int i = 1; i <= StarsService.NR_OF_STARS_TO_DISPLAY; i++) {
            Resource starsRes = i <= wonStars ? Resource.star_enabled : Resource.star_disabled;
            table.add(GraphicUtils.getImage(starsRes)).height(sideDimen).width(sideDimen).padRight(rightMargin).expand();
        }
        table.setHeight(sideDimen);
        table.setWidth((sideDimen + rightMargin) * StarsService.NR_OF_STARS_TO_DISPLAY);
        return table;
    }

}
