package libgdx.ui.screens.game;

import libgdx.ui.model.game.GameAnswerInfo;

public interface ChallengeGameScreen {

    void updateCurrentUserPressedAnswerToDb(GameAnswerInfo lastAnswerPressed);

    void updateCurrentUserPressedAnswerFromDb(String jsonGameInfo, int gameInfoVersion);

    void opponentPressedAnswer(String jsonGameInfo, int gameInfoVersion);

    int getLiveGameId();

    void usersAreLoaded();
}
