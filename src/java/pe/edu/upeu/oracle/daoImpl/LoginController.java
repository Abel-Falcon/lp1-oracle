package pe.edu.upeu.oracle.daoImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.edu.upeu.oracle.dao.UsuarioDao;
import pe.edu.upeu.oracle.daoImpl.UsuarioDaoImpl;
import pe.edu.upeu.oracle.dto.UsuariLogin;

public class LoginController extends HttpServlet {
    private UsuarioDao udao = new UsuarioDaoImpl();
    private static final int MAX_ATTEMPTS = 3;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String clave = request.getParameter("clave");
        HttpSession sesion = request.getSession();

        Integer attempts = (Integer) sesion.getAttribute("attempts");
        if (attempts == null) {
            attempts = 0;
        }

        List<UsuariLogin> lista = udao.login(username, clave);
        if (!lista.isEmpty()) {
            sesion.setAttribute("username", lista.get(0).getUsername());
            sesion.setAttribute("sexo", lista.get(0).getSexo());
            sesion.setAttribute("rol", lista.get(0).getRol());
            sesion.removeAttribute("attempts"); // reset attempts on successful login
            response.sendRedirect("home.jsp");
        } else {
            attempts++;
            if (attempts >= MAX_ATTEMPTS) {
                response.getWriter().write("Su cuenta ha sido bloqueada, comunicarse con el administrador.");
            } else {
                sesion.setAttribute("attempts", attempts);
                response.sendRedirect("index.jsp");
            }
        }
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
    public String getServletInfo() {
        return "Short description";
    }
}
