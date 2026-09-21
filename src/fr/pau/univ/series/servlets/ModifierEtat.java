package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Episode;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/etat")
public class ModifierEtat extends HttpServlet {

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final int episodeId = Integer.parseInt(request.getParameter("episode"));
		try {
			final Episode e = DaoFactory.getInstance().getEpisodeDao().readEpisode(episodeId);
			if (e != null) {
				e.setVu(!e.isVu());
				DaoFactory.getInstance().getEpisodeDao().updateEpisode(e);
			}
		} catch (final DaoException e) {
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		final String redir = "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/liste");
		response.sendRedirect(redir);
	}

}
