package libgdx.ui.controls.button;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.resources.Dimen;

public enum ButtonSize implements libgdx.controls.button.ButtonSize {

    THIN_SIZE(Dimen.width_btn_menu_normal.getDimen(), Dimen.height_btn_menu_normal.getDimen() / 1.3f),
    NORMAL_SIZE(Dimen.width_btn_menu_normal.getDimen(), Dimen.height_btn_menu_normal.getDimen()),
    SMALL_SIZE(Dimen.width_btn_menu_normal.getDimen() / 1.65f, Dimen.height_btn_menu_normal.getDimen() / 1.1f),
    START_GAME_OPTION_SIZE(Dimen.width_btn_menu_big.getDimen(), Dimen.height_btn_menu_big.getDimen() * 2.2f),
    BIG_IMAGE(MainDimen.side_btn_image.getDimen() * 1.15f, MainDimen.side_btn_image.getDimen() * 1.15f),
    BIG_MENU_ROUND_IMAGE(MainDimen.side_btn_image.getDimen() * 2.5f, MainDimen.side_btn_image.getDimen() * 2.5f),
    SMALL_MENU_ROUND_IMAGE(MainDimen.side_btn_image.getDimen() * 2.2f, MainDimen.side_btn_image.getDimen() * 2.2f),
    CAMPAIGN_LEVEL_ROUND_IMAGE(MainDimen.side_btn_image.getDimen() * 2f, MainDimen.side_btn_image.getDimen() * 2f),
    ACHIEVEMENT(Dimen.width_btn_achievement.getDimen(), Dimen.height_btn_achievement.getDimen()),
    SQUARE_QUIZ_OPTION_ANSWER(Dimen.width_btn_menu_normal.getDimen() / 1.1f, Dimen.height_btn_menu_big.getDimen() * 1.5f),
    LONG_QUIZ_OPTION_ANSWER(Dimen.width_btn_screen_size.getDimen(), Dimen.height_btn_menu_big.getDimen() / 1.1f),
    LONG_QUIZ_OPTION_ANSWER_THIN(Dimen.width_btn_screen_size.getDimen(), Dimen.height_btn_menu_big.getDimen() / 1.5f),
    HANGMAN_BUTTON(Dimen.width_hangman_button.getDimen(), Dimen.height_hangman_button.getDimen()),
    HANGMAN_SMALL_BUTTON(Dimen.width_hangman_button.getDimen() / 1.33f, Dimen.height_hangman_button.getDimen()),
    IMAGE_CLICK_ANSWER_OPTION(MainDimen.side_btn_image.getDimen() / 1.1f, MainDimen.side_btn_image.getDimen() / 1.1f),

    ONE_ROW_BUTTON_SIZE(Dimen.width_btn_menu_big.getDimen(), Dimen.height_btn_menu_normal.getDimen()),
    TWO_ROW_BUTTON_SIZE(Dimen.width_btn_menu_big.getDimen(), Dimen.height_btn_menu_normal.getDimen() * 1.5f),
    THREE_ROW_BUTTON_SIZE(Dimen.width_btn_menu_big.getDimen(), Dimen.height_btn_menu_normal.getDimen() * 2),;

    private float width;
    private float height;

    ButtonSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }
}
