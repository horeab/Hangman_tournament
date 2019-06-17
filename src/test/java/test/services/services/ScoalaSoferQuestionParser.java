package test.services.services;

import com.badlogic.gdx.Gdx;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import static org.junit.Assert.fail;


import java.util.ArrayList;
import java.util.List;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferDependencyManager;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionCategoryEnum;
import libgdx.ui.services.questions.ImageCategIncrementRes;

public class ScoalaSoferQuestionParser extends TournamentGameTestDbApiService {

    private ScoalaSoferQuestionCategoryEnum CATEGORY = ScoalaSoferQuestionCategoryEnum.cat14;

    @Test
    public void createStringFile() {
        int nrOfQuestions = getNrOfQuestions();
        for (int questionNr = 1; questionNr <= nrOfQuestions; questionNr++) {
            String question = extractQuestion(questionNr).trim();
            if (StringUtils.isBlank(question)) {
                fail(questionNr + "");
            }
            List<String> answerOptions = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                String answer = extractAnswer(questionNr, j).trim();
                char lastChar = answer.toCharArray()[answer.toCharArray().length - 1];
                if (StringUtils.isBlank(answer)) {
                    fail(questionNr + "");
                }
                if (j < 2 && lastChar != ';') {
                    fail(answer);
                } else if (j == 2 && lastChar != '.') {
                    fail(answer);
                }
                answerOptions.add(answer.substring(0, answer.length() - 1));
            }
            int questionId = questionNr - 1;
            String imageId = hasImageSameAsId(CATEGORY, questionId) ? Integer.toString(questionId) : "";
            System.out.println(questionId + ":" +
                    question + ":" +
                    StringUtils.join(answerOptions, "##") + ":" +
                    StringUtils.join(extractCorrectAnswersForQuestionNr(questionNr, questionNr == nrOfQuestions), ",") + ":" +
                    imageId
            );
        }
    }

    private boolean hasImageSameAsId(QuestionCategory questionCategory, int questionId) {
        for (ImageCategIncrementRes res : new ScoalaSoferDependencyManager().getImageCategIncrementResList()) {
            if (res.getQuestionCategory().equals(questionCategory)) {
                for (int i = res.getBeginIndex(); i <= res.getEndIndex(); i++) {
                    if (i == questionId) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private int getNrOfQuestions() {
        String answers = getFileText("answers");
        return answers.length() - answers.replaceAll("-", "").length();
    }

    private List<Integer> extractCorrectAnswersForQuestionNr(int questionNr, boolean lastQuestion) {
        List<Integer> correctAnswers = new ArrayList<>();
        String fileText = getFileText("answers");
        try {
            if (lastQuestion) {
                fileText = fileText.substring(fileText.indexOf(questionNr + "-"));
            } else {
                fileText = fileText.substring(fileText.indexOf(questionNr + "-"), fileText.indexOf(questionNr + 1 + "-"));
            }
            char[] fileTextChars = fileText.substring(fileText.indexOf("-") + 1).trim().toCharArray();
            for (int i = 0; i < fileTextChars.length; i++) {
                int answerId = -1;
                for (int j = 0; j < 3; j++) {
                    if (Character.toString(fileTextChars[i]).equals(getAnswerLetterForNr(j))) {
                        answerId = j;
                        break;
                    }
                }
                if (answerId != -1) {
                    correctAnswers.add(answerId);
                }
            }
        } catch (StringIndexOutOfBoundsException e) {
            int i = 0;
        }
        return correctAnswers;
    }

//    @Test
//    public void testStrings(){
//        for (QuestionCategory categoryEnum : (QuestionCategory[]) EnumUtils.getValues(new ScoalaSoferDependencyManager().getQuestionCategoryTypeEnum())) {
//        }
//    }

    private String extractAnswer(int questionNr, int answerNr) {
        String questionsString = getQuestionsString(questionNr);
        String endIndexString = answerNr == 2 ? questionNr + 1 + "." : getAnswerLetterForNr(answerNr + 1) + ".";
        int beginIndex = questionsString.indexOf(getAnswerLetterForNr(answerNr) + ".") + 2;
        String substring = questionsString.substring(beginIndex);
        return substring.substring(0, questionNr == getNrOfQuestions() && answerNr == 2 ? substring.length() : substring.indexOf(endIndexString));
    }

    private String getAnswerLetterForNr(int nr) {
        switch (nr) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
        }
        return null;
    }

    private String extractQuestion(int questionNr) {
        String substring = getQuestionsString(questionNr);
        return substring.substring(substring.indexOf(".") + 1, substring.indexOf(getAnswerLetterForNr(0) + ".")).trim();
    }

    private String getQuestionsString(int questionNr) {
        String fileText = getFileText("questions");
        return fileText.substring(fileText.indexOf(questionNr + "."));
    }

    private String getFileText(String fileName) {
        String gameId = "scoalasofer";
        return Gdx.files.internal("tournament_resources/implementations/" + gameId + "/questions/parser/" + CATEGORY.getIndex() + fileName + ".txt").readString().replace("\n", "").replace(":", "").replace("\r", " ");
    }
}
