import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class conexionbbdd
 */
@WebServlet("/conexionbbdd")
public class conexionbbdd extends HttpServlet {
        private static java.sql.Connection con;
		private static final String driver="com.mysql.cj.jdbc.Driver";
		private static final String user="root";
		private static final String pwd="";
		private static final String url="jdbc:mysql://localhost:3306/gestiontickets";
		private static Statement s;
		private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public conexionbbdd() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
			Class.forName(driver);
			con=DriverManager.getConnection(url,user,pwd);
			if (con==null) {
				System.out.println("no hay conexion");
			}else {
				System.out.println("hay conexion");
				s=con.createStatement();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	// TODO Auto-generated method stub
        GestionFormularios gf=new GestionFormularios();
		GestionFormularios.setS(s);
		GestionFormularios.setRequest(req);
		GestionFormularios.setResponse(resp);
		gf.gestion();
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
