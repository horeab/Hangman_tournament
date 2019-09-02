package libgdx.ui.services.dbapi;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.ui.constants.game.LiveGameStatusEnum;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.model.game.question.Question;

public class LiveGameDbApiService extends DbApiService {

    public static final String COLUMN_NAME_USER1GAMEINFOVERSION = "user1GameInfoVersion";
    public static final String COLUMN_NAME_USER2GAMEINFOVERSION = "user2GameInfoVersion";
    public static final String COLUMN_NAME_USER1ACTIVEVERSION = "user1ActiveVersion";
    public static final String COLUMN_NAME_USER2ACTIVEVERSION = "user2ActiveVersion";
    public static final String COLUMN_NAME_USER1HASUSER2GAMEINFOVERSION = "user1HasUser2GameInfoVersion";
    public static final String COLUMN_NAME_USER2HASUSER1GAMEINFOVERSION = "user2HasUser1GameInfoVersion";
    public static final String COLUMN_NAME_USER1HASUSER1GAMEINFOVERSION = "user1HasUser1GameInfoVersion";
    public static final String COLUMN_NAME_USER2HASUSER2GAMEINFOVERSION = "user2HasUser2GameInfoVersion";
    public static final String COLUMN_NAME_USER1GAMEINFO = "user1GameInfo";
    public static final String COLUMN_NAME_USER2GAMEINFO = "user2GameInfo";
    public static final String COLUMN_NAME_USER1STATUS = "user1Status";
    public static final String COLUMN_NAME_USER2STATUS = "user2Status";
    public static final String COLUMN_NAME_USER1ID = "user1Id";
    public static final String COLUMN_NAME_USER2ID = "user2Id";
    public static final String COLUMN_NAME_QUESTIONS = "questions";

    protected static final String PARAM_CURRENTUSERHASCURRENTUSERGAMEINFOVERSIONCOLNAME = "currentUserHasCurrentUserGameInfoVersionColName";
    protected static final String PARAM_OPPONENTHASCURRENTUSERGAMEINFOVERSIONCOLNAME = "opponentHasCurrentUserGameInfoVersionColName";
    protected static final String PARAM_GAMEINFOCOLUMNNAME = "gameInfoColumnName";
    protected static final String PARAM_GAMEINFOVERSIONCOLUMNNAME = "gameInfoVersionColumnName";
    protected static final String PARAM_GAMEINFO = "gameInfo";
    protected static final String PARAM_GAMEINFOVERSION = "gameInfoVersion";
    protected static final String PARAM_LIVEGAME_ID = "liveGameId";

    @DbApiServiceSubmit
    public void createLiveGame(int user1Id, int user2Id, Question[] questions, GameConfig gameConfig) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER1ID, user1Id);
        params.put(USER2ID, user2Id);
        params.put(QUESTIONS, new Gson().toJson(questions));
        params.put(GAMECONFIG, new Gson().toJson(gameConfig));
        params.put(INITIALUSERSTATUS, LiveGameStatusEnum.GAME_PENDING.getStatus());
        try {
            submitInfoPost("createLiveGame", formParametersString(params)).join();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    @DbApiServiceSubmit
    public void updateGameInfoAndGameInfoVersion(Integer liveGameId, String gameInfoColumnName, String gameInfo, String gameInfoVersionColumnName, Integer gameInfoVersion) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_LIVEGAME_ID, liveGameId);
        params.put(PARAM_GAMEINFOCOLUMNNAME, gameInfoColumnName);
        params.put(PARAM_GAMEINFO, gameInfo);
        params.put(PARAM_GAMEINFOVERSIONCOLUMNNAME, gameInfoVersionColumnName);
        params.put(PARAM_GAMEINFOVERSION, gameInfoVersion);
        submitInfoPost("updateGameInfoAndGameInfoVersion", formParametersString(params));
    }

    @DbApiServiceGet
    public String getCurrentUserOpponentGameInfoVersion(int liveGameId, String currentUserHasCurrentUserGameInfoVersionColName, String opponentHasCurrentUserGameInfoVersionColName) {
        Map<String, Object> params = new HashMap<>();
        params.put(PARAM_LIVEGAME_ID, liveGameId);
        params.put(PARAM_CURRENTUSERHASCURRENTUSERGAMEINFOVERSIONCOLNAME, currentUserHasCurrentUserGameInfoVersionColName);
        params.put(PARAM_OPPONENTHASCURRENTUSERGAMEINFOVERSIONCOLNAME, opponentHasCurrentUserGameInfoVersionColName);
        return getInfoPost("getCurrentUserOpponentGameInfoVersion", formParametersString(params));
    }

    @DbApiServiceGet
    public LiveGame getLiveGame(int user1Id, int user2Id) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER1ID, user1Id);
        params.put(USER2ID, user2Id);
        LiveGame liveGame = new Gson().fromJson(getInfoPost("getLiveGame", formParametersString(params)), LiveGame.class);
        return liveGame.getUser1Id() != 0 ? liveGame : null;
    }

    @Override
    protected String getApiModuleName() {
        return "livegame";
    }
}
