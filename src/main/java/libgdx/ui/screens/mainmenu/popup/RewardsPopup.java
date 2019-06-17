package libgdx.ui.screens.mainmenu.popup;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.button.builders.BuyOfferButtonBuilder;
import libgdx.ui.controls.button.builders.FacebookShareButtonBuilder;
import libgdx.controls.button.builders.RateAppButtonBuilder;
import libgdx.ui.controls.button.builders.WatchVideoAdButtonBuilder;
import libgdx.controls.popup.MyPopup;
import libgdx.game.external.BillingService;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.mainmenu.popup.rewards.FacebookShareRewardConfig;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;
import libgdx.ui.screens.mainmenu.popup.rewards.WatchVideoAdRewardConfig;
import libgdx.ui.services.ScreenManager;

public class RewardsPopup extends MyPopup<AbstractScreen, ScreenManager> {

    public RewardsPopup(AbstractScreen abstractScreen) {
        super(abstractScreen);
    }

    @Override
    public void addButtons() {

        BillingService billingService = TournamentGame.getInstance().getBillingService();
        if (!billingService.removeAdsAlreadyBought() && Gdx.app.getType() != Application.ApplicationType.iOS) {
            addButton(BuyOfferButtonBuilder.removeAdsButton().build());
        }

        RewardsService watchVideoAdRewardsService = new RewardsService(new WatchVideoAdRewardConfig());
        if (watchVideoAdRewardsService.isValidGiveRewardForItem()) {
            addButton(new WatchVideoAdButtonBuilder().build());
        }

        RewardsService facebookRewardsService = new RewardsService(new FacebookShareRewardConfig());
        if (facebookRewardsService.isValidGiveRewardForItem()) {
            addButton(new FacebookShareButtonBuilder(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screenManager.showMainScreen();
                }
            }).setShareButtonWithDefaultClickListener(getScreen().getCurrentUser(), getScreen().toString()).build());
        }

        if (!TournamentGame.getInstance().getMainDependencyManager().createRatingService(getScreen()).alreadyRated()) {
            addButton(new RateAppButtonBuilder(getScreen()).rateNowButton().build());
        }

    }

    @Override
    protected String getLabelText() {
        return "";
    }
}
