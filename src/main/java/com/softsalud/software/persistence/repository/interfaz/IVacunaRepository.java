package com.softsalud.software.persistence.repository.interfaz;

import com.softsalud.software.persistence.model.Vacuna;
import java.util.List;

/**
 *
 * @author Ismael
 */
public interface IVacunaRepository {
    public boolean insertar(String nombreVacuna);
    public boolean modificar(int codigo, String nombreVacuna);
    public boolean eliminar(int codigo);
    public List<Vacuna> listarVacunas();
    public Vacuna buscarVacuna(int codigo);
    public List<Vacuna> listarVacunasPorNombre(String nombre);
    public Vacuna buscarVacunaPorNombre(String nombre);
}
