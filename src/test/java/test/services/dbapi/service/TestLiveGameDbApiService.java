package test.services.dbapi.service;

import java.util.Map;

import libgdx.ui.services.dbapi.LiveGameDbApiService;
import test.services.TestUtils;

public class TestLiveGameDbApiService extends LiveGameDbApiService {

    @Override
    public String formParametersString(Map<String, Object> params) {
        return TestUtils.formTestParametersString(super.formParametersString(params));
    }

    @Override
    public String getBaseUrl() {
        return TestUtils.getBaseUrl();
    }

}
