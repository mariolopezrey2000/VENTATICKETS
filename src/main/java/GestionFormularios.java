package principal;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import clases.Evento;
import clases.r_compras;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GestionFormularios {
	private static Statement s;
	private static String DNI;
	private static String nevento;
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
			case "FORMULARIOREGISTROUSUARIO":
				registrousuario();
				break;
			case "FORMULARIOINICIOSESION":
				if (consultarusuario()) {
					DNI = request.getParameter("DNI");
					// request.setAttribute("DNI", DNI);
					iniciarpagina();
				} else {
					RequestDispatcher rd = request.getRequestDispatcher("inicio.jsp");
					request.setAttribute("error", "Usuario no registrado");
					rd.forward(request, response);
				}
				break;
			case "FORMULARIO_COMPROBAR_EVENTOS":
				cfonsultacompra();
				break;
			case "FORMLARIOCOMPRA":
				comprafinal();
				iniciarpagina();
				break;
			case "FORMULARIOHORA":
				comprobarhoras();
				break;
			case "ELIMINAR":
				cancelacion();
				iniciarpagina();
				break;
		}

	}
	public ArrayList<r_compras> consultacompras() throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		// ESCRIBIR EL ARRAYLIST EVENTOS EN LA PAGINA EN FORMA DE SELECT
		// hace un select de todas las compras relacionadas con ese dni
		String sql = "select * from r_compras where DNI_USUARIO='" + DNI + "'";
		ArrayList<r_compras> compras = new ArrayList<r_compras>();
		try {
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				r_compras c = new r_compras();
				c.setID_COMPRA(rs.getInt("ID_COMPRA"));
				c.setPRECIO_TOTAL(rs.getDouble("PRECIO_TOTAL"));
				c.setDNI_USUARIO(rs.getString("DNI_USUARIO"));
				c.setNOMBRE_EVENTO(rs.getString("NOMBRE_EVENTO"));
				c.setID_EVENTO(rs.getInt("ID_EVENTO"));
				c.setN_ENTRADAS(rs.getInt("N_ENTRADAS"));
				c.setFECHA_EVENTO(rs.getString("FECHA"));
				c.setHORA_EVENTO(rs.getString("HORA"));
				compras.add(c);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return compras;
	}

	public void iniciarpagina() throws ServletException, IOException {
		// consultar eventos disponibles y enviarselo a la pagina
		String sql = "select distinct NOMBRE from eventos where N_E_DISPONIBLES > 0 ";
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		try {
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				Evento e = new Evento();
				e.setNOMBRE(rs.getString("NOMBRE"));
				eventos.add(e);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		// ESCRIBIR EL ARRAYLIST EVENTOS EN LA PAGINA EN FORMA DE SELECT
		// hace un select de todas las compras relacionadas con ese dni
		
		
		request.setAttribute("compras", consultacompras());
		request.setAttribute("lista", eventos);
		request.setAttribute("DNI", DNI);
		rd.forward(request, response);
	}
	public void registrousuario() throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("inicio.jsp");
		if (consultarusuario()) {
			System.out.println("usuario encontrado");
			request.setAttribute("mensajeu", "Usuario ya está dado de alta");
			rd.forward(request, response);
		} else {
			if (comprobartodosdatosusuario()) {

				// insertar datos del usuario en la bbdd
				String sql = "insert into usuario (DNI,NOMBRE,CORREO,NUMEROTELEFONO) values('"
						+ request.getParameter("DNI") + "','" + request.getParameter("NOMBRE") + "_"
						+ request.getParameter("APELLIDO") + "','" + request.getParameter("CORREO") + "','"
						+ request.getParameter("NUMEROTELEFONO") + "')";
				try {
					s.executeUpdate(sql);
					DNI = request.getParameter("DNI");
					request.setAttribute("DNI",DNI);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iniciarpagina();
			} else {
				System.out.println("faltan datos");
				request.setAttribute("mensajeu", "faltan datos");
				rd.forward(request, response);
			}
		}
		
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
	public void comprobarhoras() {
		RequestDispatcher rd= request.getRequestDispatcher("index.jsp");
		String sql="select * from eventos where FECHA='"+request.getParameter("selectdedias")+"' and NOMBRE='"+request.getParameter("evento")+"'";
		ArrayList<String>horas=new ArrayList<String>();
		DNI=request.getParameter("DNI");
		try {
			ResultSet rs=s.executeQuery(sql);
			while(rs.next()) {
				horas.add(rs.getString("HORA"));
			}
			request.setAttribute("horas", horas);
			request.setAttribute("DNI", DNI);
			request.setAttribute("compras", consultacompras());
			request.setAttribute("e",request.getParameter("evento"));
			request.setAttribute("ne",request.getParameter("numentradas"));
			request.setAttribute("fecha", request.getParameter("selectdedias"));
			
			rd.forward(request, response);
		} catch (SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// metodo compra final el cual metera los datos a la base de datos.
	public void comprafinal() throws ServletException, IOException {
		// consultar el precio del evento
		if (comprobartodosdatoscompra()) {
			//CONSULTA PARA SABER PRECIO VENTA DE UN EVENTO TENIENDO EN CUENTA EL NOMBRE ,FECHA Y HORA
			int idevento = 0;
			double PRECIO_VENTA = 0;
			String NUMEROENTRADAS = request.getParameter("ne");
			String sql="";
			System.out.println(NUMEROENTRADAS + "p");
			DNI=request.getParameter("DNI");
			try {
				//CONSULTAR ID DEL EVENTO
				sql = "select ID_EVENTO from eventos where NOMBRE='" + request.getParameter("e") + "' and FECHA='"
					+ request.getParameter("f") + "' and HORA='" + request.getParameter("selectdehoras")
					+ "'";
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) {
					idevento = rs.getInt("ID_EVENTO");
				}
				//CONSULTAR PRECIO DEL EVENTO
				sql = "select PRECIO_VENTA from eventos where ID_EVENTO="+idevento+"";
				rs = s.executeQuery(sql);
				while (rs.next()) {
					PRECIO_VENTA = rs.getDouble("PRECIO_VENTA");
				}
				double preciofinal = PRECIO_VENTA * Double.parseDouble(NUMEROENTRADAS);
				System.out.println(preciofinal);
				sql = "insert into r_compras (ID_COMPRA,PRECIO_TOTAL,DNI_USUARIO,NOMBRE_EVENTO,ID_EVENTO,N_ENTRADAS,FECHA,HORA) values("
					+ consultaid() + " , " + preciofinal + " , " + DNI + " , '" + request.getParameter("e") + "',"
					+ idevento + " , " + request.getParameter("ne") + " , '"
					+ request.getParameter("f") + "' , '" + request.getParameter("selectdehoras") + "')";
				s.executeUpdate(sql);
				sql = "update eventos set N_E_DISPONIBLES=N_E_DISPONIBLES-" + request.getParameter("ne")
					+ " where ID_EVENTO=" + idevento;
				s.executeUpdate(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			request.setAttribute("compras", consultacompras());
			request.setAttribute("DNI", DNI);
			request.setAttribute("evento", nevento);
			request.setAttribute("mensajerror", "faltan datos");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

	}

	public void cfonsultacompra() throws ServletException, IOException {
		if (!request.getParameter("N_E").equals("")) {
			Integer ne = Integer.parseInt(request.getParameter("N_E"));
			String sql = "select * from eventos where NOMBRE='" + request.getParameter("selectdeespectaculos")
					+ "' AND N_E_DISPONIBLES>=" + ne + "";
			ArrayList<Evento> eventos = new ArrayList<Evento>();
			try {
				ResultSet rs = s.executeQuery(sql);
				while (rs.next()) {
					Evento e = new Evento();
					e.setID_EVENTO(rs.getInt("ID_EVENTO"));
					e.setNOMBRE(rs.getString("NOMBRE"));
					nevento = rs.getString("NOMBRE");
					e.setFECHA(rs.getString("FECHA"));
					e.setHORA(rs.getString("HORA"));
					e.setPRECIO_VENTA(rs.getDouble("PRECIO_VENTA"));
					eventos.add(e);
				}
				String numeroentradas = request.getParameter("N_E");
				request.setAttribute("DNI", DNI);
				request.setAttribute("compras", consultacompras());
				request.setAttribute("lista", eventos);
				request.setAttribute("evento", nevento);
				request.setAttribute("numeroE", numeroentradas);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block

			}
		} else {
			
			request.setAttribute("aviso", "introduzca el numero de entradas");
			request.setAttribute("DNI", DNI);
			request.setAttribute("compras", consultacompras());
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

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
		//ACTUALIZAR LAS ENTRADAS DISPONIBLES DE EVENTOS, SUMANDO LAS ENTRADAS VENDIDAS DE R_EVENTOS
		try {
			String sql = "select * from r_compras where ID_COMPRA='" + request.getParameter("ID")+"'";
			ResultSet rs = s.executeQuery(sql);
			Integer n_e=0;
			Integer id_evento=0;
			while (rs.next()) {
				n_e=rs.getInt("N_ENTRADAS");
				id_evento=rs.getInt("ID_EVENTO");
			}
			sql = "update eventos set N_E_DISPONIBLES=N_E_DISPONIBLES+" + n_e
			+ " where ID_EVENTO=" + id_evento;
			s.executeUpdate(sql);
			sql = "delete  from r_compras where ID_COMPRA='" + request.getParameter("ID") + "'";
			s.executeUpdate(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
		}

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
		listadatos[1] = request.getParameter("e");
		listadatos[2] = request.getParameter("ne");
		listadatos[3] = request.getParameter("f");
		
		if (request.getParameter("selectdehoras") == null) {
			listadatos[4] = "0";
		} else {
			listadatos[4] = request.getParameter("selectdehoras");
		}

		for (int i = 0; i < listadatos.length; i++) {
			if (listadatos[i].equals("") || listadatos[i].equals("null") || listadatos[i] == null) {
				todos = false;
			}
		}
		return todos;
	}

	

}

