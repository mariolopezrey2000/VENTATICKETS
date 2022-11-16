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
    ArrayList<String> horas=null;
    String numeroentradas="";
    String evento="";
    String e="";
    String ne="";
    String fecha="";
    try {
        compras = (ArrayList<r_compras>) request.getAttribute("compras");
        eventos = (ArrayList<Evento>) request.getAttribute("lista");
        numeroentradas=(String) request.getAttribute("numeroE");
        evento=(String) request.getAttribute("evento");
        horas=(ArrayList<String>) request.getAttribute("horas");
        e=(String) request.getAttribute("e");
        ne=(String) request.getAttribute("ne");
        fecha=(String) request.getAttribute("fecha");
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
                <input type="hidden" id="tipo" name="tipo" value="FORMULARIO_COMPROBAR_EVENTOS">
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
                <% 
                if(e!=null){
                    out.println("<label>Evento:</label><br><input type='text' name='evento' value='"+e+"'><br>");
                }else{
                    if (evento!=null){
                        out.println("<label>Evento:</label><br><input type='text' name='evento' value='"+evento+"'><br>");
                        }else{
                        out.println("<label>Evento:</label><br><input type='text' name='evento' value=''><br>");
                        };
                };
                
                if(ne!=null){
                    out.println("<label>Numero entradas: </label><br><input type='text' name='numentradas' value='"+ne+"'><br>");
                }else{
                    if (numeroentradas!=null){
                        out.println("<label>Numero entradas: </label><br><input type='text' name='numentradas' value='"+numeroentradas+"'><br>");
                        }else{
                        out.println("<label>Numero entradas: </label><br><input type='text' name='numentradas' value=''><br>");
                        };
                };    
                %>
                <select class="select" id="fname1" name="selectdedias">
                    <% 
                    if(fecha!=null){
                        out.println("<option value='"+fecha+"'>"+fecha+"</option>");
                    }else{
                        if(eventos!=null){
                            for(Evento ev : eventos){
                                if(ev.getFECHA() != null){
                                    out.println("<option value='"+ev.getFECHA()+"'>"+ev.getFECHA()+"</option>");
                                }
                            }};
                    };
                    %>
                </select>
                <br>
                <br>
                <input type="hidden" id="tipo" name="tipo" value="FORMULARIOHORA">
                <br>
                <input type="submit" value="comprobar" id="submit">
                <br>
            </form>
            <form action="servlet" method="post">
                <select class="select" id="fname1" name="selectdehoras">
                    <%
                        if(horas!=null){
                        for(String h : horas){
                        if(h != null){
                        out.println("<option value='"+h+"'>"+h+"</option>");
                        }
                        }};
                    %>
                </select>
                <input type="hidden" id="tipo" name="tipo" value="FORMLARIOCOMPRA">
                <input type="hidden" name="e" id="e" value="<%=(String)request.getAttribute("e")%>">
                <input type="hidden" name="ne" id="ne" value="<%=(String)request.getAttribute("ne")%>">
                <input type="hidden" name="f" id="f" value="<%=(String)request.getAttribute("fecha")%>">
                <input type="hidden" name="DNI" id="DNI" value="<%=(String)request.getAttribute("DNI")%>">
                <h4><%
                    if(request.getAttribute("mensajerror")!=null){
                        out.println(request.getAttribute("mensajerror"));
                    }
                    %>
                </h4>
                <input type="submit" value="COMPRAR" id="submit">
            </form>
        </div>
        <div class="formularioconsultas">
            <h3 class="formulario3">CONSULTA SUS COMPRAS</h3>
            <form method="post">
                <% 
                    if(compras!=null && compras.size()>0){
                        for(r_compras compra : compras){
                            out.println("<form action='servlet' method='post'>");
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
                            out.println("<input type='hidden' id='tipo' name='tipo' value='ELIMINAR'>");
                            out.println("<input type='hidden' id='ID' name='ID' value='"+compra.getID_COMPRA()+"'>");
                            out.println("<td><input type='submit' value='ELIMINAR' id='submit'></td>");
                            out.println("<tr>");
                            out.println("</form>");
                            out.println("<br>");
                        }
                    }else{
                        out.println("<h4>No hay compras</h4>");
                    }
                    %>
            </form>
        </div>
    </div>
    <div class="piedepagina" style="float: left;">
        <p>COMPANY</p>
    </div>
</body>

</html>