/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softsalud.software.reportGenerator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase para lograr la conexion con la BD
 * @author Gonzalez Ismael
 */
public class ConexionBD {
    /*
    * Variables Globales
    */
    private Connection conexion = null;
    
    /*
    * Constructor creado por el programador
    */
    public ConexionBD() throws FileNotFoundException, IOException, SQLException{
        FileInputStream file = new FileInputStream("src/main/resources/application.properties");
        Properties prop = new Properties();
        prop.load(file);
        
        conexion = DriverManager.getConnection(prop.getProperty("spring.datasource.url"),prop.getProperty("spring.datasource.username"),prop.getProperty("spring.datasource.password"));
    }

    /*
    * Metodo para obtener la conexion
    */
    public Connection getConexion() {
        return conexion;
    }
}
