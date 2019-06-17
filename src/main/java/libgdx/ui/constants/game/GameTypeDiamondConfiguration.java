package libgdx.ui.constants.game;

public enum GameTypeDiamondConfiguration {

    TOURNAMENT(DiamondEnum.DIAMOND, new Integer[]{2, 3}),
    CHALLENGE(DiamondEnum.DIAMOND, new Integer[]{2, 3}),;

    private DiamondEnum diamondReward;
    private Integer[] stepsForDiamondReward;

    GameTypeDiamondConfiguration(DiamondEnum diamondReward, Integer[] stepsForDiamondReward) {
        this.diamondReward = diamondReward;
        this.stepsForDiamondReward = stepsForDiamondReward;
    }

    public DiamondEnum getDiamondReward() {
        return diamondReward;
    }

    public Integer[] getStepsForDiamondReward() {
        return stepsForDiamondReward;
    }
}
