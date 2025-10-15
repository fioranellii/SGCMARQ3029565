package controller.__;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logtrack.ExceptionLogTrack;
import model.TipoUsuario;

@WebServlet(name = "TipoUsuarioServlet", urlPatterns = {"/home/tipousuario"})
public class TipoUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // obter a listagem de registros da tabela tipo_usuario e deletar um registro de tipo_usuario
        
        String action = request.getParameter("action");
        if (action != null && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            
            TipoUsuario tp = new TipoUsuario();
            tp.setId(id);
            
            try {
                tp.delete();
            } catch (Exception ex) {
                ExceptionLogTrack.getInstance().addLog(ex);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // criar e alterar registros da tabela tipo_usuario
        
        String action = request.getParameter("action");
        
        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        
        String moduloAdministrativo = request.getParameter("modulo_administrativo");
        if (moduloAdministrativo == null) {
            moduloAdministrativo = "N";
        }
        
        String moduloAgendamento = request.getParameter("modulo_agendamento");
        if (moduloAgendamento == null) {
            moduloAgendamento = "N";
        }
        
        String moduloAtendimento = request.getParameter("modulo_atendimento");
        if (moduloAtendimento == null) {
            moduloAtendimento = "N";
        }
        
        try {
            // Java Bean
            TipoUsuario tp = new TipoUsuario();

            tp.setId(id); // chave primária

            if (action.equals("update")) tp.load();

            tp.setNome(nome);
            tp.setModuloAdministrativo(moduloAdministrativo);
            tp.setModuloAgendamento(moduloAgendamento);
            tp.setModuloAtendimento(moduloAtendimento);

            tp.save();
        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }
}
