package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import fr.pau.univ.series.model.Saison;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Suppression d'un épisode puis MàJ de la saison (orphanRemoval).
 */
@WebServlet(urlPatterns = "/supprimerEpisode")
public class SupprimerEpisode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final int saisonId = Integer.parseInt(request.getParameter("saison"));
			final int episodeId = Integer.parseInt(request.getParameter("episode"));
			final Saison saison = DaoFactory.getInstance().getSaisonDao().readSaison(saisonId);
			final Episode episode = DaoFactory.getInstance().getEpisodeDao().readEpisode(episodeId);
			if (saison != null && episode != null) {
				DaoBddHelper.getInstance().beginTransaction();
				saison.removeEpisode(episode);
				DaoFactory.getInstance().getSaisonDao().updateSaison(saison, false);
				DaoBddHelper.getInstance().commitTransaction();
			}
		} catch (final Exception e) {
			try {
				DaoBddHelper.getInstance().rollbackTransaction();
			} catch (final DaoException ignored) {
				// ignore
			}
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect("http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/liste"));
	}
}
