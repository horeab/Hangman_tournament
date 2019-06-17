package libgdx.ui.services.gametypes.types.hangmangame.screencreator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import org.apache.commons.lang3.StringUtils;

import libgdx.game.Game;
import libgdx.resources.Res;
import libgdx.controls.ScreenRunnable;
import libgdx.ui.model.game.GameUser;
import libgdx.ui.resources.Dimen;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.game.screens.AbstractGameScreen;
import libgdx.ui.screens.game.creator.utils.AbstractGameScreenBackgroundCreator;
import libgdx.ui.services.game.gameservice.GameServiceContainer;
import libgdx.ui.services.gametypes.types.hangmangame.resources.HangmanSpecificResource;
import libgdx.ui.services.gametypes.types.hangmangame.service.HangmanGameService;
import libgdx.utils.ActorPositionManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;

public class HangmanScreenBackgroundCreator extends AbstractGameScreenBackgroundCreator {

    private static final String ACTOR_NAME_HANGMAN_IMAGE = "actorNameHangmanImage";
    private static final String ACTOR_NAME_SKY_BACKGROUND = "actorNameSkyBackground";

    private HangmanGameService gameService;

    public HangmanScreenBackgroundCreator(AbstractGameScreen abstractGameScreen, GameUser gameUser) {
        super(abstractGameScreen, gameUser);
        gameService = (HangmanGameService) GameServiceContainer.getGameService(gameUser.getBaseUserInfo().getId(), gameUser.getGameQuestionInfo());
    }

    @Override
    public void createBackground() {
        if (gameService.getQuestionImage() == null) {
            addSkyImages();
            createGrassTexture();
            createForestTexture();
            animateSmokeImage(getAbstractGameScreen(), 66);
            refreshHangManImg(0);
        }
    }

    @Override
    public void refreshBackground(int nrOfWrongAnswersPressed) {
        if (gameService.getQuestionImage() == null) {
            refreshSkyImages(nrOfWrongAnswersPressed);
            refreshHangManImg(nrOfWrongAnswersPressed);
        } else {
            refreshAvailableTriesTableForQuestionWithImage(nrOfWrongAnswersPressed);
        }
    }

    private void refreshAvailableTriesTableForQuestionWithImage(int nrOfWrongLettersPressed) {
        for (int i = nrOfWrongLettersPressed - 1; i >= 0; i--) {
            Image image = getAbstractGameScreen().getRoot().findActor(HangmanQuestionContainerCreatorService.AVAILABLE_TRIES_IMAGE_CELL_NAME + i);
            if (image != null) {
                image.addAction(Actions.fadeOut(0.5f));
            }
        }
    }

    private void refreshSkyImages(int nrOfWrongLettersPressed) {
        for (int i = nrOfWrongLettersPressed - 1; i >= 0; i--) {
            Image image = getAbstractGameScreen().getRoot().findActor(ACTOR_NAME_SKY_BACKGROUND + i);
            if (image != null) {
                image.addAction(Actions.fadeOut(0.5f));
            }
        }
    }

    private void addSkyImages() {
        for (int i = 6; i >= 0; i--) {
            addSkyImage(i);
        }
    }

    private void addSkyImage(int imgNr) {
        Res imgName = Game.getInstance().getMainDependencyManager().createResourceService().getByName("skyb" + imgNr);
        Image image = GraphicUtils.addTiledImage(imgName, 0, Texture.TextureWrap.Repeat);
        image.setName(ACTOR_NAME_SKY_BACKGROUND + imgNr);
        getAbstractGameScreen().addActor(image);
    }

    private void refreshHangManImg(int nrOfWrongLettersPressed) {
        Res imgName = Game.getInstance().getMainDependencyManager().createResourceService().getByName("h" + nrOfWrongLettersPressed);
        Image image = GraphicUtils.getImage(imgName);
        Image hangmanImage = getAbstractGameScreen().getRoot().findActor(ACTOR_NAME_HANGMAN_IMAGE);
        if (hangmanImage != null) {
            hangmanImage.setDrawable(image.getDrawable());
        } else {
            float hangmanImageDimenDivider = StringUtils.isNotBlank(new HangmanGameService(getGameQuestionInfo().getQuestion()).getQuestionToBeDisplayed()) ? 1.5f : 1;
            float hangmanImageDimen = Dimen.side_hangman_image.getDimen() / hangmanImageDimenDivider;
            image.setHeight(image.getHeight() / Float.valueOf(image.getWidth()) * hangmanImageDimen);
            image.setWidth(hangmanImageDimen);
            image.setPosition(0, getHangmanImageYPosition());
            ActorPositionManager.setActorCenterHorizontalOnScreen(image);
            image.setName(ACTOR_NAME_HANGMAN_IMAGE);
            getAbstractGameScreen().addActor(image);
        }
    }

    private void createGrassTexture() {
        getAbstractGameScreen().addActor(GraphicUtils.addTiledImage(HangmanSpecificResource.grass_texture, -getHangmanImageYPosition() + ScreenDimensionsManager.getScreenHeightValue(4), Texture.TextureWrap.MirroredRepeat));
    }

    private static void animateSmokeImage(final AbstractScreen abstractScreen, final int y) {
        try {
            Image image = GraphicUtils.getImage(HangmanSpecificResource.smoke);
            image.setPosition(ScreenDimensionsManager.getScreenWidthValue(87), ScreenDimensionsManager.getScreenHeightValue(y));
            image.setHeight(ScreenDimensionsManager.getScreenWidthValue(2f));
            image.setWidth(ScreenDimensionsManager.getScreenWidthValue(2f));
            RunnableAction run = new RunnableAction();
            run.setRunnable(new ScreenRunnable(abstractScreen) {
                @Override
                public void executeOperations() {
                    animateSmokeImage(abstractScreen, y);
                }
            });
            image.addAction(Actions.sequence(Actions.delay(1), Actions.scaleBy(10f, 10f, 10), run));
            image.addAction(Actions.sequence(Actions.delay(0), Actions.fadeOut(12)));
            image.addAction(Actions.sequence(Actions.delay(0), Actions.moveTo(image.getX(), ScreenDimensionsManager.getScreenHeight(), 15)));
            abstractScreen.addActor(image);
        } catch (Exception e) {
            //Ignore animation if exception happens
        }
    }

    private void createForestTexture() {
        Image image = GraphicUtils.getImage(HangmanSpecificResource.forest_texture);
        image.setPosition(0, getHangmanImageYPosition());
        image.setHeight(ScreenDimensionsManager.getNewHeightForNewWidth(ScreenDimensionsManager.getScreenWidth(), image.getWidth(), image.getHeight()));
        image.setWidth(ScreenDimensionsManager.getScreenWidth());
        getAbstractGameScreen().addActor(image);
    }

    private float getHangmanImageYPosition() {
        return ScreenDimensionsManager.getScreenHeightValue(52);
    }
}
