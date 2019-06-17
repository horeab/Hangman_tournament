package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.realopponent.RealOpponentQuestionContainerCreator;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameControlsCreatorService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service.SubmitQuestionsGameService;

public class SubmitQuestionsRealOpponentQuestionContainerCreator extends RealOpponentQuestionContainerCreator<SubmitQuestionsGameService> {

    public static final String ANSWER_DELIMITER = ":::";
    private Integer answerOptionsPressedToVerify = null;
    private Set<String> pressedAnswers = new HashSet<>();

    public SubmitQuestionsRealOpponentQuestionContainerCreator(ChallengeGameScreen abstractScreen, GameContext gameContext) {
        super(abstractScreen, gameContext);
    }

    @Override
    public Table create() {
        Table table = super.create();
        MyButton button = (MyButton) table.findActor(SubmitQuestionsGameControlsCreatorService.ANSWER_LATER_BUTTON_NAME);
        if (button != null) {
            button.remove();
        }
        return table;
    }

    @Override
    public void buttonClick(String answer) {
        pressedAnswers.add(answer);
        if (answerOptionsPressedToVerify == null) {
            answerOptionsPressedToVerify = getGameService().getInitialAnswerOptionsPressedToVerify();
        }
        answerOptionsPressedToVerify--;
        if (answerOptionsPressedToVerify == 0) {
            super.buttonClick(answer);
        }
    }

    @Override
    protected GameAnswerInfo createGameAnswerInfo(String answer) {
        String allAnswers = StringUtils.join(pressedAnswers, ANSWER_DELIMITER);
        return new GameAnswerInfo(allAnswers, getGameScreen().getMillisPassedSinceScreenDisplayed());
    }
}
