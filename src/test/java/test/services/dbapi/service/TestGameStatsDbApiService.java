package test.services.dbapi.service;

import java.util.Map;

import libgdx.ui.services.dbapi.GameStatsDbApiService;
import test.services.TestUtils;

public class TestGameStatsDbApiService extends GameStatsDbApiService {

    @Override
    public String formParametersString(Map<String, Object> params) {
        return TestUtils.formTestParametersString(super.formParametersString(params));
    }

    @Override
    public String getBaseUrl() {
        return TestUtils.getBaseUrl();
    }

}
