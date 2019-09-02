package libgdx.ui.services.game.livegame;


import com.google.gson.Gson;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.game.extrainfo.ExtraInfoItem;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.dbapi.LiveGameDbApiService;
import libgdx.ui.services.game.UserExtraInfoService;
import libgdx.ui.services.questions.RandomQuestionCreatorService;
import libgdx.utils.DateUtils;

public class LiveGameService {

    private Boolean isUser1LoggedIn;

    private LiveGameDbApiService liveGameDbApiService = new LiveGameDbApiService();
    private LiveGameActionsService liveGameActionsService = new LiveGameActionsService();
    private UserExtraInfoService userExtraInfoService = new UserExtraInfoService();

    public LiveGameService(boolean isUser1LoggedIn) {
        this.isUser1LoggedIn = isUser1LoggedIn;
    }

    public LiveGameService() {
    }

    public LiveGame createLiveGame(int user1Id, int user2Id, GameConfig gameConfig) {
        liveGameDbApiService.createLiveGame(user1Id, user2Id, new RandomQuestionCreatorService().createRandomQuestions(gameConfig.getQuestionConfig()), gameConfig);
        LiveGame liveGame = liveGameDbApiService.getLiveGame(user1Id, user2Id);
        int tries = 2;
        while (liveGame == null && tries > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //ignore
            }
            liveGame = liveGameDbApiService.getLiveGame(user1Id, user2Id);
            tries--;
        }
        if (tries == 0) {
            return createLiveGame(user1Id, user2Id, gameConfig);
        }
        return liveGame;
    }

    public LiveGame createLiveGameWithLiveGameInvite(BaseUserInfo user1, int user2Id, GameConfig gameConfig) {
        LiveGame liveGame = createLiveGame(user1.getId(), user2Id, gameConfig);
        try {
            LiveGameInvite liveGameInvite = new LiveGameInvite(liveGame.getQuestionsArray(), liveGame.getId(), user1, gameConfig);
            userExtraInfoService.addExtraInfoItem(user2Id, new ExtraInfoItem(DateUtils.getNowMillis(), liveGameInvite, true));
            return liveGame;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void updateUser1GameInfo(Integer liveGameId, String gameInfo, int currentVersion) {
        liveGameDbApiService.updateGameInfoAndGameInfoVersion(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFO, gameInfo, LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION, currentVersion);
    }

    public void updateUser2GameInfo(Integer liveGameId, String gameInfo, int currentVersion) {
        liveGameDbApiService.updateGameInfoAndGameInfoVersion(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFO, gameInfo, LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, currentVersion);
    }

    public void updateCurrentUserHasOpponentGameInfoVersion(Integer liveGameId, int version) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserHasOpponentGameInfoVersion(liveGameId), version);
    }

    public void updateCurrentUserHasCurrentUserGameInfoVersion(Integer liveGameId, int version) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserHasCurrentUserGameInfoVersion(liveGameId), version);
    }

    public void wantRematch(Integer liveGameId) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserStatusField(liveGameId), LiveGameStatusEnum.WANT_MATCH.getStatus());
    }

    public void abandonMatch(Integer liveGameId) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserStatusField(liveGameId), LiveGameStatusEnum.REJECT_MATCH.getStatus());
    }

    public void updateUserActiveChange(Integer liveGameId) {
        liveGameDbApiService.incrementColumnValue(liveGameId, getCurrentUserActiveChangeField(liveGameId));
    }

    public void deleteLiveGame(Integer liveGameId) {
        liveGameDbApiService.deleteEntity(liveGameId);
    }

    public void deleteLiveGame(int user1Id, int user2Id) {
        LiveGame liveGame = liveGameDbApiService.getLiveGame(user1Id, user2Id);
        if (liveGame != null) {
            deleteLiveGame(liveGame.getId());
        }
    }

    public void startGame(Integer liveGameId) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserStatusField(liveGameId), LiveGameStatusEnum.GAME_IN_PROGRESS.getStatus());
    }

    public int getCurrentUserStatus(Integer liveGameId) {
        return liveGameDbApiService.getColumnValue(liveGameId, getCurrentUserStatusField(liveGameId), Integer.class);
    }

    public int getOpponentStatus(Integer liveGameId) {
        return liveGameDbApiService.getColumnValue(liveGameId, getOpponentStatusField(liveGameId), Integer.class);
    }

    public void rejectChallenge(Integer liveGameId) {
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, LiveGameStatusEnum.REJECT_MATCH.getStatus());
    }

    public void acceptChallenge(LiveGameInvite liveGameInvite, AbstractScreen screen) {
        if (liveGameDbApiService.getColumnValue(liveGameInvite.getLiveGameId(), LiveGameDbApiService.COLUMN_NAME_USER1ID, Integer.class) != null) {
            liveGameActionsService.startChallengeWithCoins(liveGameInvite.getCreatorUser(), liveGameInvite.getQuestions(), liveGameInvite.getLiveGameId(), liveGameInvite.getGameConfig(), screen);
        }
    }

    public void waitingGameStart(int liveGameId) {
        liveGameDbApiService.updateColumnValue(liveGameId, getCurrentUserStatusField(liveGameId), LiveGameStatusEnum.WAITING_GAME_START.getStatus());
    }

    public void gameFinished(Integer liveGameId) {
        updateUsersStatus(liveGameId, LiveGameStatusEnum.GAME_FINISHED);
    }

    public int getCurrentUserHasCurrentUserGameInfoVersionValue(Integer liveGameId) {
        return liveGameDbApiService.getColumnValue(liveGameId, getCurrentUserHasCurrentUserGameInfoVersion(liveGameId), Integer.class);
    }

    public int[] getCurrentUserOpponentGameInfoVersion(Integer liveGameId) {
        int[] result = new int[2];
        String versions = liveGameDbApiService.getCurrentUserOpponentGameInfoVersion(liveGameId, getCurrentUserHasCurrentUserGameInfoVersion(liveGameId), getOpponentHasCurrentUserGameInfoVersion(liveGameId));
        String[] split = versions.split(":");
        result[0] = new Gson().fromJson(split[0], Integer.class);
        result[1] = new Gson().fromJson(split[1], Integer.class);
        return result;
    }

    private String getCurrentUserHasCurrentUserGameInfoVersion(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION : LiveGameDbApiService.COLUMN_NAME_USER2HASUSER2GAMEINFOVERSION;
    }

    private String getCurrentUserHasOpponentGameInfoVersion(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION : LiveGameDbApiService.COLUMN_NAME_USER2HASUSER1GAMEINFOVERSION;
    }

    public String getOpponentHasCurrentUserGameInfoVersion(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER2HASUSER1GAMEINFOVERSION : LiveGameDbApiService.COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION;
    }

    public String getOpponentActiveChangeField(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER2ACTIVEVERSION : LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION;
    }

    public String getOpponentStatusField(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER2STATUS : LiveGameDbApiService.COLUMN_NAME_USER1STATUS;
    }

    private String getCurrentUserActiveChangeField(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER1ACTIVEVERSION : LiveGameDbApiService.COLUMN_NAME_USER2ACTIVEVERSION;
    }

    public String getCurrentUserStatusField(Integer liveGameId) {
        return isUser1LoggedIn(liveGameId) ? LiveGameDbApiService.COLUMN_NAME_USER1STATUS : LiveGameDbApiService.COLUMN_NAME_USER2STATUS;
    }

    public Integer getCurrentUserStatusGameInfoVersion(int liveGameId, boolean isUser1LoggedIn) {
        return liveGameDbApiService.getColumnValue(liveGameId, isUser1LoggedIn ? LiveGameDbApiService.COLUMN_NAME_USER1GAMEINFOVERSION : LiveGameDbApiService.COLUMN_NAME_USER2GAMEINFOVERSION, Integer.class);
    }

    public boolean isUser1LoggedIn(Integer liveGameId) {
        if (isUser1LoggedIn == null) {
            isUser1LoggedIn = Integer.valueOf(TournamentGame.getInstance().getCurrentUser().getId()).equals(liveGameDbApiService.getColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1ID, Integer.class));
        }
        return isUser1LoggedIn;
    }

    private void updateUsersStatus(int liveGameId, LiveGameStatusEnum statusEnum) {
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER1STATUS, statusEnum.getStatus());
        liveGameDbApiService.updateColumnValue(liveGameId, LiveGameDbApiService.COLUMN_NAME_USER2STATUS, statusEnum.getStatus());
    }
}
