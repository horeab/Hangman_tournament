package libgdx.ui.screens.game.screens;

import java.util.Random;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.ScreenWithGameContext;
import libgdx.ui.screens.game.creator.AbstractGameScreenCreator;
import libgdx.ui.implementations.config.dependecies.LevelFinishedService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;

public abstract class AbstractGameScreen<TScreenCreator extends AbstractGameScreenCreator> extends AbstractScreen implements ScreenWithGameContext {

    private GameContext gameContext;
    private TScreenCreator screenCreator;

    public AbstractGameScreen(GameContext gameContext) {
        GameServiceContainer.clearContainer();
        this.gameContext = gameContext;
        gameContext.startMillis();
    }

    public GameUser getCurrentUserGameUser() {
        return gameContext.getCurrentUserGameUser();
    }

    @Override
    public GameContext getGameContext() {
        return gameContext;
    }

    public TScreenCreator getScreenCreator() {
        return screenCreator;
    }

    public GameConfig getGameConfig() {
        return gameContext.getGameConfig();
    }

    @Override
    protected void initFields() {
        super.initFields();
        screenCreator = initScreenCreator(gameContext);
    }

    @Override
    public void buildStage() {
        super.buildStage();
        screenCreator.create();
        modifyStats();
    }

    public void showPopupAd() {
        TournamentGame.getInstance().getAppInfoService().showPopupAd();
    }

    private void modifyStats() {
        gameStatsDbApiService.incrementGameStatsQuestionsStarted(getCurrentUser().getId(), getQuestionUniqueId(getQuestion()));
    }

    private String getQuestionUniqueId(Question question) {
        return getGameConfig().getUniqueId() + question.getQuestionString();
    }

    public Question getQuestion() {
        return gameContext.getQuestion();
    }

    public abstract void goToNextQuestionScreen();

    public abstract void executeLevelFinished();

    protected abstract TScreenCreator initScreenCreator(GameContext gameContext);

    public abstract LevelFinishedService getLevelFinishedService();
}
