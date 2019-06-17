package libgdx.ui.constants.game.achievements;

import libgdx.resources.Res;
import libgdx.ui.resources.Resource;

public enum AchievementType {

    CREATE_USER(Resource.achievement_create_user),
    FACEBOOK_SHARE(Resource.icon_facebook),
    RATE_GAME(Resource.achievement_rate_game),
    DIAMOND(Resource.diamond),
    TOURNAMENT(Resource.achievement_tournament),
    PRACTICE(Resource.achievement_practice),
    CHALLENGE(Resource.achievement_challenge),
    QUESTIONS(Resource.achievement_questions),;

    Res resource;

    AchievementType(Res resource) {
        this.resource = resource;
    }

    public Res getResource() {
        return resource;
    }
}
