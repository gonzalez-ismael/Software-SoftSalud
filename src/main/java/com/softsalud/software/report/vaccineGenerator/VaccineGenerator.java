package com.softsalud.software.report.vaccineGenerator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Gonzalez Ismael
 */
public class VaccineGenerator {
    
    @Autowired private DataSource dataSource; // Inyecta el DataSource de Spring

    public void printListVaccinePDF(String jrxml, String outputName){
        try {
            Connection conexion = dataSource.getConnection();
            // Configura los parámetros que puedan necesitar los reportes
            Map<String, Object> parameters = new HashMap<>();
            // Obtén la conexión de la fuente de datos de Spring Boot
            parameters.put("REPORT_DATA_SOURCE", conexion);

            JasperReport jr = JasperCompileManager.compileReport(jrxml);
            JasperPrint jPrint = JasperFillManager.fillReport(jr, parameters, conexion);
            JasperExportManager.exportReportToPdfFile(jPrint, outputName);
            
        } catch (JRException | SQLException ex) {
            Logger.getLogger(VaccineGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
