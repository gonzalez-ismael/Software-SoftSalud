package com.softsalud.software.sheet;

import com.softsalud.software.controller.logic.PersonaController;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Gonzalez Ismael
 */
public class ControladorImportarSheet {

    private final PersonaController personaController;
    private Workbook wb;

    public ControladorImportarSheet(PersonaController personaController) {
        this.personaController = personaController;
    }

    public String[][] Importar(File archivo) {
        String datos[][];
        try {
            wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            Iterator filaIterator = hoja.rowIterator();
            int indiceFila = -1;
            int cantFilas = hoja.getPhysicalNumberOfRows(); // Obtener el número máximo de filas
            int cantColumnas = hoja.getRow(0).getLastCellNum(); // Obtener el número máximo de columnas
            datos = new String[cantFilas][cantColumnas];
            while (filaIterator.hasNext()) {
                indiceFila++;
                Row fila = (Row) filaIterator.next();
                Iterator columnaIterator = fila.cellIterator();
                int indiceColumna = -1;
                while (columnaIterator.hasNext()) {
                    indiceColumna++;
                    Cell celda = (Cell) columnaIterator.next();
                    if (indiceFila == 0) {
                        datos[indiceFila][indiceColumna] = celda.getStringCellValue();
                    } else {
                        if (celda != null) {
                            if (null != celda.getCellType()) {
                                switch (celda.getCellType()) {
                                    case BLANK ->
                                        datos[indiceFila][indiceColumna] = " ";
                                    case NUMERIC ->
                                        datos[indiceFila][indiceColumna] = String.valueOf(Math.round(celda.getNumericCellValue()));
                                    case STRING ->
                                        datos[indiceFila][indiceColumna] = celda.getStringCellValue();
                                    case BOOLEAN ->
                                        datos[indiceFila][indiceColumna] = String.valueOf(celda.getBooleanCellValue());
                                    case FORMULA ->
                                        datos[indiceFila][indiceColumna] = celda.getCellFormula();
                                }
                            }
                        }
                    }
                }
            }
            return datos;
        } catch (IOException | EncryptedDocumentException e) {
            System.out.println("Excepcion General: " + e.getMessage());
            System.out.println("Clase: " + e.getClass());
            return null;
        }
    }

    public int[] insertarPersonas(String[][] datos, JProgressBar carga, int[] resultados) {
        AtomicInteger cantIngresados = new AtomicInteger(0);
        AtomicInteger cantRepetidos = new AtomicInteger(0);
        AtomicInteger cantFallidos = new AtomicInteger(0);
        AtomicInteger totalRegistros = new AtomicInteger(datos.length);
        
        Thread hilo = new Thread() {
            @Override
            public void run() {
                int progreso = 0;
                for (String[] registro : datos) {
                    String fecha = registro[10] + "-" + agregarCero(Integer.parseInt(registro[9])) + "-" + agregarCero(Integer.parseInt(obtenerDia(registro[8])));
                    int resultadoOperacion = personaController.agregarPersona(registro[3], registro[1], registro[2], fecha,
                            registro[6], validarNumero(registro[7]), registro[5], registro[4],
                            convertirBooleano(registro[11]), convertirBooleano(registro[12]), registro[13]);
                    switch (resultadoOperacion) {
                        case 1 -> cantIngresados.incrementAndGet();
                        case 2 -> cantRepetidos.incrementAndGet();
                        case -1 -> cantFallidos.incrementAndGet();
                    }
                    
                    progreso++;
                    int valor = (int) (((double) progreso / totalRegistros.get()) * 100);
                    carga.setValue(valor);
                }
            }
        };
        hilo.start();
        
        JOptionPane.showMessageDialog(null, "Operación Finalizada");
        
        resultados[0] = cantIngresados.get();
        resultados[1] = cantRepetidos.get();
        resultados[2] = cantFallidos.get();
        resultados[3] = totalRegistros.get();
        return resultados;
    }

    private boolean convertirBooleano(String texto) {
        if (texto.equalsIgnoreCase("Sí")) {
            return true;
        } else if (texto.equalsIgnoreCase("No")) {
            return false;
        } else {
            return false;
        }
    }

    private String validarNumero(String numero) {
        if ("-".equals(numero)) {
            return "0";
        } else {
            return numero;
        }
    }

    private String agregarCero(int numero) {
        if (numero == 0) {
            return "01";
        } else {
            if (numero < 10) {
                return "0" + numero;
            } else {
                return String.valueOf(numero);
            }
        }
    }

    private String obtenerDia(String fecha) {
        // Verificar si la cadena es un número seguido de un punto
        if (fecha.matches("\\d+\\.\\s*$")) {
            return "1"; // Devolver 1 si se cumple la condición
        }

        // Define los patrones de formato de fecha
        String[] patrones = {"d/M/yyyy", "d-M-yyyy", "d 'de' MMMM 'de' yyyy"};

        // Itera sobre los patrones y trata de analizar la fecha
        for (String patron : patrones) {
            try {
                // Crea un objeto DateTimeFormatter con el patrón y el idioma español
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patron, new Locale("es"));
                // Analiza la fecha
                LocalDate localDate = LocalDate.parse(fecha, formatter);
                // Retorna el día como cadena
                return String.valueOf(localDate.getDayOfMonth());
            } catch (Exception e) {
                // Si hay un error, continúa con el próximo patrón
                continue;
            }
        }
        // Si ninguno de los patrones coincide, lanza una excepción
//    throw new IllegalArgumentException("El formato de fecha no es válido");
        return "1";
    }
}
