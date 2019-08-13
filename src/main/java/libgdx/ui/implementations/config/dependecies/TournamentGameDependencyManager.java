package libgdx.ui.implementations.config.dependecies;

import libgdx.game.SubGameDependencyManager;
import libgdx.resources.IncrementingRes;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigFileHandler;
import libgdx.ui.services.questions.QuestionConfigService;
import libgdx.utils.EnumUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public abstract class TournamentGameDependencyManager extends SubGameDependencyManager {

    public abstract QuestionConfigService getQuestionConfigService();

    public abstract <T extends Enum<T>> Class<T> getCampaignLevelTypeEnum();

    public abstract <T extends Enum<T> & QuestionCategory> Class<T> getQuestionCategoryTypeEnum();

    public abstract <T extends Enum<T> & QuestionDifficulty> Class<T> getQuestionDifficultyTypeEnum();

    public abstract List<ImageCategIncrementRes> getImageCategIncrementResList();

    @Override
    protected String allQuestionText() {
        QuestionConfigFileHandler questionConfigFileHandler = new QuestionConfigFileHandler();
        StringBuilder text = new StringBuilder();
        for (QuestionCategory category : (QuestionCategory[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionCategoryTypeEnum())) {
            for (QuestionDifficulty difficultyLevel : (QuestionDifficulty[]) EnumUtils.getValues(TournamentGame.getInstance().getSubGameDependencyManager().getQuestionDifficultyTypeEnum())) {
                Scanner scanner = new Scanner(questionConfigFileHandler.getFileText(difficultyLevel, category));
                while (scanner.hasNextLine()) {
                    text.append(scanner.nextLine());
                }
                scanner.close();
            }
        }
        return text.toString();
    }

    @Override
    public List<? extends IncrementingRes> getIncrementResList() {
        return getImageCategIncrementResList();
    }

    public TournamentGameScreenCreator getTournamentGameScreenCreator() {
        return new TournamentGameScreenCreator();
    }

    public SinglePlayerLevelFinishedService getSinglePlayerLevelFinishedService() {
        return new SinglePlayerLevelFinishedService();
    }

    public MultiPlayerLevelFinishedService getMultiPlayerLevelFinishedService() {
        return new MultiPlayerLevelFinishedService();
    }

    public StarsService getStarsService(int gameTotalNrOfQuestions) {
        return new StarsService(gameTotalNrOfQuestions);
    }

}
