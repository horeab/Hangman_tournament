package libgdx.ui.services.dbapi.transactions;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.DiamondEnum;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.model.inventory.ExperienceWithUser;
import libgdx.ui.model.inventory.UserInventory;
import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.dbapi.UniqueDbOperationContainer;
import libgdx.ui.services.game.diamond.DiamondService;
import libgdx.utils.Utils;

public class UserInventoryDbApiService extends DbApiService {

    private static final String COLUMN_NAME_COINS = "coins";
    private static final String COLUMN_NAME_DIAMOND = "diamond";
    private static final String COLUMN_NAME_EXPERIENCE = "experience";

    private ShopTransactionsDbApiService shopTransactionsDbApiService = new ShopTransactionsDbApiService();

    @DbApiServiceSubmit
    private Thread incrementTotalCoins(int userId, int amount, String dbOperationId, String uniqueId) {
        Thread thread = new Thread();
        if (UniqueDbOperationContainer.getInstance().isDbOperationValid(dbOperationId, uniqueId)) {
            thread = incrementColumnValueForUserId(userId, COLUMN_NAME_COINS, amount);
        }
        return thread;
    }

    @DbApiServiceSubmit
    private Thread incrementTotalDiamonds(int userId, int amount, String dbOperationId, String uniqueId) {
        Thread thread = new Thread();
        if (UniqueDbOperationContainer.getInstance().isDbOperationValid(dbOperationId, uniqueId)) {
            thread = incrementColumnValueForUserId(userId, COLUMN_NAME_DIAMOND, amount);
        }
        return thread;
    }

    @DbApiServiceSubmit
    private Thread incrementExperience(int userId, int amount, String dbOperationId, String uniqueId) {
        Thread thread = new Thread();
        if (UniqueDbOperationContainer.getInstance().isDbOperationValid(dbOperationId, uniqueId)) {
            thread = incrementColumnValueForUserId(userId, COLUMN_NAME_EXPERIENCE, amount);
        }
        return thread;
    }

    @DbApiServiceGet
    public int getTotalCoins(int userId) {
        return getColumnValueForUserId(userId, COLUMN_NAME_COINS);
    }

    @DbApiServiceGet
    public int getExperience(int userId) {
        return getColumnValueForUserId(userId, COLUMN_NAME_EXPERIENCE);
    }

    @DbApiServiceGet
    public int getTotalDiamonds(int userId) {
        return getColumnValueForUserId(userId, COLUMN_NAME_DIAMOND);
    }

    @DbApiServiceGet
    public LinkedHashSet<ExperienceWithUser> selectLeaderboardExperience(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(GAMEID, TournamentGame.getInstance().getGameId());
        params.put(USERID, userId);
        return new LinkedHashSet<ExperienceWithUser>(Utils.getListFromJsonString(getInfoPost("selectLeaderboardExperience", formParametersString(params)), ExperienceWithUser.class));
    }

    @DbApiServiceGet
    public UserInventory getUserInventory(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        UserInventory userInventory = new Gson().fromJson(getInfoPost("getUserInventory", formParametersString(params)), UserInventory.class);
        if (userInventory != null && userInventory.getUserId() != 0) {
            return userInventory;
        } else {
            createUserInventory(userId);
            return getUserInventory(userId);
        }
    }

    @DbApiServiceSubmit
    public void createUserInventory(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        if ("1".equals(getInfoPost("createUserInventory", formParametersString(params)))) {
            updateColumnValueForUserId(userId, COLUMN_NAME_COINS, shopTransactionsDbApiService.selectTotalCoinsAmountForUser(userId));
            updateColumnValueForUserId(userId, COLUMN_NAME_DIAMOND, new DiamondService(userId).getTotalDiamonds());
        }
    }

    private synchronized Thread incrementColumnValueForUserId(Integer userId, String columnName, Integer valueToIncrement) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(COLUMNNAME, columnName);
        params.put(VALUETOINCREMENT, valueToIncrement);
        return submitInfoPost("incrementColumnValueForUserId", formParametersString(params));
    }

    private int getColumnValueForUserId(int userId, String columnName) {
        Integer columnValue = getColumnValueForUserId(userId, columnName, Integer.class);
        return columnValue != null ? columnValue : 0;
    }

    public void updateUserInventoryForShopTransactionTypeEnum(int userId, ShopTransactionTypeEnum shopTransactionTypeEnum, String uniqueId) {
        int coins = shopTransactionTypeEnum.getTransactionAmountEnum().getCoins();
        int experience = shopTransactionTypeEnum.getExperienceGain();
        String dbOperationId = "updateUserInventory" + shopTransactionTypeEnum.name();
        try {
            if (coins != 0) {
                incrementTotalCoins(userId, coins, "coins" + dbOperationId, uniqueId).join();
            }
            if (experience != 0) {
                incrementExperience(userId, experience, "experience" + dbOperationId, uniqueId).join();
            }
            updateDiamonds(userId, shopTransactionTypeEnum, dbOperationId, uniqueId);
        } catch (InterruptedException e) {
            //ignore
        }
        processDiamondService(userId, shopTransactionTypeEnum.getTransactionAmountEnum().getDiamond(), uniqueId);
    }

    private void processDiamondService(int userId, int diamondAmount, String uniqueId) {
        DiamondService diamondService = new DiamondService(userId);
        if (diamondAmount > 0) {
            for (int i = 0; i < diamondAmount; i++) {
                diamondService.winDiamond(uniqueId + i);
            }
        } else if (diamondAmount < 0) {
            for (int i = 0; i < Math.abs(diamondAmount); i++) {
                diamondService.payDiamond(uniqueId + i);
            }
        }
    }

    private void updateDiamonds(int userId, ShopTransactionTypeEnum shopTransactionTypeEnum, String dbOperationId, String uniqueId) throws InterruptedException {
        for (DiamondEnum diamondEnum : DiamondEnum.values()) {
            if (diamondEnum.getWinTransaction() == shopTransactionTypeEnum) {
                incrementTotalDiamonds(userId, 1, diamondEnum.name() + dbOperationId, uniqueId).join();
            } else if (diamondEnum.getPayTransaction() == shopTransactionTypeEnum) {
                incrementTotalDiamonds(userId, -1, diamondEnum.name() + dbOperationId, uniqueId).join();
                ;
            }
        }
    }

    @Override
    protected String getApiModuleName() {
        return "userinventory";
    }
}
