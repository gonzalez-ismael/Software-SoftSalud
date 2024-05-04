package com.softsalud.software.controller;

import com.softsalud.software.controller.resource.FileProperties;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import com.softsalud.software.controller.resource.GeneradorReporte;
import static com.softsalud.software.controller.resource.GeneradorReporte.*;

/**
 *
 * @author Gonzalez Ismael
 */
public class ReporteController {

    private final IPersonaRepository personaRepos;
    private final IVacunaRepository vacunaRepos;
    private final String pathJrxml = "src/main/resources/report/jrxmlfile/";
    private final String outputPath;

    public ReporteController(IPersonaRepository personaRepos, IVacunaRepository vacunaRepos) {
        this.personaRepos = personaRepos;
        this.vacunaRepos = vacunaRepos;
        FileProperties fileProperties = new FileProperties();
        this.outputPath = fileProperties.getFile().getProperty("urlOutputLocation");
    }

    public boolean generarReportePersonas(String nombreArchivo, String extension) {
        boolean seGeneroReporte = false;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + "\\" + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonas.jrxml";
        switch (extension) {
            case "pdf" ->
                seGeneroReporte = generator.generarListaPersonasMultiformato(jrxml, outputName, PDF);
            case "html" ->
                seGeneroReporte = generator.generarListaPersonasMultiformato(jrxml, outputName, HTML);
            case "xls" ->
                seGeneroReporte = generator.generarListaPersonasMultiformato(jrxml, outputName, XLS);
            default ->
                throw new AssertionError();
        }
        return seGeneroReporte;
    }

    public boolean generarReporteVacunas(String nombreArchivo, String extension) {
        boolean seGeneroReporte = false;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + "\\" + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaVacunas.jrxml";
        switch (extension) {
            case "pdf" ->
                seGeneroReporte = generator.generarListaVacunasMultiformato(jrxml, outputName, PDF);
            case "html" ->
                seGeneroReporte = generator.generarListaVacunasMultiformato(jrxml, outputName, HTML);
            case "xls" ->
                seGeneroReporte = generator.generarListaVacunasMultiformato(jrxml, outputName, XLS);
            default ->
                throw new AssertionError();
        }
        return seGeneroReporte;
    }

    public void generarReportePersonasPorVacuna(String nombreVacuna, String nombreArchivo, String extension) {
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + "\\" + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonasPorVacuna.jrxml";
        generator.generarListaPersonasPorVacunaPDF(jrxml, outputName, nombreVacuna);
    }
}
