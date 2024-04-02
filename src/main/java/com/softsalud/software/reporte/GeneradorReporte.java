package com.softsalud.software.reporte;

import com.softsalud.software.connection.ConnectionDB;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

/**
 *
 * @author Gonzalez Ismael
 */
public class GeneradorReporte {

    private final IVacunaRepository vacunaRepos;
//    private IDoseHistoryRepository doseRepos;

    public GeneradorReporte(IVacunaRepository vacunaRepos) {
        this.vacunaRepos = vacunaRepos;
    }

    public void generarListaVacunasPDF(String jrxml, String outputName) {
        try {
            List<Vacuna> listaVacunas = vacunaRepos.listarVacunas();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVacunas);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputName);
        } catch (JRException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "Error al generar el informe PDF.", ex);
        }
    }

    public void generarListaVacunasHTML(String jrxml, String outputName) {
        try {
            List<Vacuna> listaVacunas = vacunaRepos.listarVacunas();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVacunas);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            JasperExportManager.exportReportToHtmlFile(jasperPrint, outputName);
        } catch (JRException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "Error al generar el informe HTML.", ex);
        }
    }

    public void generarListaVacunasXLS(String jrxml, String outputName) {
        try {
            List<Vacuna> listaVacunas = vacunaRepos.listarVacunas();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVacunas);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            JRXlsExporter exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, outputName);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
            exporterXLS.exportReport();
        } catch (JRException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "Error al generar el informe XLS.", ex);
        }
    }

    public void generarListaPersonasPorVacunaPDF(String jrxml, String outputName, String vaccineName) {
        try {
            //Se obtiene la conexion
            ConnectionDB conn = new ConnectionDB();
            System.out.println("Conexion a: " + conn.getConnection().getCatalog());

            //MAP TIENE DOS COMPONENTES, UN STRING NOMBRE Y UN OBJETC (DATO)
            Map<String, Object> param = new HashMap();
            //CARGAR LOS PARAMETROS DE PARAM
            param.put("paramVaccineName", vaccineName);

            //Compilar el archivo jrxml a traves del compilador
            JasperReport jr = JasperCompileManager.compileReport(jrxml);
            //Creamos el objeto a imprimir
            JasperPrint jprint = JasperFillManager.fillReport(jr, param, conn.getConnection());
            //Salida a formato
            JasperExportManager.exportReportToPdfFile(jprint, outputName);

            conn.closeConnection();

        } catch (JRException | SQLException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
