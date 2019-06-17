package test.services.services.gameservice;

import org.junit.Test;

import java.util.List;

import libgdx.ui.constants.game.GameIdEnum;

public class CapitalsGameServiceTest extends GameServiceTest {

    @Test
    public void testAllQuestions() throws Exception {
        testQuestions();
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.capitals.name();
            }
        };
    }

    @Override
    protected List<String> getAllLang() {
        return super.getAllLang();
//        return Arrays.asList("tr");
    }

}
