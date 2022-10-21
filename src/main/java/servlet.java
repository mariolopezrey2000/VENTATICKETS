import java.io.IOException;

import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class servlet
 */

public class servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public servlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    private static java.sql.Connection con;
	private static final String driver="com.mysql.cj.jdbc.Driver";
	private static final String user="root";
	private static final String pwd="";
	private static final String url="jdbc:mysql://localhost:3306/compras";
	private static Statement s;

	@Override
    public void init(ServletConfig config) throws ServletException {
    	// TODO Auto-generated method stub
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		GestionFormularios gf=new GestionFormularios();
		GestionFormularios.setS(s);
		GestionFormularios.setRequest(request);
		GestionFormularios.setResponse(response);
		gf.gestion();
		
		/*response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    out.println("<html>");
    	out.println("<body>");
    	String sql="select * from usuario";
    	try {
			ResultSet resultado=s.executeQuery(sql);
			out.println("<ul>");
			while (resultado.next()) {
				out.println("<li>"+resultado.getString("nombre")+"</li>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	out.println("</body>");
    	out.println("</html>");
	    
	    
	    
		/*String sql="select * from usuario";
		try {
			ResultSet resultado=s.executeQuery(sql);
			while (resultado.next()) {
				System.out.println(resultado.getInt("DNI"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
	