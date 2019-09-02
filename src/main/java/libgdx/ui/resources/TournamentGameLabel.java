package libgdx.ui.resources;

import libgdx.game.Game;
import libgdx.resources.gamelabel.GameLabelUtils;

public enum TournamentGameLabel implements libgdx.resources.gamelabel.GameLabel {

    accept,
    decline,
    no_results,

    update_btn,
    update_info,

    resources_entry_fee,
    resources_prize,
    resources_not_enough,
    resources_buy,
    resources_current_amount,
    resources_free,
    resources_free_offer,
    resources_free_offer_video,

    tournament_go_label,
    tournament_currentuser_anon_name,
    tournament_bot_anon_name,

    mainmenu_button_tournament,
    mainmenu_button_challenge,
    mainmenu_button_leaderboard,
    mainmenu_button_practice,
    mainmenu_button_campaign,

    login_tournament_popup_info_needlogin,
    login_practice_popup_info_needlogin,
    login_challenge_popup_info_needlogin,
    login_popup_create_guest,
    login_login_btn_text,
    login_logout_btn_text,
    login_logout_guest_info,
    login_stay_connected,

    level_finished_tournamnet_success_msg1,
    level_finished_tournamnet_success_msg2,
    level_finished_fail_msg1,
    level_finished_fail_msg2,
    level_finished_success_msg1,
    level_finished_success_msg2,
    level_finished_success_opponent_abandon_game,
    level_finished_continue,
    level_finished_play_again,

    exit_challenge_info_msg,
    exit_tournament_info_msg,
    exit_game_exit_btn,
    exit_game_continue_btn,

    tournament_1,
    tournament_2,
    tournament_3,
    practice_1,
    practice_2,
    practice_3,
    challenge_1,
    challenge_2,
    challenge_3,

    live_game_other_player_not_join,
    live_game_other_player_abandon_game,
    live_game_resend_challenge,
    live_game_challenge,
    live_game_search_friends,
    live_game_waiting_player,
    live_game_friend_challenge,
    live_game_want_rematch,
    live_game_reject_rematch,

    achievement_label,
    achievement_level_up,
    achievement_diamond_win,

    achievement_create_user,
    achievement_facebook_share,
    achievement_rate_game,
    achievement_diamond,
    achievement_tournament_win,
    achievement_tournament_pay,
    achievement_practice_win,
    achievement_practice_pay,
    achievement_challenge_win,
    achievement_challenge_pay,
    achievement_questions_win,
    achievement_questions_pay,;

    @Override
    public String getText(Object... params) {
        String language = Game.getInstance().getAppInfoService().getLanguage();
        return GameLabelUtils.getText(getKey(), language, GameLabelUtils.getLabelRes(language).getPath(), params);
    }

    @Override
    public String getKey() {
        return name();
    }

    public static TournamentGameLabel getByName(String key) {
        for (TournamentGameLabel gameLabel : values()) {
            if (gameLabel.getKey().equals(key)) {
                return gameLabel;
            }
        }
        return null;
    }
}
