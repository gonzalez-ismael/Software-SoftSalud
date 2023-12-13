package com.softsalud.software.persistence.service;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.entity.Phone;
import com.softsalud.software.persistence.repository.IPersonRepository;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalez Ismael
 */
@Service
public class PersonService implements IPersonService {
    @Autowired IPersonRepository iPersonRepos;

    @Override
    public List<Person> getPeople() {
        return iPersonRepos.findAll();
    }

    @Override
    public void savePerson(Person p) {
        iPersonRepos.save(p);
    }
    
    @Override
    public void savePerson(Long dni, String name, String last_name, LocalDate birthdate, int age, List<Phone> phones, Address address, String risk_factor, boolean has_covid, boolean has_transplants) {
        Person p = new Person();
        p.setDni(dni);
        p.setName(name);
        p.setLast_name(last_name);
        p.setBirthdate(birthdate);
        p.setAge(age);
        p.setPhones(phones);
        p.setAddress(address);
        p.setRisk_factor(risk_factor);
        p.setHas_covid(has_covid);
        p.setHas_transplants(has_transplants);
        iPersonRepos.save(p);
    }

    @Override
    public void deletePerson(Long dni) {
        iPersonRepos.deleteById(dni);
    }

    @Override
    public void updatePerson(Long dni, String name, String last_name, LocalDate birthdate, int age, List<Phone> phones, Address address, String risk_factor, boolean has_covid, boolean has_transplants) {
        Person p = findPerson(dni);
        p.setDni(dni);
        p.setName(name);
        p.setLast_name(last_name);
        p.setBirthdate(birthdate);
        p.setAge(age);
        p.setPhones(phones);
        p.setAddress(address);
        p.setRisk_factor(risk_factor);
        p.setHas_covid(has_covid);
        p.setHas_transplants(has_transplants);
        iPersonRepos.save(p);
    }

    @Override
    public Person findPerson(Long dni) {
        return iPersonRepos.findById(dni).orElse(null);
    }
}
