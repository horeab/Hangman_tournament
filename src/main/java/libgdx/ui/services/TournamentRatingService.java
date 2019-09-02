package libgdx.ui.services;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.controls.button.MyButton;
import libgdx.controls.popup.RatingPopup;
import libgdx.controls.popup.RatingService;
import libgdx.ui.controls.button.builders.FacebookShareButtonBuilder;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;

public class TournamentRatingService extends RatingService<AbstractScreen> {


    public TournamentRatingService(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    protected RatingPopup createRatingPopup() {
        final BaseUserInfo currentUser = getScreen().getCurrentUser();
        return new RatingPopup(getScreen()) {
            @Override
            protected void addExtraButtons() {
                MyButton shareButton = new FacebookShareButtonBuilder(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        screenManager.showMainScreen();
                    }
                }).setShareButtonWithDefaultClickListener(currentUser, getScreen().toString()).build();
                addButton(shareButton);
            }
        };
    }

}

