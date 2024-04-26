package com.softsalud.software.reporte;

import com.softsalud.software.connection.ConnectionDB;
import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
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

    //CONSTANTES
    public static final int PDF = 1, HTML = 2, XLS = 3;
    private final IPersonaRepository personaRepos;
    private final IVacunaRepository vacunaRepos;

    public GeneradorReporte(IPersonaRepository personaRepos, IVacunaRepository vacunaRepos) {
        this.personaRepos = personaRepos;
        this.vacunaRepos = vacunaRepos;
    }

    public boolean generarListaPersonasMultiformato(String jrxml, String outputName, int tipoExtension) {
        List<Persona> listaPersonas = personaRepos.listarPersonas();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaPersonas);
        return generarReporteMultiformato(jrxml, outputName, tipoExtension, dataSource);
    }

    public boolean generarListaVacunasMultiformato(String jrxml, String outputName, int tipoExtension) {
        List<Vacuna> listaVacunas = vacunaRepos.listarVacunas();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaVacunas);
        return generarReporteMultiformato(jrxml, outputName, tipoExtension, dataSource);
    }

    private boolean generarReporteMultiformato(String jrxml, String outputName, int tipoExtension, JRBeanCollectionDataSource dataSource) {
        try {
            //Compilar el archivo jrxml a traves del compilador
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxml);
            //Creamos el objeto a imprimir
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            //Salida a formato
            switch (tipoExtension) {
                case PDF ->
                    JasperExportManager.exportReportToPdfFile(jasperPrint, outputName);
                case HTML ->
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputName);
                case XLS -> {
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, outputName);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.FALSE);
                    exporterXLS.exportReport();
                }
            }
            return true;
        } catch (JRException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, "Error al generar el informe PDF.", ex);
            return false;
        }
    }

    public void generarListaPersonasPorVacunaPDF(String jrxml, String outputName, String vaccineName) {
        try {
            //Se obtiene la conexion
            ConnectionDB conn = new ConnectionDB();
            //MAP TIENE DOS COMPONENTES, UN STRING NOMBRE Y UN OBJETC (DATO)
            Map<String, Object> param = new HashMap();
            //CARGAR LOS PARAMETROS DE PARAM
            param.put("paramVaccineName", vaccineName);

            JasperReport jr = JasperCompileManager.compileReport(jrxml);
            JasperPrint jprint = JasperFillManager.fillReport(jr, param, conn.getConnection());
            JasperExportManager.exportReportToPdfFile(jprint, outputName);

            conn.closeConnection();
        } catch (JRException ex) {
            Logger.getLogger(GeneradorReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
