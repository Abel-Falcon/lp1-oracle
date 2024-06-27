package pe.edu.upeu.oracle.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.oracle.config.Conexion;
import pe.edu.upeu.oracle.dao.AlumnoDao;
import pe.edu.upeu.oracle.entity.Alumno;

public class AlumnoDaoImpl implements AlumnoDao {
    private Connection cx;

    public AlumnoDaoImpl() {
        cx = Conexion.getConexion();
    }

    @Override
    public int createAlumno(Alumno alumno) {
        int x = 0;
        String SQL = "INSERT INTO ALUMNO (nombre, apellido, email) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getEmail());
            x = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return x;
    }

    @Override
    public int updateAlumno(Alumno alumno) {
        int x = 0;
        String SQL = "UPDATE ALUMNO SET nombre=?, apellido=?, email=? WHERE id=?";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getEmail());
            ps.setInt(4, alumno.getId());
            x = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return x;
    }

    @Override
    public int deleteAlumno(int id) {
        int x = 0;
        String SQL = "DELETE FROM ALUMNO WHERE id=?";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ps.setInt(1, id);
            x = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return x;
    }

    @Override
    public Alumno readAlumno(int id) {
        Alumno alumno = new Alumno();
        String SQL = "SELECT * FROM ALUMNO WHERE id=?";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return alumno;
    }

    @Override
    public List<Alumno> readAllAlumno() {
        List<Alumno> lista = new ArrayList<>();
        String SQL = "SELECT * FROM ALUMNO";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setEmail(rs.getString("email"));
                lista.add(alumno);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return lista;
    }
}
