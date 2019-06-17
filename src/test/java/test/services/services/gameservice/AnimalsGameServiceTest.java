package test.services.services.gameservice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.GameIdEnum;

public class AnimalsGameServiceTest extends GameServiceTest {

    @Test
    public void testAllQuestions() throws Exception {
        testQuestions();
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.animals.name();
            }

            @Override
            public String getLanguage() {
                return getAllLang().get(0);
            }
        };
    }

    @Override
    protected List<String> getAllLang() {
//        return super.getAllLang();
        return Arrays.asList("tr");
    }

}
