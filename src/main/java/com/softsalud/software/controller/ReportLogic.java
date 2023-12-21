package com.softsalud.software.controller;

import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import com.softsalud.software.reportGenerator.reportGenerator;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;

/**
 *
 * @author Gonzalez Ismael
 */
public class ReportLogic {

    private final IVaccineService iVaccineServi;

    public ReportLogic(IVaccineService iVaccineService) {
        this.iVaccineServi = iVaccineService;
    }

    public void generateVaccineReport() {
        reportGenerator generator = new reportGenerator(iVaccineServi);
        String outputName = JOptionPane.showInputDialog("Ingrese un nombre para el archivo :");
        generator.printListVaccinePDF("src/main/resources/report/jrxmlfile/list-vaccine.jrxml",
                "src/main/java/com/softsalud/software/report/output/" + outputName + ".pdf");
    }

    public void generatePersonPerVaccineReport(JRootPane rootPane, String vaccineName) {
        if (!vaccineName.isEmpty()) {
            reportGenerator generator = new reportGenerator(iVaccineServi);
            String outputName = JOptionPane.showInputDialog("Ingrese un nombre para el archivo :");
            generator.printListPersonPerVaccine("src/main/resources/report/jrxmlfile/list-personPerVaccine.jrxml",
                    "src/main/java/com/softsalud/software/report/output/" + outputName + ".pdf",
                    vaccineName);
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Ha ocurrido un error, revise e intente de nuevo.",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
