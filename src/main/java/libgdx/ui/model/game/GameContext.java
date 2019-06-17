package libgdx.ui.model.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import libgdx.ui.constants.game.HintButtonType;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;
import libgdx.utils.DateUtils;

public class GameContext {

    private GameUser currentUserGameUser;
    private List<GameUser> opponentGameUsers;
    private GameConfig gameConfig;
    private Long millisGameStarted;
    private int amountAvailableHints;

    GameContext(GameUser currentUserGameUser, List<GameUser> opponentGameUsers, GameConfig gameConfig, int amountAvailableHints) {
        this.currentUserGameUser = currentUserGameUser;
        this.opponentGameUsers = opponentGameUsers;
        this.amountAvailableHints = amountAvailableHints;
        this.gameConfig = gameConfig;
    }

    public GameConfig getGameConfig() {
        return gameConfig;
    }

    public GameUser getCurrentUserGameUser() {
        return currentUserGameUser;
    }

    public List<GameUser> getOpponentGameUsers() {
        return opponentGameUsers;
    }


    public GameUser getOpponentGameUser() {
        return opponentGameUsers != null && !opponentGameUsers.isEmpty() ? opponentGameUsers.get(0) : null;
    }

    public Question getQuestion() {
        return getCurrentUserGameUser().getGameQuestionInfo().getQuestion();
    }

    public void useHint() {
        amountAvailableHints--;
    }

    public int getAmountAvailableHints() {
        return amountAvailableHints;
    }

    public List<HintButtonType> getAvailableHints() {
        List<HintButtonType> availableHints = new ArrayList<>();
        for (int i = 0; i < amountAvailableHints; i++) {
            availableHints.add(getCurrentUserCreatorDependencies().getHintButtonType());
        }
        return availableHints;
    }

    public void startMillis() {
        if (millisGameStarted == null) {
            millisGameStarted = DateUtils.getNowMillis();
        }
    }

    public Long getMillisGameInProgress() {
        return DateUtils.getNowMillis() - millisGameStarted;
    }

    public CreatorDependencies getCurrentUserCreatorDependencies() {
        return CreatorDependenciesContainer.getCreator(getQuestion().getQuestionCategory().getCreatorDependencies());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameContext gameContext = (GameContext) o;
        return Objects.equals(currentUserGameUser, gameContext.currentUserGameUser) &&
                Objects.equals(opponentGameUsers, gameContext.opponentGameUsers);
    }

    @Override
    public int hashCode() {

        return Objects.hash(currentUserGameUser, opponentGameUsers);
    }
}
