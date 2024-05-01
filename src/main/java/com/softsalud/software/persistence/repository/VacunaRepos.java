package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Administrar altas, bajas, modificacion y mostrar contenidos de la tabla vacuna.
 *
 * @author Gonzalez Ismael
 */
public class VacunaRepos implements IVacunaRepository {

    private final Connection conn;
    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3;

    /**
     * Metodo constructor desarrollado por el programador este metodo obtiene la conexion con la bd.
     *
     * @param conn
     */
    public VacunaRepos(Connection conn) {
        this.conn = conn;
    }

    /**
     * @param nombreVacuna
     * @return boolean
     */
    @Override
    public int insertar(String nombreVacuna) {
        int resultadoOperacion;
        if (buscarVacunaPorNombre(nombreVacuna) == null) {
            String query = "INSERT INTO vacuna (nombre_vacuna) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nombreVacuna);
                resultadoOperacion = pstmt.executeUpdate();
            } catch (SQLException ex) {
                resultadoOperacion = UNKNOWNFAIL;
                Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
    }

    /**
     *
     * @param codigo
     * @param nombreVacuna
     * @return boolean
     */
    @Override
    public int modificar(int codigo, String nombreVacuna) {
        int resultadoOperacion;
        if (buscarVacunaPorNombre(nombreVacuna) == null) {
            String query = "UPDATE vacuna SET nombre_vacuna = ? WHERE codigo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, nombreVacuna);
                pstmt.setInt(2, codigo);
                resultadoOperacion = pstmt.executeUpdate();
            } catch (SQLException ex) {
                resultadoOperacion = UNKNOWNFAIL;
                Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
    }

    /**
     *
     * @param codigo
     * @return boolean
     */
    @Override
    public boolean eliminar(int codigo) {
        boolean operacionExitosa = false;
        String query = "Delete from vacuna where codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, codigo);
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = true;
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            String msg = "MENSAJE DE ERROR: Se intento eliminar un registro que esta utilizado por otras tablas.\n\n";
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, msg, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }
    
    /**
     *
     * @return 
     */
    @Override
    public int eliminarTodo() {
        int resultadoOperacion;
        String query = "DELETE FROM vacuna";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            resultadoOperacion = pstmt.executeUpdate();
        } catch (SQLException ex) {
            resultadoOperacion = UNKNOWNFAIL;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    /**
     *
     * @return Set Vacuna
     */
    @Override
    public List<Vacuna> listarVacunas() {
        List<Vacuna> vacunas = null;
        try {
            String query = "Select * from vacuna";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            vacunas = new ArrayList();
            while (rs.next()) {
                Vacuna v = new Vacuna();
                v.setCodigo(rs.getLong("codigo"));
                v.setNombre_vacuna(rs.getString("nombre_vacuna"));
                vacunas.add(v);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vacunas;
    }

    /**
     *
     * @param codigo
     * @return vacuna
     */
    @Override
    public Vacuna buscarVacuna(int codigo) {
        Vacuna vacuna = null;
        String query = "Select * from vacuna Where codigo = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                vacuna = new Vacuna();
                vacuna.setCodigo(rs.getLong("codigo"));
                vacuna.setNombre_vacuna(rs.getString("nombre_vacuna"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vacuna;
    }

    @Override
    public List<Vacuna> listarVacunasPorNombre(String nombre) {
        List<Vacuna> vacunas = null;
        String query = "Select * from vacuna where nombre_vacuna like ?";
        try (PreparedStatement pstmt = conn.prepareCall(query)) {
            pstmt.setString(1, nombre + "%");
            ResultSet rs = pstmt.executeQuery();
            vacunas = new ArrayList();
            while (rs.next()) {
                Vacuna v = new Vacuna();
                v.setCodigo(rs.getLong("codigo"));
                v.setNombre_vacuna(rs.getString("nombre_vacuna"));
                vacunas.add(v);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vacunas;
    }

    @Override
    public Vacuna buscarVacunaPorNombre(String nombre) {
        Vacuna vacuna = null;
        String query = "Select * from vacuna where nombre_vacuna = ?";
        try (PreparedStatement pstmt = conn.prepareCall(query)) {
            pstmt.setString(1, nombre);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vacuna = new Vacuna();
                vacuna.setCodigo(rs.getLong("codigo"));
                vacuna.setNombre_vacuna(rs.getString("nombre_vacuna"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vacuna;
    }
}
