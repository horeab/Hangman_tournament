package libgdx.ui.model.game.extrainfo;

import java.util.Objects;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.model.game.question.Question;

public class LiveGameInvite {

    private int liveGameId;
    private Question[] questions;
    private BaseUserInfo creatorUser;
    private GameConfig gameConfig;

    public LiveGameInvite(Question[] questions, int liveGameId, BaseUserInfo creatorUser, GameConfig gameConfig) {
        this.liveGameId = liveGameId;
        this.questions = questions;
        this.creatorUser = creatorUser;
        this.gameConfig = gameConfig;
    }

    public int getLiveGameId() {
        return liveGameId;
    }

    public BaseUserInfo getCreatorUser() {
        return creatorUser;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public Question[] getQuestions() {
        return questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiveGameInvite that = (LiveGameInvite) o;
        return liveGameId == that.liveGameId &&
                Objects.equals(creatorUser, that.creatorUser) &&
                Objects.equals(gameConfig, that.gameConfig);
    }

    @Override
    public int hashCode() {

        return Objects.hash(liveGameId, creatorUser, gameConfig);
    }
}
