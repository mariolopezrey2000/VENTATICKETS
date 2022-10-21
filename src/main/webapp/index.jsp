<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Reserva tus tiquets</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <link rel='stylesheet' type='text/css' media='screen' href='css.css'>
    
    <script src='main.js'></script>
</head>
<body>
    <div class="titulo">
        <h2 class="title">RESERVA</h2>
    </div>
    <div class="formularios">
        <div class="formularioUsuario">
            <h3>REGISTROS DE USUARIOS</h3>
            <form action="servlet" method="post">   
                <input type="text" id="fname1" name="nombre"placeholder="Nombre">
                <input type="text" id="fname1" name="apellido"placeholder="Apellido">
                <input type="text" id="fname1" name="DNI" placeholder="Dni">
                <input type="text" id="fname1" name="correo" placeholder="Correo">
                <input type="text" id="fname1" name="telf" placeholder="Numero De Telefono">
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="a"checked>
                <h4><%
                    if(request.getAttribute("mensajeu")!=null){
                        out.println(request.getAttribute("mensajeu"));
                    }
                %></h4>
                <br>
                
                <br>
                <input type="submit" value="Enivar"  id="submit">
            </form>
        </div>
        <div class="formularioCompra">
            <h3>COMPRA</h3>
            <form  action="servlet" method="post">
                <input type="text" id="fname1" name="DNI"placeholder="Dni">
                <select class="selectdeespectaculos" id="fname1" name="tipoespectaculo">
                    <option value="PARTIDO_FUTBOL">Partido De Futbol</option>
                    <option value="CONCIERTO">Concierto</option>
                    <option value="TEATRO" selected>Teatr</option>
                </select>
                <input type="number" id="fname1" name="numentradas"  placeholder="Numero De Entradas">
                <input type="date" id="fname1" name="fecha" placeholder="Fecha">
                <input type="time" id="fname1" name="hora">
                <br>
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="b"checked> 
                <h4><%
                    if(request.getAttribute("mensajeC")!=null){
                        out.println(request.getAttribute("mensajeC"));
                    }
                %></h4> 
                <input type="submit" value="Enivar"  id="submit">
                <h3 class="resultadocompra"></h3>
            </form>
        </div>
        <div class="formulariosconsultasyCancelaciones">
            <div class="formularioconsultas">
                <h3 class="formulario3">consulta compras</h3>
                <form method="post">  
                    <input class="formulario3" type="text" id="fname1" name="fname1"placeholder="Id Compra">
                    <br>
                    <input type="submit" value="Enivar"  id="submit">
                    <br>
                    <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="c"checked> 
                </form>
            </div>
            <div class="formulariocancelaciones">
                <h3 class="formulario3">Cancele su Compra</h3>
                <form method="post">   
                    <input class="formulario3" type="text" id="fname1" name="fname1"placeholder="Id Compra">
                    <input type="submit" value="Enivar"  id="submit">
                    <br>
                    <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="d" checked>
                    <br>
                </form>
            </div>
    </div>
    <div class="piedepagina">
        <p>COMPANY</p>
    </div>
</body>
</html>