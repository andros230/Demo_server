import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MainServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String lng = request.getParameter("Lng");
		String lat = request.getParameter("Lat");
		String mac = request.getParameter("Mac");

		try {
			new DB_Kit().addData(mac, lng, lat);
			String rs = new DB_Kit().select("select * from location where mac !='" + mac + "'");
			PrintWriter writer = response.getWriter();
			writer.write(rs);
			writer.flush();
			writer.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
