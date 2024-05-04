package com.softsalud.software.configuration;

import com.softsalud.software.controller.resource.FileProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Gonzalez Ismael
 */
public class ConfiguracionInicial {

    /**
     * Este método crea las rutas necesarias para el proyecto.
     */
    public static void crearDirectorios() {
        String rutaDirectorioBackup = "C:/ProgramData/Backups";
        String nombreDeUsuario = System.getProperty("user.name");
        String rutaDirectorioReportes = "C:/Users/" + nombreDeUsuario + "/Documents/Reportes de Salud";
        String rutaDirectorioDescargas = "C:/Users/" + nombreDeUsuario + "/Downloads";
        // Convertir la ruta de cadena a objeto Path
        Path pathDirectorioBackup = Paths.get(rutaDirectorioBackup);
        Path pathDirectorioReportes = Paths.get(rutaDirectorioReportes);
        Path pathDirectorioDescargas = Paths.get(rutaDirectorioDescargas);
        try {
            // Crear el directorio y todos los directorios padres si no existen
            Files.createDirectories(pathDirectorioBackup);
            Files.createDirectories(pathDirectorioReportes);
            Files.createDirectories(pathDirectorioDescargas);
        } catch (IOException ex) {
            System.err.println("Error al crear el directorio: " + ex.getMessage());
        }
    }

    /**
     * Este método establece por primera vez las carpetas predeterminadas.
     */
    public static void establecerRutasPredeterminadas() {
        FileProperties fileProperties = new FileProperties();
        String rutaBackup = fileProperties.getFile().getProperty("urlBackupLocation");
        if ("".equals(rutaBackup)) {
            fileProperties.getFile().setProperty("urlBackupLocation", "C:/ProgramData/Backups");
        }
        String rutaReports = fileProperties.getFile().getProperty("urlOutputLocation");
        if ("".equals(rutaReports)) {
            String nombreDeUsuario = System.getProperty("user.name");
            fileProperties.getFile().setProperty("urlOutputLocation", "C:/Users/" + nombreDeUsuario + "/Documents/Reportes de Salud");
        }
        fileProperties.saveFile();
    }
}
