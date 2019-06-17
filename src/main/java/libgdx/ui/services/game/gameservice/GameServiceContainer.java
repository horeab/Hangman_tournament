package libgdx.ui.services.game.gameservice;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;

public abstract class GameServiceContainer {

    private static Map<Pair<Integer, Question>, GameService> gameServices = new HashMap<>();

    public static GameService getGameService(Class<? extends GameService> gameServiceClass, int userId, Question question) {
        GameService instance = gameServices.get(new MutablePair<>(userId, question));
        if (instance == null) {
            try {
                instance = gameServiceClass.getConstructor(Question.class).newInstance(question);
                gameServices.put(new MutablePair<>(userId, question), instance);
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return instance;
    }


    public static GameService getGameService(int userId, GameQuestionInfo gameQuestionInfo) {
        return getGameService(userId, gameQuestionInfo.getQuestion());
    }

    public static GameService getGameService(int userId, Question question) {
        return CreatorDependenciesContainer.getCreator(question.getQuestionCategory().getCreatorDependencies())
                .getGameService(userId, question);
    }


    public static void clearContainer() {
        gameServices.clear();
    }
}
