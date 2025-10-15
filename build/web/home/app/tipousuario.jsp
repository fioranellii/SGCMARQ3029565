<%@page import="model.TipoUsuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tipo Usuario</title>
        <style>
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            .container{
                width: 80%;
                margin: auto;
            }

            body {
                font-family: 'Arial', sans-serif;
                background-color: #333;
                color: #2c3e50;
                line-height: 1.6;
                padding: 20px;
            }

            h1 {
                font-size: 2.5rem;
                color: #fff;
                margin-bottom: 20px;
                text-align: center;
            }

            table {
                width: 100%;
                max-width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            th, td {
                padding: 12px;
                text-align: center;
                border: 1px solid #ddd;
            }

            th {
                background-color: #3498db;
                color: white;
            }

            tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            tr:hover {
                background-color: #ecf0f1;
            }

            a {
                display: inline-block;
                background-color: #3498db;
                color: #fff;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 4px;
                margin-top: 20px;
                text-align: center;
            }

            a:hover {
                background-color: #2980b9;
            }

            /* Responsividade */
            @media (max-width: 768px) {
                table {
                    font-size: 0.9rem;
                }

                h1 {
                    font-size: 2rem;
                }

                a {
                    padding: 8px 16px;
                }
            }
        </style>
    </head>
    <body>
        <% ArrayList<TipoUsuario> dados = new TipoUsuario().getAllTableEntities(); %>
        <h1>Tipo Usuario</h1>
        <div class="container">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Administrativo</th>
                    <th>Agendamento</th>
                    <th>Atendimento</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <% for(TipoUsuario tp : dados) { %>
                <tr>
                    <td><%= tp.getId() %></td>
                    <td><%= tp.getNome() %></td>
                    <td><%= tp.getModuloAdministrativo()%></td>
                    <td><%= tp.getModuloAgendamento()%></td>
                    <td><%= tp.getModuloAtendimento()%></td>
                    <td> 
                        <a href="<%= request.getContextPath()%>/home/app/tipousuario_form.jsp?action=update&id=<%= tp.getId() %>">Alterar</a>
                        <a href="<%= request.getContextPath()%>/home?action=delete&id=<%= tp.getId() %>&task=tipousuario" onclick="return confirm('Deseja excluir TipoUsuario <%= tp.getId()%> (<%= tp.getNome() %>) ?')" >Excluir</a>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>
        </div>
        <a href="<%= request.getContextPath()%>/home/app/tipousuario_form.jsp?action=create">Adicionar</a>
    </body>
</html>
