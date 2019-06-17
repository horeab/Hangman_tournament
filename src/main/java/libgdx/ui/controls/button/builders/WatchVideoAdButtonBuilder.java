package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.controls.button.builders.ButtonWithIconBuilder;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;

public class WatchVideoAdButtonBuilder extends ButtonWithIconBuilder {

    public WatchVideoAdButtonBuilder() {
        super(TournamentGameLabel.resources_free_offer_video.getText(), Resource.video);
        setDefaultButton();
        setRewardBottomRow(new TournamentTransactionAmount(TransactionAmountEnum.WIN_WATCH_VIDEO_AD));
        addClickListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TournamentGame.getInstance().getAppInfoService().showRewardedVideoAd();
            }
        });
    }

    @Override
    public MyButton build() {
        final MyButton myButton = super.build();
        myButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                myButton.setDisabled(true);
            }
        });
        return myButton;

    }
}
