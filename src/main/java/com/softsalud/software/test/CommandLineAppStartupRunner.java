package com.softsalud.software.test;

import com.softsalud.software.persistence.service.interfaces.IAddressService;
import com.softsalud.software.persistence.service.interfaces.IDoseHistoryService;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import com.softsalud.software.persistence.service.interfaces.IPhoneService;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import com.softsalud.software.view.JFrameMain;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final IVaccineService vaccineService;
    private final IPhoneService phoneService;
    private final IAddressService addressService;
    private final IPersonService personService;
    private final IDoseHistoryService doseService;

    @Autowired
    public CommandLineAppStartupRunner(IVaccineService vaccineService, IPhoneService phoneService, IAddressService addressService, IPersonService personService, IDoseHistoryService doseService) {
        this.vaccineService = vaccineService;
        this.phoneService = phoneService;
        this.addressService = addressService;
        this.personService = personService;
        this.doseService = doseService;
    }

    @Override
    public void run(String... args) throws Exception {
//        testBack();
        System.setProperty("java.awt.headless", "false");
        JFrameMain main = new JFrameMain(vaccineService);
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }

    private void testBack() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            System.out.println("\n\n==== Menú ====");
            System.out.println("1. Vacuna");
            System.out.println("2. Telefono");
            System.out.println("3. Direccion");
            System.out.println("4. Persona");
            System.out.println("5. Relacion Vacunacion");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            // Procesar opción
            switch (opcion) {
                case 1 ->
                    mostrarVacuna();
                case 2 ->
                    mostrarTelefono();
                case 3 ->
                    mostrarDireccion();
                case 4 ->
                    mostrarPersona();
                case 5 ->
                    mostrarRelacion();
                case 0 ->
                    System.out.println("Saliendo del programa. ¡Hasta luego!");
                default ->
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } catch (Exception e) {
            System.out.println("\nTIPO DE ERROR: " + e.toString());
            System.out.println("\nMENSAJE: " + e.getMessage());
        }
    }

    public void mostrarVacuna() {
        VaccineTest v = new VaccineTest(vaccineService);
        v.initeTest();
    }

    public void mostrarTelefono() {
        PhoneTest p = new PhoneTest(phoneService);
        p.initTest();
    }

    public void mostrarDireccion() {
        AddressTest a = new AddressTest(addressService);
        a.initTest();
    }

    public void mostrarPersona() {
        PersonTest p = new PersonTest(personService, phoneService, addressService);
        p.initTest();
    }

    public void mostrarRelacion() {
        DoseHistoryTest ds = new DoseHistoryTest(doseService);
        ds.initTest();
    }
}
