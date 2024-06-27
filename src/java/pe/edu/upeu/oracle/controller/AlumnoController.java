package pe.edu.upeu.oracle.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pe.edu.upeu.oracle.dao.AlumnoDao;
import pe.edu.upeu.oracle.daoImpl.AlumnoDaoImpl;
import pe.edu.upeu.oracle.entity.Alumno;

public class AlumnoController extends HttpServlet {
    private AlumnoDao adao = new AlumnoDaoImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession sesion = request.getSession();
        String rol = (String) sesion.getAttribute("rol");

        String action = request.getParameter("action");
        if (action.equals("create") && rol.equals("Administrador")) {
            createAlumno(request, response);
        } else if (action.equals("update") && rol.equals("Administrador")) {
            updateAlumno(request, response);
        } else if (action.equals("delete") && rol.equals("Administrador")) {
            deleteAlumno(request, response);
        } else if (action.equals("read") && (rol.equals("Administrador") || rol.equals("Alumno"))) {
            readAlumno(request, response);
        } else if (action.equals("readAll") && (rol.equals("Administrador") || rol.equals("Alumno"))) {
            readAllAlumno(request, response);
        } else {
            out.println("Acceso denegado");
        }
    }

    protected void createAlumno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumno alumno = new Alumno();
        alumno.setNombre(request.getParameter("nombre"));
        alumno.setApellido(request.getParameter("apellido"));
        alumno.setEmail(request.getParameter("email"));
        adao.createAlumno(alumno);
        response.sendRedirect("alumno.jsp");
    }

    protected void updateAlumno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Alumno alumno = new Alumno();
        alumno.setId(Integer.parseInt(request.getParameter("id")));
        alumno.setNombre(request.getParameter("nombre"));
        alumno.setApellido(request.getParameter("apellido"));
        alumno.setEmail(request.getParameter("email"));
        adao.updateAlumno(alumno);
        response.sendRedirect("alumno.jsp");
    }

    protected void deleteAlumno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        adao.deleteAlumno(id);
        response.sendRedirect("alumno.jsp");
    }

    protected void readAlumno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Alumno alumno = adao.readAlumno(id);
        request.setAttribute("alumno", alumno);
        request.getRequestDispatcher("alumnoDetalle.jsp").forward(request, response);
    }

    protected void readAllAlumno(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Alumno> lista = adao.readAllAlumno();
        request.setAttribute("alumnos", lista);
        request.getRequestDispatcher("alumnoLista.jsp").forward(request, response);
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

