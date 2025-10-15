package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.print.DocFlavor;
import logtrack.ExceptionLogTrack;
import model.TipoUsuario;
import model.Usuario;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        
        try{
            switch (task) {
                case "tipousuario": doGetTipoUsuario(request, response); break;

                case "usuario" : doGetUsuario(request, response); break;

                default: doDefault(request, response);
            }
        } catch(Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        
        try{
            switch (task) {
                case "tipousuario": doPostTipoUsuario(request, response); break;

                case "usuario" : doPostUsuario(request, response); break;

                default: doDefault(request, response);
            }
        } catch(Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }
    }
    
    private void doGetTipoUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String action = request.getParameter("action");
        if (action != null && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            
            TipoUsuario tp = new TipoUsuario();
            tp.setId(id);
            
            
            tp.delete();
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }
    
    private void doGetUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String action = request.getParameter("action");
        if (action != null && action.equals("delete")) {
            int id = Integer.valueOf(request.getParameter("id"));
            
            Usuario us = new Usuario();
            us.setId(id);
            
            us.delete();
        }
        
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }
    
    private void doPostTipoUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
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

        // Java Bean
        TipoUsuario tp = new TipoUsuario();

        tp.setId(id); // chave primária

        if (action.equals("update")) tp.load();

        tp.setNome(nome);
        tp.setModuloAdministrativo(moduloAdministrativo);
        tp.setModuloAgendamento(moduloAgendamento);
        tp.setModuloAtendimento(moduloAtendimento);

        tp.save();
        
        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");
    }
    
    private void doPostUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String action = request.getParameter("action");
        
        int id = Integer.valueOf(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String cpf = request.getParameter("cpf");
        int tipoUsuario = Integer.valueOf(request.getParameter("tipoUsuario"));

        // Java Bean
        Usuario us = new Usuario();

        us.setId(id); // chave primária

        if (action.equals("update")) us.load();

        us.setNome(nome);
        us.setSenha(senha);
        us.setCpf(cpf);
        us.setTipoUsuarioId(tipoUsuario);

        us.save();
        
        response.sendRedirect(request.getContextPath() + "/home/app/usuario.jsp");
    }
    
    private void doDefault(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.sendRedirect("home/login.jsp");
    }
}
