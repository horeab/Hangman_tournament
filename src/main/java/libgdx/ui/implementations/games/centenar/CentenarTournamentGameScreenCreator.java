package libgdx.ui.implementations.games.centenar;

import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.screens.mainmenu.MainMenuScreen;

public class CentenarTournamentGameScreenCreator extends TournamentGameScreenCreator {

    @Override
    public MainMenuScreen createMainMenuScreen() {
        return new CentenarMainMenuScreen();
    }
}