package com.softsalud.software.persistence.service.interfaces;

import com.softsalud.software.persistence.entity.Vaccine;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IVaccineService {
    public List<Vaccine> getVaccines();
    public void saveVacine(String name);
    public void deleteVaccine(Long id);
    public void updateVaccine(Long id, String name);
    public Vaccine findVaccine(Long id);
}
