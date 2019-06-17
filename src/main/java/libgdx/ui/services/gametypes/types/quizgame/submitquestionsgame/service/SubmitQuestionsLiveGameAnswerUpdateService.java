package libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.service;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.services.game.livegame.LiveGameAnswerUpdateService;
import libgdx.ui.services.gametypes.types.quizgame.submitquestionsgame.screencreator.SubmitQuestionsRealOpponentQuestionContainerCreator;

public class SubmitQuestionsLiveGameAnswerUpdateService extends LiveGameAnswerUpdateService {

    public SubmitQuestionsLiveGameAnswerUpdateService(int liveGameId, AbstractGameScreenCreator screenCreator) {
        super(liveGameId, screenCreator);
    }

    @Override
    public void opponentPressedAnswer(GameUser opponent, String jsonGameInfo, int gameInfoVersion) {
        SubmitQuestionsGameService gameService = (SubmitQuestionsGameService) GameServiceContainer.getGameService(opponent.getBaseUserInfo().getId(), opponent.getGameQuestionInfo());
        gameService.setInitialAnswerOptionsPressedToVerify(getGameAnswerInfo(jsonGameInfo).size());
        super.opponentPressedAnswer(opponent, jsonGameInfo, gameInfoVersion);
    }

    @Override
    protected List<GameAnswerInfo> getGameAnswerInfo(String jsonGameInfo) {
        GameAnswerInfo allGameAnswerInfo = new Gson().fromJson(jsonGameInfo, GameAnswerInfo.class);
        String[] opponentAnswers = allGameAnswerInfo.getAnswer().split(SubmitQuestionsRealOpponentQuestionContainerCreator.ANSWER_DELIMITER);
        List<GameAnswerInfo> gameAnswerInfos = new ArrayList<>();
        for (String opponentAnswer : opponentAnswers) {
            gameAnswerInfos.add(new GameAnswerInfo(opponentAnswer, allGameAnswerInfo.getMillisAnswered()));
        }
        return gameAnswerInfos;
    }
}
