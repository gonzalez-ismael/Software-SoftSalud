package com.softsalud.software.sheet;

import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.model.Vacunacion;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunacionRepository;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Ismael
 * @param <E>
 */
public class ControladorExportarSheet<E> {

    private final IPersonaRepository iPersonaRepos;
    private final IVacunaRepository iVacunaRepos;
    private final IVacunacionRepository iVacunacionRepos;
    private final String outputPath = "src/main/resources/report/outputfile/";
    private Workbook wb;

    public ControladorExportarSheet(IPersonaRepository iPersonaRepos, IVacunaRepository iVacunaRepos, IVacunacionRepository iVacunacionRepos) {
        this.iPersonaRepos = iPersonaRepos;
        this.iVacunaRepos = iVacunaRepos;
        this.iVacunacionRepos = iVacunacionRepos;
    }

    public boolean GuardarCopiaSeguridadExcel() {
        boolean seGuardoCorrectamente = false;
        
        List<Persona> listaPersona = iPersonaRepos.listarPersonas();
        List<Vacuna> listaVacuna = iVacunaRepos.listarVacunas();
        List<Vacunacion> listaVacunacion = iVacunacionRepos.listarVacunacionesCrudo();

        try (Workbook workbook = new XSSFWorkbook()) {
            guardarListaEnHoja(workbook, listaPersona, "Listado de Personas");
            guardarListaEnHoja(workbook, listaVacuna, "Listado de Vacunas");
            guardarListaEnHoja(workbook, listaVacunacion, "Listado de Vacunaciones");
            // Guardar el archivo Excel

            String fileName = outputPath + "Copia de Seguridad de Fecha " + String.valueOf(LocalDate.now()) + " .xlsx";
            System.out.println(fileName);
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            seGuardoCorrectamente = true;
        } catch (IOException ex) {
            Logger.getLogger(ControladorExportarSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seGuardoCorrectamente;
    }

    private static <E> void guardarListaEnHoja(Workbook workbook, List<E> lista, String nombreHoja) {
        Sheet sheet = workbook.createSheet(nombreHoja);
        int rowNum = 0;
        if (!lista.isEmpty()) {
            // Obtener los títulos
            String[] titulos = null;

            if (lista.get(0) instanceof Persona persona) {
                titulos = persona.toStringTitulos();
            } else if (lista.get(0) instanceof Vacuna vacuna) {
                titulos = vacuna.toStringTitulos();
            } else if (lista.get(0) instanceof Vacunacion vacunacion) {
                titulos = vacunacion.toStringTitulos();
            }

            // Crear la fila de títulos en la hoja
            Row headerRow = sheet.createRow(rowNum++);
            for (int i = 0; i < titulos.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(titulos[i]);
            }

            // Crear las filas de datos en la hoja
            for (E objeto : lista) {
                String[] objetoData = null;
                if (objeto instanceof Persona persona) {
                    objetoData = persona.toStringPersonalizado();
                } else if (objeto instanceof Vacuna vacuna) {
                    objetoData = vacuna.toStringPersonalizado();
                } else if (objeto instanceof Vacunacion vacunacion) {
                    objetoData = vacunacion.toStringPersonalizado();
                }
                Row row = sheet.createRow(rowNum++);
                for (int i = 0; i < objetoData.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(objetoData[i]);
                }
            }
        }
    }
}
