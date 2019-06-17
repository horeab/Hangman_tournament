package test.services.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.services.game.constants.ConstantsService;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ConstantsServiceTest extends TournamentGameTestDbApiService {

    private ConstantsService constantsService;

    @Before
    public void setup() throws Exception {
        super.setup();
        constantsService = new ConstantsService();
    }

    @Test
    public void loadConstantInMemoryNewVersion() {
        Mockito.reset(preferences);
        Mockito.reset(preferencesService);
        Mockito.when(preferencesService.getPreferences()).thenReturn(preferences);
        Mockito.when(preferencesService.getPreferences().getInteger("TransactionAmountEnum_VERSION")).thenReturn(0);
        String overridenConstant = "[{\"name\":\"WIN_WATCH_VIDEO_AD\",\"coins\":\"155\",\"diamond\":\"11\"}, {\"name\":\"xWIN_xWATCH_VIDEO_AD\",\"coins\":\"150\",\"diamond\":\"10\"}, {\"name\":\"WIN_RATE_GAME\",\"diamond\":\"20\"}]";
        Mockito.when(preferencesService.getPreferences().getString("TransactionAmountEnum_VALUE")).thenReturn(overridenConstant);

        constantsService.loadConstantsInMemory();

        assertEquals(155, TransactionAmountEnum.WIN_WATCH_VIDEO_AD.getCoins());
        assertEquals(11, TransactionAmountEnum.WIN_WATCH_VIDEO_AD.getDiamond());

        verify(preferencesService, times(1)).putInteger("TransactionAmountEnum_VERSION", 1);
        verify(preferencesService, times(1)).putString("TransactionAmountEnum_VALUE", overridenConstant);
        Mockito.when(preferencesService.getPreferences().getInteger("TransactionAmountEnum_VERSION")).thenReturn(1);
        TestUtils.waitt();

        constantsService.loadConstantsInMemory();

        verify(preferencesService, times(1)).putInteger("TransactionAmountEnum_VERSION", 1);
        verify(preferencesService, times(1)).putString("TransactionAmountEnum_VALUE", overridenConstant);
        verify(preferencesService, times(1)).putInteger(Mockito.anyString(), Mockito.anyInt());
        verify(preferencesService, times(1)).putString(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void loadConstantInMemorySameVersion() {
        Mockito.reset(preferences);
        Mockito.reset(preferencesService);
        Mockito.when(preferencesService.getPreferences()).thenReturn(preferences);
        Mockito.when(preferencesService.getPreferences().getInteger("TransactionAmountEnum_VERSION")).thenReturn(1);
        String overridenConstant = "[{\"name\":\"WIN_WATCH_VIDEO_AD\",\"coins\":\"155\",\"diamond\":\"11\"}, {\"name\":\"xWIN_xWATCH_VIDEO_AD\",\"coins\":\"150\",\"diamond\":\"10\"}, {\"name\":\"WIN_RATE_GAME\",\"diamond\":\"20\"}]";
        Mockito.when(preferencesService.getPreferences().getString("TransactionAmountEnum_VALUE")).thenReturn(overridenConstant);

        constantsService.loadConstantsInMemory();

        assertEquals(155, TransactionAmountEnum.WIN_WATCH_VIDEO_AD.getCoins());
        assertEquals(11, TransactionAmountEnum.WIN_WATCH_VIDEO_AD.getDiamond());

        verify(preferencesService, times(0)).putInteger(Mockito.anyString(), Mockito.anyInt());
        verify(preferencesService, times(0)).putString(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void createJsonString() {
        constantsService.createJsonString(TransactionAmountEnum.class);
    }

}
