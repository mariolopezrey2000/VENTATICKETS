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
                <input type="hidden" name="DNI"  id="DNI" value="<%request.getAttribute("DNI");%>" placeholder="<%request.getAttribute("DNI");%>">
                <br>
                <select class="select" id="fname1" name="selectdeespectaculos" class="selectdeespectaculos" name="selectdeespectaculos">
                    <option value="PARTIDO_FUTBOL">Partido De Futbol</option>
                    <option value="CONCIERTO">Concierto </option>
                    <option value="TEATRO">Teatro</option>
                </select>
                <br>
                <input type="number" id="fname1" name="fname3" placeholder="Numero De Entradas">
                <br>
                <input type="hidden" name="tipo" value="comprobar">
                <button type="submit" class="button1" name="button">Comprobar</button>
                <br>
                <select class="select" id="fname1" name="selectdedias">
                    <% ArrayList<Evento> eventos = (ArrayList<Evento>) request.getAttribute("lista"); 
                        if(eventos.size()>0){
                            for(Evento evento : eventos){
                                out.println("<option value='"+evento.getFECHA()+"'>"+evento.getFECHA()+"</option>");
                            };
                        };
                    %>
                </select>
                <br>
                <select class="select" id="fname1" name="selecdehoras">
                <% ArrayList<Evento> event = (ArrayList<Evento>) request.getAttribute("lista"); 
                        if(event.size()>0){
                            for(Evento evento : event){
                                out.println("<option value='"+evento.getHORA()+"'>"+evento.getHORA()+"</option>");
                            }
                        };
                %>
                </select>
                <br>
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="b"checked>
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