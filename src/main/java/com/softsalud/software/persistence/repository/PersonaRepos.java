package com.softsalud.software.persistence.repository;

import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
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
public class PersonaRepos implements IPersonaRepository {

    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3;
    private final Connection conn;

    public PersonaRepos(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insertar(Persona persona) {
        int resultadoOperacion = EMPTY;
        String query = "INSERT INTO persona (dni, apellido, nombre, fecha_nac, numero_tel, numero_tel_opcional, localidad, direccion, tuvo_covid, tiene_trasplantes, factores_riesgo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, persona.getDni());
            pstmt.setString(2, persona.getApellido());
            pstmt.setString(3, persona.getNombre());
            pstmt.setDate(4, Date.valueOf(persona.getFecha_nac()));
            pstmt.setLong(5, persona.getNumero_tel());
            pstmt.setLong(6, persona.getNumero_tel_opcional());
            pstmt.setString(7, persona.getLocalidad());
            pstmt.setString(8, persona.getDireccion());
            pstmt.setBoolean(9, persona.isTuvo_covid());
            pstmt.setBoolean(10, persona.isTiene_trasplantes());
            pstmt.setString(11, persona.getFactores_riesgo());
            if (pstmt.executeUpdate() == EXITO) {
                resultadoOperacion = EXITO;
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            resultadoOperacion = CLAVEREPETIDA;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            resultadoOperacion = UNKNOWNFAIL;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    @Override
    public int modificar(Persona persona, Long buscado) {
        int resultadoOperacion = EMPTY;
        String query = "UPDATE persona SET dni = ?, apellido = ?, nombre = ?, fecha_nac = ?, numero_tel = ?, numero_tel_opcional = ?, localidad = ?, direccion = ?, tuvo_covid = ?, tiene_trasplantes = ?, factores_riesgo = ? WHERE (dni = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, persona.getDni());
            pstmt.setString(2, persona.getApellido());
            pstmt.setString(3, persona.getNombre());
            pstmt.setDate(4, Date.valueOf(persona.getFecha_nac()));
            pstmt.setLong(5, persona.getNumero_tel());
            pstmt.setLong(6, persona.getNumero_tel_opcional());
            pstmt.setString(7, persona.getLocalidad());
            pstmt.setString(8, persona.getDireccion());
            pstmt.setBoolean(9, persona.isTuvo_covid());
            pstmt.setBoolean(10, persona.isTiene_trasplantes());
            pstmt.setString(11, persona.getFactores_riesgo());
            pstmt.setLong(12, buscado);
            if (pstmt.executeUpdate() == EXITO) {
                resultadoOperacion = EXITO;
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            resultadoOperacion = CLAVEREPETIDA;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            resultadoOperacion = UNKNOWNFAIL;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    @Override
    public int eliminar(Long dni) {
        int resultadoOperacion = EMPTY;
        String query = "DELETE FROM persona WHERE (dni = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, dni);
            if (pstmt.executeUpdate() == EXITO) {
                resultadoOperacion = EXITO;
            }
        } catch (SQLException ex) {
            resultadoOperacion = UNKNOWNFAIL;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    @Override
    public int eliminarLogico(Long dni) {
        int resultadoOperacion = EMPTY;
        String query = "UPDATE persona SET visible = 0 WHERE (dni = ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, dni);
            if (pstmt.executeUpdate() == EXITO) {
                resultadoOperacion = EXITO;
            }
        } catch (SQLException ex) {
            resultadoOperacion = UNKNOWNFAIL;
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultadoOperacion;
    }

    @Override
    public List<Persona> listarPersonas() {
        List<Persona> personas = null;
        String query = """
                       SELECT dni, apellido, nombre, fecha_nac, 
                       ((YEAR(current_date()) - YEAR(fecha_nac)) - (right(current_date,5) < right(fecha_nac,5))) as edad, 
                       numero_tel, numero_tel_opcional, localidad, direccion, 
                       tuvo_covid, tiene_trasplantes, factores_riesgo
                       FROM persona WHERE visible = 1;
                       """;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            personas = new ArrayList();
            while (rs.next()) {
                Persona p = new Persona();
                p.setDni(rs.getLong("dni"));
                p.setApellido(rs.getString("apellido"));
                p.setNombre(rs.getString("nombre"));
                p.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
                p.setEdad(rs.getInt("edad"));
                p.setNumero_tel(rs.getLong("numero_tel"));
                p.setNumero_tel_opcional(rs.getLong("numero_tel_opcional"));
                p.setLocalidad(rs.getString("localidad"));
                p.setDireccion(rs.getString("direccion"));
                p.setTuvo_covid(rs.getBoolean("tuvo_covid"));
                p.setTiene_trasplantes(rs.getBoolean("tiene_trasplantes"));
                p.setFactores_riesgo(rs.getString("factores_riesgo"));
                personas.add(p);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personas;
    }

    @Override
    public Persona buscarPersona(Long dni) {
        Persona persona = null;
        String query = """
                       SELECT dni, apellido, nombre, fecha_nac,
                       ((YEAR(current_date()) - YEAR(fecha_nac)) - (right(current_date,5) < right(fecha_nac,5))) as edad, 
                       numero_tel, numero_tel_opcional, localidad, direccion, 
                       tuvo_covid, tiene_trasplantes, factores_riesgo
                       FROM persona WHERE visible = 1 AND dni = ?
                       """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                persona = new Persona();
                persona.setDni(rs.getLong("dni"));
                persona.setApellido(rs.getString("apellido"));
                persona.setNombre(rs.getString("nombre"));
                persona.setEdad(rs.getInt("edad"));
                persona.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
                persona.setNumero_tel(rs.getLong("numero_tel"));
                persona.setNumero_tel_opcional(rs.getLong("numero_tel_opcional"));
                persona.setLocalidad(rs.getString("localidad"));
                persona.setDireccion(rs.getString("direccion"));
                persona.setTuvo_covid(rs.getBoolean("tuvo_covid"));
                persona.setTiene_trasplantes(rs.getBoolean("tiene_trasplantes"));
                persona.setFactores_riesgo(rs.getString("factores_riesgo"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persona;
    }

    @Override
    public List<Persona> buscarPersonasPorDni(String dni) {
        List<Persona> personas = null;
        String query = """
                       SELECT dni, apellido, nombre, fecha_nac,
                       ((YEAR(current_date()) - YEAR(fecha_nac)) - (right(current_date,5) < right(fecha_nac,5))) as edad, 
                       numero_tel, numero_tel_opcional, localidad, direccion, 
                       tuvo_covid, tiene_trasplantes, factores_riesgo
                       FROM persona WHERE dni LIKE ? AND visible = 1;
                       """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, dni + "%");
            ResultSet rs = pstmt.executeQuery();
            personas = new ArrayList();
            while (rs.next()) {
                Persona p = new Persona();
                p.setDni(rs.getLong("dni"));
                p.setApellido(rs.getString("apellido"));
                p.setNombre(rs.getString("nombre"));
                p.setEdad(rs.getInt("edad"));
                p.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
                p.setNumero_tel(rs.getLong("numero_tel"));
                p.setNumero_tel_opcional(rs.getLong("numero_tel_opcional"));
                p.setLocalidad(rs.getString("localidad"));
                p.setDireccion(rs.getString("direccion"));
                p.setTuvo_covid(rs.getBoolean("tuvo_covid"));
                p.setTiene_trasplantes(rs.getBoolean("tiene_trasplantes"));
                p.setFactores_riesgo(rs.getString("factores_riesgo"));
                personas.add(p);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personas;
    }

    @Override
    public List<Persona> buscarPersonasPorNomYApe(String nomYApe) {
        List<Persona> personas = null;
        String query = """
                       SELECT dni, apellido, nombre, fecha_nac,
                       ((YEAR(current_date()) - YEAR(fecha_nac)) - (right(current_date,5) < right(fecha_nac,5))) as edad, 
                       numero_tel, numero_tel_opcional, localidad, direccion, 
                       tuvo_covid, tiene_trasplantes, factores_riesgo
                       FROM persona WHERE visible = 1 AND (nombre LIKE ? OR apellido LIKE ? ) ;
                       """;
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nomYApe + "%");
            pstmt.setString(2, nomYApe + "%");
            ResultSet rs = pstmt.executeQuery();
            personas = new ArrayList();
            while (rs.next()) {
                Persona p = new Persona();
                p.setDni(rs.getLong("dni"));
                p.setApellido(rs.getString("apellido"));
                p.setNombre(rs.getString("nombre"));
                p.setEdad(rs.getInt("edad"));
                p.setFecha_nac(rs.getDate("fecha_nac").toLocalDate());
                p.setNumero_tel(rs.getLong("numero_tel"));
                p.setNumero_tel_opcional(rs.getLong("numero_tel_opcional"));
                p.setLocalidad(rs.getString("localidad"));
                p.setDireccion(rs.getString("direccion"));
                p.setTuvo_covid(rs.getBoolean("tuvo_covid"));
                p.setTiene_trasplantes(rs.getBoolean("tiene_trasplantes"));
                p.setFactores_riesgo(rs.getString("factores_riesgo"));
                personas.add(p);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonaRepos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return personas;
    }
}
