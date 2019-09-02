package libgdx.ui.services.dbapi;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.ui.model.game.ConstantDb;
import libgdx.ui.model.game.ConstantKeyNameVersionDb;
import libgdx.utils.Utils;

public class ConstantsDbApiService extends DbApiService {

    @DbApiServiceGet
    public String getValue(String key) {
        Map<String, Object> params = new HashMap<>();
        params.put(KEYNAME, key);
        return new Gson().fromJson(getInfoPost("getValue", formParametersString(params)), String.class);
    }

    @DbApiServiceGet
    public List<ConstantDb> getAllConstants() {
        return Utils.getListFromJsonString(getInfoPost("getAllConstants", formParametersString(new HashMap<String, Object>())), ConstantDb.class);
    }

    @DbApiServiceGet
    public List<ConstantKeyNameVersionDb> getAllKeyWithVersion() {
        return Utils.getListFromJsonString(getInfoPost("getAllKeyWithVersion", formParametersString(new HashMap<String, Object>())), ConstantKeyNameVersionDb.class);
    }

    @Override
    protected String getApiModuleName() {
        return "constants";
    }
}
