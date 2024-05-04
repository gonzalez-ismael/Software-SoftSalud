package com.softsalud.software.view.validation;

import com.softsalud.software.view.JDialogPersona;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase agrupara todas las validaciones de la capa Vista
 *
 * @author Gonzalez Ismael
 */
public class VistaValidacion {

    public static final int DATETOSTRING = 4, STRINGTODATE = 5;

    public static boolean esFechaValida(String fecha) {
        return (esEstructuraFechaValido(fecha) && esFormatoFechaValido(fecha) && esFechaMenorHoy(fecha) && esFechaMayor1900(fecha));
    }

    private static boolean esEstructuraFechaValido(String fecha) {
        return fecha.matches("\\d{2}-\\d{2}-\\d{4}");
    }

    private static boolean esFormatoFechaValido(String fecha) {
        String[] partesFecha = fecha.split("-");
        int dia = Integer.parseInt(partesFecha[0]);
        int mes = Integer.parseInt(partesFecha[1]);
        int anio = Integer.parseInt(partesFecha[2]);

        // Verificar si la fecha es válida según el calendario gregoriano
        try {
            LocalDate fechaIngresada = LocalDate.of(anio, mes, dia);
            return true;
        } catch (DateTimeException e) {
            return false; // Fecha inválida
        }
    }

    private static boolean esFechaMenorHoy(String fecha) {
        String fechaFormateada = formatearFecha(fecha, STRINGTODATE); // Convertir la fecha ingresada a un objeto LocalDate
        LocalDate fechaIngresada = LocalDate.parse(fechaFormateada);
        LocalDate fechaActual = LocalDate.now(); // Obtener la fecha actual
        return fechaIngresada.isBefore(fechaActual) || fechaIngresada.equals(fechaActual);
    }

    private static boolean esFechaMayor1900(String fecha) {
        String fechaFormateada = formatearFecha(fecha, STRINGTODATE);
        LocalDate fechaIngresada = LocalDate.parse(fechaFormateada); // Convertir la fecha ingresada a un objeto LocalDate
        LocalDate fecha1900 = LocalDate.of(1900, 1, 1); // Crear una fecha para el año 1900
        return fechaIngresada.isAfter(fecha1900) || fechaIngresada.equals(fecha1900);
    }

    public static boolean esDNIMayorMillon(String dni) {
        return (Long.parseLong(dni) > 1000000);
    }

    public static void validarNumero(java.awt.event.KeyEvent evt) {
        int ascii = evt.getKeyChar();
        if (!(ascii >= 48 && ascii <= 57)) {
            evt.consume();
        }
    }

    public static void validarLongitudCadena(java.awt.event.KeyEvent evt, javax.swing.JTextField array, int max) {
        if (array.getText().length() >= max) {
            evt.consume();
        }
    }

    public static void validarNombreArchivo(java.awt.event.KeyEvent evt) {
        char tecla = evt.getKeyChar();
        if (tecla == '/' || tecla == '\\' || tecla == ':' || tecla == '*' || tecla == '?' || tecla == '"' || tecla == '<' || tecla == '>' || tecla == '|') {
            evt.consume();
        }
    }

    public static String formatearFecha(String fechaOriginal, int modo) {
        DateTimeFormatter formatoString = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatoDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate nuevaFecha;
        try {
            switch (modo) {
                case STRINGTODATE -> {
                    nuevaFecha = LocalDate.parse(fechaOriginal, formatoString);
                    return nuevaFecha.format(formatoDate);
                }
                case DATETOSTRING -> {
                    nuevaFecha = LocalDate.parse(fechaOriginal, formatoDate);
                    return nuevaFecha.format(formatoString);
                }
                default -> {
                    return fechaOriginal;
                }
            }
        } catch (java.time.format.DateTimeParseException ex) {
            Logger.getLogger(JDialogPersona.class.getName()).log(Level.SEVERE, null, ex);
            return fechaOriginal;
        }
    }
}
