package com.softsalud.software.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Gonzalez Ismael
 */
@Entity @Getter @Setter
public class Person {
    @Id
    private Long dni;
    private String name;
    private String last_name;
    private LocalDate birthdate;
    private int age;
    private String risk_factor;
    private boolean has_covid;
    private boolean has_transplants;
    private Long phone_number;
    private Long optional_phone_number;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
