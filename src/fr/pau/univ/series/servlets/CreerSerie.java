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

@WebServlet(urlPatterns = "/creerSerie")
public class CreerSerie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreerSerie() {
	}

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String nom = request.getParameter("nomNvlleSerie");
		if (nom != null && !nom.isBlank()) {
			try {
				DaoFactory.getInstance().getSerieDao().createSerie(new Serie(nom));
			} catch (final DaoException e) {
				request.setAttribute("erreur", e.getMessage());
			}
		}
		final String redir = "http://".concat(request.getServerName()).concat(":")
				.concat(Integer.toString(request.getServerPort())).concat(request.getContextPath()).concat("/liste");
		response.sendRedirect(redir);
	}

}
