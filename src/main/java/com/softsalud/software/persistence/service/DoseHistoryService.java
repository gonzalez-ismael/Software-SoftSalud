package com.softsalud.software.persistence.service;

import com.softsalud.software.persistence.entity.DoseHistory;
import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.entity.Vaccine;
import com.softsalud.software.persistence.entity.config.PersonVaccineId;
import com.softsalud.software.persistence.repository.IDoseHistoryRepository;
import com.softsalud.software.persistence.service.interfaces.IDoseHistoryService;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalez Ismael
 */
@Service
public class DoseHistoryService implements IDoseHistoryService {
    @Autowired IDoseHistoryRepository iDoseHistoryRepos;
    @Autowired IPersonService iPersonServi;
    @Autowired IVaccineService iVaccineServi;

    @Override
    public List<DoseHistory> getDoseHistories() {
        return iDoseHistoryRepos.findAll();
    }

    @Override
    public void saveDoseHistory(Long dni, Long code, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place) {
        DoseHistory ds = new DoseHistory();
        Person p = iPersonServi.findPerson(dni);
        Vaccine v = iVaccineServi.findVaccine(code);
        PersonVaccineId pvId = new PersonVaccineId(p,v,vaccine_lot);
        ds.setId(pvId);
        ds.setVaccination_date(vaccionation_date);
        ds.setNumber_doses(number_doses);
        ds.setVaccination_place(vaccionation_place);
        iDoseHistoryRepos.save(ds);
    }
    
    @Override
    public void saveDoseHistory(Person person, Vaccine vaccine, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place) {
        DoseHistory ds = new DoseHistory();
        PersonVaccineId pvId = new PersonVaccineId(person,vaccine,vaccine_lot);
        ds.setId(pvId);
        ds.setVaccination_date(vaccionation_date);
        ds.setNumber_doses(number_doses);
        ds.setVaccination_place(vaccionation_place);
        iDoseHistoryRepos.save(ds);
    }

    @Override
    public void deleteDoseHistory(Long dni, Long code, String vaccine_lot) {
        Person p = iPersonServi.findPerson(dni);
        Vaccine v = iVaccineServi.findVaccine(code);
        PersonVaccineId pvId = new PersonVaccineId(p,v, vaccine_lot);
        iDoseHistoryRepos.deleteById(pvId);
    }

    @Override
    public void updateDoseHistory(Long dni, Long code, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place) {
        DoseHistory ds = findDoseHistory(dni, code, vaccine_lot);
        ds.setVaccination_date(vaccionation_date);
        ds.setVaccination_place(vaccionation_place);
        ds.setNumber_doses(number_doses);
        iDoseHistoryRepos.save(ds);
    }

    @Override
    public DoseHistory findDoseHistory(Long dni, Long code, String vaccine_lot) {
        Person p = iPersonServi.findPerson(dni);
        Vaccine v = iVaccineServi.findVaccine(code);
        PersonVaccineId pvId = new PersonVaccineId(p,v, vaccine_lot);
        return iDoseHistoryRepos.findById(pvId).orElse(null);
    }
}
