package libgdx.ui.services.dbapi;

import com.google.gson.Gson;

import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.money.ShopTransactionTypeEnum;
import libgdx.constants.user.AccountCreationSource;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.services.dbapi.transactions.ShopTransactionsDbApiService;
import libgdx.ui.services.dbapi.transactions.UserInventoryDbApiService;
import libgdx.utils.DateUtils;
import libgdx.utils.Utils;

public class UsersDbApiService extends libgdx.dbapi.UsersDbApiService {

    public static final String COLUMN_NAME_EXTRAINFO = "extraInfo";
    public static final String COLUMN_NAME_EXTRAINFOCHANGE = "extraInfoChange";


    @DbApiServiceSubmit
    public synchronized int createUser(String externalId, String fullName, AccountCreationSource accountCreationSource) {
        int createdUserId = super.createUser(externalId, fullName, accountCreationSource);
        if (createdUserId != -1) {
            new UserInventoryDbApiService().createUserInventory(createdUserId);
            new ShopTransactionsDbApiService().createShopTransaction(createdUserId, ShopTransactionTypeEnum.WIN_CREATE_USER, DateUtils.getNowDateString());
        }
        return createdUserId;
    }

}
