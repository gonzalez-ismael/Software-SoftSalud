package com.softsalud.software.persistence.entity;

import com.softsalud.software.persistence.entity.config.PersonVaccineId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Gonzalez Ismael
 */
@Entity @Getter @Setter
public class DoseHistory {
    @EmbeddedId
    private PersonVaccineId id;
    private LocalDate vaccination_date;
    private String vaccine_lot;
    private int number_doses;
    private String vaccination_place;
}
