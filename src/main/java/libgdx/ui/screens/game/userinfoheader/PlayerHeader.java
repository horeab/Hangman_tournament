package libgdx.ui.screens.game.userinfoheader;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.apache.commons.lang3.mutable.MutableFloat;

import libgdx.ui.game.TournamentGame;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.resources.Resource;
import libgdx.graphics.GraphicUtils;

public abstract class PlayerHeader extends Table {

    static final float PULSE_DURATION = 0.2f;

    private BaseUserInfo player;
    private MutableFloat previousPercentageToFillAllAnswers;
    private MutableFloat previousPercentageToFillCorrectAnswers;

    PlayerHeader(BaseUserInfo player) {
        this.player = player;
        previousPercentageToFillAllAnswers = new MutableFloat(0);
        previousPercentageToFillCorrectAnswers = new MutableFloat(0);
    }

    public void animateGameOverHeaderWin() {
        MyWrappedLabel score = TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(PlayerHeaderBuilder.getScoreLabelName(player));
        if (score != null) {
            int newScore = Integer.parseInt(score.getText()) + 1;
            score.setText(String.valueOf(newScore));
        }
    }

    public void animateGameOverHeaderLose() {
    }

    public void increaseFillCorrectAnswersTable(float percentageToFill) {
        increaseFillAnswersTable(percentageToFill, getFillCorrectAnswersTable(), previousPercentageToFillCorrectAnswers, executeAfterIncreaseRunnableAction(percentageToFill));
    }

    public void increaseFillAllAnswersTable(float percentageToFill) {
        increaseFillAnswersTable(percentageToFill, getFillAllAnswersTable(), previousPercentageToFillAllAnswers, null);
    }

    private void increaseFillAnswersTable(float percentageToFill, Table table, MutableFloat previousPercentageToFill, RunnableAction executeAfterIncreaseRunnableAction) {
        if (percentageToFill != previousPercentageToFill.floatValue()) {
            table.setVisible(true);
            float parentWidth = table.getParent().getWidth();
            float amountToIncrease = getWidthToDisplayForFillCorrectLettersTable(parentWidth, percentageToFill) - getWidthToDisplayForFillCorrectLettersTable(parentWidth, previousPercentageToFill.floatValue());
            SequenceAction sequence = Actions.sequence(Actions.sizeBy(amountToIncrease, 0, getPulseDuration()));
            if (executeAfterIncreaseRunnableAction != null) {
                sequence.addAction(executeAfterIncreaseRunnableAction);
            }
            table.addAction(sequence);
        }
        previousPercentageToFill.setValue(percentageToFill);
    }

    private RunnableAction executeAfterIncreaseRunnableAction(final float percentageToFill) {
        RunnableAction runnableAction = new RunnableAction();
        runnableAction.setRunnable(new Runnable() {
            @Override
            public void run() {
                executeAfterIncrease(percentageToFill);
            }
        });
        return runnableAction;
    }

    protected void executeAfterIncrease(float percentageToFill) {
    }

    private float getWidthToDisplayForFillCorrectLettersTable(float parentWidth, float percentageToShowFillCorrectLettersTable) {
        return parentWidth * (percentageToShowFillCorrectLettersTable / 100);
    }

    public void animatePulseWrongAnswer() {
        animatePulse(Resource.game_user_header_background_red);
    }

    public BaseUserInfo getPlayer() {
        return player;
    }

    private void animatePulse(Resource ninePatch) {
        final Table fillCorrectLettersTable = getFillCorrectAnswersTable();
        Table actionTable = getActionTable();
        fillCorrectLettersTable.addAction(Actions.fadeOut(getPulseDuration() / 2));
        actionTable.setBackground(GraphicUtils.getNinePatch(ninePatch));
        RunnableAction runnableAction = new RunnableAction() {
            @Override
            public void run() {
                fillCorrectLettersTable.addAction(Actions.fadeIn(getPulseDuration() / 2));
            }
        };
        actionTable.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(getPulseDuration()), Actions.fadeOut(getPulseDuration()), runnableAction));
    }

    Table getFillCorrectAnswersTable() {
        return TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(PlayerHeaderBuilder.getFillCorrectAnswersTableName(player));
    }

    private Table getFillAllAnswersTable() {
        return TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(PlayerHeaderBuilder.getFillAllAnswersTableName(player));
    }

    Table getActionTable() {
        return TournamentGame.getInstance().getAbstractScreen().getRoot().findActor(PlayerHeaderBuilder.getActionTableName(player));
    }

    float getPulseDuration() {
        return PULSE_DURATION;
    }
}
