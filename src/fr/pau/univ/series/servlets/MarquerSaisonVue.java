package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Marque tous les épisodes d'une saison comme vus.
 */
@WebServlet(urlPatterns = "/marquerSaisonVue")
public class MarquerSaisonVue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		String anchor = null;
		try {
			final Integer saisonId = ServletUtils.parseId(request.getParameter("saison"));
			if (saisonId == null) {
				ServletUtils.setFlashError(request, "Identifiant de saison invalide.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			anchor = "saison-" + saisonId;
			final Saison saison = DaoFactory.getInstance().getSaisonDao().readSaison(saisonId);
			if (saison == null) {
				ServletUtils.setFlashError(request, "Saison introuvable.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			DaoBddHelper.getInstance().beginTransaction();
			for (final Episode ep : saison.getEpisodes()) {
				ep.setVu(true);
			}
			DaoFactory.getInstance().getSaisonDao().updateSaison(saison, false);
			DaoBddHelper.getInstance().commitTransaction();
			ServletUtils.setFlashSuccess(request, "Saison marquée comme vue.");
		} catch (final Exception e) {
			ServletUtils.rollbackQuietly();
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response, anchor);
	}
}
