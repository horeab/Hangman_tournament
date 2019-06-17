package libgdx.ui.implementations.games.paintings;

import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.screens.mainmenu.MainMenuScreen;

public class PaintingsTournamentGameScreenCreator extends TournamentGameScreenCreator {

    @Override
    public MainMenuScreen createMainMenuScreen() {
        return new PaintingsMainMenuScreen();
    }
}