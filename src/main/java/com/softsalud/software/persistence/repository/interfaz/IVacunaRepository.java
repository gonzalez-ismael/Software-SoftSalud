package com.softsalud.software.persistence.repository.interfaz;

import com.softsalud.software.persistence.model.Vacuna;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IVacunaRepository {
    public int insertar(String nombreVacuna);
    public int modificar(int codigo, String nombreVacuna);
    public boolean eliminar(int codigo);
    public int eliminarTodo();
    public List<Vacuna> listarVacunas();
    public Vacuna buscarVacuna(int codigo);
    public List<Vacuna> listarVacunasPorNombre(String nombre);
    public Vacuna buscarVacunaPorNombre(String nombre);
}
