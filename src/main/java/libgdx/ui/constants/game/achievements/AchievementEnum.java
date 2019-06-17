package libgdx.ui.constants.game.achievements;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static libgdx.ui.constants.money.ShopTransactionTypeEnum.*;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.resources.TournamentGameLabel;

public enum AchievementEnum {

    CREATE_USER(AchievementType.CREATE_USER, TournamentGameLabel.achievement_create_user, Collections.singletonList(WIN_CREATE_USER), Constants.ONE_AMOUNT),
    FACEBOOK_SHARE(AchievementType.FACEBOOK_SHARE, TournamentGameLabel.achievement_facebook_share, Collections.singletonList(WIN_BONUS_FACEBOOK_SHARE), Constants.ONE_AMOUNT),
    WIN_CHALLENGE(AchievementType.CHALLENGE, TournamentGameLabel.achievement_challenge_win, Arrays.asList(WIN_CHALLENGE_OPT_1, WIN_CHALLENGE_OPT_2, WIN_CHALLENGE_OPT_3), Constants.GAME_TYPE_AMOUNT),
    DIAMOND(AchievementType.DIAMOND, TournamentGameLabel.achievement_diamond, Collections.singletonList(WIN_DIAMOND), Constants.DIAMONDS_AMOUNT),
    WIN_TOURNAMENT(AchievementType.TOURNAMENT, TournamentGameLabel.achievement_tournament_win, Arrays.asList(WIN_TOURNAMENT_OPT_1, WIN_TOURNAMENT_OPT_2, WIN_TOURNAMENT_OPT_2), Constants.GAME_TYPE_AMOUNT),
    QUESTIONS_WIN(AchievementType.QUESTIONS, TournamentGameLabel.achievement_questions_win, Collections.<ShopTransactionTypeEnum>emptyList(), Constants.QUESTIONS_AMOUNT),;

    private AchievementType achievementType;
    private TournamentGameLabel gameLabel;
    private List<ShopTransactionTypeEnum> shopTransactionTypeEnums;
    private List<Integer> levelUpAmount;

    AchievementEnum(AchievementType achievementType, TournamentGameLabel gameLabel, List<ShopTransactionTypeEnum> shopTransactionTypeEnums, List<Integer> levelUpAmount) {
        this.achievementType = achievementType;
        this.shopTransactionTypeEnums = shopTransactionTypeEnums;
        this.levelUpAmount = levelUpAmount;
        this.gameLabel = gameLabel;
    }

    public static AchievementEnum getAchievementEnum(ShopTransactionTypeEnum shopTransactionTypeEnum) {
        for (AchievementEnum achievementEnum : values()) {
            if (achievementEnum.getShopTransactionTypeEnums().contains(shopTransactionTypeEnum)) {
                return achievementEnum;
            }
        }
        return null;
    }

    public AchievementType getAchievementType() {
        return achievementType;
    }

    public ShopTransactionTypeEnum[] getShopTransactionTypeEnumsArray() {
        return getShopTransactionTypeEnums().toArray(new ShopTransactionTypeEnum[getShopTransactionTypeEnums().size()]);
    }

    public TournamentGameLabel getGameLabel() {
        return gameLabel;
    }

    public List<ShopTransactionTypeEnum> getShopTransactionTypeEnums() {
        return shopTransactionTypeEnums;
    }

    public Integer[] getLevelUpAmountArray() {
        return getLevelUpAmount().toArray(new Integer[getLevelUpAmount().size()]);
    }

    public List<Integer> getLevelUpAmount() {
        return levelUpAmount;
    }

    private static class Constants {
        private static List<Integer> ONE_AMOUNT = Collections.singletonList(1);
        private static List<Integer> GAME_TYPE_AMOUNT = Arrays.asList(3, 8, 15, 30, 60, 120, 200);
        private static List<Integer> DIAMONDS_AMOUNT = Arrays.asList(3, 8, 15, 30, 60);
        private static List<Integer> QUESTIONS_AMOUNT = Arrays.asList(20, 60, 120, 200, 500, 1000);
    }
}