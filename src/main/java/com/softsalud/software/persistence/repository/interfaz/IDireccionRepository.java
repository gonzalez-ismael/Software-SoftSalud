package com.softsalud.software.persistence.repository.interfaz;

import com.softsalud.software.persistence.model.Direccion;
import java.util.List;

/**
 *
 * @author Ismael
 */
public interface IDireccionRepository {
    public boolean insertar(Direccion direccion);
    public boolean insertar(String barrio, String calle, int numCasa);
    public boolean modificar(Direccion direccion, Long buscado);
    public boolean eliminar(Long id);
    public List<Direccion> listarDirecciones();
    public Direccion buscarDireccion(Long id);
    public Direccion buscarDireccion(String barrio, String calle, int numCasa);
}
