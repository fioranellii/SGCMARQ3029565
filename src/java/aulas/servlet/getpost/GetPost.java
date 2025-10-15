package aulas.servlet.getpost;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GetPost extends HttpServlet {
    
    ArrayList <InformacaoFormulario> dados;

    @Override
    public void init() throws ServletException {
        super.init();
        dados = new ArrayList<>();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String campoA = request.getParameter("campoA");
        String opcaoA = request.getParameter("opcaoA");
        String opcaoB = request.getParameter("opcaoB");
        
        System.out.println( " campoA : " + campoA );
        System.out.println( " opcaoA : " + opcaoA );
        System.out.println( " opcaoB : " + opcaoB );
        
        if (campoA != null) { // verifica se os dados vieram do formulario
            InformacaoFormulario info = new InformacaoFormulario();
            info.setCampoA(campoA);
            info.setOpcaoA(opcaoA);
            info.setOpcaoB(opcaoB);
            
            dados.add(info); // vai alimentando o arraylist
        }
        
        // vai gerar uma nova requisicao HTTP vindo do cliente (novos cabeçarios de requisicao e resposta)        
//        response.sendRedirect("/SGCMAQ3029956/aulas/servlet/getpost/getpost_form.html"); // ele retorna para você a pagina do formulario depois de mandar o form
        
        response.setContentType("text/html;charset=UTF-8");
        
        String html = "<!DOCTYPE html>";
        html +="<html>";
        html +="<head>";
        html +="<title>Dados do Formulário</title>";
        html +="</head>";
        html +="<body>";
        html +="<h1>Dados dos Formulário</h1>";
        
        html += "<table>";
        html += "<tr>";
        html += "<th>Campo A</th>";
        html += "<th>Opcao A</th>";
        html += "<th>Opcao B</th>";
        html += "</tr>";
        
        for(InformacaoFormulario info : dados) {
            html += "<tr>";
            html += "<td>" + info.getCampoA() + "</td>";
            html += "<td>" + info.getOpcaoA()+ "</td>";
            html += "<td>" + info.getOpcaoB()+ "</td>";
            html += "</tr>";
        }
        
        html += "</table></br></br>";
        html += "<a href=\"/SGCMAQ3029956/aulas/servlet/getpost/getpost_form.html\">Adicionar Informação</a>";
        
        html +="</body>";
        html +="</html>";
        
        PrintWriter pw = response.getWriter();
        pw.write(html);
        pw.flush();
        pw.close();
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        dados.clear();
        dados = null;
    }

}
