package aulas.servlet.ciclo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@WebServlet(name = "Ciclo", urlPatterns = {"/aulas/servlet/ciclo"})
public class Ciclo extends HttpServlet {
    
    @Override
    public void init() throws ServletException {
        //Inicialização dos recursos
        //cria/atribui as variaveis globais(como conexão a banco de dados)
        //leitura dos parametros iniciais e criaçao de variaveis criar/atribuir as variaveis globais
        super.init();
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recebimento de requisiçoes HTTP enquanto o servlet estiver ativo
//        super.service(req, resp);
        //enchaminha as requisicoes aos metodos HTTP implementados
        
        System.out.println("\n---- HeaderNames Request");
        
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + " : " + request.getHeader(name));
        }
        System.out.println("\n---- HeaderNames Request");
        
        String html = "<!DOCTYPE html>";
        html += "<html>";
        html += "<head>";
        html += "<title>Servlet Ciclo</title>";
        html += "</head>";
        html += "<body>";
        html += "<h1>Ciclo de vida de um Servlet</h1>";
        html += "</body>";
        html += "</html>";
        
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter pw = response.getWriter();
        
        pw.write(html);
        pw.close();
        
        System.out.println("\n---- HeaderNames Response");
        for(String name : response.getHeaderNames()){
            System.out.println(name + " : " + response.getHeader(name));
        }
        System.out.println("\n---- HeaderNames Response");
    }
    
    @Override
    public void destroy() {
        //Encerramento da servlet, liberação dos recursos

        //chamado pelo servletContainer, ultilizado para liberacao de recursos
        super.destroy();
    }
    
}
