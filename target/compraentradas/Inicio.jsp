<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
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
        <h2 class="title">RESERVA DE TICKETS</h2>
    </div>
    <div class="formularios">
        <div class="formularioUsuario">
            <h3>REGISTRO DE USUARIOS</h3>
            <form method="post" action="servlet">   
                <input type="text" id="fname1" name="NOMBRE"placeholder="Nombre">
                <input type="text" id="fname1" name="APELLIDO"placeholder="Apellido">
                <input type="text" id="fname1" name="DNI" placeholder="Dni">
                <input type="text" id="fname1" name="CORREO" placeholder="Correo">
                <input type="text" id="fname1" name="NUMEROTELEFONO" placeholder="Numero De Telefono">
                <br>
                <h4><%
                    if(request.getAttribute("mensajeu")!=null){
                        out.println(request.getAttribute("mensajeu"));
                    }
                %></h4> 
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="a"checked>
                <br>
                <input type="submit" value="Enivar"  id="submit">
            </form>
        </div>
        <div class="formulariosconsultas">
            <h3>INICIO</h3>
            <form method="post"  action="servlet">   
                <input type="text" id="fname1" name="DNI"placeholder="Dni">
                <br>
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="consultausuario"checked>
                <br>
                <%if(request.getAttribute("error")!=null){
                    out.println("<h4>"+request.getAttribute("error")+"</h4>");
                }%>
                <input type="submit" value="Enivar"  id="submit">
            </form>
            
            
        </div>
    </div>
    <div class="piedepagina">
        <p>COMPANY</p>
    </div>
</body>
</html>