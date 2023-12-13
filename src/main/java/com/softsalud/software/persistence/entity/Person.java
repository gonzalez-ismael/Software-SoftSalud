package com.softsalud.software.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_phone",
            joinColumns = {
                @JoinColumn(name = "person_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "phone_id")}
    )
    private List<Phone> phones = new ArrayList<Phone>();
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
