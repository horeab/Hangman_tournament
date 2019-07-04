package libgdx.startgame;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.util.Language;
import libgdx.ui.util.TestDataCreator;
import libgdx.utils.startgame.test.DefaultBillingService;
import libgdx.utils.startgame.test.DefaultFacebookService;

public class StartGame {

    public static void main(String[] args) {
        TournamentGame game = new TournamentGame(
                new DefaultFacebookService(),
                new DefaultBillingService(),
                TestDataCreator.createAppInfoService(Language.de)){
            @Override
            public void create() {
                super.create();
                loginService = TestDataCreator.createLoginService();
            }
        };
        libgdx.utils.startgame.StartGame.main(game, args);
    }
}
