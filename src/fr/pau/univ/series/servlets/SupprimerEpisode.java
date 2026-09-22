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

@WebServlet(urlPatterns = "/supprimerEpisode")
public class SupprimerEpisode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final Integer saisonId = ServletUtils.parseId(request.getParameter("saison"));
			final Integer episodeId = ServletUtils.parseId(request.getParameter("episode"));
			if (saisonId == null || episodeId == null) {
				ServletUtils.setFlashError(request, "Identifiants invalides.");
				ServletUtils.redirectListe(request, response);
				return;
			}
			final Saison saison = DaoFactory.getInstance().getSaisonDao().readSaison(saisonId);
			final Episode episode = DaoFactory.getInstance().getEpisodeDao().readEpisode(episodeId);
			if (saison != null && episode != null) {
				DaoBddHelper.getInstance().beginTransaction();
				saison.removeEpisode(episode);
				DaoFactory.getInstance().getSaisonDao().updateSaison(saison, false);
				DaoBddHelper.getInstance().commitTransaction();
				ServletUtils.setFlashSuccess(request, "Épisode supprimé.");
			} else {
				ServletUtils.setFlashError(request, "Saison ou épisode introuvable.");
			}
		} catch (final Exception e) {
			ServletUtils.rollbackQuietly();
			ServletUtils.setFlashError(request, e.getMessage());
			e.printStackTrace();
		}
		ServletUtils.redirectListe(request, response);
	}
}
