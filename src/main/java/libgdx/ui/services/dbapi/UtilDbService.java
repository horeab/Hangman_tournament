package libgdx.ui.services.dbapi;

import libgdx.dbapi.DbApiService;

public class UtilDbService extends DbApiService {

    @Override
    protected String getApiModuleName() {
        return "util";
    }

    public boolean verifyServer() {
        return "valid".equals(getInfoPost("verifyServer", ""));
    }
}
