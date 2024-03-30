package com.softsalud.software;

import com.softsalud.software.connection.ConnectionDB;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.repository.VacunaRepos;
import com.softsalud.software.view.JFrameMain;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Ismael
 */
public class SoftSaludSoftware {

//    public static void main(String[] args) {
//        try {
//            ConnectionDB conexion = new ConnectionDB();
//            System.out.println("\n\n");
//            System.out.println("Conexion a: " + conexion.getConnection().getCatalog());
//            System.out.println("\n\n");
//
////            main2(conexion);
//            
//            JFrameMain main = new JFrameMain(conexion.getConnection());
//            System.setProperty("java.awt.headless", "false");
//            main.setLocationRelativeTo(null);
//            main.setVisible(true);
//            
//            conexion.closeConnection();
//        } catch (SQLException ex) {
//            System.out.println("ERROR: " + ex.getMessage());
//        }
//    }
    
    public static void main2(ConnectionDB conexion){
         VacunaRepos logica = new VacunaRepos(conexion.getConnection());

            boolean sepudo = false;
//            sepudo = logica.insertar("Sputnik");
//            sepudo = logica.modificar(7, "Sputnik 2");
//            sepudo = logica.eliminar(5);
//            System.out.println("Insercion: " + sepudo);

            // Obtenemos el conjunto de vacunas utilizando el método getVacunas()
            List<Vacuna> vacunas = logica.listarVacunas();

            // Verificamos si el conjunto de vacunas no es nulo
            if (vacunas != null) {
                // Iteramos sobre el conjunto de vacunas e imprimimos cada vacuna
                System.out.println("Lista de Vacunas:");
                for (Vacuna vacuna : vacunas) {
                    System.out.println("Código: " + vacuna.getCodigo() + ", Nombre: " + vacuna.getNombreVacuna());
                }
            } else {
                System.out.println("No se pudo obtener la lista de vacunas.");
            }

            Vacuna find = logica.buscarVacuna(4);

            if (find != null) {
                // Iteramos sobre el conjunto de vacunas e imprimimos cada vacuna
                System.out.println("\n\nVacuna:");
                System.out.println("Código: " + find.getCodigo() + ", Nombre: " + find.getNombreVacuna());

            } else {
                System.out.println("No se pudo obtener la vacuna buscada.\n\n");
            }
    }
}
