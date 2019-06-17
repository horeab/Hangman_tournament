package libgdx.ui.screens.game.creator;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.resources.dimen.MainDimen;
import libgdx.ui.screens.game.creator.service.graphicschange.QuestionFinishedOperationsService;
import libgdx.ui.screens.game.creator.utils.headeranimation.HeaderAnimationService;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenQuestionContainerCreator;
import libgdx.ui.model.game.question.Question;
import libgdx.ui.screens.game.userinfoheader.ScreenPlayerHeaderContainer;
import libgdx.utils.ScreenDimensionsManager;

public abstract class AbstractGameScreenCreator<TGameScreen extends AbstractGameScreen, TGameScreenQuestionContainerCreator extends AbstractGameScreenQuestionContainerCreator> {

    private TGameScreenQuestionContainerCreator gameScreenQuestionContainerCreator;
    private AbstractGameScreenBackgroundCreator gameScreenBackgroundCreator;
    private QuestionFinishedOperationsService questionFinishedOperationsService;
    private TGameScreen gameScreen;
    private GameContext gameContext;

    public AbstractGameScreenCreator(TGameScreen gameScreen, GameContext gameContext) {
        this.gameScreen = gameScreen;
        this.gameContext = gameContext;
        this.questionFinishedOperationsService = new QuestionFinishedOperationsService(gameScreen);
        this.gameScreenBackgroundCreator = gameContext.getCurrentUserCreatorDependencies().getGameScreenBackgroundCreator(gameScreen, gameContext.getCurrentUserGameUser());
    }

    public QuestionFinishedOperationsService getQuestionFinishedOperationsService() {
        return questionFinishedOperationsService;
    }

    public void create() {
        gameScreenBackgroundCreator.createBackground();
        gameScreenQuestionContainerCreator = createGameScreenQuestionContainerCreator();
        float questionContainerWidth = ScreenDimensionsManager.getScreenWidthValue(95);
        Table allContent = new Table();
        allContent.add(ScreenPlayerHeaderContainer.getPlayerHeaderContainer(gameScreen)).padTop(MainDimen.vertical_general_margin.getDimen() / 2).width(questionContainerWidth).row();
        allContent.add(gameScreenQuestionContainerCreator.create()).width(questionContainerWidth).growY().row();
        allContent.setWidth(ScreenDimensionsManager.getScreenWidth());
        allContent.setHeight(ScreenDimensionsManager.getScreenHeight());
        gameScreen.addActor(allContent);
        beforeStartGame();
    }

    protected TGameScreen getGameScreen() {
        return gameScreen;
    }

    public GameUser getCurrentUserGameUser() {
        return gameContext.getCurrentUserGameUser();
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public Question getQuestion() {
        return gameContext.getCurrentUserGameUser().getGameQuestionInfo().getQuestion();
    }

    public HeaderAnimationService getOpponentHeaderAnimationService() {
        return getGameScreenQuestionContainerCreator().getOpponentHeaderAnimationService();
    }

    public TGameScreenQuestionContainerCreator getGameScreenQuestionContainerCreator() {
        return gameScreenQuestionContainerCreator;
    }

    protected void beforeStartGame() {
        gameScreenQuestionContainerCreator.beforeStartGame();
        HeaderAnimationService opponentHeaderAnimationService = getGameScreenQuestionContainerCreator().getOpponentHeaderAnimationService();
        if (opponentHeaderAnimationService != null) {
            opponentHeaderAnimationService.gameInfoChanged();
        }
        getGameScreenQuestionContainerCreator().getCurrentUserHeaderAnimationService().gameInfoChanged();
        getGameScreenQuestionContainerCreator().processGameInfo(getCurrentUserGameUser().getGameQuestionInfo());
    }

    protected abstract TGameScreenQuestionContainerCreator createGameScreenQuestionContainerCreator();

}
