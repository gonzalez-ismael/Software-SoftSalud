package com.softsalud.software.test;

import com.softsalud.software.persistence.entity.Phone;
import com.softsalud.software.persistence.service.interfaces.IPhoneService;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Ismael
 */
@Component
public class PhoneTest {

    private final IPhoneService phoneService;

    @Autowired
    public PhoneTest(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    public void initTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                // Mostrar menú
                System.out.println("\n\n==== Menú ====");
                System.out.println("1. Agregar Teléfono");
                System.out.println("2. Mostrar Teléfonos");
                System.out.println("3. Actualizar Teléfono");
                System.out.println("4. Eliminar Teléfono");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                // Leer opción del usuario
                opcion = scanner.nextInt();

                // Procesar opción
                switch (opcion) {
                    case 1 ->
                        agregarTelefono(this.phoneService);
                    case 2 ->
                        mostrarTelefonos(this.phoneService);
                    case 3 ->
                        actualizarTelefono(this.phoneService);
                    case 4 ->
                        eliminarTelefono(this.phoneService);
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

    private static void agregarTelefono(IPhoneService servi) {
        System.out.println("\nAgregando un teléfono...");
        Scanner s = new Scanner(System.in);
        Long input = s.nextLong();
        servi.savePhone(input);
    }

    private static void mostrarTelefonos(IPhoneService servi) {
        System.out.println("\nMostrando teléfonos...");
        List<Phone> telefonos = servi.getPhones();
        telefonos.forEach(p -> System.out.println("ID: " + p.getId() + ", Número: " + p.getPhone()));
    }

    private static void actualizarTelefono(IPhoneService servi) {
        System.out.println("\nActualizando un teléfono...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Long id = s1.nextLong();
        Long numero = s2.nextLong();
        servi.updatePhone(id, numero);
    }

    private static void eliminarTelefono(IPhoneService servi) {
        System.out.println("\nEliminando un teléfono...");
        Scanner s = new Scanner(System.in);
        Long input = s.nextLong();
        servi.deletePhone(input);
    }

}
