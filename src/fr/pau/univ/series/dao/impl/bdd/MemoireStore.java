package fr.pau.univ.series.dao.impl.bdd;

import java.util.ArrayList;
import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.SeriesException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;

/**
 * Stockage temporaire en mémoire (données de démo) en attendant JPA / BDD réelle.
 */
public final class MemoireStore {

	private static MemoireStore instance;
	private final List<Serie> series = new ArrayList<>();

	private MemoireStore() {
		initData();
	}

	public static synchronized MemoireStore getInstance() {
		if (instance == null) {
			instance = new MemoireStore();
		}
		return instance;
	}

	public List<Serie> getSeries() {
		return this.series;
	}

	private void initData() {
		// Réinitialise les compteurs pour repartir proprement au démarrage serveur
		DaoFactory.lastSerieId = 0;
		DaoFactory.lastSaisonId = 0;
		DaoFactory.lastEpisodeId = 0;

		final Serie got = new Serie("Game of Thrones");
		final Saison got1 = new Saison("Season one", 1);
		final Episode got11 = new Episode("Winter is coming", 1);
		final Episode got12 = new Episode("The Kingsroad", 2);
		final Episode got13 = new Episode("Lord Snow", 3);
		final Episode got14 = new Episode("Cripples, Bastards, and Broken Things", 4);
		final Episode got15 = new Episode("The Wolf and the Lion", 5);
		final Episode got16 = new Episode("A Golden Crown", 6);
		final Episode got17 = new Episode("You Win or You Die", 7);
		final Episode got18 = new Episode("The Pointy End", 8);
		final Saison got2 = new Saison("Season two", 2);
		final Episode got21 = new Episode("The North Remembers", 1);
		final Episode got22 = new Episode("The Night Lands", 2);
		final Episode got23 = new Episode("What Is Dead May Never Die", 3);
		final Episode got24 = new Episode("Garden of Bones", 4);
		final Episode got25 = new Episode("The Ghost of Harrenhal", 5);
		final Episode got26 = new Episode("The Old Gods and the New", 6);
		final Episode got27 = new Episode("A Man Without Honor", 7);
		final Episode got28 = new Episode("The Prince of Winterfell", 8);

		final Serie tgp = new Serie("The Good Place");
		final Saison tgp1 = new Saison("Season one", 1);
		final Episode tgp11 = new Episode("Pilot", 1);
		final Episode tgp12 = new Episode("Flying", 2);
		final Episode tgp13 = new Episode("Tahani Al-Jamil", 3);
		final Episode tgp14 = new Episode("Jason Mendoza", 4);
		final Episode tgp15 = new Episode("Category 55 Emergency Doomsday Crisis", 5);
		final Episode tgp16 = new Episode("What We Owe to Each Other", 6);
		final Episode tgp17 = new Episode("The Eternal Shriek", 7);
		final Episode tgp18 = new Episode("Most Improved Player", 8);
		final Saison tgp2 = new Saison("Season two", 2);
		final Episode tgp21 = new Episode("Everything Is Great!", 1);
		final Episode tgp22 = new Episode("Dance Dance Resolution", 2);
		final Episode tgp23 = new Episode("Team Cockroach", 3);
		final Episode tgp24 = new Episode("Existential Crisis", 4);
		final Episode tgp25 = new Episode("The Trolley Problem", 5);
		final Episode tgp26 = new Episode("Janet and Michael", 6);
		final Episode tgp27 = new Episode("Derek", 7);
		final Episode tgp28 = new Episode("Leap to Faith", 8);

		try {
			got1.addEpisode(got11);
			got1.addEpisode(got12);
			got1.addEpisode(got13);
			got1.addEpisode(got14);
			got1.addEpisode(got15);
			got1.addEpisode(got16);
			got1.addEpisode(got17);
			got1.addEpisode(got18);
			got.addSaison(got1);
			got2.addEpisode(got21);
			got2.addEpisode(got22);
			got2.addEpisode(got23);
			got2.addEpisode(got24);
			got2.addEpisode(got25);
			got2.addEpisode(got26);
			got2.addEpisode(got27);
			got2.addEpisode(got28);
			got.addSaison(got2);
			this.series.add(got);

			tgp1.addEpisode(tgp11);
			tgp1.addEpisode(tgp12);
			tgp1.addEpisode(tgp13);
			tgp1.addEpisode(tgp14);
			tgp1.addEpisode(tgp15);
			tgp1.addEpisode(tgp16);
			tgp1.addEpisode(tgp17);
			tgp1.addEpisode(tgp18);
			tgp.addSaison(tgp1);
			tgp2.addEpisode(tgp21);
			tgp2.addEpisode(tgp22);
			tgp2.addEpisode(tgp23);
			tgp2.addEpisode(tgp24);
			tgp2.addEpisode(tgp25);
			tgp2.addEpisode(tgp26);
			tgp2.addEpisode(tgp27);
			tgp2.addEpisode(tgp28);
			tgp.addSaison(tgp2);
			this.series.add(tgp);
		} catch (final SeriesException e) {
			e.printStackTrace();
		}
	}
}
