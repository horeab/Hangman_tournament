package libgdx.ui.screens.game.userinfoheader;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Resource;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.SoundUtils;

class MultipleQuestionPlayerHeader extends PlayerHeader {

    private int gameTotalNrOfQuestions;
    private boolean firstTimeIncrement = true;

    MultipleQuestionPlayerHeader(BaseUserInfo player, int gameTotalNrOfQuestions) {
        super(player);
        this.gameTotalNrOfQuestions = gameTotalNrOfQuestions;
    }

    @Override
    public void animateGameOverHeaderWin() {
        super.animateGameOverHeaderWin();
    }

    @Override
    protected void executeAfterIncrease(float percentageToFill) {
        if (TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(PlayerHeaderBuilder.getStarImageName(0, getPlayer().getId())) != null) {
            processGoalsOnIncrementTouch(percentageToFill);
        }
    }

    private void processGoalsOnIncrementTouch(float percentageToFill) {
        int answeredQuestions = Math.round(gameTotalNrOfQuestions * (percentageToFill / 100));
        List<Integer> questionsToAddStar = TournamentGame.getInstance().getSubGameDependencyManager().getStarsService(gameTotalNrOfQuestions).getQuestionsToAddFinishIconAndStarIcon();
        for (int i = 0; i < questionsToAddStar.size(); i++) {
            if (!firstTimeIncrement && answeredQuestions == questionsToAddStar.get(i)) {
                SoundUtils.playSound(i == 0 ? Resource.sound_success_game_over : Resource.sound_star);
            }
            if (answeredQuestions >= questionsToAddStar.get(i)) {
                processGoalImage(PlayerHeaderBuilder.getStarImageName(i, getPlayer().getId()), i == 0 ? Resource.finish_enabled : Resource.star_enabled);
            }
        }
        firstTimeIncrement = false;
    }

    private void processGoalImage(String goalName, Resource changeToOnIncrementTouch) {
        Image goalImage = TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(goalName);
        if (goalImage != null) {
            goalImage.setDrawable(GraphicUtils.getImage(changeToOnIncrementTouch).getDrawable());
        }
    }

    @Override
    float getPulseDuration() {
        return  0.2f;
    }
}
