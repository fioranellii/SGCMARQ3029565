package controller.__;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.Usuario;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/home/usuario"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obter a listagem de registros da tabela tipo_usuario e deletar um registro de tipo_usuario
        
        String action = request.getParameter("action");
        if (action != null && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            
            Usuario us = new Usuario();
            us.setId(id);
            
            try {
                us.delete();
            } catch (Exception ex) {
                ExceptionLogTrack.getInstance().addLog(ex);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // criar e alterar registros da tabela usuario
        
        String action = request.getParameter("action");
        
        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        int tipoUsuario = Integer.valueOf(request.getParameter("tipoUsuario"));
        
        try {
            // Java Bean
            Usuario us = new Usuario();

            us.setId(id); // chave primária

            if (action.equals("update")) us.load();

            us.setNome(nome);
            us.setSenha(senha);
            us.setCpf(cpf);
            us.setTipoUsuarioId(tipoUsuario);

            us.save();
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }
}
