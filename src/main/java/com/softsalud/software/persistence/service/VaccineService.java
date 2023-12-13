package com.softsalud.software.persistence.service;

import com.softsalud.software.persistence.entity.Vaccine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import com.softsalud.software.persistence.repository.IVaccineRepository;

/**
 *
 * @author Gonazalez Ismael
 */
@Service
public class VaccineService implements IVaccineService {

    @Autowired
    IVaccineRepository iVaccineRepos;

    @Override
    public List<Vaccine> getVaccines() {
        return iVaccineRepos.findAll();
    }

    @Override
    public void saveVacine(String name) {
        Vaccine vaccine = new Vaccine();
        vaccine.setNameVaccine(name);
        iVaccineRepos.save(vaccine);
    }

    @Override
    public void updateVaccine(Long id, String name) {
        Vaccine vaccine = findVaccine(id);
        vaccine.setNameVaccine(name);
        iVaccineRepos.save(vaccine);
    }

    @Override
    public void deleteVaccine(Long id) {
        iVaccineRepos.deleteById(id);
    }

    @Override
    public Vaccine findVaccine(Long id) {
        return iVaccineRepos.findById(id).orElse(null);
    }

}
