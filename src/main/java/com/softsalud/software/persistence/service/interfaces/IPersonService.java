package com.softsalud.software.persistence.service.interfaces;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.entity.Person;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Ismael
 */
public interface IPersonService {
    public List<Person> getPeople();
    public void savePerson(Long dni, String name, String last_name, LocalDate birthdate, int age, Long phone_number,
             Long optional_phone, Address address, String risk_factor, boolean has_covid, boolean has_transplants);
    public void savePerson(Person person);
    public void deletePerson(Long dni);
    public void updatePerson(Long dni, String name, String last_name, LocalDate birthdate, int age, Long phone_number,
             Long optional_phone, Address address, String risk_factor, boolean has_covid, boolean has_transplants);
    public Person findPerson(Long dni);
}
