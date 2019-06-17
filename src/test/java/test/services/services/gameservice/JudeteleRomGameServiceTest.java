package test.services.services.gameservice;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.GameIdEnum;

public class JudeteleRomGameServiceTest extends GameServiceTest {

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @Test
    public void testAllQuestions() throws Exception {
        testQuestions();
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.judetelerom.name();
            }
        };
    }

    @Override
    protected List<String> getAllLang() {
        return Arrays.asList("ro");
    }

}
