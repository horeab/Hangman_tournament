package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.mainmenu.popup.rewards.FacebookShareRewardConfig;
import libgdx.ui.screens.mainmenu.popup.rewards.RewardsService;

public class FacebookShareButtonBuilder extends InternetButtonWithIconBuilder {

    private RewardsService rewardsService;

    private String text;
    private ClickListener refreshScreenClickListener;

    public FacebookShareButtonBuilder(ClickListener refreshScreenClickListener) {
        this.refreshScreenClickListener = refreshScreenClickListener;
        rewardsService = new RewardsService(new FacebookShareRewardConfig());
    }

    public FacebookShareButtonBuilder setShareButtonWithDefaultClickListener(BaseUserInfo currentUser, String uniqueTransactionId) {
        setShareButton(currentUser, createClickListenerShareButton(), uniqueTransactionId);
        return this;
    }

    public FacebookShareButtonBuilder setShareButton(BaseUserInfo currentUser, ClickListener clickListener, String uniqueTransactionId) {
        text = MainGameLabel.facebook_share_btn.getText();
        if (rewardsService.isValidGiveRewardForItem()) {
            setRewardBottomRow(new TournamentTransactionAmount(rewardsService.getRewardConfig().getRewardShopTransaction().getTransactionAmountEnum()));
        }
        addClickListener(createClickListenerShareButtonWithReward(currentUser, uniqueTransactionId));
        addClickListener(clickListener);
        return this;
    }

    private ClickListener createClickListenerShareButtonWithReward(final BaseUserInfo currentUser, final String uniqueTransactionId) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (rewardsService.isValidGiveRewardForItem()) {
                    rewardsService.giveReward(currentUser.getId(), uniqueTransactionId);
                }
            }
        };
    }

    private ClickListener createClickListenerShareButton() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TournamentGame.getInstance().getFacebookService().shareApp();
            }
        };
    }

    @Override
    public MyButton build() {
        setButtonSkin(ButtonSkin.BLUE);
        prepareInternetButtonWithIcon(text, Resource.icon_facebook, refreshScreenClickListener);
        return super.build();
    }
}
