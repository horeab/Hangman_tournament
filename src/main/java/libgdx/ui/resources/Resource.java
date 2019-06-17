package libgdx.ui.resources;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.I18NBundle;

import libgdx.resources.Res;
import libgdx.ui.game.TournamentGame;

public enum Resource implements Res {

    // @formatter:off
    //BUTTONS
    btn_blue_down("buttons/btn_blue_down.png", Texture.class),
    btn_blue_up("buttons/btn_blue_up.png", Texture.class),
    btn_red_down("buttons/btn_red_down.png", Texture.class),
    btn_red_up("buttons/btn_red_up.png", Texture.class),
    btn_letter_disabled("buttons/letter/btn_letter_disabled.png", Texture.class),
    btn_letter_down("buttons/letter/btn_letter_down.png", Texture.class),
    btn_letter_up("buttons/letter/btn_letter_up.png", Texture.class),
    btn_letter_correct("buttons/letter/btn_letter_correct.png", Texture.class),
    btn_letter_wrong("buttons/letter/btn_letter_wrong.png", Texture.class),
    btn_green_down("buttons/btn_green_down.png", Texture.class),
    btn_green_up("buttons/btn_green_up.png", Texture.class),
    btn_opt1_up("buttons/gameoptions/btn_opt1_up.png", Texture.class),
    btn_opt1_down("buttons/gameoptions/btn_opt1_down.png", Texture.class),
    btn_opt2_up("buttons/gameoptions/btn_opt2_up.png", Texture.class),
    btn_opt2_down("buttons/gameoptions/btn_opt2_down.png", Texture.class),
    btn_opt3_up("buttons/gameoptions/btn_opt3_up.png", Texture.class),
    btn_opt3_down("buttons/gameoptions/btn_opt3_down.png", Texture.class),
    btn_achievement_item("buttons/btn_achievement_item.png", Texture.class),
    btn_answer_opt_correct("buttons/answeroption/btn_answer_opt_correct.png", Texture.class),
    btn_answer_opt_wrong("buttons/answeroption/btn_answer_opt_wrong.png", Texture.class),
    btn_answer_opt_up("buttons/answeroption/btn_answer_opt_up.png", Texture.class),
    btn_answer_opt_down("buttons/answeroption/btn_answer_opt_down.png", Texture.class),
    btn_answer_opt_disabled("buttons/answeroption/btn_answer_opt_disabled.png", Texture.class),
    btn_long_answer_opt_correct("buttons/answeroption/btn_long_answer_opt_correct.png", Texture.class),
    btn_long_answer_opt_notselected("buttons/answeroption/btn_long_answer_opt_notselected.png", Texture.class),
    btn_long_answer_opt_selected("buttons/answeroption/btn_long_answer_opt_selected.png", Texture.class),
    btn_long_answer_opt_wrong("buttons/answeroption/btn_long_answer_opt_wrong.png", Texture.class),
    btn_long_answer_opt_up("buttons/answeroption/btn_long_answer_opt_up.png", Texture.class),
    btn_long_answer_opt_down("buttons/answeroption/btn_long_answer_opt_down.png", Texture.class),
    btn_long_answer_opt_disabled("buttons/answeroption/btn_long_answer_opt_disabled.png", Texture.class),
    btn_question_correct("buttons/answeroption/btn_question_correct.png", Texture.class),
    btn_question_wrong("buttons/answeroption/btn_question_wrong.png", Texture.class),
    btn_question_up("buttons/answeroption/btn_question_up.png", Texture.class),
    btn_question_down("buttons/answeroption/btn_question_down.png", Texture.class),
    btn_question_disabled("buttons/answeroption/btn_question_disabled.png", Texture.class),
    btn_campaign("buttons/mainmenu/btn_campaign.png", Texture.class),
    btn_tournament("buttons/mainmenu/btn_tournament.png", Texture.class),
    btn_practice("buttons/mainmenu/btn_practice.png", Texture.class),
    btn_challenge("buttons/mainmenu/btn_challenge.png", Texture.class),
    //LOGIN
    btn_login_up("buttons/connect/btn_login_up.png", Texture.class),
    btn_login_down("buttons/connect/btn_login_down.png", Texture.class),
    btn_login_disabled("buttons/connect/btn_login_disabled.png", Texture.class),
    btn_logout_up("buttons/connect/btn_logout_up.png", Texture.class),
    btn_logout_down("buttons/connect/btn_logout_down.png", Texture.class),
    ////////////////////////////////////////////////////////////////////////
    btn_pro_up("buttons/imagebuttons/btn_pro_up.png", Texture.class),
    btn_pro_down("buttons/imagebuttons/btn_pro_down.png", Texture.class),
    btn_leaderboard_up("buttons/imagebuttons/btn_leaderboard_up.png", Texture.class),
    btn_leaderboard_down("buttons/imagebuttons/btn_leaderboard_down.png", Texture.class),
    btn_hint("buttons/imagebuttons/btn_hint.png", Texture.class),
    btn_hint_disabled("buttons/imagebuttons/btn_hint_disabled.png", Texture.class),
    btn_background_circle_up("buttons/background_circle/btn_background_circle_up.png", Texture.class),
    btn_background_circle_down("buttons/background_circle/btn_background_circle_down.png", Texture.class),
    //QUESTIONS////////////////////////////////////////////////////////////////////////////////////////////
    labels_da("labels/labels_da", I18NBundle.class),
    labels_de("labels/labels_de", I18NBundle.class),
    labels_en("labels/labels_en", I18NBundle.class),
    labels_es("labels/labels_es", I18NBundle.class),
    labels_fr("labels/labels_fr", I18NBundle.class),
    labels_it("labels/labels_it", I18NBundle.class),
    labels_hu("labels/labels_hu", I18NBundle.class),
    labels_nl("labels/labels_nl", I18NBundle.class),
    labels_pl("labels/labels_pl", I18NBundle.class),
    labels_pt("labels/labels_pt", I18NBundle.class),
    labels_ro("labels/labels_ro", I18NBundle.class),
    labels_tr("labels/labels_tr", I18NBundle.class),
    //GENERAL////////////////////////////////////////////////////////////////////////////////////////////
    unknown_user("general/unknown_user.png", Texture.class),
    white_color_texture("backgrounds/white_color_texture.png", Texture.class),
    pay_transaction("backgrounds/transactions/pay_transaction.png", Texture.class),
    win_transaction("backgrounds/transactions/win_transaction.png", Texture.class),
    icon_facebook("general/icon_facebook.png", Texture.class),
    icon_google("general/icon_google.png", Texture.class),
    icon_login("general/icon_login.png", Texture.class),
    main_menu_screen_background("backgrounds/main_menu_screen_background.png", Texture.class),
    more_transparent_background("backgrounds/more_transparent_background.png", Texture.class),
    stars_table_background("backgrounds/stars_table_background.png", Texture.class),
    title_clouds_background("general/title_clouds_background.png", Texture.class),
    title_rays("general/title_rays.png", Texture.class),
    coins("general/coins.png", Texture.class),
    video("general/video.png", Texture.class),
    level_container("general/level_container.png", Texture.class),
    star_disabled("general/star_disabled.png", Texture.class),
    star_enabled("general/star_enabled.png", Texture.class),
    finish_disabled("general/finish_disabled.png", Texture.class),
    finish_enabled("general/finish_enabled.png", Texture.class),
    sound_on("general/sound_on.png", Texture.class),
    sound_off("general/sound_off.png", Texture.class),
    achievement("general/achievement.png", Texture.class),
    heart_full("general/heart_full.png", Texture.class),
    //DIAMOND//////////////////////////////////////////////////////////////////////////////////////////
    diamond("general/diamonds/diamond.png", Texture.class),
    diamond_sparkle("general/diamonds/diamond_sparkle.png", Texture.class),
    //SOUNDS////////////////////////////////////////////////////////////////////////////////////////////
    sound_fail_game_over("sounds/sound_fail_game_over.mp3", Sound.class),
    sound_success_game_over("sounds/sound_success_game_over.mp3", Sound.class),
    sound_star("sounds/sound_star.wav", Sound.class),
    sound_correct_answer("sounds/sound_correct_answer.mp3", Sound.class),
    sound_wrong_answer("sounds/sound_wrong_answer.wav", Sound.class),
    sound_image_bang("sounds/sound_image_bang.wav", Sound.class),
    //ACHIEVEMENTS////////////////////////////////////////////////////////////////////////////////////////////
    achievement_challenge("general/achievements/achievement_challenge.png", Texture.class),
    achievement_create_user("general/achievements/achievement_create_user.png", Texture.class),
    achievement_practice("general/achievements/achievement_practice.png", Texture.class),
    achievement_questions("general/achievements/achievement_questions.png", Texture.class),
    achievement_rate_game("general/achievements/achievement_rate_game.png", Texture.class),
    achievement_tournament("general/achievements/achievement_tournament.png", Texture.class),
    //TOURNAMENT///////////////////////////////////////////////////////////////////////////////////////
    trophy("tournament/trophy.png", Texture.class),
    trophy_big("tournament/trophy_big.png", Texture.class),
    trophy_rays("tournament/trophy_rays.png", Texture.class),
    //GAME BACKGROUND///////////////////////////////////////////////////////////////////////////////////////
    levelup_background("backgrounds/levelup/levelup_background.png", Texture.class),
    levelup_fill_red("backgrounds/levelup/levelup_fill_red.png", Texture.class),
    levelup_fill_green("backgrounds/levelup/levelup_fill_green.png", Texture.class),
    levelup_fill_yellow("backgrounds/levelup/levelup_fill_yellow.png", Texture.class),
    game_user_header_background_default("backgrounds/ingame/game_user_header_background_default.png", Texture.class),
    game_user_header_background_red("backgrounds/ingame/game_user_header_background_red.png", Texture.class),
    game_user_header_background_green("backgrounds/ingame/game_user_header_background_green.png", Texture.class),
    game_user_header_background_multiple_answers("backgrounds/ingame/game_user_header_background_multiple_answers.png", Texture.class),
    game_user_header_background_multiple_answers_wrong("backgrounds/ingame/game_user_header_background_multiple_answers_wrong.png", Texture.class),
    userstatusbar_background_default("backgrounds/userstatusbar/userstatusbar_background_default.png", Texture.class),
    userstatusbar_experience_empty("backgrounds/userstatusbar/userstatusbar_experience_empty.png", Texture.class),
    userstatusbar_experience_full("backgrounds/userstatusbar/userstatusbar_experience_full.png", Texture.class),
    //OTHER BACKGROUND///////////////////////////////////////////////////////////////////////////////////////
    levelfinished_fail_background("backgrounds/levelfinished/levelfinished_fail_background.png", Texture.class),
    levelfinished_success_background("backgrounds/levelfinished/levelfinished_success_background.png", Texture.class),
    levelfinished_tournament_win_user_success_background("backgrounds/levelfinished/tournament_win_user_success_background.png", Texture.class),
    levelfinished_user_fail_background("backgrounds/levelfinished/user_fail_background.png", Texture.class),
    levelfinished_user_success_background("backgrounds/levelfinished/user_success_background.png", Texture.class),
    leaderboard_currentuser_background("backgrounds/leaderboard/currentuser_background.png", Texture.class),
    leaderboard_otheruser_background("backgrounds/leaderboard/otheruser_background.png", Texture.class),
    //GUEST PROFILE PICTURE///////////////////////////////////////////////////////////////////////////////////////
    guest0("guestuser/profilepictures/guest0.png", Texture.class),
    guest1("guestuser/profilepictures/guest1.png", Texture.class),
    guest2("guestuser/profilepictures/guest2.png", Texture.class),
    guest3("guestuser/profilepictures/guest3.png", Texture.class),
    guest4("guestuser/profilepictures/guest4.png", Texture.class),
    guest5("guestuser/profilepictures/guest5.png", Texture.class),
    guest6("guestuser/profilepictures/guest6.png", Texture.class),
    guest7("guestuser/profilepictures/guest7.png", Texture.class),
    guest8("guestuser/profilepictures/guest8.png", Texture.class),
    guest9("guestuser/profilepictures/guest9.png", Texture.class),
    //CAMPAIGN///////////////////////////////////////////////////////////////////////////////////////
    lock_icon("campaign/lock_icon.png", Texture.class),
    bomb("campaign/bomb.png", Texture.class),
    fire("campaign/fire.png", Texture.class),
    campaign_wall_explosion("campaign/campaign_wall_explosion.png", Texture.class),
    table_left_right_dotted_line("campaign/connectinglines/table_left_right_dotted_line.png", Texture.class),
    table_right_left_dotted_line("campaign/connectinglines/table_right_left_dotted_line.png", Texture.class),
    table_down_center_left_dotted_line("campaign/connectinglines/table_down_center_left_dotted_line.png", Texture.class),
    table_up_center_left_dotted_line("campaign/connectinglines/table_up_center_left_dotted_line.png", Texture.class),
    table_center_right_dotted_line("campaign/connectinglines/table_center_right_dotted_line.png", Texture.class),
    btn_campaign_disabled("campaign/buttons/btn_campaign_disabled.png", Texture.class),
    btn_campaign_wall_up("campaign/buttons/btn_campaign_wall_up.png", Texture.class),
    btn_campaign_wall_down("campaign/buttons/btn_campaign_wall_down.png", Texture.class),
    btn_campaign_0_down("campaign/buttons/btn_0_down.png", Texture.class),
    btn_campaign_0_up("campaign/buttons/btn_0_up.png", Texture.class),
    btn_campaign_1_down("campaign/buttons/btn_1_down.png", Texture.class),
    btn_campaign_1_up("campaign/buttons/btn_1_up.png", Texture.class),
    btn_campaign_2_down("campaign/buttons/btn_2_down.png", Texture.class),
    btn_campaign_2_up("campaign/buttons/btn_2_up.png", Texture.class),
    btn_campaign_3_down("campaign/buttons/btn_3_down.png", Texture.class),
    btn_campaign_3_up("campaign/buttons/btn_3_up.png", Texture.class),
    btn_campaign_4_down("campaign/buttons/btn_4_down.png", Texture.class),
    btn_campaign_4_up("campaign/buttons/btn_4_up.png", Texture.class),
    btn_campaign_5_down("campaign/buttons/btn_5_down.png", Texture.class),
    btn_campaign_5_up("campaign/buttons/btn_5_up.png", Texture.class),
    ///////////////////////////////////
    campaign_level_0_background("campaign/background/level_0_background.png", Texture.class),
    campaign_level_1_background("campaign/background/level_1_background.png", Texture.class),
    campaign_level_2_background("campaign/background/level_2_background.png", Texture.class),
    campaign_level_3_background("campaign/background/level_3_background.png", Texture.class),
    campaign_level_4_background("campaign/background/level_4_background.png", Texture.class),
    campaign_level_5_background("campaign/background/level_5_background.png", Texture.class),;

    // @formatter:on

    private String path;
    private Class<?> classType;

    Resource(String path, Class<?> classType) {
        this.path = path;
        this.classType = classType;
    }

    @Override
    public String getPath() {
        return TournamentGame.getInstance().getAppInfoService().getResourcesFolder() + path;
    }

    public String getRawPath() {
        return path;
    }

    @Override
    public Class<?> getClassType() {
        return classType;
    }


}
