package test.services.services;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.Collections;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.screens.ChallengeGameScreen;
import libgdx.ui.screens.levelfinished.challenge.AbstractLevelFinishedChallengeScreen;
import libgdx.ui.screens.livegame.PlayerVersusPlayerScreen;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.StartScreenWithCoinsService;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanQuestionCreator;
import libgdx.ui.services.listeners.user.UserExtraInfoListener;
import libgdx.ui.services.questions.RandomQuestionCreatorService;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LiveGameServiceAllListenersTest extends TournamentGameTestDbApiService {

    //sarmale
    private Question[] questions;
    private LiveGameService liveGameService;
    private StartScreenWithCoinsService startScreenWithCoinsService;
    private LiveGameActionsService liveGameActionsService;
    private BaseUserInfo opponent;
    private BaseUserInfo currentUser;

    @Before
    public void setup() throws Exception {
        super.setup();
        GameConfig gameConfig = new GameConfig(new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5, 5), GameTypeStage.challenge_3, DateUtils.getDateString(DateUtils.minusDays(100)));
        RandomCategoryAndDifficulty randomCategoryAndDifficulty = gameConfig.getQuestionConfig().getRandomCategoryAndDifficulty();
        questions = new Question[]{new Question(2, randomCategoryAndDifficulty.getQuestionDifficulty(),randomCategoryAndDifficulty.getQuestionCategory(), "sarmale")};
        screenManager.showMainScreen();
        //Screen change should be wait at least 1500 millis, else test will fail
        TestUtils.waitt(1500);
        usersDbApiService.createUser("528701832", "hhh aaa", AccountCreationSource.FACEBOOK);
        opponent = usersDbApiService.getUser("333111", AccountCreationSource.GOOGLE);
        currentUser = usersDbApiService.getUser("528701832", AccountCreationSource.FACEBOOK);
        liveGameActionsService = Mockito.mock(LiveGameActionsService.class);
        HangmanQuestionCreator questionCreator = Mockito.mock(HangmanQuestionCreator.class);
        PowerMockito.whenNew(LiveGameActionsService.class).withAnyArguments().thenReturn(liveGameActionsService);
        PowerMockito.whenNew(HangmanQuestionCreator.class).withAnyArguments().thenReturn(questionCreator);
        liveGameService = new LiveGameService();
        startScreenWithCoinsService = new StartScreenWithCoinsService(currentUser, gameConfig);
        new UserExtraInfoListener(opponentId, (AbstractScreen) TournamentGame.getInstance().getScreen()).start();
        PowerMockito.whenNew(RandomQuestionCreatorService.class).withAnyArguments().thenReturn(new RandomQuestionCreatorService() {
            @Override
            public Question[] createRandomQuestions(QuestionConfig questionConfig) {
                return questions;
            }
        });
        waitListener();
        liveGameService.createLiveGameWithLiveGameInvite(currentUser, opponentId, gameConfig);
    }

    @Test
    public void test() {
        LiveGame liveGame = liveGameDbApiService.getLiveGame(currentUserId, opponentId);
        int liveGameId = liveGame.getId();
        LiveGameInvite liveGameInvite = new LiveGameInvite(liveGame.getQuestionsArray(), liveGame.getId(), currentUser, new Gson().fromJson(liveGame.getGameConfig(), GameConfig.class));

        waitListener();

        TestUtils.waitt();
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser1Status());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), liveGame.getUser2Status());
        GameConfig gameConfig = new Gson().fromJson(liveGame.getGameConfig(), GameConfig.class);
        verify(liveGameActionsService, times(1)).displayLiveGameChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).displayLiveGameChallenge(any(LiveGameInvite.class), any(AbstractScreen.class));
        verify(liveGameActionsService, times(0)).showChallengePlayerVersusPlayerScreen(any(GameContext.class), anyInt(), any(AbstractScreen.class));

        startScreenWithCoinsService.startChallenge(opponent, questions, liveGameId);
        screenChange();
        //Screen has changed to WaitingForOtherUserScreen
        screenChange();

        liveGameService.acceptChallenge(liveGameInvite, (AbstractScreen) TournamentGame.getInstance().getScreen());
        //Because the update method is mocked I have to call it manually
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.WAITING_GAME_START.getStatus());
        waitListener();
        GameContext gameContext = new GameContextService().createGameContext(currentUser, Collections.singletonList(opponent), gameConfig, liveGame.getQuestionsArray());
        assertEquals(LiveGameStatusEnum.WAITING_GAME_START.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.WAITING_GAME_START.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        verify(liveGameActionsService, times(1)).startChallengeWithCoins(liveGameInvite.getCreatorUser(), questions, liveGameInvite.getLiveGameId(),
                liveGameInvite.getGameConfig(), (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).startChallengeWithCoins(any(BaseUserInfo.class), any(Question[].class), anyInt(), any(GameConfig.class), any(AbstractScreen.class));
        verify(liveGameActionsService, times(2)).showChallengePlayerVersusPlayerScreen(gameContext, liveGameId, (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(2)).showChallengePlayerVersusPlayerScreen(any(GameContext.class), anyInt(), any(AbstractScreen.class));

        screenManager.showChallengePlayerVersusPlayerScreen(gameContext, liveGameId);
        screenChange();
        TestUtils.waitt(Math.round(PlayerVersusPlayerScreen.COUNTDOWN_AMOUNT_MILLIS * 1000 * 1.5f));
        waitListener();

        String question = liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_QUESTIONS, String.class);
        assertEquals("[{\"i\":0,\"l\":2,\"y\":\"_0\",\"t\":\"cat5\",\"s\":\"sarmale\"}]", question);

        screenManager.startGameChallengeScreen(new GameContextService().createGameContext(currentUser, Collections.singletonList(opponent), gameConfig, this.questions),
                liveGameId);
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus());
        screenChange();
        ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).usersAreLoaded();
        waitListener();
        assertEquals(LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));
        verify(liveGameActionsService, times(1)).usersAreLoaded((ChallengeGameScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).usersAreLoaded(any(ChallengeGameScreen.class));
        //first and last letter is pressed
        GameQuestionInfo gameQuestionInfo = ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).getGameContext().getCurrentUserGameUser().getGameQuestionInfo();
        GameAnswerInfo gameAnswerInfo = new Gson().fromJson(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), GameAnswerInfo.class);
        assertNull(gameAnswerInfo);
        assertEquals(this.questions[0], gameQuestionInfo.getQuestion());
        assertEquals(2, gameQuestionInfo.getAnswers().size());
        assertTrue(gameQuestionInfo.getAnswerIds().contains("s"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("e"));
        verify(liveGameActionsService, times(0)).currentUserPressedAnswer(anyString(), anyInt(), any(libgdx.ui.screens.game.ChallengeGameScreen.class));

        pressLetter("x", 1);
        waitListener();
        gameQuestionInfo = ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).getGameContext().getCurrentUserGameUser().getGameQuestionInfo();
        gameAnswerInfo = new Gson().fromJson(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), GameAnswerInfo.class);
        assertEquals("x", gameAnswerInfo.getAnswer());
        assertEquals(this.questions[0], gameQuestionInfo.getQuestion());
        assertEquals(3, gameQuestionInfo.getAnswers().size());
        assertTrue(gameQuestionInfo.getAnswerIds().contains("s"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("e"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("x"));
        verify(liveGameActionsService, times(1)).currentUserPressedAnswer(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), 1, (ChallengeGameScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).currentUserPressedAnswer(anyString(), anyInt(), any(libgdx.ui.screens.game.ChallengeGameScreen.class));
        TestUtils.waitt();

        pressLetter("z", 2);
        TestUtils.waitt();
        waitListener();
        liveGameService.updateUser2GameInfo(liveGameId, "gameInfo2", 1);
        waitListener();
        gameQuestionInfo = ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).getGameContext().getCurrentUserGameUser().getGameQuestionInfo();
        gameAnswerInfo = new Gson().fromJson(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), GameAnswerInfo.class);
        TestUtils.waitt();
        assertEquals("z", gameAnswerInfo.getAnswer());
        assertEquals(this.questions[0], gameQuestionInfo.getQuestion());
        assertEquals(4, gameQuestionInfo.getAnswers().size());
        assertTrue(gameQuestionInfo.getAnswerIds().contains("s"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("e"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("x"));
        assertTrue(gameQuestionInfo.getAnswerIds().contains("z"));
        verify(liveGameActionsService, times(1)).currentUserPressedAnswer(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, String.class), 2, (ChallengeGameScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(2)).currentUserPressedAnswer(anyString(), anyInt(), any(libgdx.ui.screens.game.ChallengeGameScreen.class));
        verify(liveGameActionsService, times(1)).opponentPressedAnswer("gameInfo2", 1, (ChallengeGameScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(1)).opponentPressedAnswer(anyString(), anyInt(), any(libgdx.ui.screens.game.ChallengeGameScreen.class));

        pressLetter("a", 3);
        pressLetter("r", 4);
        pressLetter("m", 5);
        pressLetter("l", 6);
        TestDataCreator.onlyOneLetterPressed(((AbstractGameScreen) TournamentGame.getInstance().getAbstractScreen()).getGameContext().getOpponentGameUser());
        liveGameService.updateUser2GameInfo(liveGameId, new Gson().toJson(gameContext.getOpponentGameUser().getGameQuestionInfo()), 2);
        waitListener();
        assertTrue(TournamentGame.getInstance().getSubGameDependencyManager().getMultiPlayerLevelFinishedService().isGameFinished(((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).getGameContext()));
        //Runnable actions are not executed by tests so I have to call the levelFinished method manually
        ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).executeLevelFinished();
        waitListener();
        assertEquals(LiveGameStatusEnum.GAME_FINISHED.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, Integer.class));
        assertEquals(LiveGameStatusEnum.GAME_FINISHED.getStatus(), liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, Integer.class));

        //Level finished screen is displayed
        screenChange();

        assertEquals(liveGameId, liveGameDbApiService.getLiveGame(currentUserId, opponentId).getId());
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.WANT_MATCH.getStatus());
        waitListener();
        verify(liveGameActionsService, times(1)).startChallengeWithCoins(any(BaseUserInfo.class), any(Question[].class), anyInt(), any(GameConfig.class), any(AbstractScreen.class));

        //new rematch liveGame will be created for the two users
        ((AbstractLevelFinishedChallengeScreen) TournamentGame.getInstance().getScreen()).onPlayAgainButtonClick();
        waitListener();
        LiveGame rematchLiveGame = liveGameDbApiService.getLiveGame(currentUserId, opponentId);
        assertEquals(currentUserId, rematchLiveGame.getUser1Id());
        assertEquals(opponentId, rematchLiveGame.getUser2Id());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), rematchLiveGame.getUser1Status());
        assertEquals(LiveGameStatusEnum.GAME_PENDING.getStatus(), rematchLiveGame.getUser2Status());
        assertNotEquals(liveGameId, rematchLiveGame.getId());
        verify(liveGameActionsService, times(1)).startChallengeWithCoins(opponent, rematchLiveGame.getQuestionsArray(), rematchLiveGame.getId(), rematchLiveGame.getGameConfigObject(), (AbstractScreen) TournamentGame.getInstance().getScreen());
        verify(liveGameActionsService, times(2)).startChallengeWithCoins(any(BaseUserInfo.class), any(Question[].class), anyInt(), any(GameConfig.class), any(AbstractScreen.class));
    }

    private void screenChange() {
        TestUtils.waitt(1500);
        ((AbstractScreen) TournamentGame.getInstance().getScreen()).buildStage();
        ((AbstractScreen) TournamentGame.getInstance().getScreen()).afterBuildStage();
    }

    private void pressLetter(String letter, int version) {
        ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).getScreenCreator().getGameScreenQuestionContainerCreator().buttonClick(letter);
        GameAnswerInfo lastAnswerPressed = new GameAnswerInfo(letter, 1L);
        ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).updateCurrentUserPressedAnswerToDb(lastAnswerPressed);
        ((ChallengeGameScreen) TournamentGame.getInstance().getScreen()).updateCurrentUserPressedAnswerFromDb(new Gson().toJson(lastAnswerPressed), version);
    }

    private void waitListener() {
        int defaultQueryPeriodMillis = 3000;
        TestUtils.waitt(Math.round(defaultQueryPeriodMillis));
    }
}
