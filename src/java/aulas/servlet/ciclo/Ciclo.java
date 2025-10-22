package aulas.servlet.ciclo;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@WebServlet(name="Ciclo", urlPatterns={"/aulas/servlet/ciclo"})
public class Ciclo extends HttpServlet {

    @Override
    public void init() throws ServletException {
        /*
        Inicialização do Servlet.
        
        Leitura dos parâmetros inicais e criaçao variáveis globais comuns as requisições (como conexão a banco de dados).
        */
        super.init();
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        Recebimento de requisições HTTP enquanto o servlet estiver ativo.
        
        super.service(...) encaminha a resquisições aos métodos HTTP implementados.
        */
//        super.service(req, resp);
        
        System.out.println("\n--- HeaderNames Request");
        Enumeration<String> headerNames = request.getHeaderNames();
        while( headerNames.hasMoreElements() ) {
            String name = headerNames.nextElement();
            System.out.println( name + " : " + request.getHeader(name) );
        }
        System.out.println("--- HeaderNames Request\n");

        String html = "<!DOCTYPE html>";        
        html += "<html>";
        html += "<head>";
        html += "<title>Servlet Ciclo de Vida</title>";
        html += "</head>";
        html += "<body>";
        html += "<h1>Ciclo de Vida de um Servlet</h1>";
        html += "</body>";
        html += "</html>";
        
        // http://www.iana.org/assignments/media-types/
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter pw = response.getWriter();        
        pw.write(html);
        pw.close();
        
        System.out.println("\n--- HeaderNames Response");
        for( String name : response.getHeaderNames() ) {
            System.out.println( name + " : " + response.getHeader(name) );
        }
        System.out.println("--- HeaderNames Response\n");
        
    }

    @Override
    public void destroy() {
        /*
        Encerramento do Servlet.
        
        Chamado quando o servlet for encerrado, utilizado para liberação de rercuros (como conexão a banco de dados).
        */
        super.destroy();
    }

}