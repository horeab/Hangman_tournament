package test.services.dbapi.service;

import java.util.Map;

import libgdx.ui.services.dbapi.UserGamesDbApiService;
import test.services.TestUtils;

public class TestUserGamesDbApiService extends UserGamesDbApiService {

    @Override
    public String formParametersString(Map<String, Object> params) {
        return TestUtils.formTestParametersString(super.formParametersString(params));
    }

    @Override
    public String getBaseUrl() {
        return TestUtils.getBaseUrl();
    }

}
