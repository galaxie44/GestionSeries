package fr.pau.univ.series.dao.impl.bdd;

import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.exception.SeriesException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;

/**
 * Insère les données de démo en BDD si la table Serie est vide.
 */
public final class DataSeeder {

	private DataSeeder() {
	}

	public static void seedIfEmpty() throws DaoException, SeriesException {
		final DaoBddHelper bdd = DaoBddHelper.getInstance();
		final long count = bdd.getEm().createQuery("SELECT COUNT(s) FROM Serie s", Long.class).getSingleResult();
		if (count > 0) {
			System.out.println("[GestionSeries] Données déjà présentes (" + count + " série(s)).");
			return;
		}

		System.out.println("[GestionSeries] Insertion des données de démo...");
		bdd.beginTransaction();
		try {
			final Serie got = new Serie("Game of Thrones");
			final Saison got1 = new Saison("Season one", 1);
			got1.addEpisode(new Episode("Winter is coming", 1));
			got1.addEpisode(new Episode("The Kingsroad", 2));
			got1.addEpisode(new Episode("Lord Snow", 3));
			got1.addEpisode(new Episode("Cripples, Bastards, and Broken Things", 4));
			got1.addEpisode(new Episode("The Wolf and the Lion", 5));
			got1.addEpisode(new Episode("A Golden Crown", 6));
			got1.addEpisode(new Episode("You Win or You Die", 7));
			got1.addEpisode(new Episode("The Pointy End", 8));
			got.addSaison(got1);

			final Saison got2 = new Saison("Season two", 2);
			got2.addEpisode(new Episode("The North Remembers", 1));
			got2.addEpisode(new Episode("The Night Lands", 2));
			got2.addEpisode(new Episode("What Is Dead May Never Die", 3));
			got2.addEpisode(new Episode("Garden of Bones", 4));
			got2.addEpisode(new Episode("The Ghost of Harrenhal", 5));
			got2.addEpisode(new Episode("The Old Gods and the New", 6));
			got2.addEpisode(new Episode("A Man Without Honor", 7));
			got2.addEpisode(new Episode("The Prince of Winterfell", 8));
			got.addSaison(got2);

			final Serie tgp = new Serie("The Good Place");
			final Saison tgp1 = new Saison("Season one", 1);
			tgp1.addEpisode(new Episode("Pilot", 1));
			tgp1.addEpisode(new Episode("Flying", 2));
			tgp1.addEpisode(new Episode("Tahani Al-Jamil", 3));
			tgp1.addEpisode(new Episode("Jason Mendoza", 4));
			tgp1.addEpisode(new Episode("Category 55 Emergency Doomsday Crisis", 5));
			tgp1.addEpisode(new Episode("What We Owe to Each Other", 6));
			tgp1.addEpisode(new Episode("The Eternal Shriek", 7));
			tgp1.addEpisode(new Episode("Most Improved Player", 8));
			tgp.addSaison(tgp1);

			final Saison tgp2 = new Saison("Season two", 2);
			tgp2.addEpisode(new Episode("Everything Is Great!", 1));
			tgp2.addEpisode(new Episode("Dance Dance Resolution", 2));
			tgp2.addEpisode(new Episode("Team Cockroach", 3));
			tgp2.addEpisode(new Episode("Existential Crisis", 4));
			tgp2.addEpisode(new Episode("The Trolley Problem", 5));
			tgp2.addEpisode(new Episode("Janet and Michael", 6));
			tgp2.addEpisode(new Episode("Derek", 7));
			tgp2.addEpisode(new Episode("Leap to Faith", 8));
			tgp.addSaison(tgp2);

			bdd.getEm().persist(got);
			bdd.getEm().persist(tgp);
			bdd.commitTransaction();
			System.out.println("[GestionSeries] Données de démo insérées.");
		} catch (final Exception e) {
			bdd.rollbackTransaction();
			throw e;
		}
	}
}
