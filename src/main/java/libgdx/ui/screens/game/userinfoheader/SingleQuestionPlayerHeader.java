package libgdx.ui.screens.game.userinfoheader;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.Resource;
import libgdx.graphics.GraphicUtils;

class SingleQuestionPlayerHeader extends PlayerHeader {

    SingleQuestionPlayerHeader(BaseUserInfo player) {
        super(player);
    }

    @Override
    public void animateGameOverHeaderWin() {
        super.animateGameOverHeaderWin();
        animateGameOver(Resource.game_user_header_background_green);
    }

    @Override
    public void animateGameOverHeaderLose() {
        super.animateGameOverHeaderLose();
        animateGameOver(Resource.game_user_header_background_red);
    }

    private void animateGameOver(Resource backgroundRes) {
        final Table fillCorrectAnswersTable = getFillCorrectAnswersTable();
        if (fillCorrectAnswersTable != null) {
            getActionTable().setBackground(GraphicUtils.getNinePatch(backgroundRes));
            RunnableAction runnableAction = new RunnableAction() {
                @Override
                public void run() {
                    fillCorrectAnswersTable.setVisible(false);
                }
            };
            fillCorrectAnswersTable.addAction(Actions.sequence(Actions.fadeOut(PULSE_DURATION), runnableAction));
            getActionTable().addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
        }
    }
}
