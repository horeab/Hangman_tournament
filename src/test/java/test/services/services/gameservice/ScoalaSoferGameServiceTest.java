package test.services.services.gameservice;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import libgdx.ui.constants.game.GameIdEnum;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionCategoryEnum;
import libgdx.ui.implementations.games.scoalasofer.questionconfig.ScoalaSoferQuestionDifficultyLevel;

public class ScoalaSoferGameServiceTest extends GameServiceTest {

    @Test
    public void testAllQuestions() throws Exception {
        testQuestions();
    }

    @Override
    public QuestionDifficulty[] getQuestionDifficulties(QuestionCategory questionCategory) {
        if (Arrays.asList(
                ScoalaSoferQuestionCategoryEnum.cat0.name(),
                ScoalaSoferQuestionCategoryEnum.cat1.name(),
                ScoalaSoferQuestionCategoryEnum.cat2.name(),
                ScoalaSoferQuestionCategoryEnum.cat3.name(),
                ScoalaSoferQuestionCategoryEnum.cat4.name())
                .contains(questionCategory.name())) {
            return new QuestionDifficulty[]{ScoalaSoferQuestionDifficultyLevel._0};
        } else if (Arrays.asList(
                ScoalaSoferQuestionCategoryEnum.cat5.name(),
                ScoalaSoferQuestionCategoryEnum.cat6.name(),
                ScoalaSoferQuestionCategoryEnum.cat7.name(),
                ScoalaSoferQuestionCategoryEnum.cat8.name(),
                ScoalaSoferQuestionCategoryEnum.cat9.name())
                .contains(questionCategory.name())) {
            return new QuestionDifficulty[]{ScoalaSoferQuestionDifficultyLevel._1};
        } else if (Arrays.asList(
                ScoalaSoferQuestionCategoryEnum.cat10.name(),
                ScoalaSoferQuestionCategoryEnum.cat11.name(),
                ScoalaSoferQuestionCategoryEnum.cat12.name(),
                ScoalaSoferQuestionCategoryEnum.cat13.name(),
                ScoalaSoferQuestionCategoryEnum.cat14.name())
                .contains(questionCategory.name())) {
            return new QuestionDifficulty[]{ScoalaSoferQuestionDifficultyLevel._2};
        }
        return super.getQuestionDifficulties(questionCategory);
    }

    @Override
    protected AppInfoServiceImpl getAppInfoService() {
        return new AppInfoServiceImpl() {
            @Override
            public String getGameIdPrefix() {
                return GameIdEnum.scoalasofer.name();
            }
        };
    }

    @Override
    protected List<String> getAllLang() {
        return Arrays.asList("ro");
    }

}
