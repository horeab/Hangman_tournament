package libgdx.ui.screens.game.userinfoheader;

import java.util.HashMap;
import java.util.Map;

import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.campaign.CampaignScreen;
import libgdx.ui.screens.game.ChallengeGameScreen;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.screens.CampaignGameScreen;

public class ScreenPlayerHeaderContainer {

    private static Map<Long, PlayerHeaderContainer> playerHeaderContainerWithId = new HashMap<>();

    public static PlayerHeaderContainer getPlayerHeaderContainer(AbstractGameScreen abstractGameScreen) {
        long millisPassedSinceScreenDisplayed = abstractGameScreen.getMillisScreenFirstTimeDisplayed();
        if (playerHeaderContainerWithId.get(millisPassedSinceScreenDisplayed) == null) {
            playerHeaderContainerWithId.clear();
            playerHeaderContainerWithId.put(millisPassedSinceScreenDisplayed, getContainer(abstractGameScreen));
        }
        return playerHeaderContainerWithId.get(millisPassedSinceScreenDisplayed);
    }

    private static PlayerHeaderContainer getContainer(AbstractGameScreen abstractGameScreen) {
        PlayerHeaderContainer container = new PlayerHeaderContainer();
        container.setWithScoreLabel(abstractGameScreen instanceof ChallengeGameScreen);
        container.setWithAnswerGoal(abstractGameScreen instanceof CampaignGameScreen);
        GameContext gameContext = abstractGameScreen.getGameContext();
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        container.addPlayer(currentUserGameUser.getBaseUserInfo());
        for (GameUser gameUser : gameContext.getOpponentGameUsers()) {
            container.addPlayer(gameUser.getBaseUserInfo());
        }
        container.create(currentUserGameUser.getTotalNrOfQuestions() );
        return container;
    }
}
