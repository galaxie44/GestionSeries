package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.model.Saison;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/creerSaison")
public class CreerSaison extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final Integer serieId = ServletUtils.parseId(request.getParameter("serie"));
			if (serieId == null) {
				ServletUtils.setFlashError(request, "Identifiant de série invalide.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (serie != null) {
				final int numero = serie.getSaisons().size() + 1;
				final Saison saison = new Saison("Season " + numero, numero);
				DaoBddHelper.getInstance().beginTransaction();
				serie.addSaison(saison);
				DaoFactory.getInstance().getSerieDao().updateSerie(serie, false);
				DaoBddHelper.getInstance().commitTransaction();
				ServletUtils.setFlashSuccess(request, "Saison ajoutée.");
			} else {
				ServletUtils.setFlashError(request, "Série introuvable.");
			}
		} catch (final Exception e) {
			ServletUtils.rollbackQuietly();
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response);
	}
}
