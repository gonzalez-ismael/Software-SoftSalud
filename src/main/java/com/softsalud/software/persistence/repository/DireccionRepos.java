package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.model.Direccion;
import com.softsalud.software.persistence.repository.interfaz.IDireccionRepository;
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
 * Administrar altas, bajas, modificacion y mostrar contenidos de la tabla direccion.
 *
 * @author Ismael
 */
public class DireccionRepos implements IDireccionRepository {

    private final Connection conn;
    private final int EXITO = 1;

    /**
     * Metodo constructor desarrollado por el programador este metodo obtiene la conexion con la bd.
     *
     * @param conn
     */
    public DireccionRepos(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insertar(Direccion direccion) {
        boolean operacionExitosa = false;
        String query = "INSERT INTO direccion values (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, 0);
            pstmt.setString(2, direccion.getBarrio());
            pstmt.setString(3, direccion.getCalle());
            pstmt.setInt(4, direccion.getNumero());
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }
    
    @Override
    public boolean insertar(String barrio, String calle, int numCasa){
        boolean operacionExitosa = false;
        String query = "INSERT INTO direccion values (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, 0);
            pstmt.setString(2, barrio);
            pstmt.setString(3, calle);
            pstmt.setInt(4, numCasa);
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }

    @Override
    public boolean modificar(Direccion direccion, Long buscado) {
        boolean operacionExitosa = false;
        String query = "UPDATE direccion SET barrio = ?, calle = ?, numero = ? WHERE (id = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, direccion.getBarrio());
            pstmt.setString(2, direccion.getCalle());
            pstmt.setInt(3, direccion.getNumero());
            pstmt.setLong(4, buscado);
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = true;
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            String msg = "MENSAJE DE ERROR: Se intento eliminar un registro que esta utilizado por otras tablas.\n\n";
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, msg, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }

    @Override
    public boolean eliminar(Long id) {
        boolean operacionExitosa = false;
        String query = "Delete from direccion where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = true;
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            String msg = "MENSAJE DE ERROR: Se intento eliminar un registro que esta utilizado por otras tablas.\n\n";
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, msg, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }

    @Override
    public List<Direccion> listarDirecciones() {
        List<Direccion> direcciones = null;
        try {
            String query = "Select * from direccion";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            direcciones = new ArrayList();
            while (rs.next()) {
                Direccion d = new Direccion();
                d.setId(rs.getLong("id"));
                d.setBarrio(rs.getString("barrio"));
                d.setCalle(rs.getString("calle"));
                d.setNumero(rs.getInt("numero"));
                direcciones.add(d);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return direcciones;
    }

    @Override
    public Direccion buscarDireccion(Long id) {
        Direccion direccion = null;
        String query = "Select * from direccion Where id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1,id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                direccion = new Direccion();
                direccion.setId(rs.getLong("id"));
                direccion.setBarrio(rs.getString("barrio"));
                direccion.setCalle(rs.getString("calle"));
                direccion.setNumero(rs.getInt("numero"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return direccion;
    }
    
    @Override
    public Direccion buscarDireccion(String barrio, String calle, int numCasa) {
        Direccion direccion = null;
        String query = "Select * from direccion Where barrio = ? and calle = ? and numero = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, barrio);
            pstmt.setString(2, calle);
            pstmt.setInt(3, numCasa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                direccion = new Direccion();
                direccion.setId(rs.getLong("id"));
                direccion.setBarrio(rs.getString("barrio"));
                direccion.setCalle(rs.getString("calle"));
                direccion.setNumero(rs.getInt("numero"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DireccionRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return direccion;
    }
}
