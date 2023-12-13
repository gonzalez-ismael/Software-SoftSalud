package com.softsalud.software.test;

import com.softsalud.software.persistence.entity.Vaccine;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ismael
 */
@Component
public class VaccineTest {

    private final IVaccineService vacunaService;

    @Autowired
    public VaccineTest(IVaccineService vacunaService) {
        this.vacunaService = vacunaService;
    }

    public void initeTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                // Mostrar menú
                System.out.println("\n\n==== Menú ====");
                System.out.println("1. Agregar Vacuna");
                System.out.println("2. Mostrar Vacunas");
                System.out.println("3. Actualizar Vacuna");
                System.out.println("4. Eliminar Vacuna");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                // Leer opción del usuario
                opcion = scanner.nextInt();
                
                // Procesar opción
                switch (opcion) {
                    case 1 ->
                        agregarVacuna(this.vacunaService);
                    case 2 ->
                        mostrarVacunas(this.vacunaService);
                    case 3 ->
                        actualizarVacuna(this.vacunaService);
                    case 4 ->
                        eliminarVacuna(this.vacunaService);
                    case 0 ->
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                    default ->
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                }
            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println("\nERROR: " + e.toString());
        }
    }

    private static void agregarVacuna(IVaccineService servi) {
        System.out.println("\nAgregando una vacuna...");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();
        if (!input.isEmpty()) {
            servi.saveVacine(input);
        }
    }

    private static void mostrarVacunas(IVaccineService servi) {
        System.out.println("\nMostrando vacunas...");
        List<Vaccine> vacunas = servi.getVaccines();
        vacunas.forEach(v -> System.out.println("ID: " + v.getCode() + ", Nombre: " + v.getNameVaccine()));
    }

    private static void actualizarVacuna(IVaccineService servi) {
        System.out.println("\nActualizando una vacuna...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Long id = s1.nextLong();
        String name = s2.nextLine();
        if (!name.isEmpty()) {
            servi.updateVaccine(id, name);
        }
    }

    private static void eliminarVacuna(IVaccineService servi) {
        System.out.println("\nEliminando una vacuna...");
        Scanner s = new Scanner(System.in);
        Long input = s.nextLong();
        servi.deleteVaccine(input);
    }
}
