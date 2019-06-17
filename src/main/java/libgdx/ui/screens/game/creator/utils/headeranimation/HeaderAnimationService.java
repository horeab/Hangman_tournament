package libgdx.ui.screens.game.creator.utils.headeranimation;

import com.badlogic.gdx.Gdx;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.ui.model.game.GameUser;

public abstract class HeaderAnimationService {

    protected PlayerHeader playerHeader;

    HeaderAnimationService(PlayerHeader playerHeader) {
        this.playerHeader = playerHeader;
    }

    public abstract void gameInfoChanged();

    void executeAnimation(final Float percentToIncrease) {
        Gdx.app.postRunnable(new ScreenRunnable(TournamentGame.getInstance().getAbstractScreen()) {
            @Override
            public void executeOperations() {
                if (percentToIncrease != null) {
                    playerHeader.increaseFillCorrectAnswersTable(percentToIncrease);
                } else {
                    playerHeader.animatePulseWrongAnswer();
                }
            }
        });
    }

    public static HeaderAnimationService createInstance(GameUser gameUser, PlayerHeader playerHeader) {
        return gameUser.userHasMultipleQuestions() ? new MultipleQuestionHeaderAnimationService(gameUser, playerHeader) : new SingleQuestionHeaderAnimationService(gameUser.getGameQuestionInfo(), playerHeader);
    }

}
