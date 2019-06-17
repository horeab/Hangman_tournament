package test.services.services;

import com.badlogic.gdx.Gdx;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanQuestionCreator;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.services.questions.QuestionCreator;
import libgdx.ui.services.questions.RandomQuestionCreatorService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QuestionCreatorTest extends TournamentGameTestDbApiService {


    @Test
    public void createRandomQuestionIds() {
        Question[] randomQuestions = new RandomQuestionCreatorService().createRandomQuestions(new QuestionConfig(HangmanArenaQuestionCategoryEnum.cat0, 15));
        List<Integer> questionLine = new ArrayList<>();
        assertEquals(15, randomQuestions.length);
        for (Question question : randomQuestions) {
            if (questionLine.contains(question.getQuestionLineInQuestionFile())) {
                fail();
            }
            questionLine.add(question.getQuestionLineInQuestionFile());
        }

        new RandomQuestionCreatorService().createRandomQuestions(new QuestionConfig(HangmanArenaQuestionCategoryEnum.cat0, 4));
    }

    @Test
    public void createRandomQuestion() {
        QuestionCreator questionCreator = new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._2, HangmanArenaQuestionCategoryEnum.cat1);
        List<Integer> expectedRandomQuestionLines = new ArrayList<>(Arrays.asList(0, 1, 2, 19, 20, 21));
        List<String> expectedRandomQuestionStrings = new ArrayList<>(Arrays.asList("Gâscă", "Urs panda", "Mistreţ", "Corb", "Lebădă", "Molid"));
        for (int i = 0; i < 100; i++) {
            expectedRandomQuestionLines.remove(Integer.valueOf(questionCreator.createRandomQuestion().getQuestionLineInQuestionFile()));
            expectedRandomQuestionStrings.remove(questionCreator.createRandomQuestion().getQuestionString());
        }
        assertEquals("[20, 21]", expectedRandomQuestionLines.toString());
        assertEquals("[Molid]", expectedRandomQuestionStrings.toString());

        assertTrue(new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat1).createRandomQuestion().getQuestionLineInQuestionFile() > 0);
        assertTrue(new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat1).createRandomQuestion().getQuestionLineInQuestionFile() > 0);

        questionCreator = new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5);
        expectedRandomQuestionLines = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 19, 20, 288, 289, 290));
        for (int i = 0; i < 2000; i++) {
            expectedRandomQuestionLines.remove(Integer.valueOf(questionCreator.createRandomQuestion().getQuestionLineInQuestionFile()));
        }

        assertEquals("[289, 290]", expectedRandomQuestionLines.toString());
    }


    @Test
    public void createRandomQuestionTwoDiff() {
        //Tari si Animale trebuie gasite - Cipru este alta dificutlate decat cea inscrisa _0 asa ca nu trebuie gasit
        QuestionCreator questionCreator = new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat0);
        List<String> expectedRandomQuestionStrings = new ArrayList<>(Arrays.asList("Austria", "Belgia", "Furnică", "Maimuţă", "Cipru", "Brad", "Orhidee"));
        for (int i = 0; i < 300; i++) {
            expectedRandomQuestionStrings.remove(questionCreator.createRandomQuestion().getQuestionString());
        }
        assertEquals("[Furnică, Maimuţă, Cipru, Brad, Orhidee]", expectedRandomQuestionStrings.toString());
    }


    @Test
    public void getAllQuestions() {
        QuestionCreator questionCreator = new HangmanQuestionCreator(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat0);
        List<Question> allQuestions = questionCreator.getAllQuestions(Arrays.asList(HangmanArenaQuestionDifficultyLevel._0), HangmanArenaQuestionCategoryEnum.cat0);
        List<String> expectedRandomQuestionStrings = new ArrayList<>(Arrays.asList("Austria", "Belgia", "Furnică", "Maimuţă", "Cipru", "Brad", "Orhidee"));
        for (Question question : allQuestions) {
            expectedRandomQuestionStrings.remove(question.getQuestionString());
        }
        assertEquals("[Furnică, Maimuţă, Cipru, Brad, Orhidee]", expectedRandomQuestionStrings.toString());
    }

    @Test
    public void createStringFile() {
        Scanner scanner = getFileContents();
        int lineNr = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Integer> randomVal = getRandomVal(lineNr);
            System.out.print(lineNr + ":" + ":" + line + ":" + randomVal.get(0) + "," + randomVal.get(1) + "," + randomVal.get(2) + ":" + lineNr + "\n");
//            System.out.print(lineNr + ":" + line + ":" + line + ":" + randomVal.get(0) + "," + randomVal.get(1) + "," + randomVal.get(2) + ":50,50:999" + "\n");
            lineNr++;
        }
    }


    @Test
    public void parseQuestion() {
        Scanner scanner = getFileContents();
        int lineNr = 0;
        String question = "";
        List<String> answers = new LinkedList<>();
        int answerIndex = 0;
        int answerInc = 0;
        int questionIndex = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty() && question.isEmpty()) {
                question = line;
                for (int i = 1; i <= 15; i++) {
                    int indexAddLength = String.valueOf(i).length() - 1;
                    if (question.substring(0, 3 + indexAddLength).equals(i + ". ")) {
                        question = question.substring(3 + indexAddLength);
                        break;
                    }
                }
            } else if (!line.isEmpty() && answers.size() != 4) {
                if (line.startsWith("@@")) {
                    answerIndex = answerInc;
                }
                answers.add(line.replace("@@", ""));
                answerInc++;
            }
            if (answers.size() == 4) {
                System.out.print(questionIndex + ":" + question + ":" + StringUtils.join(answers, "##") + ":" + answerIndex + ":" + "\n");
                question = "";
                answers.clear();
                answerIndex = 0;
                answerInc = 0;
                questionIndex++;
            }
            lineNr++;
        }
    }

    private Scanner getFileContents() {
        TournamentGame instance = TournamentGame.getInstance();
        String gameId = GameIdEnum.countryhistro.name();
        String lang = instance.getAppInfoService().getLanguage();
        lang = "ro";
        String difficulty = "1";
        String category = "9";
        return new Scanner(getFileText("tournament_resources/implementations/" + gameId + "/questions/" + lang + "/diff" + difficulty + "/questions_diff" + difficulty + "_cat" + category + ".txt"));
    }

    private List<Integer> getRandomVal(int index) {
        int val1 = getRandomVal(index, Arrays.asList());
        int val2 = getRandomVal(index, Arrays.asList(val1));
        int val3 = getRandomVal(index, Arrays.asList(val1, val2));
        return Arrays.asList(val1, val2, val3);
    }

    private Integer getRandomVal(int index, List<Integer> alreadyAddedVals) {
        int TOTAL_NR_OF_ELEMENTS = getTotalNr();
        int randomVal = new Random().nextInt(TOTAL_NR_OF_ELEMENTS);
        int bufferVal = 8;
        while (alreadyAddedVals.contains(randomVal)
                || randomVal > TOTAL_NR_OF_ELEMENTS - 1
                || randomVal < index - bufferVal
                || randomVal > index + bufferVal
                || randomVal == index) {
            randomVal = new Random().nextInt(TOTAL_NR_OF_ELEMENTS);
        }
        return randomVal;
    }

    private int getTotalNr() {
        Scanner scanner = getFileContents();
        int lineNr = 0;
        while (scanner.hasNextLine()) {
            lineNr++;
            scanner.nextLine();
        }
        return lineNr;
    }

    private String getFileText(String path) {
        return Gdx.files.internal(path).readString();
    }
}
