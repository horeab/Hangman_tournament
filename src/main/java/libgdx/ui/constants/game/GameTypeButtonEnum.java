package libgdx.ui.constants.game;

import libgdx.ui.controls.button.ButtonSkin;

public enum GameTypeButtonEnum {

    TOURNAMENT_1(GameTypeStage.tournament_1, ButtonSkin.GAME_OPT3),
    TOURNAMENT_2(GameTypeStage.tournament_2, ButtonSkin.GAME_OPT3),
    TOURNAMENT_3(GameTypeStage.tournament_3, ButtonSkin.GAME_OPT3),

    PRACTICE_1(GameTypeStage.practice_1, ButtonSkin.GAME_OPT2),
    PRACTICE_2(GameTypeStage.practice_2, ButtonSkin.GAME_OPT2),
    PRACTICE_3(GameTypeStage.practice_3, ButtonSkin.GAME_OPT2),

    CHALLENGE_1(GameTypeStage.challenge_1, ButtonSkin.GAME_OPT1),
    CHALLENGE_2(GameTypeStage.challenge_2, ButtonSkin.GAME_OPT1),
    CHALLENGE_3(GameTypeStage.challenge_3, ButtonSkin.GAME_OPT1),;

    private GameTypeStage gameTypeStage;
    private ButtonSkin buttonSkin;

    GameTypeButtonEnum(GameTypeStage gameTypeStage, ButtonSkin buttonSkin) {
        this.gameTypeStage = gameTypeStage;
        this.buttonSkin = buttonSkin;
    }

    public GameTypeStage getGameTypeStage() {
        return gameTypeStage;
    }

    public ButtonSkin getButtonSkin() {
        return buttonSkin;
    }

    public static GameTypeButtonEnum getGameTypeButtonEnum(GameTypeStage gameTypeStage) {
        for (GameTypeButtonEnum buttonEnum : values()) {
            if (buttonEnum.gameTypeStage == gameTypeStage) {
                return buttonEnum;
            }
        }
        return null;
    }
}
