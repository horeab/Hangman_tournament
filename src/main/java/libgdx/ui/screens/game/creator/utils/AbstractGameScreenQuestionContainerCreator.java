package libgdx.ui.screens.game.creator.utils;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import libgdx.ui.constants.game.HintButtonType;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.controls.button.ButtonSkin;
import libgdx.ui.controls.button.HintButton;
import libgdx.controls.button.MyButton;
import libgdx.ui.model.game.GameAnswerInfo;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameQuestionInfo;
import libgdx.ui.model.game.GameUser;
import libgdx.game.model.BaseUserInfo;
import libgdx.ui.screens.game.creator.QuestionContainerCreatorService;
import libgdx.ui.screens.game.creator.service.GameControlsService;
import libgdx.ui.screens.game.creator.service.graphicschange.QuestionFinishedOperationsService;
import libgdx.ui.screens.game.creator.utils.headeranimation.HeaderAnimationService;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.userinfoheader.PlayerHeader;
import libgdx.ui.screens.game.userinfoheader.PlayerHeaderContainer;
import libgdx.ui.screens.game.userinfoheader.ScreenPlayerHeaderContainer;
import libgdx.ui.services.game.gameservice.GameService;
import libgdx.ui.services.game.gameservice.GameServiceContainer;

public abstract class AbstractGameScreenQuestionContainerCreator<TScreen extends AbstractGameScreen, TGameService extends GameService> {

    private TScreen gameScreen;
    private GameContext gameContext;
    private TGameService gameService;
    private HeaderAnimationService opponentHeaderAnimationService;
    private HeaderAnimationService currentUserHeaderAnimationService;
    private AbstractGameScreenBackgroundCreator screenBackgroundCreator;
    private RefreshQuestionDisplayService refreshQuestionDisplayService;
    private QuestionContainerCreatorService questionContainerCreatorService;
    private GameControlsService gameControlsService;

    public AbstractGameScreenQuestionContainerCreator(TScreen screen, GameContext gameContext) {
        this.gameScreen = screen;
        this.gameContext = gameContext;
        GameUser currentUserGameUser = gameContext.getCurrentUserGameUser();
        this.gameService = (TGameService) GameServiceContainer.getGameService(currentUserGameUser.getBaseUserInfo().getId(), currentUserGameUser.getGameQuestionInfo());
        this.screenBackgroundCreator = gameContext.getCurrentUserCreatorDependencies().getGameScreenBackgroundCreator(screen, currentUserGameUser);
        this.questionContainerCreatorService = gameContext.getCurrentUserCreatorDependencies().getQuestionContainerCreatorService(currentUserGameUser, screen);
        this.opponentHeaderAnimationService = createOpponentHeaderAnimationService(gameContext);
        this.currentUserHeaderAnimationService = HeaderAnimationService.createInstance(currentUserGameUser, getPlayerHeaderContainer().getCurrentUserHeader());
        this.refreshQuestionDisplayService = gameContext.getCurrentUserCreatorDependencies().getRefreshQuestionDisplayService(getGameScreen(), questionContainerCreatorService.getAllAnswerButtons());
        this.gameControlsService = new GameControlsService(questionContainerCreatorService.getAllAnswerButtons(), questionContainerCreatorService.getHintButtons());
    }

    public abstract void buttonClick(String answer);

    private HeaderAnimationService createOpponentHeaderAnimationService(GameContext gameContext) {
        return gameContext.getOpponentGameUser() != null ? HeaderAnimationService.createInstance(
                gameContext.getOpponentGameUser(),
                getPlayerHeader(gameContext.getOpponentGameUser().getBaseUserInfo())) : null;
    }

    private PlayerHeader getPlayerHeader(BaseUserInfo baseUserInfo) {
        return getPlayerHeaderContainer().getPlayerHeader(baseUserInfo);
    }

    private PlayerHeaderContainer getPlayerHeaderContainer() {
        return ScreenPlayerHeaderContainer.getPlayerHeaderContainer(getGameScreen());
    }

