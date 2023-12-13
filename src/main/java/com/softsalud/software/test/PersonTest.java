package com.softsalud.software.test;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.entity.Person;
import com.softsalud.software.persistence.entity.Phone;
import com.softsalud.software.persistence.service.interfaces.IAddressService;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import com.softsalud.software.persistence.service.interfaces.IPhoneService;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gonzalez Ismael
 */
@Component
public class PersonTest {

    private final IPersonService personService;
    private final IPhoneService phoneService;
    private final IAddressService addressService;

    @Autowired
    public PersonTest(IPersonService personService, IPhoneService phoneService, IAddressService addressService) {
        this.personService = personService;
        this.phoneService = phoneService;
        this.addressService = addressService;
    }

    public void initTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                // Mostrar menú
                System.out.println("\n\n==== Menú ====");
                System.out.println("1. Agregar Persona");
                System.out.println("2. Mostrar Personas");
                System.out.println("3. Actualizar Persona");
                System.out.println("4. Eliminar Persona");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                // Leer opción del usuario
                opcion = scanner.nextInt();

                // Procesar opción
                switch (opcion) {
                    case 1 ->
                        agregarPersona(this.personService, this.phoneService, this.addressService);
                    case 2 ->
                        mostrarPersonas(this.personService);
                    case 3 ->
                        actualizarPersona(this.personService);
                    case 4 ->
                        eliminarPersona(this.personService);
                    case 0 ->
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                    default ->
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println("\nERROR PERSONA: " + e.toString());
        }
    }

    private static void agregarPersona(IPersonService perServi, IPhoneService phoServi, IAddressService addServi) {
        System.out.println("\nAgregando una persona...");
        Scanner s = new Scanner(System.in);
        Long dni = s.nextLong();
        String name = "Papa";
        String last_name = "Pardo";
        LocalDate date = LocalDate.of(1998, 11, 18);
        LocalDate now = LocalDate.now();
        Period time = Period.between(date, now);
        int age = time.getYears();
        String risk_factors = "Diabetes tipo 2";
        boolean has_covid = false;
        boolean has_transplants = false;
        List<Phone> phones = new ArrayList<>();
        phones.add(phoServi.findPhone(Long.valueOf("2")));
        Address address = addServi.findAddress(Long.valueOf("1"));

        Person p = new Person();
        p.setDni(dni);
        p.setName(name);
        p.setLast_name(last_name);
        p.setBirthdate(date);
        p.setAge(age);
        p.setRisk_factor(risk_factors);
        p.setHas_covid(has_covid);
        p.setHas_transplants(has_transplants);
        p.setPhones(phones);
        p.setAddress(address);

        System.out.println("PERSONA: " + p.toString());
        perServi.savePerson(p);
    }

    private static void mostrarPersonas(IPersonService servi) {
        System.out.println("\nMostrando personas...");
        List<Person> personas = servi.getPeople();
        personas.forEach(p -> System.out.println("DNI: " + p.getDni() + ", Nombre: " + p.getName()));
    }

    private static void actualizarPersona(IPersonService servi) {
        System.out.println("\nActualizando una persona...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Long dni = s1.nextLong();
        String name = s2.nextLine();
        if (!name.isEmpty()) {
//            servi.updatePerson(dni, name);
        }
    }

    private static void eliminarPersona(IPersonService servi) {
        System.out.println("\nEliminando una persona...");
        Scanner s = new Scanner(System.in);
        Long dni = s.nextLong();
        servi.deletePerson(dni);
    }
}
