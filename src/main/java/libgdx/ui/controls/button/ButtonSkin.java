package libgdx.ui.controls.button;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import libgdx.resources.MainResource;
import libgdx.resources.Res;
import libgdx.ui.resources.Resource;
import libgdx.graphics.GraphicUtils;

public enum ButtonSkin implements libgdx.controls.button.ButtonSkin{

    NORMAL_ORANGE(MainResource.btn_menu_up, MainResource.btn_menu_down, MainResource.btn_menu_up, MainResource.btn_lowcolor_up, null),
    BLUE(Resource.btn_blue_up, Resource.btn_blue_down, Resource.btn_blue_up, MainResource.btn_lowcolor_up, null),
    RED(Resource.btn_red_up, Resource.btn_red_down, Resource.btn_red_up, MainResource.btn_lowcolor_up, null),
    GREEN(Resource.btn_green_up, Resource.btn_green_down, Resource.btn_green_up, MainResource.btn_lowcolor_up, null),
    LOW_COLOR(MainResource.btn_lowcolor_up, MainResource.btn_lowcolor_down, MainResource.btn_lowcolor_up, MainResource.btn_lowcolor_up, null),
    SQUARE(Resource.btn_letter_up, Resource.btn_letter_down, Resource.btn_letter_up, Resource.btn_letter_disabled, null),
    SQUARE_CORRECT(Resource.btn_letter_correct, Resource.btn_letter_correct, Resource.btn_letter_correct, Resource.btn_letter_correct, Color.FOREST),
    SQUARE_WRONG(Resource.btn_letter_wrong, Resource.btn_letter_wrong, Resource.btn_letter_wrong, Resource.btn_letter_wrong, Color.RED),
    GAME_OPT1(Resource.btn_opt1_up, Resource.btn_opt1_down, Resource.btn_opt1_up, Resource.btn_opt1_up, null),
    GAME_OPT2(Resource.btn_opt2_up, Resource.btn_opt2_down, Resource.btn_opt2_up, Resource.btn_opt2_up, null),
    GAME_OPT3(Resource.btn_opt3_up, Resource.btn_opt3_down, Resource.btn_opt3_up, Resource.btn_opt3_up, null),
    HINT(Resource.btn_hint, Resource.btn_hint, Resource.btn_hint, Resource.btn_hint_disabled, null),
    COINS(Resource.coins, Resource.coins, Resource.coins, Resource.coins, null),
    BACKGROUND_CIRCLE(Resource.btn_background_circle_up, Resource.btn_background_circle_down, Resource.btn_background_circle_up, Resource.btn_background_circle_up, null),
    LEADERBOARD(Resource.btn_leaderboard_up, Resource.btn_leaderboard_down, Resource.btn_leaderboard_up, Resource.btn_leaderboard_up, null),
    PRO(Resource.btn_pro_up, Resource.btn_pro_down, Resource.btn_pro_up, Resource.btn_pro_up, null),
    SETTINGS_MENU(MainResource.btn_settings_up, MainResource.btn_settings_down, MainResource.btn_settings_up, MainResource.btn_settings_up, null),
    ACHIEVEMENT_ITEM(Resource.btn_achievement_item, Resource.btn_achievement_item, Resource.btn_achievement_item, Resource.btn_achievement_item, null),
    SQUARE_ANSWER_OPTION(Resource.btn_answer_opt_up, Resource.btn_answer_opt_down, Resource.btn_answer_opt_up, Resource.btn_answer_opt_disabled, null),
    LONG_ANSWER_OPTION(Resource.btn_long_answer_opt_up, Resource.btn_long_answer_opt_down, Resource.btn_long_answer_opt_up, Resource.btn_long_answer_opt_disabled, null),
    LONG_ANSWER_SUBMIT_QUESTIONS_OPTION(Resource.btn_long_answer_opt_notselected, Resource.btn_long_answer_opt_notselected, Resource.btn_long_answer_opt_notselected, Resource.btn_long_answer_opt_notselected, null),
    LONG_ANSWER_SUBMIT_QUESTIONS_OPTION_SELECTED(Resource.btn_long_answer_opt_selected, Resource.btn_long_answer_opt_selected, Resource.btn_long_answer_opt_selected, Resource.btn_long_answer_opt_selected, null),
    SQUARE_ANSWER_OPTION_CORRECT(Resource.btn_answer_opt_correct, Resource.btn_answer_opt_correct, Resource.btn_answer_opt_correct, Resource.btn_answer_opt_correct, Color.FOREST),
    SQUARE_ANSWER_OPTION_WRONG(Resource.btn_answer_opt_wrong, Resource.btn_answer_opt_wrong, Resource.btn_answer_opt_wrong, Resource.btn_answer_opt_wrong, Color.RED),
    LONG_ANSWER_OPTION_CORRECT(Resource.btn_long_answer_opt_correct, Resource.btn_long_answer_opt_correct, Resource.btn_long_answer_opt_correct, Resource.btn_long_answer_opt_correct, Color.FOREST),
    LONG_ANSWER_OPTION_WRONG(Resource.btn_long_answer_opt_wrong, Resource.btn_long_answer_opt_wrong, Resource.btn_long_answer_opt_wrong, Resource.btn_long_answer_opt_wrong, Color.RED),
    ANSWER_IMAGE_CLICK(Resource.btn_question_up, Resource.btn_question_down, Resource.btn_question_up, Resource.btn_question_disabled, null),
    ANSWER_IMAGE_CLICK_CORRECT(Resource.btn_question_correct, Resource.btn_question_correct, Resource.btn_question_correct, Resource.btn_question_correct, Color.FOREST),
    ANSWER_IMAGE_CLICK_WRONG(Resource.btn_question_wrong, Resource.btn_question_wrong, Resource.btn_question_wrong, Resource.btn_question_wrong, Color.RED),
    CAMPAIGN_LEVEL_0(Resource.btn_campaign_0_up, Resource.btn_campaign_0_down, Resource.btn_campaign_0_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_1(Resource.btn_campaign_1_up, Resource.btn_campaign_1_down, Resource.btn_campaign_1_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_2(Resource.btn_campaign_2_up, Resource.btn_campaign_2_down, Resource.btn_campaign_2_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_3(Resource.btn_campaign_3_up, Resource.btn_campaign_3_down, Resource.btn_campaign_3_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_4(Resource.btn_campaign_4_up, Resource.btn_campaign_4_down, Resource.btn_campaign_4_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_5(Resource.btn_campaign_5_up, Resource.btn_campaign_5_down, Resource.btn_campaign_5_up, Resource.btn_campaign_disabled, null),
    CAMPAIGN_LEVEL_WALL(Resource.btn_campaign_wall_up, Resource.btn_campaign_wall_down, Resource.btn_campaign_wall_up, Resource.btn_campaign_disabled, null),;

    ButtonSkin(Res imgUp, Res imgDown, Res imgChecked, Res imgDisabled, Color buttonDisabledFontColor) {
        this.imgUp = imgUp;
        this.imgDown = imgDown;
        this.imgChecked = imgChecked;
        this.imgDisabled = imgDisabled;
        this.buttonDisabledFontColor = buttonDisabledFontColor;
    }

    private Res imgUp;
    private Res imgDown;
    private Res imgChecked;
    private Res imgDisabled;
    private Color buttonDisabledFontColor;

    @Override
    public Drawable getImgUp() {
        return GraphicUtils.getImage(imgUp).getDrawable();
    }

    @Override
    public Drawable getImgDown() {
        return GraphicUtils.getImage(imgDown).getDrawable();
    }

    @Override
    public Drawable getImgChecked() {
        return GraphicUtils.getImage(imgChecked).getDrawable();
    }

    @Override
    public Drawable getImgDisabled() {
        return GraphicUtils.getImage(imgDisabled).getDrawable();
    }

    @Override
    public Color getButtonDisabledFontColor() {
        return buttonDisabledFontColor;
    }
}
