package com.softsalud.software.reporte;

import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;

/**
 *
 * @author Gonzalez Ismael
 */
public class ReporteController {

    private final IVacunaRepository vacunaRepos;
    private final String pathJrxml = "src/main/resources/report/jrxmlfile/";
    private final String outputPath = "src/main/resources/report/outputfile/";

    public ReporteController(IVacunaRepository vacunaRepos) {
        this.vacunaRepos = vacunaRepos;
    }

    public void generarReporteVacunas(String nombreArchivo, String extension) {
        GeneradorReporte generator = new GeneradorReporte(vacunaRepos);
        String outputName = outputPath + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaVacunas.jrxml";
        switch (extension) {
            case "pdf" ->
                generator.generarListaVacunasPDF(jrxml, outputName);
            case "html" ->
                generator.generarListaVacunasHTML(jrxml, outputName);
            case "xls" ->
                generator.generarListaVacunasXLS(jrxml, outputName);
            default ->
                throw new AssertionError();
        }
    }

    public void generarReportePersonasPorVacuna(String nombreVacuna, String nombreArchivo, String extension) {
        GeneradorReporte generator = new GeneradorReporte(vacunaRepos);
        String outputName = outputPath + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonasPorVacuna.jrxml";
        generator.generarListaPersonasPorVacunaPDF(jrxml, outputName, nombreVacuna);
    }
}
