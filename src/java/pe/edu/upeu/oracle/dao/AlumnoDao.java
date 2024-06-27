package pe.edu.upeu.oracle.dao;

import java.util.List;
import pe.edu.upeu.oracle.entity.Alumno;

public interface AlumnoDao {
    public int createAlumno(Alumno alumno);
    public int updateAlumno(Alumno alumno);
    public int deleteAlumno(int id);
    public Alumno readAlumno(int id);
    public List<Alumno> readAllAlumno();
}

