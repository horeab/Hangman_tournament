package libgdx.ui.screens.game.userinfoheader;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.ArrayList;
import java.util.List;

import libgdx.ui.model.user.BaseUserInfo;
import libgdx.ui.resources.Dimen;
import libgdx.ui.resources.Resource;

public class PlayerHeaderContainer extends Table {

    private boolean withScoreLabel;
    private boolean withAnswerGoal;
    private List<PlayerHeader> playerHeaders = new ArrayList<>();
    private List<BaseUserInfo> players = new ArrayList<>();

    public void addPlayer(BaseUserInfo player) {
        players.add(player);
    }

    public PlayerHeader getCurrentUserHeader() {
        return playerHeaders.get(0);
    }

    public PlayerHeader getPlayerHeader(BaseUserInfo baseUserInfo) {
        for (PlayerHeader playerHeader : playerHeaders) {
            if (playerHeader.getPlayer().equals(baseUserInfo)) {
                return playerHeader;
            }
        }
        return null;
    }

    public void setWithScoreLabel(boolean withScoreLabel) {
        this.withScoreLabel = withScoreLabel;
    }

    public void setWithAnswerGoal(boolean withAnswerGoal) {
        this.withAnswerGoal = withAnswerGoal;
    }

    void create(int totalNrOfQuestions) {
        for (int i = 0; i < players.size(); i++) {
            PlayerHeaderBuilder playerHeaderBuilder = new PlayerHeaderBuilder(players.get(i), totalNrOfQuestions);
            if (withScoreLabel) {
                playerHeaderBuilder.withScoreLabel(getOpponent(i));
            }
            if (totalNrOfQuestions > 1) {
                playerHeaderBuilder.withFillCorrectAnswersTableColorRes(Resource.game_user_header_background_multiple_answers);
                if (withAnswerGoal) {
                    playerHeaderBuilder.withAnswerGoal(true);
                }
            }
            PlayerHeader playerHeader = playerHeaderBuilder.build();
            playerHeaders.add(playerHeader);
            add(playerHeader).height(playerHeader.getPrefHeight()).row();
        }
    }

    private BaseUserInfo getOpponent(int i) {
        if (players.size() == 2) {
            return i == 0 ? players.get(1) : players.get(0);
        }
        return null;
    }
}
