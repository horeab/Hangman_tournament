package libgdx.ui.screens.tournament.creator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import libgdx.controls.label.MyWrappedLabelConfigBuilder;
import libgdx.controls.labelimage.InventoryLabelImageBuilder;
import libgdx.resources.gamelabel.MainGameLabel;
import libgdx.ui.constants.overridable.TransactionAmountEnum;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.controls.CountDownCounter;
import libgdx.controls.ScreenRunnable;
import libgdx.controls.animations.ActorAnimation;
import libgdx.controls.label.MyWrappedLabel;
import libgdx.ui.controls.labelimage.prize.IfLevelUpPrizeLabelImage;
import libgdx.ui.model.game.GameContext;
import libgdx.ui.model.game.GameContextService;
import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.TournamentGameLabel;
import libgdx.ui.resources.Resource;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.ui.services.game.UserProfilePictureUrlService;
import libgdx.ui.services.tournament.TournamentService;
import libgdx.utils.ActorPositionManager;
import libgdx.graphics.GraphicUtils;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.ui.util.TournamentStage;
import libgdx.utils.model.RGBColor;

import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_LEFTMARGIN1;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_LEFTMARGIN2;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_TOPMARGIN1;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_TOPMARGIN2;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_TOPMARGIN3;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE0_TOPMARGIN4;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE1_LEFTMARGIN1;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE1_LEFTMARGIN2;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE1_TOPMARGIN1;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE1_TOPMARGIN2;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE2_LEFTMARGIN1;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE2_LEFTMARGIN2;
import static libgdx.ui.screens.tournament.creator.TournamentLoadingScreenUserProfilePictureCreator.STAGE2_TOPMARGIN1;

public class TournamentLoadingScreenCreator {

    private static final RGBColor CURRENT_USER_BORDER_COLOR = new RGBColor(1, 0, 102, 255);
    private MyWrappedLabel loadingLabel;

    private AbstractScreen abstractScreen;
    protected BaseUserInfo currentUser;
    private TournamentInfoWithUserInfo[] imagesWithTournamentUsers;
    private TournamentContext tournamentContext;
    private TournamentService tournamentService = new TournamentService();
    private GameConfig gameConfig;

    TournamentLoadingScreenCreator(AbstractScreen abstractScreen, TournamentInfoWithUserInfo[] imagesWithTournamentUsers, TournamentContext tournamentContext, GameConfig gameConfig) {
        this.tournamentContext = tournamentContext;
        this.imagesWithTournamentUsers = imagesWithTournamentUsers;
        this.abstractScreen = abstractScreen;
        this.currentUser = abstractScreen.getCurrentUser();
        this.gameConfig = gameConfig;
        this.loadingLabel = new MyWrappedLabel(MainGameLabel.loading.getText());
    }

    private TournamentStage getTournamentStage() {
        return tournamentContext.getTournamentStage();
    }

    protected void create() {
        ActorPositionManager.setActorCenterScreen(loadingLabel);
        loadingLabel.setStyleDependingOnContrast();
        abstractScreen.addActor(loadingLabel);
        loadingLabel.clearActions();
        loadingLabel.setVisible(false);
        new ActorAnimation(addTournamentImage(0, 40, Resource.trophy_rays, Dimen.width_tournament_trophy_rays_img), abstractScreen).animateFastFadeInFadeOut();
        drawTournamentConnectLines();
        addTournamentImage(37, 60, Resource.trophy, Dimen.width_tournament_trophy_img);
        for (TournamentInfoWithUserInfo imageWithUser : imagesWithTournamentUsers) {
            TournamentUser user = imageWithUser.getTournamentUser();
            RGBColor borderColor = new RGBColor(1, 0, 204, 0);
            if (currentUser.getId() == user.getId() && imageWithUser.getTournamentStage() == getTournamentStage()
                    ||
                    //always the opponent is on position 1
                    !user.isGameOver() && user.getPositionForStage(getTournamentStage()) == 1 && imageWithUser.getTournamentStage() == getTournamentStage()) {
                borderColor = CURRENT_USER_BORDER_COLOR;
            }
            Table profilePictureContainer = imageWithUser.getUserInfo().getProfilePictureContainer();
            addActorToStageWithAnimation(drawUserRectangleBorder(profilePictureContainer.getX(), profilePictureContainer.getY(), borderColor));
            addActorToStageWithAnimation(profilePictureContainer);
            if (user.isGameOver()) {
                abstractScreen.addActor(drawUserRectangleBorder(profilePictureContainer.getX(), profilePictureContainer.getY(), new RGBColor(0.8f, 1, 1, 1)));
            }
        }
        addCountdownCounter();
        addPrizeLabel();
    }

