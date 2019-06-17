package libgdx.ui.implementations.config.dependecies;

import java.util.List;

import libgdx.game.SubGameDependencyManager;
import libgdx.resources.IncrementingRes;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.constants.game.question.QuestionDifficulty;
import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.services.questions.ImageCategIncrementRes;
import libgdx.ui.services.questions.QuestionConfigService;

public abstract class TournamentGameDependencyManager extends SubGameDependencyManager {

    public abstract QuestionConfigService getQuestionConfigService();

    public abstract <T extends Enum<T>> Class<T> getCampaignLevelTypeEnum();

    public abstract <T extends Enum<T> & QuestionCategory> Class<T> getQuestionCategoryTypeEnum();

    public abstract <T extends Enum<T> & QuestionDifficulty> Class<T> getQuestionDifficultyTypeEnum();

    public abstract List<ImageCategIncrementRes> getImageCategIncrementResList();

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
