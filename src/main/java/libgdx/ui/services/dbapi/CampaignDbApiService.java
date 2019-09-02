package libgdx.ui.services.dbapi;

import com.google.gson.Gson;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libgdx.dbapi.DbApiService;
import libgdx.dbapi.DbApiServiceGet;
import libgdx.dbapi.DbApiServiceSubmit;
import libgdx.ui.constants.game.campaign.CampaignLevel;
import libgdx.ui.constants.game.campaign.CampaignLevelStatusEnum;
import libgdx.ui.model.game.CampaignLevelDb;
import libgdx.utils.DateUtils;
import libgdx.utils.Utils;

public class CampaignDbApiService extends DbApiService {

    public static final String COLUMN_NAME_QUESTIONANSWERED = "questionAnswered";
    private static final String COLUMN_NAME_STATUS = "status";

    @DbApiServiceSubmit
    public void createCampaignLevel(int userId, CampaignLevel campaignLevelEnum) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(LEVEL, campaignLevelEnum.getIndex());
        params.put(ENTITYCREATIONDATE, DateUtils.getNowDateString());
        try {
            submitInfoPost("createCampaignLevel", formParametersString(params)).join();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    @DbApiServiceGet
    public List<CampaignLevelDb> getAllCampaignLevels(Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        return Utils.getListFromJsonString(getInfoPost("getAllCampaignLevels", formParametersString(params)), CampaignLevelDb.class);
    }

    @DbApiServiceSubmit
    public Integer getQuestionsAnswered(Integer userId, int level) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(COLUMNNAME, COLUMN_NAME_QUESTIONANSWERED);
        params.put(LEVEL, level);
        return new Gson().<Integer>fromJson(getInfoPost("getColumnValueForUserIdAndLevel", formParametersString(params)), Integer.class);
    }

    @DbApiServiceSubmit
    public void updateQuestionsAnswered(Integer userId, int level, int questionAnswered) {
        updateColumnValueForUserIdAndLevel(userId, level, questionAnswered, COLUMN_NAME_QUESTIONANSWERED);
    }

    @DbApiServiceSubmit
    public void updateStatus(Integer userId, int level, CampaignLevelStatusEnum campaignLevelStatusEnum) {
        updateColumnValueForUserIdAndLevel(userId, level, campaignLevelStatusEnum.getStatus(), COLUMN_NAME_STATUS);
    }

    private <TType> void updateColumnValueForUserIdAndLevel(Integer userId, int level, TType val, String columnName) {
        Map<String, Object> params = new HashMap<>();
        params.put(USERID, userId);
        params.put(COLUMNNAME, columnName);
        params.put(COLUMNVALUE, val);
        params.put(LEVEL, level);
        try {
            submitInfoPost("updateColumnValueForUserIdAndLevel", formParametersString(params)).join();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    @Override
    protected String getApiModuleName() {
        return "campaign";
    }
}
