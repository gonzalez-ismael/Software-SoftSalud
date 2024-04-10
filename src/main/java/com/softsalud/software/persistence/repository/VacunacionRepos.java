package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.model.Vacunacion;
import com.softsalud.software.persistence.repository.interfaz.IVacunacionRepository;
import java.sql.Connection;
import java.sql.Date;
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
 *
 * @author Gonzalez Ismael
 */
public class VacunacionRepos implements IVacunacionRepository {

    private final Connection conn;
    private final int EMPTY = -1, EXITO = 1;

    /**
     * Metodo constructor desarrollado por el programador este metodo obtiene la conexion con la bd.
     *
     * @param conn
     */
    public VacunacionRepos(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insertar(Vacunacion vacunacion) {
        int operacionExitosa = 0;
        String query = "INSERT INTO historial_vacunacion (persona_dni, vacuna_codigo, lote_vacuna, numero_dosis, fecha_vacunacion, lugar_vacunacion) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, vacunacion.getPersona_dni());
            pstmt.setLong(2, vacunacion.getVacuna_codigo());
            pstmt.setString(3, vacunacion.getLote_vacuna());
            pstmt.setInt(4, vacunacion.getNumero_dosis());
            pstmt.setDate(5, Date.valueOf(vacunacion.getFecha_vacunacion()));
            pstmt.setString(6, vacunacion.getLugar_vacunacion());
            if (pstmt.executeUpdate() == EXITO) {
                operacionExitosa = EXITO;
            }
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return operacionExitosa;
    }

    @Override
    public int modificar(Vacunacion nuevaVacunacion, Long dniPersona, Long codigoVacuna, String loteVacuna) {
        int resultadoOperacion = EMPTY;
        String query = "UPDATE historial_vacunacion SET persona_dni = ?, vacuna_codigo = ?, lote_vacuna = ?, numero_dosis = ?, fecha_vacunacion = ?, lugar_vacunacion = ? "
                + "WHERE (persona_dni = ?) and (vacuna_codigo = ?) and (lote_vacuna = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, nuevaVacunacion.getPersona_dni());
            pstmt.setLong(2, nuevaVacunacion.getVacuna_codigo());
            pstmt.setString(3, nuevaVacunacion.getLote_vacuna());
            pstmt.setInt(4, nuevaVacunacion.getNumero_dosis());
            pstmt.setDate(5, Date.valueOf(nuevaVacunacion.getFecha_vacunacion()));
            pstmt.setString(6, nuevaVacunacion.getLugar_vacunacion());
            pstmt.setLong(7, dniPersona);
            pstmt.setLong(8, codigoVacuna);
            pstmt.setString(9, loteVacuna);
            if (pstmt.executeUpdate() == EXITO) {
                resultadoOperacion = EXITO;
            }
            System.out.println("\n\nRESULTADO: " + resultadoOperacion);
        } catch (SQLIntegrityConstraintViolationException ex) {
            resultadoOperacion = 2;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            resultadoOperacion = 3;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    @Override
    public boolean eliminar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Vacunacion> listarVacunaciones() {
        List<Vacunacion> listado = null;
        try {
//            String query = "Select * from historial_vacunacion";
            String query = """
                           Select p.dni, CONCAT(p.nombre, ' ', p.apellido) as nombre, v.nombre_vacuna, hp.lote_vacuna, hp.numero_dosis, hp.fecha_vacunacion, hp.lugar_vacunacion
                           from historial_vacunacion as hp
                           inner join persona as p on hp.persona_dni = p.dni
                           inner join vacuna as v on hp.vacuna_codigo = v.codigo;
                           """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            listado = new ArrayList();
            while (rs.next()) {
                Vacunacion v = new Vacunacion();
                v.setPersona_dni(rs.getLong("dni"));
                v.setNombre_completo(rs.getString("nombre"));
                v.setNombre_vacuna(rs.getString("nombre_vacuna"));
                v.setLote_vacuna(rs.getString("lote_vacuna"));
                v.setNumero_dosis(rs.getInt("numero_dosis"));
                v.setFecha_vacunacion(rs.getDate("fecha_vacunacion").toLocalDate());
                v.setLugar_vacunacion(rs.getString("lugar_vacunacion"));
                listado.add(v);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listado;
    }

    @Override
    public Vacunacion buscarVacunacion(Long dniPersona, Long codigoVacuna, String loteVacuna) {
        Vacunacion vacunacion = null;
        String query = "Select * from historial_vacunacion where persona_dni = ? and vacuna_codigo = ? and lote_vacuna = ?";
        try (PreparedStatement pstmt = conn.prepareCall(query)) {
            pstmt.setLong(1, dniPersona);
            pstmt.setLong(2, codigoVacuna);
            pstmt.setString(3, loteVacuna);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vacunacion = new Vacunacion();
                vacunacion.setPersona_dni(rs.getLong("persona_dni"));
                vacunacion.setVacuna_codigo(rs.getLong("vacuna_codigo"));
                vacunacion.setLote_vacuna(rs.getString("lote_vacuna"));
                vacunacion.setNumero_dosis(rs.getByte("numero_dosis"));
                vacunacion.setFecha_vacunacion(rs.getDate("fecha_vacunacion").toLocalDate());
                vacunacion.setLugar_vacunacion(rs.getString("lugar_vacunacion"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(VacunaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return vacunacion;
    }
}