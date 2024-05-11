package com.softsalud.software.persistence.repository.interfaz;

import com.softsalud.software.persistence.model.Vacunacion;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IVacunacionRepository {
    public int insertar(Vacunacion vacunacion);
    public int modificar(Vacunacion nuevaVacunacion, Long dniPersona, Long codigoVacuna, String loteVacuna);
    public int eliminar(Vacunacion vacunacion);
    public int eliminarTodo();
    public List<Vacunacion> listarVacunaciones();
    public List<Vacunacion> listarVacunacionesCrudo();
    public Vacunacion buscarVacunacion(Long dniPersona, Long codigoVacuna, String loteVacuna);
    public List<Vacunacion> buscarVacunacionesPorDni(String dni);
    public boolean existeVacunacionConDni(String dni);
    public boolean existeVacunacionConMarcaVacuna(String marca);
    public List<Vacunacion> buscarVacunacionesPorNomYApe(String nomYApe);
    public List<Vacunacion> buscarVacunacionesPorNombreVacuna(String nombreVacuna);
    public List<Vacunacion> buscarVacunacionesPorDosis(String dosis);
}
