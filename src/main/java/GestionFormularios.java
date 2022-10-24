import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestionFormularios {
	private static Statement s;
	private static String DNI;
	private static HttpServletRequest request;
	private static HttpServletResponse response;

	public static HttpServletRequest getRequest() {
		return request;
	}

	public static void setRequest(HttpServletRequest request) {
		GestionFormularios.request = request;
	}

	public static HttpServletResponse getResponse() {
		return response;
	}

	public static void setResponse(HttpServletResponse response) {
		GestionFormularios.response = response;
	}

	public static Statement getS() {
		return s;
	}

	public static void setS(Statement s) {
		GestionFormularios.s = s;
	}

	public void gestion() throws ServletException, IOException {
		switch (request.getParameter("tipo")) {
			case "iniciar":
				iniciarpagina();
				break;
			case "a":
				registrousuario();
				DNI=request.getParameter("DNI");
					//request.setAttribute("DNI", DNI);
				iniciarpagina();
				break;
			case "b":
				break;
			case "comprobar":
				cfonsultacompra();
				break;
			case "d":
				cancelacion();
				break;
			case "consultausuario":
				if(consultarusuario()){
					DNI=request.getParameter("DNI");
					//request.setAttribute("DNI", DNI);
					iniciarpagina();
				}else{
					RequestDispatcher rd=request.getRequestDispatcher("inicio.jsp");
					request.setAttribute("error", "Usuario no registrado");
					rd.forward(request, response);
				}
				break;
		}

	}
	public void iniciarpagina() throws ServletException, IOException {
		//consultar eventos disponibles y enviarselo a la pagina
		String sql="select distinct NOMBRE from eventos";
		ArrayList<Evento> eventos=new ArrayList<Evento>();
		try {
			ResultSet rs=s.executeQuery(sql);
			while (rs.next()) {
				Evento e=new Evento();
				e.setNOMBRE(rs.getString("NOMBRE"));
				eventos.add(e);
			}
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		//ESCRIBIR EL ARRAYLIST EVENTOS EN LA PAGINA EN FORMA DE SELECT
		request.setAttribute("lista", eventos);
		request.setAttribute("DNI", DNI);
		rd.forward(request, response);
	}
	//metodo que coge de la base de datos los dias y horas disponibles del tipo de espectaculos
	public void cfonsultacompra() throws ServletException, IOException {
		String sql="select * from eventos where NOMBRE='"+request.getParameter("selectdeespectaculos")+"'";
		ArrayList<Evento> eventos=new ArrayList<Evento>();
		try {
			ResultSet rs=s.executeQuery(sql);
			while (rs.next()) {
				Evento e=new Evento();
				e.setID_EVENTO(rs.getInt("ID_EVENTO"));
				e.setNOMBRE(rs.getString("NOMBRE"));
				e.setFECHA(rs.getString("FECHA"));
				e.setHORA(rs.getString("HORA"));
				e.setPRECIO_VENTA(rs.getDouble("PRECIO_VENTA"));
				eventos.add(e);
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("DNI", DNI);
			request.setAttribute("lista", eventos);	
			rd.forward(request, response);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public void registrousuario() throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("inicio.jsp");
		if (consultarusuario()) {
			System.out.println("usuario encontrado");
			request.setAttribute("mensajeu", "Usuario ya está dado de alta");
		} else {
			if (comprobartodosdatosusuario()) {
				
				//insertar datos del usuario en la bbdd
				String sql="insert into usuario (DNI,NOMBRE,CORREO,NUMEROTELEFONO) values('"+request.getParameter("DNI")+"','"+request.getParameter("NOMBRE")+"_"+request.getParameter("APELLIDO")+"','"+request.getParameter("CORREO")+"','"+request.getParameter("NUMEROTELEFONO")+"')";
				try {
					s.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rd=request.getRequestDispatcher("index.jsp");
			} else {
				System.out.println("faltan datos");
				request.setAttribute("mensajeu", "faltan datos");
			}
		}
		rd.forward(request, response);
	}

	public Integer consultaid() {
		String sql = "select ID_COMPRA from r_compras";
		ArrayList<Integer> listabbdd = new ArrayList<Integer>();
		try {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				listabbdd.add(rs.getInt("ID_COMPRA"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int num = 1;
		if (listabbdd.size() != 0) {
			Collections.sort(listabbdd);
			for (int i = 0; i < listabbdd.size(); i++) {
				if (listabbdd.get(i) == i + 1) {
					num = i + 1;
				} else {
					return i + 1;
				}
			}
			return num + 1;
		} else {
			return 1;
		}
	}

	public void cancelacion() {

	}

	public boolean comprobartodosdatosusuario() {
		boolean todos = true;
		String[] listadatos = new String[5];
		listadatos[0] = request.getParameter("NOMBRE");
		listadatos[1] = request.getParameter("APELLIDO");
		listadatos[2] = request.getParameter("DNI");
		listadatos[3] = request.getParameter("CORREO");
		listadatos[4] = request.getParameter("NUMEROTELEFONO");
		for (int i = 0; i < listadatos.length; i++) {
			if (listadatos[i].equals("")) {
				todos = false;
			}
		}
		return todos;
	}

	public boolean comprobartodosdatoscompra() {
		boolean todos = true;
		String[] listadatos = new String[5];
		listadatos[0] = request.getParameter("DNI");
		listadatos[1] = request.getParameter("tipoespectaculo");
		listadatos[2] = String.valueOf(request.getParameter("numentradas"));
		listadatos[3] = request.getParameter("fecha");
		listadatos[4] = request.getParameter("hora");
		for (int i = 0; i < listadatos.length; i++) {
			if (listadatos[i].equals("") || listadatos[i] == null) {
				todos = false;
			}
		}
		return todos;
	}

	public boolean consultarusuario() {
		ResultSet rs;
		boolean encontrado = false;
		String comprobarusuario = "select * from usuario where DNI=" + request.getParameter("DNI");
		try {
			rs = s.executeQuery(comprobarusuario);
			while (rs.next()) {
				if (rs.getInt("DNI") == Integer.parseInt(request.getParameter("DNI"))) {
					encontrado = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encontrado;
	}

}
