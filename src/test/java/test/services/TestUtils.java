package test.services;

import libgdx.dbapi.DbApiService;

public class TestUtils {

    private static boolean RUNTESTLOCALLY = false;

    public static String formTestParametersString(String parametersString) {
        parametersString = parametersString.substring(0, parametersString.length() - 1);
        if (parametersString.length() > 1) {
            parametersString = parametersString + ",";
        }
        parametersString = parametersString + "\"test\":\"test123\"}";
        return parametersString;
    }

    public static String getBaseUrl() {
        return RUNTESTLOCALLY ? "http://localhost/gameApi/api" : DbApiService.BASE_URL;
    }

    public static void waitt() {
        waitt(100);
    }

    public static void waitt(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.toString();
        }
    }

}
