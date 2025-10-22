<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulário</title>
    </head>
    <body>
        <h1>Formulário</h1>
        <form action="<%= request.getContextPath() %>/aulas/jsp/formulariojsp" method="post">
            <p>
                <label for="campoA">Campo A: </label>
                <input type="text" id="campoA" name="campoA" pattern="\d+" title="apenas dígitos"  required/><br/><br/>

                <input type="checkbox" id="opcaoA" name="opcaoA" value="opcaoA"/>
                <label for="opcaoA">Opção A</label><br/><br/>

                <input type="checkbox" id="opcaoB" name="opcaoB" value="opcaoB"/>
                <label for="opcaoB">Opção B</label><br/><br/>

                <input type="submit" name="btEnviar" value="Salvar"/>
            </p>            
        </form>
    </body>
</html>
