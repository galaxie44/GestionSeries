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

@WebServlet(urlPatterns = "/supprimerSaison")
public class SupprimerSaison extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final Integer serieId = ServletUtils.parseId(request.getParameter("serie"));
			final Integer saisonId = ServletUtils.parseId(request.getParameter("saison"));
			if (serieId == null || saisonId == null) {
				ServletUtils.setFlashError(request, "Identifiants invalides.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			final Saison saison = DaoFactory.getInstance().getSaisonDao().readSaison(saisonId);
			if (serie != null && saison != null) {
				DaoBddHelper.getInstance().beginTransaction();
				serie.removeSaison(saison);
				DaoFactory.getInstance().getSerieDao().updateSerie(serie, false);
				DaoBddHelper.getInstance().commitTransaction();
				ServletUtils.setFlashSuccess(request, "Saison supprimée.");
			} else {
				ServletUtils.setFlashError(request, "Série ou saison introuvable.");
			}
		} catch (final Exception e) {
			ServletUtils.rollbackQuietly();
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response);
	}
}
