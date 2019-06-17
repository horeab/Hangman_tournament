package libgdx.ui.services.dbapi;

public class UtilDbService extends DbApiService {

    @Override
    protected String getApiModuleName() {
        return "util";
    }

    public boolean verifyServer() {
        return "valid".equals(getInfoPost("verifyServer", ""));
    }
}
