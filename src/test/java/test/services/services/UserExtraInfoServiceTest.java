package test.services.services;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import test.TournamentGameTestDbApiService;
import libgdx.ui.constants.game.GameTypeStage;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionCategoryEnum;
import libgdx.ui.implementations.games.hangmanarena.questionconfig.HangmanArenaQuestionDifficultyLevel;
import libgdx.ui.model.game.LiveGame;
import libgdx.ui.model.game.extrainfo.ExtraInfo;
import libgdx.ui.model.game.extrainfo.ExtraInfoItem;
import libgdx.ui.model.game.extrainfo.LiveGameInvite;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.model.game.question.QuestionConfig;
import libgdx.ui.model.game.question.RandomCategoryAndDifficulty;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.services.dbapi.UsersDbApiService;
import libgdx.ui.services.game.UserExtraInfoService;
import libgdx.utils.DateUtils;
import test.services.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserExtraInfoServiceTest extends TournamentGameTestDbApiService {

    private UserExtraInfoService userExtraInfoService;
    private Question[] questions;

    @Before
    public void setup() throws Exception {
        super.setup();
        GameConfig gameConfig = new GameConfig(new QuestionConfig(HangmanArenaQuestionDifficultyLevel._0, HangmanArenaQuestionCategoryEnum.cat5, 5), GameTypeStage.challenge_3, DateUtils.getDateString(DateUtils.minusDays(100)));
        RandomCategoryAndDifficulty randomCategoryAndDifficulty = gameConfig.getQuestionConfig().getRandomCategoryAndDifficulty();
        questions = new Question[]{new Question( 2, randomCategoryAndDifficulty.getQuestionDifficulty(), randomCategoryAndDifficulty.getQuestionCategory(), "sarmale")};
        userExtraInfoService = new UserExtraInfoService();
    }

    @Test
    public void testAll() {
//        DateTime creationDate = DateTime.parse("04/02/2011 20:27:05", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        Date creationDate = new Date();

        String item1Json = "{\"creationDate\":" + creationDate.getTime() + ",\"itemJson\":\"{\\\"liveGameId\\\":1,\\\"questions\\\":[{\\\"l\\\":2,\\\"y\\\":\\\"_0\\\",\\\"t\\\":\\\"cat5\\\",\\\"s\\\":\\\"sarmale\\\"}],\\\"" +
                "creatorUser\\\":{\\\"id\\\":" + opponent.getId() + ",\\\"externalId\\\":\\\"333111\\\",\\\"accountCreationSource\\\":\\\"GOOGLE\\\",\\\"" +
                "fullName\\\":\\\"hhh aaa\\\"},\\\"gameConfig\\\":{\\\"c\\\":{\\\"l\\\":[\\\"_5\\\"],\\\"c\\\":[\\\"cat4\\\"],\\\"a\\\":1},\\\"s\\\":\\\"tournament_1\\\",\\\"q\\\":\\\"1\\\"}}\"," +
                "\"itemTypeToString\":\"class libgdx.ui.model.game.extrainfo.LiveGameInvite\",\"expirable\":true}";
        String item2Json = "{\"creationDate\":" + creationDate.getTime() + ",\"itemJson\":\"{\\\"liveGameId\\\":2,\\\"questions\\\":[{\\\"l\\\":2,\\\"y\\\":\\\"_0\\\",\\\"t\\\":\\\"cat5\\\",\\\"s\\\":\\\"sarmale\\\"}],\\\"creatorUser\\\":{\\\"id\\\":" + opponent.getId() + ",\\\"" +
                "externalId\\\":\\\"333111\\\",\\\"accountCreationSource\\\":\\\"GOOGLE\\\",\\\"fullName\\\":\\\"hhh aaa\\\"},\\\"gameConfig\\\":{\\\"c\\\":{\\\"l\\\":[\\\"_5\\\"],\\\"c\\\":[\\\"cat4\\\"],\\\"a\\\":1},\\\"s\\\":\\\"tournament_1\\\",\\\"q\\\":\\\"1\\\"}}\",\"" +
                "itemTypeToString\":\"class libgdx.ui.model.game.extrainfo.LiveGameInvite\",\"expirable\":true}";

        assertEquals(0, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        assertNull(usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        LiveGameInvite item1 = new LiveGameInvite(questions, 1, opponent, getGameConfig());
        userExtraInfoService.removeExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[]}", usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(0, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        ///////////
        userExtraInfoService.addExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item1Json + "]}", usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(1, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        assertEquals(item1, new ArrayList<>(userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class)).get(0));
        ///////////
        LiveGameInvite item2 = new LiveGameInvite(questions, 2, opponent, getGameConfig());
        userExtraInfoService.addExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item2, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item1Json + "," + item2Json + "]}",
                usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(2, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        assertEquals(item1, new ArrayList<>(userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class)).get(0));
        assertEquals(item2, new ArrayList<>(userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class)).get(1));
        assertEquals(item2, userExtraInfoService.getExtraInfoItem(new ExtraInfoItem(creationDate.getTime(), item2, true), LiveGameInvite.class));
        assertNull(userExtraInfoService.getExtraInfoItem(new ExtraInfoItem(creationDate.getTime(), item2, true), LiveGame.class));
        ///////////
        userExtraInfoService.removeExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item2Json + "]}", usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(1, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        assertEquals(item2, new ArrayList<>(userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class)).get(0));
        ///////////
        userExtraInfoService.removeExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item2Json + "]}", usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        ///////////
        userExtraInfoService.removeExtraInfoItem(opponent.getId(), new Gson().fromJson(usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class), ExtraInfo.class), new ExtraInfoItem(creationDate.getTime(), item2, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[]}", usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(0, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        ///////////
        userExtraInfoService.addExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        userExtraInfoService.addExtraInfoItem(opponent.getId(), new ExtraInfoItem(creationDate.getTime(), item1, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item1Json + "]}",
                usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(1, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
        ///////////
        userExtraInfoService.addExtraInfoItem(opponent.getId(), new ExtraInfoItem(DateUtils.minusMillis(creationDate, UserExtraInfoService.MILLIS_FROM_NOW__EXTRA_INFO_ITEM_EXPIRES + 1000).getTime(),
                item2, true));
        TestUtils.waitt();
        assertEquals("{\"items\":[" + item1Json + "]}",
                usersDbApiService.getColumnValue(opponent.getId(), UsersDbApiService.COLUMN_NAME_EXTRAINFO, String.class));
        assertEquals(1, userExtraInfoService.getExtraInfoItems(opponent.getId(), LiveGameInvite.class).size());
    }

    @Test
    public void itemCreationDateIsExpired() {
        assertFalse(userExtraInfoService.itemCreationDateIsExpired(DateUtils.getNowMillis()));
        assertFalse(userExtraInfoService.itemCreationDateIsExpired(DateUtils.minusMillis(UserExtraInfoService.MILLIS_FROM_NOW__EXTRA_INFO_ITEM_EXPIRES - 1000).getTime()));
        assertTrue(userExtraInfoService.itemCreationDateIsExpired(DateUtils.minusMillis(UserExtraInfoService.MILLIS_FROM_NOW__EXTRA_INFO_ITEM_EXPIRES + 1000).getTime()));
    }

}
