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
    public List<Vacunacion> listarVacunaciones();
    public List<Vacunacion> listarVacunacionesCrudo();
    public Vacunacion buscarVacunacion(Long dniPersona, Long codigoVacuna, String loteVacuna);
}
