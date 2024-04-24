package com.softsalud.software.reporte;

import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;

/**
 *
 * @author Gonzalez Ismael
 */
public class ReporteController {

    private final IPersonaRepository personaRepos;
    private final IVacunaRepository vacunaRepos;
    private final String pathJrxml = "src/main/resources/report/jrxmlfile/";
    private final String outputPath = "src/main/resources/report/outputfile/";

    public ReporteController(IPersonaRepository personaRepos, IVacunaRepository vacunaRepos) {
        this.personaRepos = personaRepos;
        this.vacunaRepos = vacunaRepos;
    }

    public boolean generarReportePersonas(String nombreArchivo, String extension) {
        boolean seGeneroReporte = false;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonas.jrxml";
        switch (extension) {
            case "pdf" ->
                seGeneroReporte = generator.generarListaPersonasPDF(jrxml, outputName);
            case "html" ->
                generator.generarListaVacunasHTML(jrxml, outputName);
            case "xls" ->
                generator.generarListaVacunasXLS(jrxml, outputName);
            default ->
                throw new AssertionError();
        }
        return seGeneroReporte;
    }

    public boolean generarReporteVacunas(String nombreArchivo, String extension) {
        boolean seGeneroReporte = false;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaVacunas.jrxml";
        switch (extension) {
            case "pdf" ->
                seGeneroReporte = generator.generarListaVacunasPDF(jrxml, outputName);
            case "html" ->
                seGeneroReporte = generator.generarListaVacunasHTML(jrxml, outputName);
            case "xls" ->
                seGeneroReporte = generator.generarListaVacunasXLS(jrxml, outputName);
            default ->
                throw new AssertionError();
        }
        return seGeneroReporte;
    }

    public void generarReportePersonasPorVacuna(String nombreVacuna, String nombreArchivo, String extension) {
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonasPorVacuna.jrxml";
        generator.generarListaPersonasPorVacunaPDF(jrxml, outputName, nombreVacuna);
    }
}
