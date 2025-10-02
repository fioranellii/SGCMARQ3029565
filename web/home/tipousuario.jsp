<%@page import="model.TipoUsuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tipo Usuario</title>
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
                        <a href="<%= request.getContextPath()%>/home/tipousuario_form.jsp?action=update&id=<%= tp.getId() %>">Alterar</a>
                        <a href="<%= request.getContextPath()%>/home/tipousuario?action=delete&id=<%= tp.getId() %>" onclick="return confirm('Deseja excluir TipoUsuario <%= tp.getId()%> (<%= tp.getNome() %>) ?')" >Excluir</a>
                    </td>
                </tr>
                <% }%>
            </tbody>
        </table>
        </div>
        <a href="<%= request.getContextPath()%>/home/tipousuario_form.jsp?action=create">Adicionar</a>
    </body>
</html>