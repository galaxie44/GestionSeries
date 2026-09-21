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

@WebServlet(urlPatterns = "/creerEpisode")
public class CreerEpisode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		try {
			final int idSaison = Integer.parseInt(request.getParameter("saison"));
			final String nomEpisode = request.getParameter("nomEpisode");
			if (nomEpisode != null && !nomEpisode.isBlank()) {
				final Saison sais = DaoFactory.getInstance().getSaisonDao().readSaison(idSaison);
				if (sais != null) {
					final Episode ep = new Episode(nomEpisode.trim(), sais.getEpisodes().size() + 1);
					DaoBddHelper.getInstance().beginTransaction();
					// create sans transaction + update saison (PDF p.67)
					DaoFactory.getInstance().getEpisodeDao().createEpisode(ep, false);
					sais.addEpisode(ep);
					DaoFactory.getInstance().getSaisonDao().updateSaison(sais, false);
					DaoBddHelper.getInstance().commitTransaction();
				}
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
