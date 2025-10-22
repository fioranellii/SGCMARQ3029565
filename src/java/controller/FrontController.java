package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logtrack.ExceptionLogTrack;
import model.TipoUsuario;
import model.Usuario;

public class FrontController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        try {

            switch (task) {

                case "tipousuario":
                    doGetTipoUsuario(request, response);
                    break;

                case "usuarios":
                    doGetUsuarios(request, response);
                    break;

                case "logout":
                    doGetLogout(request, response);
                    break;

                default:
                    doDefault(request, response);

            }

        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        try {

            switch (task) {

                case "tipousuario":
                    doPostTipoUsuario(request, response);
                    break;

                case "usuarios":
                    doPostUsuarios(request, response);
                    break;

                case "login":
                    doPostLogin(request, response);

                default:
                    doDefault(request, response);

            }

        } catch (Exception ex) {
            ExceptionLogTrack.getInstance().addLog(ex);
        }

    }

    private void doGetTipoUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String action = request.getParameter("action");
        if ((action != null)
                && action.equals("delete")) {

            int id = Integer.valueOf(request.getParameter("id"));

            TipoUsuario tp = new TipoUsuario();
            tp.setId(id);

            tp.delete();
        }

        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");

    }

    private void doGetUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String action = request.getParameter("action");
        if ((action != null)
                && action.equals("delete")) {

            int id = Integer.valueOf(request.getParameter("id"));

            Usuario us = new Usuario();
            us.setId(id);

            us.delete();

        }

        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");

    }

    private void doGetLogout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession sessao = request.getSession(false);
        if (sessao != null) {

            sessao.invalidate();
        }
        response.sendRedirect("home/login.jsp");
    }

    private void doPostTipoUsuario(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

        tp.setId(id);

        if (action.equals("update")) {
            tp.load();
        }

        tp.setNome(nome);
        tp.setModuloAdministrativo(moduloAdministrativo);
        tp.setModuloAgendamento(moduloAgendamento);
        tp.setModuloAtendimento(moduloAtendimento);

        tp.save();

        response.sendRedirect(request.getContextPath() + "/home/app/tipousuario.jsp");

    }

    private void doPostUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String action = request.getParameter("action");

        int id = Integer.valueOf(request.getParameter("id"));

        String nome = request.getParameter("nome");

        String cpf = request.getParameter("cpf");

        String senha = request.getParameter("senha");

        int tipoUsuarioId = Integer.valueOf(request.getParameter("tipo_usuario_id"));

        Usuario us = new Usuario();

        us.setId(id);

        if (action.equals("update")) {
            us.load();
        }

        us.setNome(nome);
        us.setCpf(cpf);
        us.setSenha(senha);
        us.setTipoUsuarioId(tipoUsuarioId);

        us.save();

        response.sendRedirect(request.getContextPath() + "/home/app/usuarios.jsp");

    }

    private void doPostLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int id = Integer.valueOf(request.getParameter("id"));

        String senha = request.getParameter("senha");

        Usuario usuarioTry = new Usuario();

        usuarioTry.setId(id);
        usuarioTry.setSenha(senha);

        usuarioTry.getSenha();

        Usuario usuario = new Usuario();
        usuario.setId(id);
        boolean status = usuario.load();
        if ((status == true) && (usuario.getSenha().equals(usuarioTry.getSenha()))) {

            //true crie uma sessao se não houver alguma, false do contrário
            //informações armazenadas no servidor
            HttpSession sessao = request.getSession(false);
            if (sessao != null) {
                //se a sessão ja existir
                sessao.invalidate();
            }

            sessao = request.getSession(true);

            sessao.setAttribute("usuario", "(" + usuario.getNome() + ", " + usuario.getId() + ")");

            TipoUsuario tipoUsuario = new TipoUsuario();

            tipoUsuario.setId(usuario.getTipoUsuarioId());
            tipoUsuario.load();

            sessao.setAttribute("tipo_usuario", tipoUsuario);
            sessao.setMaxInactiveInterval(60 * 60);//segundos

            //criado e armazenado no cliente
            Cookie cookie = new Cookie("id", String.valueOf(id));
            cookie.setMaxAge(60 * 10);//em segundos
            response.addCookie(cookie);

            response.sendRedirect("home/app/menu.jsp");
        } else {

            request.setAttribute("msg", "id e/ou senha incorreto(s)");
            request.getRequestDispatcher("/home/login.jsp").forward(request, response);

        }
    }

    private void doDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.sendRedirect("home/login.jsp");

    }

}
