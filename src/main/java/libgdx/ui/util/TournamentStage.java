package libgdx.ui.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.question.QuestionDifficulty;

public enum TournamentStage {

    STAGE_0(0, Constants.NR_PLAYERS_TOURNAMENT, TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getEasyDiff()),

    STAGE_1(1, Constants.NR_PLAYERS_TOURNAMENT / 2, TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getMediumDiff()),

    STAGE_2(2, Constants.NR_PLAYERS_TOURNAMENT / 4, TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getHardDiff()),

    GAME_FINISH(3, Constants.NR_PLAYERS_TOURNAMENT / 4, TournamentGame.getInstance().getSubGameDependencyManager().getQuestionConfigService().getEasyDiff());

    private int stageNr;
    private int playerNrForStage;
    private List<QuestionDifficulty> questionDifficultyLevel;

    TournamentStage(int stageNr, int playerNrForStage, List<QuestionDifficulty> questionDifficultyLevel) {
        this.stageNr = stageNr;
        this.playerNrForStage = playerNrForStage;
        this.questionDifficultyLevel = questionDifficultyLevel;
    }

    public int getPlayerNrForStage() {
        return playerNrForStage;
    }

    public static class Constants {
        public static final int NR_PLAYERS_TOURNAMENT = 8;
    }

    public static TournamentStage getNextStage(TournamentStage tournamentStage) {
        if (tournamentStage == STAGE_0) {
            return STAGE_1;
        } else if (tournamentStage == STAGE_1) {
            return STAGE_2;
        } else if (tournamentStage == STAGE_2) {
            return GAME_FINISH;
        }
        return STAGE_0;
    }

    public QuestionDifficulty getRandomQuestionDifficultyLevel() {
        ArrayList<QuestionDifficulty> list = new ArrayList<QuestionDifficulty>(questionDifficultyLevel);
        Collections.shuffle(list);
        return list.get(0);
    }

    public static List<QuestionDifficulty> getQuestionDifficultyLevelToPressFirstAndLastLetter() {
        List<QuestionDifficulty> questionDifficultyLevelsToPressFirstAndLastLetter = new ArrayList<>();
        questionDifficultyLevelsToPressFirstAndLastLetter.addAll(STAGE_0.questionDifficultyLevel);
        questionDifficultyLevelsToPressFirstAndLastLetter.addAll(STAGE_1.questionDifficultyLevel);
        return questionDifficultyLevelsToPressFirstAndLastLetter;
    }
}
