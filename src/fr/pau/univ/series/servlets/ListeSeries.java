package fr.pau.univ.series.servlets;

import java.io.IOException;

import fr.pau.univ.series.dao.DaoFactory;
import fr.pau.univ.series.exception.DaoException;
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
		try {
			request.setAttribute("series", DaoFactory.getInstance().getSerieDao().readAllSeries());
		} catch (final DaoException e) {
			request.setAttribute("erreur", e.getMessage());
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher("/ListeSeries.jsp").forward(request, response);
	}
}
