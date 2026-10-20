
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BEAN.Persona"%>
<%
    if (request.getAttribute("lista") == null) {
        response.sendRedirect("PersonaDAO");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ventana de Mantenimiento</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 30px;
        }

        .contenedor {
            width: 650px;
            margin: auto;
            background-color: white;
            padding: 25px;
            border: 1px solid #ccc;
            border-radius: 8px;
        }

        h2, h3 {
            text-align: center;
        }

        .campo {
            margin-bottom: 15px;
        }

        label {
            display: inline-block;
            width: 100px;
            font-weight: bold;
        }

        input {
            width: 300px;
            padding: 8px;
            box-sizing: border-box;
        }

        .botones {
            margin-top: 20px;
            text-align: center;
        }

        button {
            padding: 9px 15px;
            margin: 4px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: white;
        }

        .agregar {
            background-color: #198754;
        }

        .modificar {
            background-color: #0d6efd;
        }

        .eliminar {
            background-color: #dc3545;
        }

        .limpiar {
            background-color: #6c757d;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #aaa;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #ddd;
        }
    </style>
</head>

<body>

<div class="contenedor">

    <h2>Mantenimiento de Personas</h2>

    <form action="PersonaDAO" method="post">

        <div class="campo">
            <label for="codigo">Código:</label>
            <input type="number" name="codigo" id="codigo"
                   required>
        </div>

        <div class="campo">
            <label for="nombre">Nombre:</label>
            <input type="text" name="nombre" id="nombre"
                   required>
        </div>

        <div class="campo">
            <label for="apellido">Apellido:</label>
            <input type="text" name="apellido" id="apellido"
                   required>
        </div>

        <div class="botones">
            <button type="submit" name="accion"
                    value="agregar" class="agregar">
                Agregar
            </button>

            <button type="submit" name="accion"
                    value="modificar" class="modificar">
                Modificar
            </button>

            <button type="submit" name="accion"
                    value="eliminar" class="eliminar"
                    formnovalidate
                    onclick="return confirm('¿Deseas eliminar este registro?');">
                Eliminar
            </button>

            <button type="reset" class="limpiar">
                Limpiar
            </button>
        </div>

    </form>

    <h3>Lista de Personas</h3>

    <table>
        <thead>
            <tr>
                <th>CÓDIGO</th>
                <th>NOMBRE</th>
                <th>APELLIDO</th>
            </tr>
        </thead>

        <tbody>
            <%
                ArrayList<Persona> lista =
                    (ArrayList<Persona>) request.getAttribute("lista");

                if (lista != null && !lista.isEmpty()) {
                    for (Persona p : lista) {
            %>
            <tr>
                <td><%= p.getCodigo() %></td>
                <td><%= p.getNombre() %></td>
                <td><%= p.getApellido() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="3">No hay personas registradas.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</div>

</body>
</html>
