package pe.edu.upeu.oracle.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.upeu.oracle.config.Conexion;
import pe.edu.upeu.oracle.dao.UsuarioDao;
import pe.edu.upeu.oracle.dto.UsuariLogin;
import pe.edu.upeu.oracle.entity.Usuario;

public class UsuarioDaoImpl implements UsuarioDao {
    private Connection cx;

    public UsuarioDaoImpl() {
        cx = Conexion.getConexion();
    }

    @Override
    public List<UsuariLogin> login(String username, String clave) {
        List<UsuariLogin> lista = new ArrayList<>();
        String SQL = "SELECT u.username, u.sexo, r.nombre as rol FROM Usuario u "
                   + "JOIN Rol r ON u.rol_id = r.id WHERE u.username=? AND u.clave=?";
        try {
            PreparedStatement ps = cx.prepareStatement(SQL);
            ps.setString(1, username);
            ps.setString(2, clave);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UsuariLogin ul = new UsuariLogin();
                ul.setUsername(rs.getString("username"));
                ul.setSexo(rs.getString("sexo"));
                ul.setRol(rs.getString("rol"));
                lista.add(ul);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return lista;
    }

    @Override
    public int createUsuario(Usuario usuario) {
        // Implementation code...
        return 0;
    }

    @Override
    public int updateUsuario(Usuario usuario) {
        // Implementation code...
        return 0;
    }

    @Override
    public int deleteUsuario(int id) {
        // Implementation code...
        return 0;
    }

    @Override
    public Usuario readUsuario(int id) {
        // Implementation code...
        return null;
    }

    @Override
    public List<Usuario> readAllUsuario() {
        // Implementation code...
        return null;
    }

    @Override
    public boolean buscarUsuario(String username) {
        // Implementation code...
        return false;
    }
}
