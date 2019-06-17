package libgdx.ui.services.game.gameservice;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import libgdx.game.Game;
import libgdx.resources.Res;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.resources.gamelabel.SpecificPropertiesUtils;
import libgdx.utils.EnumUtils;
import libgdx.graphics.GraphicUtils;

public abstract class GameService {

    protected Question question;

    public GameService(Question question) {
        this.question = question;
    }

    public boolean addAnswerToGameInfo(GameUser gameUser, GameAnswerInfo gameAnswerInfo) {
        boolean isAnswerCorrectInQuestion = isAnswerCorrectInQuestion(gameAnswerInfo.getAnswer());
        gameUser.addAnswerToGameQuestionInfo(gameAnswerInfo.getAnswer(), gameAnswerInfo.getMillisAnswered());
        return isAnswerCorrectInQuestion;
    }

    private boolean isGameFinished(Set<String> answersIds) {
        return isGameFinishedSuccessful(answersIds) || isGameFinishedFailed(answersIds);
    }

    public Image getQuestionImage() {
        Image image = null;
        String[] split = question.getQuestionString().split(":");
        int imageSplitPos = getImageToBeDisplayedPositionInString();
        if (split.length == imageSplitPos + 1 && StringUtils.isNotBlank(split[imageSplitPos])) {
            image = GraphicUtils.getImage(Game.getInstance().getMainDependencyManager().createResourceService().getByName("img_cat" + question.getQuestionCategory().getIndex() + "_" + split[imageSplitPos]));
        }
        return image;
    }

    public String getQuestionToBeDisplayed() {
        String questionString = question.getQuestionString();
        String questionToBeDisplayed = questionString.contains(":") ? questionString.split(":")[getQuestionToBeDisplayedPositionInString()] : "";
        if (StringUtils.isBlank(questionToBeDisplayed)) {
            TournamentGame game = TournamentGame.getInstance();
            String generalQuestionToBeDisplayed = SpecificPropertiesUtils.getText(game.getAppInfoService().getLanguage() + "_" + game.getGameIdPrefix() + "_" + question.getQuestionCategory().name() + "_question");
            questionToBeDisplayed = generalQuestionToBeDisplayed != null ? generalQuestionToBeDisplayed : "";
        }
        return questionToBeDisplayed;
    }

    public GameAnswerInfo simulatePressedAnswer(GameQuestionInfo gameQuestionInfo) {
        GameAnswerInfo gameAnswerInfo = null;
        Set<String> answerIds = gameQuestionInfo.getAnswerIds();
        if (!isGameFinished(answerIds)) {
            int correctAnswerFactor = getSimulatePressedLetterCorrectAnswerFactor();
            List<String> correctAnswers = getUnpressedCorrectAnswers(answerIds);
            List<String> wrongAnswers = createWrongAnswerList(gameQuestionInfo, getAllAnswerOptions(), correctAnswers);

            String simulatedPressedLetter = correctAnswers.get(0);
            if (!wrongAnswers.isEmpty()) {
                int randomVal = new Random().nextInt(100);
                simulatedPressedLetter = randomVal > correctAnswerFactor ? simulatedPressedLetter : wrongAnswers.get(0);
            }
            Long simulatedPressedLetterMillis = gameQuestionInfo.getTotalAnswerMillis() + getMillisLetterPressed(answerIds);
            gameAnswerInfo = new GameAnswerInfo(simulatedPressedLetter, simulatedPressedLetterMillis);

        }
        return gameAnswerInfo;
    }

    private List<String> createWrongAnswerList(GameQuestionInfo gameQuestionInfo, List<String> allAnswers, List<String> correctAnswers) {
        List<String> newAllAnswersList = new ArrayList<>(allAnswers);
        Collections.shuffle(newAllAnswersList);
        newAllAnswersList.removeAll(gameQuestionInfo.getAnswerIds());
        newAllAnswersList.removeAll(correctAnswers);
        return newAllAnswersList;
    }

    private Long getMillisLetterPressed(Set<String> answersIds) {
        float diff0 = 1.6f;
        float diff1 = 1.4f;
        float diff2 = 1.2f;
        float multiplier = diff0;
        QuestionDifficulty questionDifficultyLevel = question.getQuestionDifficultyLevel();
        if (getEasyDiff().contains(questionDifficultyLevel)) {
            multiplier = diff0;
        } else if (getMediumDiff().contains(questionDifficultyLevel)) {
            multiplier = diff1;
        } else if (getHardDiff().contains(questionDifficultyLevel)) {
            multiplier = diff2;
        }
        List<Long> fastIntervalsToPressAnswer = getFastIntervalsToPressAnswer();
        List<Long> slowIntervalsToPressAnswer = getSlowIntervalsToPressAnswer();
        float percentOfAnswersFound = getPercentOfCorrectAnswersPressed(answersIds);
        List<Long> intervalList = percentOfAnswersFound < 50 ? fastIntervalsToPressAnswer : slowIntervalsToPressAnswer;
        float millis = intervalList.get(new Random().nextInt(intervalList.size())) * multiplier;
        return (long) millis;
    }

    public boolean compareAnswerStrings(String answer1, String answer2) {
        return answer1.equals(answer2);
    }

    public List<QuestionDifficulty> getEasyDiff() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getEasyDiff();
    }

    public List<QuestionDifficulty> getMediumDiff() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getMediumDiff();
    }

    public List<QuestionDifficulty> getHardDiff() {
        return TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getHardDiff();
    }

    protected static int getRandomCorrectAnswerFactor(int baseFactor) {
        if (new Random().nextInt(100) > 90) {
            baseFactor = baseFactor - baseFactor / 10;
        } else if (new Random().nextInt(100) > 70) {
            baseFactor = baseFactor - baseFactor / 20;
        }
        return baseFactor;
    }

    public void processNewGameQuestionInfo(GameUser gameUser, GameQuestionInfo gameQuestionInfo) {
    }

    public abstract int getImageToBeDisplayedPositionInString();

    protected abstract int getQuestionToBeDisplayedPositionInString();

    public abstract boolean isGameFinishedSuccessful(Set<String> answersIds);

    public abstract boolean isGameFinishedFailed(Set<String> answersIds);

    public abstract int getNrOfWrongAnswersPressed(Set<String> answerIds);

    public abstract boolean isAnswerCorrectInQuestion(String answer);

    public abstract String getRandomUnpressedAnswerFromQuestion(Set<String> answersIds);

    public abstract float getPercentOfCorrectAnswersPressed(Set<String> answerIds);

    public abstract List<String> getAllAnswerOptions();

    public abstract List<String> getUnpressedCorrectAnswers(Set<String> answerIds);

    protected abstract int getSimulatePressedLetterCorrectAnswerFactor();

    protected abstract List<Long> getFastIntervalsToPressAnswer();

    protected abstract List<Long> getSlowIntervalsToPressAnswer();
}
