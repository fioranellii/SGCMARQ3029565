<%@page import="aulas.servlet.getpost.InformacaoFormulario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Dados do Formulário</title>
    </head>
    <body>
        
        <% ArrayList<InformacaoFormulario> dados = (ArrayList<InformacaoFormulario>) request.getAttribute("dados"); %>
        
        <h1>Dados do Formulário</h1>
        <table>
            
            <tr>
                <th>Campo A</th>
                <th>Opção A</th>
                <th>Opção B</th>
            </tr>
            
            <% if( dados != null ) {%>
            <% for( InformacaoFormulario info : dados ) { %>
            <tr>
                <td><%= info.getCampoA() %></td>
                <td><%= info.getOpcaoA() %></td>
                <td><%= info.getOpcaoB() %></td>
            </tr>
            <% } %>
            <% } %>
                
        </table></br>
            
        <a href="<%= request.getContextPath() %>/aulas/jsp/formulario.jsp">Adicionar Informação</a>    
    </body>
</html>
