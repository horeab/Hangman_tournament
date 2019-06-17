package test.services.services;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.livegame.LiveGameAnswerUpdateService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;

public class LiveGameAnswerUpdateServiceTest extends TournamentGameTestDbApiService {

    private int liveGameId;

    private LiveGameAnswerUpdateService liveGameAnswerUpdateService;
    private GameConfig gameConfig;
    private GameContext gameContext;

    @Before
    public void setup() throws Exception {
        super.setup();
        gameConfig = new GameConfig(new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5, 5), GameTypeStage.challenge_3, DateUtils.getDateString(DateUtils.minusDays(100)));
        new LiveGameService().createLiveGameWithLiveGameInvite(currentUser, opponent.getId(), gameConfig);
        liveGameId = liveGameDbApiService.getLiveGame(currentUser.getId(), opponent.getId()).getId();
        gameContext = TestDataCreator.createEmptyGameContext(gameConfig, new ArrayList<>(), TestDataCreator.QUESTION_0);
        screenManager.startGameChallengeScreen(gameContext, liveGameId);
        TestUtils.waitt(1500);
        ((AbstractScreen) TournamentGame.getInstance().getScreen()).buildStage();
    }

    @Test
    public void opponentPressedAnswer() {
        liveGameAnswerUpdateService = new LiveGameAnswerUpdateService(liveGameId, ((AbstractGameScreen) TournamentGame.getInstance().getAbstractScreen()).getScreenCreator());
        String jsonGameInfo1 = "{\"answer\":\"a\",\"millisAnswered\":1}";
        GameContext gameContext = new GameContextService().createGameContext(currentUser, Collections.singletonList(opponent), gameConfig, TestDataCreator.QUESTION_0);
        GameUser opponentGameUser = gameContext.getOpponentGameUser();
        assertEquals(0, opponentGameUser.getGameQuestionInfo().getAnswers().size());
        liveGameAnswerUpdateService.opponentPressedAnswer(opponentGameUser, jsonGameInfo1, 1);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(1), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION, Integer.class));
        assertEquals(1, opponentGameUser.getGameQuestionInfo().getAnswers().size());

        String jsonGameInfo2 = "{\"answer\":\"b\",\"millisAnswered\":1}";
        liveGameAnswerUpdateService.opponentPressedAnswer(opponentGameUser, jsonGameInfo2, 2);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(2), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION, Integer.class));
        assertEquals(2, opponentGameUser.getGameQuestionInfo().getAnswers().size());
    }

    @Test
    public void updateCurrentUserPressedAnswerToDb() {
        LiveGameService liveGameService = new LiveGameService();
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        liveGameAnswerUpdateService = new LiveGameAnswerUpdateService(liveGameId, ((AbstractGameScreen) TournamentGame.getInstance().getAbstractScreen()).getScreenCreator());
        assertEquals(1, liveGameAnswerUpdateService.getCurrentGameInfoVersion());

        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerToDb(new GameAnswerInfo("a", 1L), TournamentGame.getInstance().getAbstractScreen());
        TestUtils.waitt(1000);
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("a"), 1);
        liveGameDbApiService.updateColumnValue(liveGameId, liveGameService.getOpponentHasCurrentUserGameInfoVersion(liveGameId), 1);
        assertEquals("{\"answer\":\"a\",\"millisAnswered\":1}", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(2, liveGameAnswerUpdateService.getCurrentGameInfoVersion());
        assertEquals(1, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[0]);
        assertEquals(1, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[1]);

        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerToDb(new GameAnswerInfo("b", 12L), TournamentGame.getInstance().getAbstractScreen());
        TestUtils.waitt(1000);
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("b"), 2);
        liveGameDbApiService.updateColumnValue(liveGameId, liveGameService.getOpponentHasCurrentUserGameInfoVersion(liveGameId), 2);
        assertEquals("{\"answer\":\"b\",\"millisAnswered\":12}", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(3, liveGameAnswerUpdateService.getCurrentGameInfoVersion());
        assertEquals(2, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[0]);
        assertEquals(2, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[1]);

        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerToDb(new GameAnswerInfo("c", 21L), TournamentGame.getInstance().getAbstractScreen());
        TestUtils.waitt(1000);
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("c"), 3);
//        liveGameDbApiService.updateColumnValue(liveGameId, liveGameService.getOpponentHasCurrentUserGameInfoVersion(liveGameId), 3);
        assertEquals("{\"answer\":\"c\",\"millisAnswered\":21}", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(4, liveGameAnswerUpdateService.getCurrentGameInfoVersion());
        assertEquals(3, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[0]);
        assertEquals(2, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[1]);

        //update should not be made because the opponent has not the latest gameVersion
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerToDb(new GameAnswerInfo("d", 121L), TournamentGame.getInstance().getAbstractScreen());
        TestUtils.waitt(1000);
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("d"), 4);
        assertEquals("{\"answer\":\"c\",\"millisAnswered\":21}", liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class));
        assertEquals(4, liveGameAnswerUpdateService.getCurrentGameInfoVersion());
        assertEquals(4, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[0]);
        assertEquals(2, liveGameService.getCurrentUserOpponentGameInfoVersion(liveGameId)[1]);
    }


    @Test
    public void updateCurrentUserPressedAnswerFromDb() {
        liveGameAnswerUpdateService = new LiveGameAnswerUpdateService(liveGameId, ((AbstractGameScreen) TournamentGame.getInstance().getAbstractScreen()).getScreenCreator());
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("g"), 1);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(1), Integer.valueOf(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION, Integer.class)));
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("n"), 2);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(2), Integer.valueOf(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION, Integer.class)));
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("o"), 3);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(3), Integer.valueOf(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION, Integer.class)));
        liveGameAnswerUpdateService.updateCurrentUserPressedAnswerFromDb(currentUserGameUser, jsonLetterPressed("c"), 4);
        TestUtils.waitt();
        assertEquals(Integer.valueOf(4), Integer.valueOf(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION, Integer.class)));
    }

    private String jsonLetterPressed(String letter) {
        return new Gson().toJson(new GameAnswerInfo(letter, 1L));
    }
}
