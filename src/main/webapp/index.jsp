<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="clases.Evento"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Reserva tus tiquets</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='css.css'>
    <script src='main.js'></script>
</head>

<body>
    <div class="titulo">
        <h2 class="title">RESERVA TICKETS</h2>
    </div>
    <div class="formularios">
        <div class="formularioCompra">
            <h3>COMPRA</h3>
            <form action="servlet" method="post">
                <br><h4>
                <% out.println("DNI:"+request.getAttribute("DNI")); %>
                </h4>
                
               
                <br>
                <select class="select" id="fname1" name="selectdeespectaculos" class="selectdeespectaculos" name="selectdeespectaculos">
                    <option value="PARTIDO_FUTBOL">Partido De Futbol</option>
                    <option value="CONCIERTO">Concierto </option>
                    <option value="TEATRO">Teatro</option>
                </select>
                <br>
                <input type="text" id="N_E" name="N_E" placeholder="Numero De Entradas">
                <br>
                <input type="hidden"  id="tipo" name="tipo" value="comprobar">
                <button type="submit" class="button1" name="button">Comprobar</button>
                <br>
              </form>
              <form action="servlet" method="post">
             <input type="hidden" name="DNI"  id="DNI" value="<%=(String)request.getAttribute("DNI")%>">
                <input type="hidden" name="evento"  id="evento" value="<%=(String)request.getAttribute("evento")%>">
                <input type="text" name="numentradas"  id="numentradas" value="<%=(String)request.getAttribute("numeroE")%>">
                <select class="select" id="fname1" name="selectdedias">
                    <% ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("lista");
                    	
                        if(eventos != null){
                            for(Evento evento : eventos){
                                out.println("<option value='"+evento.getFECHA()+"'>"+evento.getFECHA()+"</option>");
                            };
                        };
                    %>
                </select>
                <br>
                <select class="select" id="fname1" name="selectdehoras">
                <% ArrayList<Evento> ev = (ArrayList<Evento>) request.getAttribute("lista"); 
                        if(ev != null){
                            for(Evento event : ev){
                                out.println("<option value='"+event.getHORA()+"'>"+event.getHORA()+"</option>");
                            };
                        };
                %>
                </select>
                <br>
                <input type="hidden"  id="tipo" name="tipo" value="b">
                <br>
                <h4><%
                    if(request.getAttribute("mensajerror")!=null){
                        out.println(request.getAttribute("mensajerror"));
                    }
                %></h4>
                <br>   
                <input type="submit" value="Enivar"  id="submit">
                <br>
                
            </form>
        </div>
        <div class="formulariosconsultas">
            <div class="formularioconsultas">
                <h3 class="formulario3">CONSULTA SUS COMPRAS</h3>
                <form method="post">  
                    <input class="formulario3" type="text" id="fname1" name="fname1"placeholder="Dni">
                    <br>
                    <input type="submit" value="Enivar"  id="submit">
                    <br>
                    <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="c"checked> 
                </form>
            </div>
            
        </div>
    </div>
    <div class="piedepagina">
        <p>COMPANY</p>
    </div>
</body>
</html>