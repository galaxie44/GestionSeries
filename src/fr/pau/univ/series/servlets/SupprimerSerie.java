package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Suppression d'une série (cascade saisons + épisodes via JPA).
 */
@WebServlet(urlPatterns = "/supprimerSerie")
public class SupprimerSerie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final int serieId = Integer.parseInt(request.getParameter("serie"));
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (serie != null) {
				// CascadeType.ALL + orphanRemoval : saisons et épisodes partent avec
				DaoFactory.getInstance().getSerieDao().deleteSerie(serie, true);
			}
		} catch (final DaoException | NumberFormatException e) {
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		response.sendRedirect(buildListeUrl(request));
	}

	private String buildListeUrl(final HttpServletRequest request) {
		return "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/liste");
	}
}
