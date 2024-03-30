package com.softsalud.software.persistence.repository.interfaz;

import com.softsalud.software.persistence.model.Persona;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IPersonaRepository {
    public int insertar(Persona persona);
    public int modificar(Persona persona, Long buscado);
    public int eliminar(Long dni);
    public int eliminarLogico(Long dni);
    public List<Persona> listarPersonas();
    public Persona buscarPersona(Long dni);
    public List<Persona> buscarPersonasPorDni(String dni);
    public List<Persona> buscarPersonasPorNomYApe(String nomYApe);
}