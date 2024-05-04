package com.softsalud.software.controller.resource;

import com.softsalud.software.view.JDialogConfig;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase se usa para poder obtener directamente el archivo properties del software.
 *
 * @author Gonzalez Ismael
 */
public class FileProperties {

    private final String rutaProperties = "src/main/resources/app.properties";
    private Properties properties;

    public FileProperties() {
        try {
            FileInputStream file = new FileInputStream(rutaProperties);
            Properties propertiesTemp = new Properties();
            propertiesTemp.load(file);
            this.properties = propertiesTemp;
        } catch (IOException ex) {
            Logger.getLogger(JDialogConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método get properties.
     *
     * @return Properties: devuelve el archivo de propiedades de la aplicación.
     */
    public Properties getFile() {
        return properties;
    }

    /**
     * Método set properties.
     */
    public void saveFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(rutaProperties);
            properties.store(fileOut, "Configuración de la aplicación");
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(JDialogConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
