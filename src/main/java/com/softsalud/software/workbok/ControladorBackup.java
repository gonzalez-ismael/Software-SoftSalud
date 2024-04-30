package com.softsalud.software.workbok;

import com.softsalud.software.controller.logic.PersonaController;
import com.softsalud.software.controller.logic.VacunaController;
import com.softsalud.software.controller.logic.VacunacionController;
import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.model.Vacunacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Esta clase se encarga de guardar y restaurar copias de seguridad de los datos.
 *
 * @author Gonzalez Ismael
 * @param <E> Objeto Genérico para poder trabajar con la Clase Persona, Vacuna y Vacunación.
 */
public class ControladorBackup<E> {

    //Constantes
    private final PersonaController personaController;
    private final VacunaController vacunaController;
    private final VacunacionController vacunacionController;
    private final String outputPath = "src/main/resources/report/outputfile/";

    /**
     * Constructor creado por el programodor.
     *
     * @param personaController
     * @param vacunaController
     * @param vacunacionController
     */
    public ControladorBackup(PersonaController personaController, VacunaController vacunaController, VacunacionController vacunacionController) {
        this.personaController = personaController;
        this.vacunaController = vacunaController;
        this.vacunacionController = vacunacionController;
    }

    /**
     * Este método guarda una copia con todos los datos disponibles de las personas, vacunas y vacunaciones en un archivo excel.
     *
     * @return valor lógico indicando si se realizo la creación o no.
     */
    public boolean guardarCopiaSeguridadExcel() {
        boolean seGuardoCorrectamente = false;

        List<Persona> listaPersona = personaController.listarPersonas();
        List<Vacuna> listaVacuna = vacunaController.listarVacunas();
        List<Vacunacion> listaVacunacion = vacunacionController.listarVacunaciones();

        try (Workbook workbook = new XSSFWorkbook()) {
            crearHojaDeDatos(workbook, listaPersona, "Listado de Personas");
            crearHojaDeDatos(workbook, listaVacuna, "Listado de Vacunas");
            crearHojaDeDatos(workbook, listaVacunacion, "Listado de Vacunaciones");

            // Guardar el archivo Excel
            String fileName = outputPath + "Copia de Seguridad de Fecha " + String.valueOf(LocalDate.now()) + " .xlsx";
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            fileOut.close();
            seGuardoCorrectamente = true;
        } catch (IOException ex) {
            Logger.getLogger(ControladorBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seGuardoCorrectamente;
    }

    /**
     * Este método se encarga de crear una hoja de datos en el workbook con los datos que se pasan como parámetro.
     *
     * @param <E> este es el dato generico para poder usar 3 tipos de datos diferentes con un mismo toString().
     * @param workbook este es el archivo excel al cual se le agregan las hojas de datos.
     * @param lista esta es la lista de datos, pueden ser de Persona, Vacuna o Vacunación.
     * @param nombreHoja este es el nombre que va a tener la hoja de datos que se va a crear.
     */
    private <E> void crearHojaDeDatos(Workbook workbook, List<E> lista, String nombreHoja) {
        Sheet hoja = workbook.createSheet(nombreHoja);
        int rowNum = 0;
        if (!lista.isEmpty()) {
            // Obtener los títulos
            String[] encabezados = obtenerArregloConEncabezados(lista);
            // Crear la fila de títulos en la hoja
            insertarArregloConEncabezados(hoja, rowNum++, encabezados);
            // Crear las filas de datos en la hoja
            for (E tipoObjeto : lista) {
                //Obtengo cada registro por cada fila de la hoja
                String[] registro = obtenerRegistro(tipoObjeto);
                //Inserto cada columna de datos de la correspondiente fila
                insertarRegistroDeDatosEnHoja(registro, hoja, rowNum++);
            }
        }
    }

    /**
     * Este método privado obtiene los encabezados.
     *
     * @param <E> este es el dato generico para poder usar 3 tipos de datos diferentes con un mismo toString().
     * @param lista esta es la lista de datos, pueden ser de Persona, Vacuna o Vacunación.
     * @return Retorna un arreglo con los encabezados.
     */
    private <E> String[] obtenerArregloConEncabezados(List<E> lista) {
        String[] titulos = null;
        switch (lista.get(0)) {
            case Persona persona ->
                titulos = Persona.toStringTitulos();
            case Vacuna vacuna ->
                titulos = Vacuna.toStringTitulos();
            case Vacunacion vacunacion ->
                titulos = Vacunacion.toStringTitulos();
            default -> {
            }
        }
        return titulos;
    }

    /**
     * Este método inserta en la primer fila, los encabezados.
     *
     * @param sheet es la hoja de datos actual.
     * @param rowNum es la fila actual.
     * @param encabezados son los encabezados a insertar.
     */
    private void insertarArregloConEncabezados(Sheet sheet, int rowNum, String[] encabezados) {
        Row headerRow = sheet.createRow(rowNum);
        for (int i = 0; i < encabezados.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(encabezados[i]);
        }
    }

    /**
     * Este método retorna la fila de datos correspondiente al tipo de dato
     *
     * @param <E> este es el dato generico para poder usar 3 tipos de datos diferentes con un mismo toString().
     * @param tipoObjeto es el tipo de objecto generico a analizar para retornar el arreglo con los datos correctos.
     * @return retorna un arreglo con los datos a posteriormente insertar.
     */
    private <E> String[] obtenerRegistro(E tipoObjeto) {
        String[] registro;
        switch (tipoObjeto) {
            case Persona persona ->
                registro = persona.toStringPersonalizado();
            case Vacuna vacuna ->
                registro = vacuna.toStringPersonalizado();
            case Vacunacion vacunacion ->
                registro = vacunacion.toStringPersonalizado();
            default ->
                registro = null;
        }
        return registro;
    }

    /**
     * Este método inserta los datos en la hoja de datos.
     *
     * @param registro es el arreglo con los datos.
     * @param hoja es la hoja donde se van a insertar la fila.
     * @param rowNum es la fila actual.
     */
    private void insertarRegistroDeDatosEnHoja(String[] registro, Sheet hoja, int rowNum) {
        if (registro != null) {
            Row row = hoja.createRow(rowNum++);
            for (int i = 0; i < registro.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(registro[i]);
            }
        }
    }

    /**
     * Este método se encarga de validar si un documento excel puede funcionar como backup para recuperar la base de datos.
     *
     * @param archivoBackup
     * @return Retorna un valor booleano indicando si es o no válido.
     */
    public boolean esArchivoExcelValidoParaImportar(File archivoBackup) {
        try (Workbook wb = WorkbookFactory.create(new FileInputStream(archivoBackup))) {
            if (wb.getNumberOfSheets() != 3) {
                return false;
            }

            Sheet primeraHoja = wb.getSheetAt(0);
            Sheet segundaHoja = wb.getSheetAt(1);
            Sheet terceraHoja = wb.getSheetAt(2);

            String nombrePrimeraHoja = primeraHoja.getSheetName();
            String nombreSegundaHoja = segundaHoja.getSheetName();
            String nombreTerceraHoja = terceraHoja.getSheetName();

            if (!sonHojasValidas(nombrePrimeraHoja, nombreSegundaHoja, nombreTerceraHoja)) {
                return false;
            }

            return sonValidosEncabezados(primeraHoja, segundaHoja, terceraHoja);

        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ControladorBackup.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Este método válida si los títulos de las hojas coinciden con los de un archivo backup.
     *
     * @param primeraHoja
     * @param segundaHoja
     * @param terceraHoja
     * @return devuelve verdadero o falso según la válidez de las hojas.
     */
    private boolean sonHojasValidas(String primeraHoja, String segundaHoja, String terceraHoja) {
        boolean condicion1 = (primeraHoja.equals("Listado de Personas"));
        boolean condicion2 = (segundaHoja.equals("Listado de Vacunas"));
        boolean condicion3 = (terceraHoja.equals("Listado de Vacunaciones"));
        return (condicion1 && condicion2 && condicion3);
    }

    /**
     * Este método válida si los encabezados de las hojas coinciden con los de un archivo backup.
     *
     * @param primeraHoja
     * @param segundaHoja
     * @param terceraHoja
     * @return devuelve verdadero o falso según la válidez de los encabezados.
     */
    private boolean sonValidosEncabezados(Sheet primeraHoja, Sheet segundaHoja, Sheet terceraHoja) {
        boolean condicion1 = Arrays.equals(Persona.toStringTitulos(), obtenerEncabezadosHojas(primeraHoja));
        boolean condicion2 = Arrays.equals(Vacuna.toStringTitulos(), obtenerEncabezadosHojas(segundaHoja));
        boolean condicion3 = Arrays.equals(Vacunacion.toStringTitulos(), obtenerEncabezadosHojas(terceraHoja));
        return (condicion1 && condicion2 && condicion3);
    }

    /**
     * Este método se encargar de obtener la primer fila de una hoja, los encabezados.
     *
     * @param hoja
     * @return devuelve un arreglo de String con los datos de la primer fila de la hoja.
     */
    private String[] obtenerEncabezadosHojas(Sheet hoja) {
        Row primeraFila = hoja.getRow(0);
        int totalCeldas = primeraFila.getLastCellNum(); // Obtener el número total de celdas en la fila

        String[] encabezados = new String[totalCeldas];

        for (int i = 0; i < totalCeldas; i++) {
            Cell celda = primeraFila.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            encabezados[i] = celda.getStringCellValue();
        }
        return encabezados;
    }

    /**
     * Este método sobrescribe los datos actuales con una copia hecha previamente.
     *
     * @param archivoBackup
     * @return
     */
    public int[] sobrescribirCopiaSeguridad(File archivoBackup) {
        int[] resultados = new int[9];
        int cantInscriptos = 0, cantRepetidos = 0, cantFallidos = 0;
        try {
            Workbook wb = WorkbookFactory.create(new FileInputStream(archivoBackup));

            String[][] listadoPersonas = Workbok.procesarArchivoExcel(wb.getSheetAt(0));
            String[][] listadoVacunas = Workbok.procesarArchivoExcel(wb.getSheetAt(1));
            String[][] listadoVacunacion = Workbok.procesarArchivoExcel(wb.getSheetAt(2));

            for (int i = 1; i < listadoPersonas.length; i++) {
                String[] registro = listadoPersonas[i];
                int resultadoOperacion = personaController.agregarPersona(registro[0], registro[1], registro[2],
                        registro[4], registro[5], registro[6], registro[7], registro[8],
                        Boolean.parseBoolean(registro[9]), Boolean.parseBoolean(registro[10]), registro[11]);
                switch (resultadoOperacion) {
                    case 1 ->
                        cantInscriptos++;
                    case 2 ->
                        cantRepetidos++;
                    default ->
                        cantFallidos++;
                }
            }

            resultados[0] = cantInscriptos;
            cantInscriptos = 0;
            resultados[1] = cantRepetidos;
            cantRepetidos = 0;
            resultados[2] = cantFallidos;
            cantFallidos = 0;

            for (int i = 1; i < listadoVacunas.length; i++) {
                String[] registro = listadoVacunas[i];
                int resultadoOperacion = vacunaController.agregarVacuna(registro[1]);
                switch (resultadoOperacion) {
                    case 1 ->
                        cantInscriptos++;
                    case 2 ->
                        cantRepetidos++;
                    default ->
                        cantFallidos++;
                }
            }

            resultados[3] = cantInscriptos;
            cantInscriptos = 0;
            resultados[4] = cantRepetidos;
            cantRepetidos = 0;
            resultados[5] = cantFallidos;
            cantFallidos = 0;

            for (int i = 1; i < listadoVacunacion.length; i++) {
                String[] registro = listadoVacunacion[i];
                int resultadoOperacion = vacunacionController.agregarVacunacion(registro[0], registro[1],
                        registro[2], registro[3],
                        registro[4], registro[5]);
                switch (resultadoOperacion) {
                    case 1 ->
                        cantInscriptos++;
                    case 2 ->
                        cantRepetidos++;
                    default ->
                        cantFallidos++;
                }
            }

            resultados[6] = cantInscriptos;
            resultados[7] = cantRepetidos;
            resultados[8] = cantFallidos;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(ControladorBackup.class.getName()).log(Level.SEVERE, null, ex);
        }

        return resultados;
    }

}
