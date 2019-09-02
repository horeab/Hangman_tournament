package libgdx.ui.model.game;

import java.util.ArrayList;
import java.util.List;

import libgdx.game.model.BaseUserInfo;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.gametypes.creatordependencies.CreatorDependenciesContainer;
import libgdx.ui.services.questions.RandomQuestionCreatorService;

public class GameContextService {

    public GameContext createGameContext(BaseUserInfo currentUser, List<BaseUserInfo> opponentsBaseUserInfo, GameConfig gameConfig) {
        return createGameContext(currentUser, opponentsBaseUserInfo, gameConfig, gameConfig.getGameTypeStage().getGameType().getAmountAvailableHints());
    }

    public GameContext createGameContext(BaseUserInfo currentUser, List<BaseUserInfo> opponentsBaseUserInfo, GameConfig gameConfig, int availableHints) {
        Question[] randomQuestions = new RandomQuestionCreatorService().createRandomQuestions(gameConfig.getQuestionConfig());
        GameUser gameUser = createGameUser(currentUser, randomQuestions);
        List<GameUser> gameUsers = createGameContext(opponentsBaseUserInfo, randomQuestions);
        return createGameContext(gameUser, gameUsers, gameConfig, availableHints);
    }

    public GameContext createGameContext(BaseUserInfo currentUser, List<BaseUserInfo> opponentsBaseUserInfo, GameConfig gameConfig, Question... questions) {
        if (questions.length == 0) {
            return createGameContext(currentUser, opponentsBaseUserInfo, gameConfig);
        }
        GameUser gameUser = createGameUser(currentUser, questions);
        List<GameUser> gameUsers = createGameContext(opponentsBaseUserInfo, questions);
        return createGameContext(gameUser, gameUsers, gameConfig, gameConfig.getGameTypeStage().getGameType().getAmountAvailableHints());
    }

    private GameContext createGameContext(GameUser gameUser, List<GameUser> gameUsers, GameConfig gameConfig, int amountAvailableHints) {
        return new GameContext(gameUser, gameUsers, gameConfig, amountAvailableHints);
    }

    private GameUser createGameUser(BaseUserInfo baseUserInfo, Question... questions) {
        GameUser gameUser = createNewGameUser(baseUserInfo);
        for (Question question : questions) {
            addNewQuestionInfo(gameUser, question);
        }
        return gameUser;
    }

    private List<GameUser> createGameContext(List<BaseUserInfo> opponentsBaseUserInfo, Question... questions) {
        List<GameUser> gameUsers = new ArrayList<>();
        for (BaseUserInfo baseUserInfo : opponentsBaseUserInfo) {
            gameUsers.add(createGameUser(baseUserInfo, questions));
        }
        return gameUsers;
    }

    private void addNewQuestionInfo(GameUser gameUser, Question question) {
        GameQuestionInfo gameQuestionInfo = new GameQuestionInfo(question);
        gameUser.addGameQuestionInfoToList(gameQuestionInfo);
        CreatorDependenciesContainer.getCreator(question.getQuestionCategory().getCreatorDependencies()).getGameService(gameUser.getBaseUserInfo().getId(), gameQuestionInfo.getQuestion()).processNewGameQuestionInfo(gameUser, gameQuestionInfo);
    }

    private GameUser createNewGameUser(BaseUserInfo baseUserInfo) {
        return new GameUser(baseUserInfo);
    }
}
