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
        <h2 class="title">RESERVA TICKETS</h2>
    </div>
    <div class="formularios">
        <div class="formularioCompra">
            <h3>COMPRA</h3>
            <form method="post">
                <input type="text" id="fname1" name="fname1"placeholder="Dni">
                <br>
                <select class="select" id="fname1" id="selectdeespectaculos" class="selectdeespectaculos" name="selectdeespectaculos">
                    <option value="paypal">Partido De Futbol</option>
                    <option value="tarjeta">Concierto </option>
                    <option value="contrarembolso" selected>Teatro</option>
                </select>
                <br>
                <button type="submit" class="button1" name="button">Comprobar</button>
                <br>
                <input type="number" id="fname1" name="fname3" placeholder="Numero De Entradas">
                <br>
                <select class="select" id="fname1" name="selectdedias">
                </select>
                <br>
                <select class="select" id="fname1" name="selecdehoras">
                </select>
                <br>
                <input type="checkbox" name="tipo" id="tipo" class="checkbox" value="b"checked>
                <br>   
                <input type="submit" value="Enivar"  id="submit">
                <br><br>
                <h3 class="resultadocompra"></h3>
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