package libgdx.ui.services.questions;

import libgdx.resources.IncrementingRes;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.game.TournamentGame;

public class ImageCategIncrementRes extends IncrementingRes {

    public final static String JPG = "jpg";
    public final static String PNG = "png";

    private QuestionCategory questionCategory;

    public ImageCategIncrementRes(int beginIndex, int endIndex, QuestionCategory questionCategory, String imgExtension) {
        super(beginIndex, endIndex,
                TournamentGame.getInstance().getAppInfoService().getImplementationGameResourcesFolder() + "questions/images/" + getCategNr(questionCategory) + "/%s." + imgExtension,
                "img_" + getCategNr(questionCategory) + "_%s");
        this.questionCategory = questionCategory;
    }

    public ImageCategIncrementRes(int index, QuestionCategory questionCategory, String imgExtension) {
        this(index, index, questionCategory, imgExtension);
    }

    private static String getCategNr(QuestionCategory questionCategory) {
        return "cat" + questionCategory.getIndex();
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }
}
