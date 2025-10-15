package aulas.servlet.status;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Status", urlPatterns = {"/aulas/servlet/status"})
public class Status extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        int valor = 13/0; // retorna o status 500
        
        resp.setContentType("text/plain;charset=UFT-8");
        PrintWriter pw = resp.getWriter();
        pw.write("testando método HTTP GET");
        pw.close();
    }
    
}
