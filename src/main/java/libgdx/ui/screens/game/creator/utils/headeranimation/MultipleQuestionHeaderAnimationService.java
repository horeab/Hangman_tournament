package libgdx.ui.screens.game.creator.utils.headeranimation;

import com.badlogic.gdx.Gdx;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;

public class MultipleQuestionHeaderAnimationService extends HeaderAnimationService {

    private GameUser gameUser;

    MultipleQuestionHeaderAnimationService(GameUser gameUser, PlayerHeader playerHeader) {
        super(playerHeader);
        this.gameUser = gameUser;
    }

    @Override
    public void gameInfoChanged() {
        new Thread(new ScreenRunnable(TournamentGame.getInstance().getAbstractScreen()) {
            @Override
            public void executeOperations() {
                final float percentageOfWonQuestions = LevelFinishedService.getPercentageOfWonQuestions(gameUser);
                final float percentageOfFinishedQuestions = LevelFinishedService.getPercentageOfFinishedQuestions(gameUser);
                executeAnimation(percentageOfWonQuestions);
                Gdx.app.postRunnable(new ScreenRunnable(TournamentGame.getInstance().getAbstractScreen()) {
                    @Override
                    public void executeOperations() {
                        increaseFill(percentageOfFinishedQuestions);
                    }
                });
            }
        }).start();

    }

    private void increaseFill(float percentageOfFinishedQuestions) {
        playerHeader.increaseFillAllAnswersTable(percentageOfFinishedQuestions);
    }
}
