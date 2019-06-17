package libgdx.ui.controls.button.builders;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.controls.button.ButtonBuilder;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.model.transactions.TournamentTransactionAmount;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;

public class LoginButtonBuilder extends ButtonBuilder {

    private Runnable redirectAfterLogin;

    public LoginButtonBuilder(Runnable redirectAfterLogin) {
        this.redirectAfterLogin = redirectAfterLogin;
    }

    public LoginButtonBuilder setFacebookConnectButton() {
        setButtonSkin(ButtonSkin.BLUE);
        prepareInternetButtonWithIconBuilder(Resource.icon_facebook, createLoginClickListener(AccountCreationSource.FACEBOOK), TournamentGameLabel.login_login_btn_text.getText());
        setLoggedOutRewardBottomRow(new TournamentTransactionAmount(TransactionAmountEnum.WIN_CONNECT_FACEBOOK));
        return this;
    }

    public LoginButtonBuilder setGoogleConnectButton() {
        setButtonSkin(ButtonSkin.RED);
        prepareInternetButtonWithIconBuilder(Resource.icon_google, createLoginClickListener(AccountCreationSource.GOOGLE), TournamentGameLabel.login_login_btn_text.getText());
        return this;
    }

    public LoginButtonBuilder setGuestConnectButton() {
        setButtonSkin(ButtonSkin.GREEN);
        prepareInternetButtonWithIconBuilder(Resource.icon_login, createLoginClickListener(AccountCreationSource.INTERNAL), TournamentGameLabel.login_popup_create_guest.getText());
        return this;
    }

    public LoginButtonBuilder setDisconnectButton(AccountCreationSource accountCreationSource) {
        setButtonSkin(ButtonSkin.LOW_COLOR);
        prepareInternetButtonWithIconBuilder(Resource.btn_logout_up, createLoginClickListener(accountCreationSource), TournamentGameLabel.login_logout_btn_text.getText());
        return this;
    }

    private ButtonBuilder prepareInternetButtonWithIconBuilder(Resource icon, ClickListener clickListener, String text) {
        return new InternetButtonWithIconBuilder().prepareInternetButtonWithIcon(this, text, icon, clickListener);
    }

    private ClickListener createLoginClickListener(final AccountCreationSource accountCreationSource) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TournamentGame.getInstance().getLoginService().loginClick(accountCreationSource, redirectAfterLogin);
            }
        };
    }

}
