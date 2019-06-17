package libgdx.ui.constants.game;

import libgdx.ui.game.TournamentGame;

public enum GameType {

    TOURNAMENT(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getTournamentAmountAvailableHints(), GameTypeDiamondConfiguration.TOURNAMENT),
    PRACTICE(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getPracticeAmountAvailableHints(), null),
    CHALLENGE(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getChallengeAmountAvailableHints(), GameTypeDiamondConfiguration.CHALLENGE),
    CAMPAIGN(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getCampaignAmountAvailableHints(), null),;

    private int amountAvailableHints;
    private GameTypeDiamondConfiguration gameTypeDiamondConfiguration;

    GameType(int amountAvailableHints, GameTypeDiamondConfiguration gameTypeDiamondConfiguration) {
        this.amountAvailableHints = amountAvailableHints;
        this.gameTypeDiamondConfiguration = gameTypeDiamondConfiguration;
    }

    public GameTypeDiamondConfiguration getGameTypeDiamondConfiguration() {
        return gameTypeDiamondConfiguration;
    }

    public int getAmountAvailableHints() {
        return amountAvailableHints;
    }
}
