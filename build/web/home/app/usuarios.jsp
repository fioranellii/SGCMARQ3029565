<%@page import="model.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta htus-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuário</title>
    </head>
    <body>
        
        <% ArrayList<Usuario> dados = new Usuario().getAllTableEntities(); %>
        
        <h1>Usuário</h1>
        
        <table>
            
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>CPF</th>
                <th>Tipo Usuário</th>
                <th></th>
                <th></th>
            </tr>
            
            <% for( Usuario us : dados ) { %>
            <tr>
                <td><%= us.getId() %></td>
                <td><%= us.getNome() %></td>
                <td><%= us.getCpf() %></td>
                <td><%= us.getTipoUsuarioId() %></td>
                
                <td><a href="<%= request.getContextPath()  %>/home/app/usuarios_form.jsp?action=update&id=<%= us.getId()%>" >Alterar</a></td>
                
                <td><a href="<%= request.getContextPath() %>/home?action=delete&id=<%= us.getId()%>&task=usuarios" onclick="return confirm('Deseja realmente excluir Usuário <%= us.getId()%> (<%= us.getNome() %>) ?')">Excluir</a></td>
            </tr>
            <% } %>
            
        </table>
        
            <a href="<%= request.getContextPath()  %>/home/app/usuarios_form.jsp?action=create" >Adicionar</a>
    </body>
</html>
