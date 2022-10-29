<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="clases.Evento"%>
<%@ page import="clases.r_compras"%>


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
    <%
    ArrayList<Evento> eventos = null;
    ArrayList<r_compras> compras=null;
    String numeroentradas="";
    try {
        compras = (ArrayList<r_compras>) request.getAttribute("compras");
        eventos = (ArrayList<Evento>) request.getAttribute("lista");
        numeroentradas=(String) request.getAttribute("numeroE");
        
    } catch (Exception ex) {
        
    }
%>
    <div class="titulo">
        <h2 class="title">RESERVA TICKETS</h2>
    </div>
    <div class="formularios">
        <div class="formularioCompra">
            <h3>COMPRA</h3>
            <form action="servlet" method="post">
                <br>
                <h4>
                    <% out.println("DNI:"+request.getAttribute("DNI")); %>
                </h4>


                <br>
                <select class="select" id="fname1" name="selectdeespectaculos" class="selectdeespectaculos"
                    name="selectdeespectaculos">
                    <option value="PARTIDO_FUTBOL">Partido De Futbol</option>
                    <option value="CONCIERTO">Concierto </option>
                    <option value="TEATRO">Teatro</option>
                </select>
                <br>
                <input type="text" id="N_E" name="N_E" placeholder="Numero De Entradas">
                <br>
                <input type="hidden" id="tipo" name="tipo" value="comprobar">
                <h4><%
                    if(request.getAttribute("aviso")!=null){
                        out.println(request.getAttribute("aviso"));
                    }
                %></h4>
                <button type="submit" class="button1" name="button">Comprobar</button>
                <br>
            </form>
            <form action="servlet" method="post">
                <input type="hidden" name="DNI" id="DNI" value="<%=(String)request.getAttribute("DNI")%>">
                <input type="hidden" name="evento" id="evento" value="<%=(String)request.getAttribute("evento")%>">
                <%if (numeroentradas!=null){
                out.println("<input type='text' name='numentradas' value='"+numeroentradas+"'>");
                }else{
                out.println("<input type='text' name='numentradas' value=''>");
                }%>
                <select class="select" id="fname1" name="selectdedias">
                    <% 
                        if(eventos!=null){
                            for(Evento evento : eventos){
                                if(evento.getFECHA() != null){
                                    out.println("<option value='"+evento.getFECHA()+"'>"+evento.getFECHA()+"</option>");
                                }
                            }};
                        
                    %>
                </select>
                <br>
                <select class="select" id="fname1" name="selectdehoras">
                <%
                if(eventos!=null){
                for(Evento evento : eventos){
                    if(evento.getHORA() != null){
                        out.println("<option value='"+evento.getHORA()+"'>"+evento.getHORA()+"</option>");
                    }
                }};
            
                %>
                </select>
                <br>
                <input type="hidden" id="tipo" name="tipo" value="b">
                <br>
                <h4><%
                    if(request.getAttribute("mensajerror")!=null){
                        out.println(request.getAttribute("mensajerror"));
                    }
                %></h4>
                <br>
                <input type="submit" value="Enivar" id="submit">
                <br>

            </form>
        </div>
        <div class="formulariosconsultas">
            <div class="formularioconsultas">
                <h3 class="formulario3">CONSULTA SUS COMPRAS</h3>
                <form method="post">
                    <% 
                    if(compras!=null){
                        for(r_compras compra : compras){
                            out.println("<table>");
                            out.println("<tr>");
                            out.println("<td>ID_COMPRA: "+compra.getID_COMPRA()+"</td>");
                            out.println("<td>PRECIO TOTAL: "+compra.getPRECIO_TOTAL()+"</td>");
                            out.println("<td>DNI_USUARIO: "+compra.getDNI_USUARIO()+"</td>");
                            out.println("<td>NOMBRE EVENTO: "+compra.getNOMBRE_EVENTO()+"</td>");
                            out.println("<td>ID_EVENTO: "+compra.getID_EVENTO()+"</td>");
                            out.println("<td>Numero de entradas: "+compra.getN_ENTRADAS()+"</td>");
                            out.println("<td>FECHA: "+compra.getFECHA_EVENTO()+"</td>");
                            out.println("<td>HORA: "+compra.getHORA_EVENTO()+"</td>");
                            out.println("<tr>");
                            out.println("<br>");
                        }
                    }else{
                        out.println("<h4>No hay compras</h4>");
                    }
                    %>
                </form>
            </div>

        </div>
    </div>
    <div class="piedepagina">
        <p>COMPANY</p>
    </div>
</body>

</html>