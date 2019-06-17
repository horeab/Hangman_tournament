package libgdx.ui.services;

import java.util.Arrays;
import java.util.Set;

import libgdx.resources.Res;
import libgdx.resources.ResourceService;
import libgdx.ui.game.TournamentGame;
import libgdx.ui.constants.game.question.QuestionCategory;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.services.gametypes.types.hangmangame.HangmanGameCreatorDependencies;
import libgdx.ui.services.gametypes.types.hangmangame.resources.HangmanSpecificResource;
import libgdx.utils.EnumUtils;


public class TournamentGameResourceService extends ResourceService {

    public Set<Res> getAllRes() {
        TournamentGameDependencyManager dependencyManager = TournamentGame.getInstance().getSubGameDependencyManager();
        Set<Res> allRes = super.getAllRes();
//        if the game contains hangman questions, the resources should be loaded
        for (QuestionCategory category : (QuestionCategory[]) EnumUtils.getValues(dependencyManager.getQuestionCategoryTypeEnum())) {
            if (category.getCreatorDependencies().equals(HangmanGameCreatorDependencies.class)) {
                allRes.addAll(Arrays.asList(HangmanSpecificResource.values()));
                break;
            }
        }
        return allRes;
    }
}