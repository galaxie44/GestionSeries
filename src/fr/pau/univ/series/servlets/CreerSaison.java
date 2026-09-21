package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.dao.impl.bdd.DaoBddHelper;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.exception.SeriesException;
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
			final int serieId = Integer.parseInt(request.getParameter("serie"));
			final Serie serie = DaoFactory.getInstance().getSerieDao().readSerie(serieId);
			if (serie != null) {
				final int numero = serie.getSaisons().size() + 1;
				final Saison saison = new Saison("Season " + numero, numero);
				DaoBddHelper.getInstance().beginTransaction();
				serie.addSaison(saison);
				DaoFactory.getInstance().getSerieDao().updateSerie(serie, false);
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