    private void addPrizeLabel() {
        Table prizeLabelImage = new IfLevelUpPrizeLabelImage(currentUser.getId(), gameConfig.getGameTypeStage()) {
            @Override
            protected InventoryLabelImageBuilder getInventoryLabelImageBuilder(TransactionAmountEnum transactionAmountEnum) {
                return super.getInventoryLabelImageBuilder(transactionAmountEnum).setTextColor(MyWrappedLabelConfigBuilder.getScreenContrastStyle());
            }
        }.create();
        prizeLabelImage.setPosition(ScreenDimensionsManager.getScreenWidth() / 2 - prizeLabelImage.getWidth() / 2, ScreenDimensionsManager.getScreenHeightValue(93));
        abstractScreen.addActor(prizeLabelImage);
    }

    private Image addTournamentImage(float xPos, float yPos, Resource resource, Dimen width) {
        Image image = GraphicUtils.getImage(resource);
        image.setHeight(ScreenDimensionsManager.getNewHeightForNewWidth(width.getDimen(), image.getWidth(), image.getHeight()));
        image.setWidth(width.getDimen());
        image.setPosition(ScreenDimensionsManager.getScreenWidthValue(xPos), ScreenDimensionsManager.getScreenHeightValue(yPos));
        addActorToStageWithAnimation(image);
        return image;
    }

    private void addCountdownCounter() {
        CountDownCounter countDownCounter = new CountDownCounter(3000, TournamentGameLabel.tournament_go_label.getText(), abstractScreen) {
            @Override
            public void executeAfterCountDownCounter() {
                startGameScreen(tournamentService.getCurrentOpponentForTournamentStage(tournamentContext.getTournamentUsers(), getTournamentStage()), gameConfig);
            }
        };
        countDownCounter.start();
        countDownCounter.getCountdownCounterLabel().setPosition(ScreenDimensionsManager.getScreenWidth() / 2 - countDownCounter.getCountdownCounterLabel().getWidth() / 2, ScreenDimensionsManager.getScreenHeightValue(81));
        abstractScreen.addActor(countDownCounter.getCountdownCounterLabel());
    }

