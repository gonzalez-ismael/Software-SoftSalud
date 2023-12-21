package com.softsalud.software.persistence.service.interfaces;

import com.softsalud.software.persistence.entity.DoseHistory;
import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.entity.Vaccine;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Gonzalez Ismael
 */
public interface IDoseHistoryService {
    public List<DoseHistory> getDoseHistories();
    public void saveDoseHistory(Long dni, Long code, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place);
    public void saveDoseHistory(Person person, Vaccine vaccine, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place);
    public void deleteDoseHistory(Long dni, Long code, String vaccine_lot);
    public void updateDoseHistory(Long dni, Long code, String vaccine_lot, LocalDate vaccionation_date, int number_doses, String vaccionation_place);
    public DoseHistory findDoseHistory(Long dni, Long code, String vaccine_lot);
}
