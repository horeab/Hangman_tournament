package libgdx.ui.constants.game.question;

import libgdx.ui.services.gametypes.creatordependencies.CreatorDependencies;

public interface QuestionCategory {

    int getIndex();

    Class<? extends CreatorDependencies> getCreatorDependencies();

    String name();
}
