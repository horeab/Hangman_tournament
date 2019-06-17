package libgdx.ui.model.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import libgdx.game.Game;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.utils.SoundUtils;

public class GameUser {

    private BaseUserInfo baseUserInfo;
    private List<GameQuestionInfo> allQuestionInfos = new LinkedList<>();
    private List<GameQuestionInfo> openQuestionInfos = new LinkedList<>();
    private int wonQuestions = 0;
    private int lostQuestions = 0;

    GameUser(BaseUserInfo baseUserInfo) {
        this.baseUserInfo = baseUserInfo;
    }

    public BaseUserInfo getBaseUserInfo() {
        return baseUserInfo;
    }

    private void setWonQuestion(GameQuestionInfo gameQuestionInfo) {
        if (gameQuestionInfo.isQuestionOpen()) {
            gameQuestionInfo.setStatus(GameQuestionInfoStatus.WON);
            wonQuestions++;
            openQuestionInfos.remove(gameQuestionInfo);
            incrementWonQuestions(gameQuestionInfo);
        }
    }

    private void incrementWonQuestions(GameQuestionInfo gameQuestionInfo) {
        AbstractScreen abstractScreen = TournamentGame.getInstance().getAbstractScreen();
        BaseUserInfo currentUser = abstractScreen.getCurrentUser();
        if (baseUserInfo.getId() == currentUser.getId()) {
            abstractScreen.getGameStatsDbApiService().incrementGameStatsQuestionsWon(currentUser.getId(), gameQuestionInfo.getQuestion().getQuestionString() + gameQuestionInfo.getQuestion().getQuestionLineInQuestionFile());
        }
    }

    public void setLostQuestion(GameQuestionInfo gameQuestionInfo) {
        if (gameQuestionInfo.isQuestionOpen()) {
            gameQuestionInfo.setStatus(GameQuestionInfoStatus.LOST);
            lostQuestions++;
            openQuestionInfos.remove(gameQuestionInfo);
        }
    }

    public int getWonQuestions() {
        return wonQuestions;
    }

    public int getLostQuestions() {
        return lostQuestions;
    }

    public int getFinishedQuestions() {
        return wonQuestions + lostQuestions;
    }

    public void addAnswerToGameQuestionInfo(GameQuestionInfo gameQuestionInfo, String answerId, Long millisAnswered) {
        if (gameQuestionInfo != null) {
            gameQuestionInfo.addAnswer(answerId, millisAnswered);
            setQuestionFinishedStatus(gameQuestionInfo);
        }
    }

    public void addAnswerToGameQuestionInfo(String answerId, Long millisAnswered) {
        addAnswerToGameQuestionInfo(getGameQuestionInfo(), answerId, millisAnswered);
    }

    private void setQuestionFinishedStatus(GameQuestionInfo gameQuestionInfo) {
        GameService gameService = GameServiceContainer.getGameService(getBaseUserInfo().getId(), gameQuestionInfo);
        boolean userSuccess = gameService.isGameFinishedSuccessful(gameQuestionInfo.getAnswerIds());
        if (userSuccess) {
            playSound(Resource.sound_correct_answer);
            setWonQuestion(gameQuestionInfo);
        } else {
            boolean userFail = gameService.isGameFinishedFailed(gameQuestionInfo.getAnswerIds());
            if (userFail) {
                playSound(Resource.sound_wrong_answer);
                setLostQuestion(gameQuestionInfo);
            }
        }
    }

    private void playSound(Resource sound_correct_answer) {
        if (TournamentGame.getInstance().getCurrentUser().getId() == getBaseUserInfo().getId()) {
            SoundUtils.playSound(sound_correct_answer);
        }
    }

    public GameQuestionInfo getGameQuestionInfo() {
        return openQuestionInfos.isEmpty() ? null : openQuestionInfos.get(0);
    }

    public Long getTotalAnswerMillisForGameQuestionInfoList() {
        long totalAnswerMillisForGameQuestionInfoList = 0;
        for (GameQuestionInfo gameQuestionInfo : allQuestionInfos) {
            totalAnswerMillisForGameQuestionInfoList = totalAnswerMillisForGameQuestionInfoList + gameQuestionInfo.getTotalAnswerMillis();
        }
        return totalAnswerMillisForGameQuestionInfoList;
    }

    public GameQuestionInfo getGameQuestionInfo(int questionIndex) {
        return allQuestionInfos.get(questionIndex);
    }

    public boolean userHasMultipleQuestions() {
        return getTotalNrOfQuestions() > 1;
    }

    public List<GameQuestionInfo> getAllQuestionInfos() {
        return allQuestionInfos;
    }

    public int getTotalNrOfQuestions() {
        return allQuestionInfos.size();
    }

    public void addGameQuestionInfoToList(GameQuestionInfo gameQuestionInfo) {
        allQuestionInfos.add(gameQuestionInfo);
        openQuestionInfos.add(gameQuestionInfo);
    }

    public List<GameQuestionInfo> getOpenQuestionInfos() {
        return openQuestionInfos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameUser gameUser = (GameUser) o;
        return Objects.equals(baseUserInfo, gameUser.baseUserInfo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(baseUserInfo);
    }
}