    public Table create() {
        Table table = new Table();
        table.add(questionContainerCreatorService.createQuestionTable()).grow().row();
        table.add(questionContainerCreatorService.createAnswerOptionsTable()).bottom();
        addOnAnswerClick();
        addOnHintClick();
        return table;
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public HeaderAnimationService getOpponentHeaderAnimationService() {
        return opponentHeaderAnimationService;
    }

    public HeaderAnimationService getCurrentUserHeaderAnimationService() {
        return currentUserHeaderAnimationService;
    }

    private void addOnHintClick() {
        for (final HintButton hintButton : (List<HintButton>) questionContainerCreatorService.getHintButtons()) {
            hintButton.getMyButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    getHintButtonOnClick(hintButton).run();
                    getGameContext().useHint();
                }
            });
        }
    }

    private Runnable getHintButtonOnClick(HintButton hintButton) {
        if (hintButton.getHintButtonType().equals(HintButtonType.HINT_PRESS_RANDOM_ANSWER)) {
            return createRandomAnswerHintButtonOnClick();
        } else if (hintButton.getHintButtonType().equals(HintButtonType.HINT_DISABLE_TWO_ANSWERS)) {
            return createHintDisableTwoAnswersButtonOnClick();
        }
        return new Runnable() {
            @Override
            public void run() {
            }
        };
    }

    private Runnable createRandomAnswerHintButtonOnClick() {
        return new Runnable() {
            @Override
            public void run() {
                String randomUnpressedAnswer = gameService.getRandomUnpressedAnswerFromQuestion(gameContext.getCurrentUserGameUser().getGameQuestionInfo().getAnswerIds());
                if (StringUtils.isNotBlank(randomUnpressedAnswer)) {
                    buttonClick(randomUnpressedAnswer);
                }
            }
        };
    }

    private Runnable createHintDisableTwoAnswersButtonOnClick() {
        return new Runnable() {
            @Override
            public void run() {
                List<String> originalAnswerOptions = gameService.getAllAnswerOptions();
                List<String> answerOptions = new ArrayList<>(originalAnswerOptions);
                answerOptions.removeAll(gameService.getUnpressedCorrectAnswers(gameContext.getCurrentUserGameUser().getGameQuestionInfo().getAnswerIds()));
                List<String> answersToDisable = new ArrayList<>();
                if (answerOptions.size() > 0 && originalAnswerOptions.size() > 2) {
                    answersToDisable.add(answerOptions.get(0));
                }
                if (answerOptions.size() > 1 && originalAnswerOptions.size() > 3) {
                    answersToDisable.add(answerOptions.get(1));
                }
                for (String answer : answersToDisable) {
                    gameControlsService.disableButton((MyButton) questionContainerCreatorService.getAllAnswerButtons().get(answer));
                }
                gameControlsService.disableAllHintButtons();
            }
        };
    }

    private void addOnAnswerClick() {
        for (final Map.Entry<String, MyButton> entry : (Set<Map.Entry<String, MyButton>>) questionContainerCreatorService.getAllAnswerButtons().entrySet()) {
            entry.getValue().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buttonClick(entry.getKey());
                }
            });
        }
    }

    public void processGameInfo(final GameQuestionInfo gameQuestionInfo) {
        getCurrentUserHeaderAnimationService().gameInfoChanged();
        refreshQuestionDisplayService.refreshQuestion(gameQuestionInfo);
        int nrOfWrongLettersPressed = gameService.getNrOfWrongAnswersPressed(gameQuestionInfo.getAnswerIds());
        screenBackgroundCreator.refreshBackground(nrOfWrongLettersPressed);
        for (final GameAnswerInfo gameAnswerInfo : new ArrayList<>(gameQuestionInfo.getAnswers())) {
            ButtonSkin buttonSkin = gameService.isAnswerCorrectInQuestion(gameAnswerInfo.getAnswer()) ? questionContainerCreatorService.correctAnswerSkin() : questionContainerCreatorService.wrongAnswerSkin();
            MyButton button = (MyButton) questionContainerCreatorService.getAllAnswerButtons().get(gameAnswerInfo.getAnswer());
            try {
                gameControlsService.disableButton(button);
            } catch (NullPointerException e) {
                int i = 0;
            }
            button.setButtonSkin(buttonSkin);
        }
        if (!gameQuestionInfo.isQuestionOpen()) {
            RunnableAction action1 = new RunnableAction();
            action1.setRunnable(new ScreenRunnable(getGameScreen()) {
                @Override
                public void executeOperations() {
                    gameControlsService.disableTouchableAllControls();
                    refreshQuestionDisplayService.gameOverQuestion(gameQuestionInfo);
                }
            });
            RunnableAction action2 = new RunnableAction();
            action2.setRunnable(new ScreenRunnable(getGameScreen()) {
                @Override
                public void executeOperations() {
                    new QuestionFinishedOperationsService(getGameScreen()).executeFinishedQuestionOperations();
                }
            });
            getGameScreen().addAction(Actions.sequence(action1, Actions.delay(1f), action2));
        }
    }

    public void beforeStartGame() {
    }

    public RefreshQuestionDisplayService getRefreshQuestionDisplayService() {
        return refreshQuestionDisplayService;
    }

    public GameControlsService getGameControlsService() {
        return gameControlsService;
    }

    public TGameService getGameService() {
        return gameService;
    }

    public TScreen getGameScreen() {
        return gameScreen;
    }
}

