package libgdx.ui.services.dbapi;

import com.google.gson.Gson;

import org.apache.commons.lang3.math.NumberUtils;


import java.util.HashMap;
import java.util.Map;

import libgdx.utils.DateUtils;

public class UserGamesDbApiService extends DbApiService {

    @DbApiServiceSubmit
    public void createUserGame(int user1Id, int user2Id, String uniqueOperationId) {
        if (UniqueDbOperationContainer.getInstance().isDbOperationValid(user1Id + user2Id + "", uniqueOperationId)) {
            Map<String, Object> params = new HashMap<>();
            params.put(USER1ID, user1Id);
            params.put(USER2ID, user2Id);
            params.put(ENTITYCREATIONDATE, Long.toString(DateUtils.getNowMillis()));
            try {
                submitInfoPost("createUserGame", formParametersString(params)).join();
            }catch (InterruptedException e){
                //ignore
            }
        }
    }

    @DbApiServiceGet
    public int selectTotalGamesUser1AgainstUser2(int user1Id, int user2Id) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER1ID, user1Id);
        params.put(USER2ID, user2Id);
        String amount = new Gson().fromJson(getInfoPost("selectTotalGamesUser1AgainstUser2", formParametersString(params)), String.class);
        return NumberUtils.isCreatable(amount) ? new Gson().fromJson(amount, Integer.class) : 0;
    }

    @Override
    protected String getApiModuleName() {
        return "usergames";
    }
}
