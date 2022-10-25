package principal;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import clases.Evento;
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
				comprafinal();
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
	//metodo compra final el cual metera los datos a la base de datos.
	public void comprafinal() throws ServletException, IOException {
		//consultar el precio del evento
		if (comprobartodosdatoscompra()) {
			String sql="select PRECIO_VENTA from eventos where ID_EVENTO='3'";
			String precio=request.getParameter("numentradas");
			System.out.println(precio+"p");
			double preciod=0;
			try {
				ResultSet rs=s.executeQuery(sql);
				while (rs.next()) {
					preciod=rs.getDouble("PRECIO_VENTA");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//calcular precio total
			 double preciofinal=preciod*Double.parseDouble(request.getParameter("numentradas"));
			 System.out.println(preciofinal);
			 sql="select ID_EVENTO from eventos where NOMBRE='"+request.getParameter("evento")+"' and FECHA='"+request.getParameter("selectdedias")+"' and HORA='"+request.getParameter("selectdehoras")+"'";
				int idevento=0;
				try {
					ResultSet rs=s.executeQuery(sql);
					while (rs.next()) {
						idevento=rs.getInt("ID_EVENTO");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(consultaid());
				System.out.println(preciofinal);
				System.out.println(DNI);
				System.out.println(request.getParameter("evento"));
				System.out.println(idevento);
				System.out.println(request.getParameter("numentradas"));
				System.out.println(request.getParameter("selectdedias"));
				System.out.println(request.getParameter("selectdehoras"));
				//insertar datos de la compra en la bbdd
				sql="insert into r_compras (ID_COMPRA,PRECIO_TOTAL,DNI_USUARIO,NOMBRE_EVENTO,ID_EVENTO,N_ENTRADAS,FECHA,HORA) values("+consultaid()+" , "+preciofinal+" , "+DNI+" , '"+request.getParameter("evento")+"',"+idevento+" , "+request.getParameter("numentradas")+" , '"+request.getParameter("selectdedias")+"' , '"+request.getParameter("selectdehoras")+"')";
				try {
					s.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else {
			request.setAttribute("DNI", DNI);
			
			request.setAttribute("evento",nevento);
			request.setAttribute("mensajerror", "faltan datos");
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
		
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
				nevento=rs.getString("NOMBRE");
				e.setFECHA(rs.getString("FECHA"));
				e.setHORA(rs.getString("HORA"));
				e.setPRECIO_VENTA(rs.getDouble("PRECIO_VENTA"));
				eventos.add(e);
			}
			String numeroentradas=request.getParameter("N_E");
			request.setAttribute("DNI", DNI);
			request.setAttribute("lista", eventos);	
			request.setAttribute("evento",nevento);
			request.setAttribute("numeroE", numeroentradas);
			System.out.println(numeroentradas);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
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
		listadatos[1] = request.getParameter("evento");
		listadatos[2] = request.getParameter("numentradas");
		if (request.getParameter("selectdedias")==null) {
			listadatos[3]="";
		}else {
			listadatos[3] = request.getParameter("selectdedias");
		}
		if (request.getParameter("selectdedias")==null) {
			listadatos[4]="";
		}else {
			listadatos[4] = request.getParameter("selectdehoras");
		}
		
		
		for (int i = 0; i < listadatos.length; i++) {
			if (listadatos[i].equals("") || listadatos[i].equals("null")|| listadatos[i]== null) {
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
