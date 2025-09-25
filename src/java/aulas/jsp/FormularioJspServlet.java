package aulas.jsp;

import aulas.servlet.getpost.InformacaoFormulario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet(name = "FormularioJspServlet", urlPatterns = {"/aulas/jsp/formulariojsp"})
public class FormularioJspServlet extends HttpServlet {

    ArrayList<InformacaoFormulario> dados;

    @Override
    public void init() throws ServletException {
        super.init();
        dados = new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String campoA = request.getParameter("campoA");
        String opcaoA = request.getParameter("opcaoA");
        String opcaoB = request.getParameter("opcaoB");

        System.out.println("CampoA : " + campoA);
        System.out.println("OpcaoA : " + opcaoA);
        System.out.println("OpcaoB : " + opcaoB);

        if (campoA != null) {
            InformacaoFormulario info = new InformacaoFormulario();
            info.setCampoA(campoA);
            info.setOpcaoA(opcaoA);
            info.setOpcaoB(opcaoB);

            dados.add(info);
        }
        
        request.setAttribute("dados", dados);
        request.getRequestDispatcher("/aulas/jsp/dados_formulario.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        dados.clear();
        dados = null;
    }

}
