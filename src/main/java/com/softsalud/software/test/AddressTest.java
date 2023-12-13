package com.softsalud.software.test;

import com.softsalud.software.persistence.entity.Address;
import com.softsalud.software.persistence.service.interfaces.IAddressService;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gonzalez Ismael
 */
@Component
public class AddressTest {

    private final IAddressService addressService;

    @Autowired
    public AddressTest(IAddressService addressService) {
        this.addressService = addressService;
    }

    public void initTest() {
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                // Mostrar menú
                System.out.println("\n\n==== Menú ====");
                System.out.println("1. Agregar Dirección");
                System.out.println("2. Mostrar Direcciones");
                System.out.println("3. Actualizar Dirección");
                System.out.println("4. Eliminar Dirección");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                // Leer opción del usuario
                opcion = scanner.nextInt();

                // Procesar opción
                switch (opcion) {
                    case 1 ->
                        agregarDireccion(this.addressService);
                    case 2 ->
                        mostrarDirecciones(this.addressService);
                    case 3 ->
                        actualizarDireccion(this.addressService);
                    case 4 ->
                        eliminarDireccion(this.addressService);
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

    private static void agregarDireccion(IAddressService servi) {
        System.out.println("\nAgregando una dirección...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        String barrio = s1.nextLine();
        String calle = s2.nextLine();
        Integer num = s3.nextInt();
        if (!barrio.isEmpty() && !calle.isEmpty()) {
            servi.saveAddress(barrio, calle, num);
        }
    }

    private static void mostrarDirecciones(IAddressService servi) {
        System.out.println("\nMostrando direcciones...");
        List<Address> direcciones = servi.getAddresses();
        direcciones.forEach(d -> System.out.println("ID: " + d.getId() + ", Direccion: " + d.getDistrict() + " " + d.getStreet() + " " + d.getNumber()));
    }

    private static void actualizarDireccion(IAddressService servi) {
        System.out.println("\nActualizando una dirección...");
        Scanner s1 = new Scanner(System.in);
        Scanner s2 = new Scanner(System.in);
        Scanner s3 = new Scanner(System.in);
        Scanner s4 = new Scanner(System.in);
        Long id = s1.nextLong();
        String barrio = s2.nextLine();
        String calle = s3.nextLine();
        Integer num = s4.nextInt();
        if (!barrio.isEmpty() && !calle.isEmpty()) {
            servi.updateAddress(id, barrio, calle, num);
        }
    }

    private static void eliminarDireccion(IAddressService servi) {
        System.out.println("\nEliminando una dirección...");
        Scanner s = new Scanner(System.in);
        Long input = s.nextLong();
        servi.deleteAddress(input);
    }
}
