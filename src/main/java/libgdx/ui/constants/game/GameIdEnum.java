package libgdx.ui.constants.game;

import libgdx.game.GameId;
import libgdx.ui.implementations.config.dependecies.TournamentGameDependencyManager;
import libgdx.ui.implementations.games.anatomy.AnatomyDependencyManager;
import libgdx.ui.implementations.games.animals.AnimalsDependencyManager;
import libgdx.ui.implementations.games.astronomy.AstronomyDependencyManager;
import libgdx.ui.implementations.games.bundesde.BundesDeDependencyManager;
import libgdx.ui.implementations.games.capitals.CapitalsDependencyManager;
import libgdx.ui.implementations.games.centenar.CentenarDependencyManager;
import libgdx.ui.implementations.games.conthistory.ContHistoryDependencyManager;
import libgdx.ui.implementations.games.countrygeo.CountryGeoDependencyManager;
import libgdx.ui.implementations.games.countryhistro.CountryHistRoDependencyManager;
import libgdx.ui.implementations.games.cunro.CunRoDependencyManager;
import libgdx.ui.implementations.games.flags.FlagsDependencyManager;
import libgdx.ui.implementations.games.geoquiz.GeoQuizDependencyManager;
import libgdx.ui.implementations.games.hangmanarena.HangmanArenaDependencyManager;
import libgdx.ui.implementations.games.judetelerom.JudeteleRomDependencyManager;
import libgdx.ui.implementations.games.kennstde.KennstDeDependencyManager;
import libgdx.ui.implementations.games.paintings.PaintingsDependencyManager;
import libgdx.ui.implementations.games.scoalasofer.ScoalaSoferDependencyManager;

public enum GameIdEnum implements GameId {

    anatomy(AnatomyDependencyManager.class),
    animals(AnimalsDependencyManager.class),
    astronomy(AstronomyDependencyManager.class),
    bundesde(BundesDeDependencyManager.class),
    capitals(CapitalsDependencyManager.class),
    centenar(CentenarDependencyManager.class),
    conthistory_eur(ContHistoryDependencyManager.class),
    conthistory_asia(ContHistoryDependencyManager.class),
    countrygeo(CountryGeoDependencyManager.class),
    countryhistro(CountryHistRoDependencyManager.class),
    cunro(CunRoDependencyManager.class),
    flags(FlagsDependencyManager.class),
    geoquiz(GeoQuizDependencyManager.class),
    hangman(HangmanArenaDependencyManager.class),
    judetelerom(JudeteleRomDependencyManager.class),
    kennstde(KennstDeDependencyManager.class),
    paintings(PaintingsDependencyManager.class),
    scoalasofer(ScoalaSoferDependencyManager.class),
    ;

    private Class<? extends TournamentGameDependencyManager> dependencyManagerClass;

    GameIdEnum(Class<? extends TournamentGameDependencyManager> dependencyManagerClass) {
        this.dependencyManagerClass = dependencyManagerClass;
    }

    @Override
    public TournamentGameDependencyManager getDependencyManager() {
        try {
            return dependencyManagerClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
