package libgdx.ui.screens.tournament.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import libgdx.ui.controls.user.UserInfo;
import libgdx.ui.model.user.TournamentUser;
import libgdx.ui.resources.Dimen;
import libgdx.ui.screens.AbstractScreen;
import libgdx.ui.screens.actionoptions.gameconfig.GameConfig;
import libgdx.ui.screens.actionoptions.gameconfig.TournamentContext;
import libgdx.utils.ScreenDimensionsManager;
import libgdx.ui.util.TournamentStage;

public class TournamentLoadingScreenUserProfilePictureCreator {

    static final float STAGE0_LEFTMARGIN1 = 7;
    static final float STAGE0_LEFTMARGIN2 = 77;
    static final float STAGE0_TOPMARGIN1 = 80;
    static final float STAGE0_TOPMARGIN2 = 50;
    static final float STAGE0_TOPMARGIN3 = 35;
    static final float STAGE0_TOPMARGIN4 = 10;

    static final float STAGE1_LEFTMARGIN1 = 19;
    static final float STAGE1_LEFTMARGIN2 = 65;
    static final float STAGE1_TOPMARGIN1 = 66;
    static final float STAGE1_TOPMARGIN2 = 21;

    static final float STAGE2_LEFTMARGIN1 = 32;
    static final float STAGE2_LEFTMARGIN2 = 53.5f;
    static final float STAGE2_TOPMARGIN1 = 42;

    private AbstractScreen abstractScreen;
    private TournamentContext tournamentContext;
    private GameConfig gameConfig;

    public TournamentLoadingScreenUserProfilePictureCreator(AbstractScreen abstractScreen, TournamentContext tournamentContext, GameConfig gameConfig) {
        this.abstractScreen = abstractScreen;
        this.tournamentContext = tournamentContext;
        this.gameConfig = gameConfig;
    }

    public void create() {
        drawStages(tournamentContext);
    }

    private void drawStages(TournamentContext tournamentContext) {
        List<TournamentInfoWithUserInfo> tournamentImagesWithUrls = new ArrayList<>();
        TournamentStage currentTournamentStage = tournamentContext.getTournamentStage();
        List<TournamentUser> tournamentUsers = tournamentContext.getTournamentUsers();
        switch (currentTournamentStage) {
            case STAGE_0: {
                tournamentImagesWithUrls.addAll(createStage0(tournamentUsers));
                break;
            }
            case STAGE_1: {
                tournamentImagesWithUrls.addAll(createStage0(tournamentUsers));
                tournamentImagesWithUrls.addAll(createStage1(tournamentUsers));
                break;
            }
            case STAGE_2: {
                tournamentImagesWithUrls.addAll(createStage0(tournamentUsers));
                tournamentImagesWithUrls.addAll(createStage1(tournamentUsers));
                tournamentImagesWithUrls.addAll(createStage2(tournamentUsers));
                break;
            }
        }
        TournamentInfoWithUserInfo[] usersWithImages = tournamentImagesWithUrls.toArray(new TournamentInfoWithUserInfo[tournamentImagesWithUrls.size()]);
        new TournamentLoadingScreenCreator(abstractScreen, usersWithImages, tournamentContext, gameConfig).create();
    }

    private List<TournamentInfoWithUserInfo> createStage2(List<TournamentUser> tournamentUsers) {
        List<Float> xPositions = Arrays.asList(STAGE2_LEFTMARGIN1, STAGE2_LEFTMARGIN2);
        List<Float> yPositions = Arrays.asList(STAGE2_TOPMARGIN1, STAGE2_TOPMARGIN1);
        return createTournamentImagesWithUrls(tournamentUsers, TournamentStage.STAGE_2, xPositions, yPositions);
    }

    private List<TournamentInfoWithUserInfo> createStage1(List<TournamentUser> tournamentUsers) {
        List<Float> xPositions = Arrays.asList(STAGE1_LEFTMARGIN1, STAGE1_LEFTMARGIN1, STAGE1_LEFTMARGIN2, STAGE1_LEFTMARGIN2);
        List<Float> yPositions = Arrays.asList(STAGE1_TOPMARGIN1, STAGE1_TOPMARGIN2, STAGE1_TOPMARGIN1, STAGE1_TOPMARGIN2);
        return createTournamentImagesWithUrls(tournamentUsers, TournamentStage.STAGE_1, xPositions, yPositions);
    }

    private List<TournamentInfoWithUserInfo> createStage0(List<TournamentUser> tournamentUsers) {
        List<Float> xPositions = Arrays.asList(STAGE0_LEFTMARGIN1, STAGE0_LEFTMARGIN1, STAGE0_LEFTMARGIN1, STAGE0_LEFTMARGIN1, STAGE0_LEFTMARGIN2, STAGE0_LEFTMARGIN2, STAGE0_LEFTMARGIN2, STAGE0_LEFTMARGIN2);
        List<Float> yPositions = Arrays.asList(STAGE0_TOPMARGIN1, STAGE0_TOPMARGIN2, STAGE0_TOPMARGIN3, STAGE0_TOPMARGIN4, STAGE0_TOPMARGIN1, STAGE0_TOPMARGIN2, STAGE0_TOPMARGIN3, STAGE0_TOPMARGIN4);
        return createTournamentImagesWithUrls(tournamentUsers, TournamentStage.STAGE_0, xPositions, yPositions);
    }

    private List<TournamentInfoWithUserInfo> createTournamentImagesWithUrls(List<TournamentUser> tournamentUsers, TournamentStage tournamentStage, List<Float> xPositions, List<Float> yPositions) {
        List<TournamentInfoWithUserInfo> tournamentImagesWithUrls = new ArrayList<>();
        int pos = 0;
        for (int position = 0; position < tournamentStage.getPlayerNrForStage(); position++) {
            TournamentUser userForPositionAndStage = getUserForPositionAndStage(position, tournamentStage, tournamentUsers);
            UserInfo userInfo = new UserInfo(userForPositionAndStage.getBaseUserInfo(), Dimen.side_tournament_profile_picture);
            userInfo.getProfilePictureContainer().setWidth(Dimen.side_tournament_profile_picture.getDimen());
            userInfo.getProfilePictureContainer().setHeight(Dimen.side_tournament_profile_picture.getDimen());
            userInfo.getProfilePictureContainer().setPosition(ScreenDimensionsManager.getScreenWidthValue(xPositions.get(pos)), ScreenDimensionsManager.getScreenHeightValue(yPositions.get(pos)));
            tournamentImagesWithUrls.add(new TournamentInfoWithUserInfo(userInfo, tournamentStage, userForPositionAndStage));
            pos++;
        }
        return tournamentImagesWithUrls;
    }

    private TournamentUser getUserForPositionAndStage(int position, TournamentStage stage, List<TournamentUser> tournamentUsers) {
        for (TournamentUser tournamentUser : tournamentUsers) {
            Integer posForStage = tournamentUser.getTournamentStageAndPosition().get(stage);
            if (posForStage != null && posForStage == position) {
                return tournamentUser;
            }
        }
        return null;

    }

}
