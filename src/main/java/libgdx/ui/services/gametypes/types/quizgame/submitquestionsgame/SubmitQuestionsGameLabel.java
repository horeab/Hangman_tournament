package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame;

import libgdx.game.Game;
import libgdx.resources.gamelabel.GameLabel;
import libgdx.resources.gamelabel.GameLabelUtils;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;

public enum SubmitQuestionsGameLabel implements GameLabel {

    answer_later,
    delete_answers,
    submit_answer,

    question_from_question,;

    @Override
    public String getText(Object... params) {
        String language = Game.getInstance().getAppInfoService().getLanguage();
        return GameLabelUtils.getText(getKey(), language, SpecificPropertiesUtils.getLabelFilePath(), params);
    }

    @Override
    public String getKey() {
        return name();
    }
}
