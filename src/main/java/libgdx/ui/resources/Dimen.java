package libgdx.ui.resources;

import java.util.Arrays;

import libgdx.resources.dimen.DimenUtils;
import libgdx.utils.ScreenDimensionsManager;

public enum Dimen implements libgdx.resources.dimen.Dimen {

    //BUTTONS
    width_btn_menu_normal(50),
    width_btn_screen_size(95),
    height_btn_menu_normal(8),
    width_btn_menu_big(62),
    height_btn_menu_big(12),
    width_btn_achievement(80),
    height_btn_achievement(30),
    side_btn_login(5),
    //PROFILE PICTURE
    side_leaderboard_userinfo_profile_picture(8),
    side_userinfo_container_profile_picture(11),
    side_game_player_header_profile_picture(4.50f),
    side_game_player_header_profile_picture_small(2f),
    side_userstatus_profile_picture(5.50f),
    //TOURNAMENT
    width_tournament_trophy_img(25),
    width_tournament_trophy_rays_img(100),
    side_tournament_profile_picture(8),
    side_tournament_profile_picture_border(1.5f),
    side_tournament_connect_lines(0.5f),
    //hangman
    width_hangman_button(15.5f),
    height_hangman_button(7f),
    width_hangman_letter(9f),
    side_hangman_image(33),
    //USERINFO
    height_userinfo_row(5),
    height_userinfo_row_small(2),
    side_userinfo_container(26),
    //DIAMOND
    side_diamond_level_up(4.3f),
    ////////////////////////////////////
    ;

    private float dimen;

    Dimen(float dimen) {
        this.dimen = dimen;
    }

    @Override
    public float getDimen() {
        return DimenUtils.getDimen(this);
    }

    @Override
    public int getIntegerValueOfDimen() {
        return DimenUtils.getIntegerValueOfDimen(this);
    }

    @Override
    public float getRawDimen() {
        return dimen;
    }
}
