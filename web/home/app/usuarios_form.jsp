<%@page import="model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuário</title>
    </head>
    <body>
        <% 
            Usuario us = null;
            
            String action = request.getParameter("action");
            if( action == null ) {
                action = "create";
            } else {
                if( action.equals("update") ) {
                    
                    int id = Integer.valueOf( request.getParameter("id") );
                    
                    us = new Usuario();
                    us.setId(id);
                    us.load();
                    
                }
            }            

        %>
        <h1>Tipo Usuário</h1>
        
        <form action="<%= request.getContextPath()  %>/home?action=<%= action %>&task=usuarios" method="post">
            
            <label for="id">Id:</label>
            <input type="text" id="id" name ="id" pattern="\d+" title="apenas dígitos" value="<%= ( us != null ) ? us.getId() : "" %>" <%= ( us != null ) ? "readonly" : "" %> required><br/>   
            
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name ="nome" value="<%= ( ( us != null ) && ( us.getNome() != null ) ) ? us.getNome() : "" %>" ><br/>
            
            <label for="cpf">CPF:</label>
            <input type="text" id="cpf" name ="cpf" pattern="\d{3}\.\d{3}\.\d{3}-\d{2}" title="DDD.DDD.DDD-DD" value="<%= ( ( us != null ) && ( us.getCpf() != null ) ) ? us.getCpf() : "" %>"><br/> 
            
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name ="senha" value="<%= ( ( us != null ) ) ? us.getSenha() : "" %>" required><br/>
            
            <label for="tipo_usuario_id">Tipo Usuário ID:</label>
            <input type="text" id="tipo_usuario_id" name ="tipo_usuario_id" pattern="\d+" title="apenas dígitos" value="<%= ( ( us != null ) ) ? us.getTipoUsuarioId() : "" %>" required><br/>
            
            <input type="submit" name="Salvar" value="Salvar"> 
        </form>
        
    </body>
</html>
