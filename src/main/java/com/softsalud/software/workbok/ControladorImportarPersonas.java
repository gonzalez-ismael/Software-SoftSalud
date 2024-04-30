package com.softsalud.software.workbok;

import com.softsalud.software.controller.logic.PersonaController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author Gonzalez Ismael
 */
public class ControladorImportarPersonas {

    private final PersonaController personaController;

    public ControladorImportarPersonas(PersonaController personaController) {
        this.personaController = personaController;
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
