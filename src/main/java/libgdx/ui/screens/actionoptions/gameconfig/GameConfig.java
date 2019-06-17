package libgdx.ui.screens.actionoptions.gameconfig;

import libgdx.ui.constants.game.GameTypeDiamondConfiguration;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.model.game.question.QuestionConfig;

public class GameConfig {

    private QuestionConfig c;
    private GameTypeStage s;
    private String q;

    public GameConfig(QuestionConfig questionConfig, GameTypeStage gameTypeStage, String uniqueId) {
        this.c = questionConfig;
        this.s = gameTypeStage;
        this.q = uniqueId;
    }

    public QuestionConfig getQuestionConfig() {
        return c;
    }

    public void setQuestionConfig(QuestionConfig c) {
        this.c = c;
    }

    public GameTypeDiamondConfiguration getGameTypeDiamondConfiguration() {
        return s.getGameType().getGameTypeDiamondConfiguration();
    }

    public GameTypeStage getGameTypeStage() {
        return s;
    }

    public String getUniqueId() {
        return q;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameConfig that = (GameConfig) o;

        if (s != that.s) return false;
        return q != null ? q.equals(that.q) : that.q == null;
    }

    @Override
    public int hashCode() {
        int result = s != null ? s.hashCode() : 0;
        result = 31 * result + (q != null ? q.hashCode() : 0);
        return result;
    }
}
