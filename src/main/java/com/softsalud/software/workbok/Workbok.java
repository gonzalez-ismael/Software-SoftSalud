package com.softsalud.software.workbok;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

/**
 * Esta clase se encarga de tranformar tablas excel en matrices de datos String.
 *
 * @author Gonzalez Ismael
 */
public class Workbok {

    public Workbok() {
    }

    public static String[][] procesarArchivoExcel(File archivo) {
        String datos[][] = null;
        try {
            Workbook wb = WorkbookFactory.create(new FileInputStream(archivo));
            Sheet hoja = wb.getSheetAt(0);
            datos = procesarArchivoExcel(hoja);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Workbok.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(Workbok.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }

    public static String[][] procesarArchivoExcel(Sheet hoja) {
        String datos[][];
        try {
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
                                    case BLANK -> datos[indiceFila][indiceColumna] = " ";
                                    case NUMERIC -> datos[indiceFila][indiceColumna] = String.valueOf(Math.round(celda.getNumericCellValue()));
                                    case STRING -> datos[indiceFila][indiceColumna] = celda.getStringCellValue();
                                    case BOOLEAN -> datos[indiceFila][indiceColumna] = String.valueOf(celda.getBooleanCellValue());
                                    case FORMULA -> datos[indiceFila][indiceColumna] = celda.getCellFormula();
                                }
                            }
                        }
                    }
                }
            }
            return datos;
        } catch (EncryptedDocumentException e) {
            System.out.println("Excepcion General: " + e.getMessage());
            System.out.println("Clase: " + e.getClass());
            return null;
        }
    }
    
    /**
     * Este método se encargar de obtener la primer fila de una hoja, los encabezados.
     *
     * @param hoja
     * @return devuelve un arreglo de String con los datos de la primer fila de la hoja.
     */
    public static String[] obtenerEncabezadosHojas(Sheet hoja) {
        Row primeraFila = hoja.getRow(0);
        int totalCeldas = primeraFila.getLastCellNum(); // Obtener el número total de celdas en la fila

        String[] encabezados = new String[totalCeldas];

        for (int i = 0; i < totalCeldas; i++) {
            Cell celda = primeraFila.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            encabezados[i] = celda.getStringCellValue();
        }
        return encabezados;
    }
}
