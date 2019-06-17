package libgdx.ui.controls.button;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.button.ButtonBuilder;
import libgdx.controls.button.MyButton;
import libgdx.resources.MainResource;
import libgdx.resources.Res;
import libgdx.resources.dimen.MainDimen;
import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.labelimage.LabelImage;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.resources.FontManager;
import libgdx.graphics.GraphicUtils;

public class RoundButtonBuilder extends ButtonBuilder {

    private Res icon;
    private TournamentGameLabel text;
    private AbstractScreen screen;
    private boolean withSparkle;
    private float fontDimen = FontManager.getNormalFontDim();

    public RoundButtonBuilder(AbstractScreen screen) {
        this.screen = screen;
    }

    public ButtonBuilder setFontDimen(float fontDimen) {
        this.fontDimen = fontDimen;
        return this;
    }

    public RoundButtonBuilder setTournamentButton() {
        this.icon = Resource.btn_tournament;
        withSparkle = true;
        setup(TournamentGameLabel.mainmenu_button_tournament);
        return this;
    }

    public RoundButtonBuilder setCampaignButton() {
        this.icon = Resource.btn_campaign;
        withSparkle = true;
        setup(TournamentGameLabel.mainmenu_button_campaign);
        return this;
    }

    public RoundButtonBuilder setPracticeButton() {
        this.icon = Resource.btn_practice;
        setup(TournamentGameLabel.mainmenu_button_practice);
        return this;
    }

    public RoundButtonBuilder setChallengeButton() {
        this.icon = Resource.btn_challenge;
        setup(TournamentGameLabel.mainmenu_button_challenge);
        return this;
    }


    private void setup(TournamentGameLabel text) {
        this.text = text;
        setButtonSkin(ButtonSkin.BACKGROUND_CIRCLE);
        setFixedButtonSize(ButtonSize.BIG_MENU_ROUND_IMAGE);
    }

    @Override
    public MyButton build() {
        Table table = new Table();
        float horizontalGeneralMarginDimen = MainDimen.horizontal_general_margin.getDimen();
        float iconDimen = horizontalGeneralMarginDimen * 9;
        Stack iconWithSparkle = new Stack();
        iconWithSparkle.addActor(GraphicUtils.getImage(icon));
        if (withSparkle) {
            Image sparkle = GraphicUtils.getImage(Resource.diamond_sparkle);
            new ActorAnimation(sparkle, screen).animateZoomInZoomOut(0.7f);
            iconWithSparkle.addActor(sparkle);
        }
        Table iconTable = new Table();
        iconTable.add(iconWithSparkle)
                .width(iconDimen)
                .height(iconDimen);
        table.add(iconTable).row();
        LabelImage textTable = createTextTable(text.getText(), horizontalGeneralMarginDimen * 17, fontDimen);
        textTable.setBackground(GraphicUtils.getNinePatch(MainResource.popup_background));
        table.add(textTable).padTop(MainDimen.vertical_general_margin.getDimen() / 2);
        addCenterTextImageColumn(table);
        return super.build();
    }

}
