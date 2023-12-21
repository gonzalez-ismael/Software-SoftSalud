package com.softsalud.software.test;

import com.softsalud.software.persistence.entity.DoseHistory;
import com.softsalud.software.persistence.service.interfaces.IDoseHistoryService;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ismael
 */
@Component
public class DoseHistoryTest {

    private final IDoseHistoryService iDoseHistoryServi;

    @Autowired
    public DoseHistoryTest(IDoseHistoryService iDoseHistoryServi) {
        this.iDoseHistoryServi = iDoseHistoryServi;
    }

    public void initTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                // Mostrar menú
                System.out.println("\n\n==== Menú ====");
                System.out.println("1. Agregar Relacion Vacunacion");
                System.out.println("2. Mostrar Relaciones de Vacunacion");
                System.out.println("3. Actualizar Relacion de Vacunacion");
                System.out.println("4. Eliminar Relacion de Vacunacion");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                // Leer opción del usuario
                opcion = scanner.nextInt();

                // Procesar opción
                switch (opcion) {
                    case 1 ->
                        agregarRelacionVacunacion(this.iDoseHistoryServi);
                    case 2 ->
                        mostrarRelacionesVacunacion(this.iDoseHistoryServi);
                    case 3 ->
                        actualizarRelacionVacunacion(this.iDoseHistoryServi);
                    case 4 ->
                        eliminarRelacionVacunacion(this.iDoseHistoryServi);
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

    private static void agregarRelacionVacunacion(IDoseHistoryService servi) {
        System.out.println("\nAgregando una Relacion de Vacunacion...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Long dni = s1.nextLong();
        Long code = s2.nextLong();
        LocalDate vaccination_date = LocalDate.of(2023, 12, 13);
        String vaccine_lot = "LOT23/12";
        int number_doses = 1;
        String vaccination_place = "MI CASA JOJO";
        servi.saveDoseHistory(dni, code, vaccine_lot, vaccination_date, number_doses, vaccination_place);
    }

    private static void mostrarRelacionesVacunacion(IDoseHistoryService servi) {
        System.out.println("\nMostrando Relaciones de Vacunacion...");
        List<DoseHistory> relacionesVacunacion = servi.getDoseHistories();
        relacionesVacunacion.forEach(r -> System.out.println("ID: " + r.getId().getPerson() + " " + r.getId().getVaccine()
                + ", lote vacuna: " + r.getId().getVaccine_lot()
                + ", fecha: " + r.getVaccination_date()
                + ", lugar de vacunacion: " + r.getVaccination_place()
                + ", numero de dosis: " + r.getNumber_doses()
        ));
    }

    private static void actualizarRelacionVacunacion(IDoseHistoryService servi) {
        System.out.println("\nModificando una Relacion de Vacunacion...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Long dni = s1.nextLong();
        Long code = s2.nextLong();
        LocalDate vaccination_date = LocalDate.of(2023, 12, 13);
        String vaccine_lot = "LOT23/14";
        int number_doses = 2;
        String vaccination_place = "TU CASA JOJO";
        servi.updateDoseHistory(dni, code, vaccine_lot, vaccination_date, number_doses, vaccination_place);
    }

    private static void eliminarRelacionVacunacion(IDoseHistoryService servi) {
        System.out.println("\nEliminando una Relacion de Vacunacion...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        Long dni = s1.nextLong();
        Long code = s2.nextLong();
        String vaccine_lot = s3.next();
        servi.deleteDoseHistory(dni, code,vaccine_lot);
    }

}
