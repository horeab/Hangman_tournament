package libgdx.ui.implementations.games.scoalasofer;

import libgdx.ui.screens.TournamentGameScreenCreator;
import libgdx.ui.screens.mainmenu.MainMenuScreen;

public class ScoalaSoferTournamentGameScreenCreator extends TournamentGameScreenCreator {

    @Override
    public MainMenuScreen createMainMenuScreen() {
        return new ScoalaSoferMainMenuScreen();
    }
}