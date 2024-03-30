package com.softsalud.software.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Clase que se encarga de crear y cerrar la conexion con la bd.
 * @author Gonzalez Ismael
 */
public class ConnectionDB {

    /*
        Variables Globales
     */
    private Connection connection = null;

    /*
        Constructor creado por el programador.
     */
    public ConnectionDB() {
        try {
            FileInputStream file = new FileInputStream("src/main/resources/app.properties");
            Properties properties = new Properties();
            properties.load(file);

            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");

            connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
        Método para obtener la conexion.
     */
    public Connection getConnection() {
        return connection;
    }

    /*
        Método para cerrar la conexion.
    */
    public void closeConnection() {
        try {
            this.getConnection().close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
