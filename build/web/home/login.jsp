<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #121212; /* fundo escuro */
                color: #e0e0e0; /* texto claro */
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            form {
                background-color: #1e1e1e; /* fundo do formulário um pouco mais claro */
                padding: 30px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.8);
                width: 300px;
            }

            h1 {
                text-align: center;
                color: #fff;
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                color: #bbb;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #555;
                border-radius: 4px;
                background-color: #2c2c2c;
                color: #e0e0e0;
                box-sizing: border-box;
            }

            input[type="text"]::placeholder,
            input[type="password"]::placeholder {
                color: #888;
            }

            input[type="submit"] {
                width: 100%;
                padding: 10px;
                background-color: #3a86ff; /* azul vibrante */
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-weight: bold;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #265ecf;
            }

        </style>
    </head>
    <body>
        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {%>
        <script>
            alert('<%= msg%>');
        </script>
        <% }%>

        <%
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                response.sendRedirect("home/app/menu.jsp");
            }
        %>

        <%  int id = -1;
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(id)) {
                    id = Integer.parseInt(c.getValue());
                }
            }
        %>

        <h1>Login</h1>

        <form action="<%= request.getContextPath()%>/home?task=login" method="post">

            <label for="id">ID:</label>
            <input type="text" id="id" name="id" pattern="\d+" title="apenas digitos" value=" <%= id != -1 ? id : "" %>" required />
            
            <label for="senha">Senha:</label>
            <input type="password" id="senha" name="senha" required />

            <input type="submit" value="login" />
        </form>
    </body>
</html>

