package test.services.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import test.TournamentGameTestDbApiService;
import libgdx.utils.DateUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
public class DateUtilsTest {

    @Test
    public void getNowMillis() {
        TournamentGameTestDbApiService.assertDateTimeNow(DateUtils.getNowMillis());
    }

    @Test
    public void getDateString() {
        assertEquals("2018/10/15 16:53:39.123", DateUtils.getDateString(DateUtils.getDate("2018/10/15 16:53:39.123")));
    }

    @Test
    public void getDate() {
        assertEquals(DateUtils.getDate("2018/10/15 16:53:39"), DateUtils.getDate(DateUtils.getDateString(DateUtils.getDate("2018/10/15 16:53:39"))));
    }

    @Test
    public void minusDays() {
        assertEquals("2018/10/13 16:53:39.000", DateUtils.getDateString(DateUtils.minusDays(DateUtils.getDate("2018/10/15 16:53:39"), 2)));
    }

    @Test
    public void plusDays() {
        assertEquals("2018/10/17 16:53:39.000", DateUtils.getDateString(DateUtils.plusDays(DateUtils.getDate("2018/10/15 16:53:39"), 2)));
    }
    @Test
    public void minusMinutes() {
        assertEquals("2018/10/15 16:51:39.000", DateUtils.getDateString(DateUtils.minusMinutes(DateUtils.getDate("2018/10/15 16:53:39"), 2)));
    }

    @Test
    public void minusSeconds() {
        assertEquals("2018/10/15 16:53:37.000", DateUtils.getDateString(DateUtils.minusSeconds(DateUtils.getDate("2018/10/15 16:53:39"), 2)));
    }

    @Test
    public void minusMillis() {
        assertEquals("2018/10/15 16:53:34.000", DateUtils.getDateString(DateUtils.minusMillis(DateUtils.getDate("2018/10/15 16:53:39"), 5000)));
    }

    @Test
    public void afterBefore() {
        assertTrue(DateUtils.getDate("2018/10/15 16:53:39").after(DateUtils.getDate("2018/10/15 16:53:38")));
        assertFalse(DateUtils.getDate("2018/10/15 16:53:39").before(DateUtils.getDate("2018/10/15 16:53:38")));
    }

}
