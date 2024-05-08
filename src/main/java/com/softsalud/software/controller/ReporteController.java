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

    public String generarCarnetVacunacion(String nombreArchivo, String dni, String extension) {
        boolean seGeneroReporte;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + "\\" + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "carnetVacunacion.jrxml";
        seGeneroReporte = generator.generarCarnetVacunacion(jrxml, outputName, dni);
        return generarResultado(seGeneroReporte, outputName);
    }

    public String generarReportePersonas(String nombreArchivo, String extension) {
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
        return generarResultado(seGeneroReporte, outputName);
    }

    public String generarReporteVacunas(String nombreArchivo, String extension) {
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
        return generarResultado(seGeneroReporte, outputName);
    }

    public String generarReportePersonasPorVacuna(String nombreVacuna, String nombreArchivo, String extension) {
        boolean seGeneroReporte;
        GeneradorReporte generator = new GeneradorReporte(personaRepos, vacunaRepos);
        String outputName = outputPath + "\\" + nombreArchivo + "." + extension;
        String jrxml = pathJrxml + "listaPersonasPorVacuna.jrxml";
        seGeneroReporte = generator.generarListaPersonasPorVacunaPDF(jrxml, outputName, nombreVacuna);
        return generarResultado(seGeneroReporte, outputName);
    }

    private String generarResultado(boolean seGeneroReporte, String outputName) {
        if (seGeneroReporte) {
            return outputName;
        } else {
            return null;
        }
    }

    public boolean existePersona(Long dni) {
        return personaRepos.buscarPersona(dni) != null;
    }

    public boolean existeVacuna(String nombre) {
        return vacunaRepos.buscarVacunaPorNombre(nombre) != null;
    }
}
