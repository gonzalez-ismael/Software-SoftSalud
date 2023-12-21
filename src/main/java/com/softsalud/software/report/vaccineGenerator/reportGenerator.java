package com.softsalud.software.report.vaccineGenerator;

import com.softsalud.software.persistence.entity.Vaccine;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.springframework.util.ResourceUtils;

/**
 *
 * @author Gonzalez Ismael
 */
public class reportGenerator {
    
    private final IVaccineService vaccineServi;
//    private IDoseHistoryRepository doseRepos;

    public reportGenerator(IVaccineService vaccineService) {
        this.vaccineServi = vaccineService;
    }

    public void printListVaccinePDF(String jrxml, String outputName){
        try {
            List<Vaccine> vaccines = vaccineServi.getVaccines();
            File file = ResourceUtils.getFile(jrxml);
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vaccines);
            Map<String, Object> parameters = new HashMap<>();
//            parameters.put("name_vaccine","name_vaccine");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputName);
        } catch (FileNotFoundException | JRException ex) {
            Logger.getLogger(reportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void printListPersonPerVaccine(String jrxml, String vaccineName, String outputName){
//        try {
//            List<Object> vaccines = doseRepos.listPeoplePerVaccine(vaccineName);
//            File file = ResourceUtils.getFile(jrxml);
//            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vaccines);
//            Map<String, Object> parameters = new HashMap<>();
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//            JasperExportManager.exportReportToPdfFile(jasperPrint, outputName);
//        } catch (FileNotFoundException | JRException ex) {
//            Logger.getLogger(reportGenerator.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
