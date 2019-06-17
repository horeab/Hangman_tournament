package test.services.dbapi.service;

import java.util.Map;

import libgdx.ui.services.dbapi.CampaignDbApiService;
import libgdx.ui.services.dbapi.ConstantsDbApiService;
import test.services.TestUtils;

public class TestCampaignDbApiService extends CampaignDbApiService {

    @Override
    public String formParametersString(Map<String, Object> params) {
        return TestUtils.formTestParametersString(super.formParametersString(params));
    }

    @Override
    public String getBaseUrl() {
        return TestUtils.getBaseUrl();
    }

}
