package libgdx.ui.implementations.games.bundesde;

import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.screens.mainmenu.MainMenuScreen;

public class BundesDeTournamentGameScreenCreator extends TournamentGameScreenCreator {

    @Override
    public MainMenuScreen createMainMenuScreen() {
        return new BundesDeMainMenuScreen();
    }
}