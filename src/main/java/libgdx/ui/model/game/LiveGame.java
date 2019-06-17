package libgdx.ui.model.game;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.model.game.question.Question;

public class LiveGame {

    private int id;
    private int user1Id;
    private int user2Id;
    private int user1Status;
    private int user2Status;
    private String user1GameInfo;
    private Integer user1GameInfoVersion;
    private String user2GameInfo;
    private Integer user2GameInfoVersion;
    private Integer user1ActiveVersion;
    private Integer user2ActiveVersion;
    private Integer user1HasUser2GameInfoVersion;
    private Integer user2HasUser1GameInfoVersion;
    private Integer user1HasUser1GameInfoVersion;
    private Integer user2HasUser2GameInfoVersion;
    private String questions;
    private String gameConfig;

    public int getId() {
        return id;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public Integer getUser1Status() {
        return user1Status;
    }

    public Integer getUser2Status() {
        return user2Status;
    }

    public String getUser1GameInfo() {
        return user1GameInfo;
    }

    public String getUser2GameInfo() {
        return user2GameInfo;
    }

    public Integer getUser1GameInfoVersion() {
        return user1GameInfoVersion;
    }

    public Integer getUser2GameInfoVersion() {
        return user2GameInfoVersion;
    }

    public Integer getUser1ActiveVersion() {
        return user1ActiveVersion;
    }

    public Integer getUser2ActiveVersion() {
        return user2ActiveVersion;
    }

    public Integer getUser1HasUser2GameInfoVersion() {
        return user1HasUser2GameInfoVersion;
    }

    public Integer getUser2HasUser1GameInfoVersion() {
        return user2HasUser1GameInfoVersion;
    }

    public Integer getUser1HasUser1GameInfoVersion() {
        return user1HasUser1GameInfoVersion;
    }

    public Integer getUser2HasUser2GameInfoVersion() {
        return user2HasUser2GameInfoVersion;
    }

    public String getQuestions() {
        return questions;
    }

    public String getGameConfig() {
        return gameConfig;
    }

    public GameConfig getGameConfigObject() {
        if (StringUtils.isNotBlank(gameConfig)) {
            return new Gson().fromJson(gameConfig, GameConfig.class);
        }
        return null;
    }

    public Question[] getQuestionsArray() {
        if (StringUtils.isNotBlank(questions)) {
            return new Gson().fromJson(questions, Question[].class);
        }
        return null;
    }
}
