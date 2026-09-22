package fr.pau.univ.series.servlets;

import java.io.IOException;
import java.util.List;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
import fr.pau.univ.series.model.Serie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/liste")
public class ListeSeries extends HttpServlet {

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		ServletUtils.consumeFlash(request);
		final String q = request.getParameter("q");
		final boolean onlyAVoir = "1".equals(request.getParameter("aVoir"))
				|| "true".equalsIgnoreCase(request.getParameter("aVoir"));
		request.setAttribute("q", q == null ? "" : q);
		request.setAttribute("aVoir", onlyAVoir);
		try {
			final List<Serie> series = DaoFactory.getInstance().getSerieDao().findSeries(q, onlyAVoir);
			request.setAttribute("series", series);
		} catch (final DaoException e) {
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/ListeSeries.jsp").forward(request, response);
	}
}
