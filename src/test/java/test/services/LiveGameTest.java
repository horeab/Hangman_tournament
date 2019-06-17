package test.services;

import com.google.gson.Gson;

import org.junit.Before;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;

import test.TournamentGameTestDbApiService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gamescreens.PracticeOptionsScreen;
import libgdx.ui.services.game.livegame.LiveGameActionsService;
import libgdx.ui.services.game.livegame.LiveGameService;
import libgdx.utils.DateUtils;
import libgdx.ui.util.TestDataCreator;

public class LiveGameTest extends TournamentGameTestDbApiService {

    protected LiveGameService liveGameService;
    protected LiveGame liveGame;
    protected LiveGameInvite liveGameInvite;
    protected int liveGameId;
    protected LiveGameActionsService liveGameActionsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        liveGameActionsService = Mockito.mock(LiveGameActionsService.class);
        PowerMockito.whenNew(LiveGameActionsService.class).withAnyArguments().thenReturn(liveGameActionsService);
        liveGameService = new LiveGameService();
        liveGameService.createLiveGame(opponent.getId(), currentUserId, getGameConfig());
        TestUtils.waitt();
        liveGame = liveGameDbApiService.getLiveGame(opponentId, currentUserId);
        liveGameId = liveGame.getId();
        liveGameInvite = new LiveGameInvite(liveGame.getQuestionsArray(), liveGame.getId(), this.opponent, new Gson().fromJson(liveGame.getGameConfig(), GameConfig.class));

        TournamentGame.getInstance().getScreenManager().startGameChallengeScreen(TestDataCreator.createEmptyGameContext(new GameConfig(PracticeOptionsScreen.getDefaultQuestionConfig(), GameTypeStage.challenge_1, DateUtils.getNowDateString()), new ArrayList<>()), liveGameId);
        //Screen change should be wait at least 1500 millis, else test will fail
        TestUtils.waitt(1500);
    }

    @Override
    public GameConfig getGameConfig() {
        return new GameConfig(new QuestionConfig(HangmanArenaQuestionDifficultyLevel._3, HangmanArenaQuestionCategoryEnum.cat5, 5), GameTypeStage.challenge_3, DateUtils.getDateString(DateUtils.minusDays(100)));
    }
}
