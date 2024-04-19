package com.softsalud.software.excel;

import com.softsalud.software.controller.logic.PersonaController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 *
 * @author Gonzalez Ismael
 */
public class ControladorExcel {

    private PersonaController personaController;
    private Workbook wb;

    public ControladorExcel(PersonaController personaController) {
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

    public String Exportar(File archivo, JTable tablaD) {
        String respuesta = "No se realizo correctamente la exportacion";
        int numFila = tablaD.getRowCount(), numColum = tablaD.getColumnCount();

        if (archivo.getName().endsWith("xls")) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        Sheet hoja = wb.createSheet("Pruebita");
        try {
            for (int i = -1; i < numFila; i++) {
                Row fila = hoja.createRow(i + 1);
                for (int j = 0; j < numColum; j++) {
                    Cell celda = fila.createCell(j);
                    if (i == -1) {
                        celda.setCellValue(String.valueOf(tablaD.getColumnName(j)));
                    } else {
                        celda.setCellValue(String.valueOf(tablaD.getValueAt(i, j)));
                    }
                    wb.write(new FileOutputStream(archivo));
                }
            }
            respuesta = "Exportacion Exitosa.";
        } catch (IOException e) {
            System.out.println("Excepcion General: " + e.getMessage());
            System.out.println("Clase: " + e.getClass());
        }
        return respuesta;
    }

    public void mostrarTabla(String[][] datos, JProgressBar carga) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                int totalRegistros = datos.length * datos[0].length;
                int progreso = 0;

                for (int i = 0; i < datos.length; i++) {
                    for (int j = 0; j < datos[i].length; j++) {
                        // Realizar aquí la instrucción para cada registro
                        // Por ejemplo, puedes actualizar una tabla o cualquier otro componente de la interfaz de usuario
                        System.out.print(datos[i][j] + " ");
                        // Simulación de procesamiento
                        try {
                            Thread.sleep(1); // Simulación de un proceso que tarda un tiempo
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ControladorExcel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        progreso++;
                        int valor = (int) (((double) progreso / totalRegistros) * 100);
                        carga.setValue(valor);
                    }
                    System.out.println("");
                }
                JOptionPane.showMessageDialog(null, "Operación Finalizada");
            }
        };
        hilo.start();
    }

    public void insertarPersonas(String[][] datos, JProgressBar carga) {
        int cantIngresados = 0, cantRepetidos = 0, cantFallidos = 0, cantTotal = 0;
        int resultadoOperacion;
        cantTotal = (datos.length * datos[0].length);
        for (String[] registro : datos) {

            String fecha = registro[10] + "-" + agregarCero(Integer.parseInt(registro[9])) + "-" + agregarCero(Integer.parseInt(obtenerDia(registro[8])));
            resultadoOperacion = personaController.agregarPersona(registro[3], registro[1], registro[2], fecha,
                    registro[6], validarNumero(registro[7]), registro[5], registro[4],
                    convertirBooleano(registro[11]), convertirBooleano(registro[12]), registro[13]);
//            System.out.println("Registro 1: " + registro[3] + ", " + registro[1] + ", " + registro[2] + ", " + fecha + ", " +
//                    registro[6] + ", " + registro[7] + ", " + registro[5] + ", " + registro[4] + ", " + 
//                    Boolean.parseBoolean(registro[8]) + ", " + Boolean.parseBoolean(registro[9]) + ", " + registro[10]);
            switch (resultadoOperacion) {
                case 1 ->
                    cantIngresados++;
                case 2 ->
                    cantRepetidos++;
                case -1 ->
                    cantFallidos++;
            }
        }
        System.out.println("\nCantidad de personas ingresadas: " + cantIngresados);
        System.out.println("\nCantidad de personas repetidas: " + cantRepetidos);
        System.out.println("\nCantidad de personas fallidas: " + cantFallidos);
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

    public PersonaController getPersonaController() {
        return personaController;
    }

    public void setPersonaController(PersonaController personaController) {
        this.personaController = personaController;
    }
}