    private void addActorToStageWithAnimation(Actor actor) {
        actor.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.1f)));
        abstractScreen.addActor(actor);
    }

    private void drawTournamentConnectLines() {
        addHorizontalLineToImg(STAGE0_LEFTMARGIN1 + 7, STAGE1_LEFTMARGIN1, STAGE1_TOPMARGIN1, 8, false);
        addHorizontalLineToImg(STAGE0_LEFTMARGIN1 + 7, STAGE1_LEFTMARGIN1, STAGE1_TOPMARGIN2, 8, false);
        addHorizontalLineToImg(STAGE0_LEFTMARGIN2 - 6, STAGE1_LEFTMARGIN2, STAGE1_TOPMARGIN1, 7, false);
        addHorizontalLineToImg(STAGE0_LEFTMARGIN2 - 6, STAGE1_LEFTMARGIN2, STAGE1_TOPMARGIN2, 7, false);
        addHorizontalLineToImg(STAGE2_LEFTMARGIN1 - 4, STAGE2_LEFTMARGIN2 - 10, STAGE2_TOPMARGIN1, 70, getTournamentStage() == TournamentStage.STAGE_2);

        //STAGE0
        addVerticalLineToImg(STAGE0_TOPMARGIN1, STAGE0_TOPMARGIN2, STAGE0_LEFTMARGIN1, 30, getTournamentStage() == TournamentStage.STAGE_0);
        addVerticalLineToImg(STAGE0_TOPMARGIN3, STAGE0_TOPMARGIN4, STAGE0_LEFTMARGIN1, 30, false);
        addVerticalLineToImg(STAGE0_TOPMARGIN1, STAGE0_TOPMARGIN2, STAGE0_LEFTMARGIN2, 30, false);
        addVerticalLineToImg(STAGE0_TOPMARGIN3, STAGE0_TOPMARGIN4, STAGE0_LEFTMARGIN2, 30, false);

        //STAGE1
        addVerticalLineToImg(STAGE1_TOPMARGIN1, STAGE1_TOPMARGIN2 + 3.5f, STAGE1_LEFTMARGIN1 + 1.8f, 45f, getTournamentStage() == TournamentStage.STAGE_1);
        addVerticalLineToImg(STAGE1_TOPMARGIN1, STAGE1_TOPMARGIN2 + 3.5f, STAGE1_LEFTMARGIN2 - 0.5f, 45f, false);

        //CUP CONNECT
        addVerticalLineToImg(STAGE2_TOPMARGIN1 + 5, STAGE2_TOPMARGIN1 + 4, 42.5f, 20, getTournamentStage() == TournamentStage.STAGE_2);

    }

    private void addVerticalLineToImg(float yPosTop, float yPosBottom, float xPos, float lineHeight, boolean isCurrentUser) {
        int borderSize = getBorderSize(isCurrentUser);
        addLine(borderSize, ScreenDimensionsManager.getScreenHeightValue(lineHeight), borderSize, ScreenDimensionsManager.getScreenHeightValue(yPosTop), ScreenDimensionsManager.getScreenWidthValue(xPos + 6.5f), ScreenDimensionsManager.getScreenHeightValue(yPosBottom), isCurrentUser);
    }

    private void addHorizontalLineToImg(float xPosLeft, float xPosRight, float YPos, float lineWidth, boolean isCurrentUser) {
        int borderSize = getBorderSize(isCurrentUser);
        addLine(Math.round(ScreenDimensionsManager.getScreenHeightValue(lineWidth)), borderSize, ScreenDimensionsManager.getScreenWidthValue(xPosRight), borderSize, ScreenDimensionsManager.getScreenWidthValue(xPosLeft), ScreenDimensionsManager.getScreenHeightValue(YPos + 3.5f), isCurrentUser);
    }

    private int getBorderSize(boolean isCurrentUser) {
        int borderSize = Dimen.side_tournament_connect_lines.getIntegerValueOfDimen();
        if (isCurrentUser) {
            borderSize = borderSize * 2;
        }
        return borderSize;
    }

    private void addLine(float pixmapWidth, float pixmapHeight, float rectangleWidth, float rectangleHeight, float imgPosX, float imgPosY, boolean isCurrentUser) {
        RGBColor color = new RGBColor(1, 0, 0, 0);
        if (isCurrentUser) {
            color = CURRENT_USER_BORDER_COLOR;
        }
        Pixmap pixmap = new Pixmap(Math.round(pixmapWidth), Math.round(pixmapHeight), Pixmap.Format.RGBA8888);
        pixmap.setColor(color.r, color.g, color.b, color.a);
        pixmap.fillRectangle(0, 0, Math.round(rectangleWidth), Math.round(rectangleHeight));
        Texture pixmaptex = new Texture(pixmap);
        pixmap.dispose();
        Image image = new Image(pixmaptex);
        image.setPosition(imgPosX, imgPosY);
        addActorToStageWithAnimation(image);
    }

    private Image drawUserRectangleBorder(Float xPos, Float yPos, RGBColor color) {
        int borderSize = Dimen.side_tournament_profile_picture_border.getIntegerValueOfDimen();
        int containerDim = Dimen.side_tournament_profile_picture.getIntegerValueOfDimen() + borderSize;
        Pixmap pixmap = new Pixmap(containerDim, containerDim, Pixmap.Format.RGBA8888);
        pixmap.setColor(color.r, color.g, color.b, color.a);
        pixmap.fillRectangle(0, 0, containerDim, containerDim);
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Image image = new Image(pixmapTex);
        image.setPosition(xPos - borderSize / 2, yPos - borderSize / 2);
        return image;
    }

    private static Pair<Image, String>[] createImageUrlPair(TournamentInfoWithUserInfo[] usersWithImages) {
        List<MutablePair<Image, String>> result = new ArrayList<>();
        for (TournamentInfoWithUserInfo userWithImage : usersWithImages) {
            Image profilePicture = userWithImage.getUserInfo().getProfilePictureImage();
            result.add(new MutablePair<>(profilePicture, new UserProfilePictureUrlService(userWithImage.getTournamentUser().getBaseUserInfo()).getProfilePictureUrl()));
        }
        return result.toArray(new MutablePair[result.size()]);
    }

    private void startGameScreen(BaseUserInfo player2, GameConfig gameConfig) {
        final GameContext gameContext = new GameContextService().createGameContext(currentUser, Collections.singletonList(player2), gameConfig, tournamentContext.getAvailableHints());
        Gdx.app.postRunnable(new ScreenRunnable(TournamentGame.getInstance().getAbstractScreen()) {
            @Override
            public void executeOperations() {
                TournamentGame.getInstance().getScreenManager().startGameTournamentScreen(gameContext, tournamentContext);
            }
        });
    }
}
