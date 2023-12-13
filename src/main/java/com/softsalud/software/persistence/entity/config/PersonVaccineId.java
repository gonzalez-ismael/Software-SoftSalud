package com.softsalud.software.persistence.entity.config;

import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.entity.Vaccine;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Gonzalez Ismael
 */
@Embeddable @Getter @Setter
public class PersonVaccineId {
    @ManyToOne
    @JoinColumn(name = "person_dni")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "vaccine_code")
    private Vaccine vaccine;

    public PersonVaccineId() {
    }
    
    public PersonVaccineId(Person person, Vaccine vaccine) {
        this.person = person;
        this.vaccine = vaccine;
    }
}
