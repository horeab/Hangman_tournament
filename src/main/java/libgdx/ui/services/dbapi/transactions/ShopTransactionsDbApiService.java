package libgdx.ui.services.dbapi.transactions;

import com.google.gson.Gson;

import org.apache.commons.lang3.math.NumberUtils;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.ui.model.shop.ShopTransaction;
import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.dbapi.UniqueDbOperationContainer;
import libgdx.ui.services.game.ShopTransactionNotificationService;
import libgdx.utils.DateUtils;
import libgdx.utils.Utils;

public class ShopTransactionsDbApiService extends DbApiService {

    @DbApiServiceSubmit
    public void createShopTransaction(int userId, ShopTransactionTypeEnum shopTransactionTypeEnum, String uniqueId) {
        if (UniqueDbOperationContainer.getInstance().isDbOperationValid(shopTransactionTypeEnum.name(), uniqueId)) {
            Map<String, Object> params = new HashMap<>();
            params.put(USERID, userId);
            params.put(ENTITYCREATIONDATE, DateUtils.getNowDateString());
            params.put(COINSAMOUNT, String.valueOf(shopTransactionTypeEnum.getTransactionAmountEnum().getCoins()));
            params.put(EXPERIENCEGAIN, String.valueOf(shopTransactionTypeEnum.getExperienceGain()));
            params.put(TRANSACTIONTYPE, shopTransactionTypeEnum.name());
            ShopTransactionNotificationService shopTransactionNotificationService = new ShopTransactionNotificationService(userId, shopTransactionTypeEnum);
            shopTransactionNotificationService.beforeShopTransaction();
            try {
                submitInfoPost("createShopTransaction", formParametersString(params)).join();
                new UserInventoryDbApiService().updateUserInventoryForShopTransactionTypeEnum(userId, shopTransactionTypeEnum, uniqueId);
            } catch (InterruptedException e) {
                //ignore
            }
            shopTransactionNotificationService.afterShopTransaction();
        }
    }

    @DbApiServiceGet
    public int selectTotalAmountShopTransactionType(int userId, ShopTransactionTypeEnum... shopTransactionTypeEnums) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(TRANSACTIONTYPELIST, Arrays.asList(shopTransactionTypeEnums));
        String amount = new Gson().fromJson(getInfoPost("selectTotalAmountShopTransactionType", formParametersString(params)), String.class);
        return NumberUtils.isNumber(amount) ? new Gson().fromJson(amount, Integer.class) : 0;
    }

    @DbApiServiceGet
    int selectTotalCoinsAmountForUser(int userId, ShopTransactionTypeEnum...
            shopTransactionTypeEnums) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(TRANSACTIONTYPELIST, Arrays.asList(shopTransactionTypeEnums));
        String amount = new Gson().fromJson(getInfoPost("selectTotalCoinsAmountForUser", formParametersString(params)), String.class);
        return NumberUtils.isNumber(amount) ? new Gson().fromJson(amount, Integer.class) : 0;
    }

    @DbApiServiceGet
    public List<ShopTransaction> selectShopTransactionsForUser(int userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        return Utils.getListFromJsonString(getInfoPost("selectShopTransactionsForUser", formParametersString(params)), ShopTransaction.class);
    }


    @Override
    protected String getApiModuleName() {
        return "shop";
    }
}
