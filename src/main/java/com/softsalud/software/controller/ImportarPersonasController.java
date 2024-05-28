package com.softsalud.software.controller;

import com.softsalud.software.controller.resource.Workbok;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * Esta clase se encarga de importar el listado de persona de google drive y validar el listado.
 *
 * @author Gonzalez Ismael
 */
public class ImportarPersonasController {

    //CONSTANTES
    private final PersonaController personaController;

    /**
     * Constructor creado por el desarollador.
     *
     * @param personaController
     */
    public ImportarPersonasController(PersonaController personaController) {
        this.personaController = personaController;
    }

    /**
     * Este método se encarga de crear un hilo que inserta personas mientras carga la barra de control.
     *
     * @param datos
     * @param carga
     * @param resultados
     * @return
     */
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
                    int resultadoOperacion = personaController.agregarRegistro(registro[3], registro[1], registro[2], fecha,
                            registro[6], validarNumero(registro[7]), registro[5], registro[4],
                            convertirBooleano(registro[11]), convertirBooleano(registro[12]), registro[13]);
                    switch (resultadoOperacion) {
                        case 1 ->
                            cantIngresados.incrementAndGet();
                        case 2 ->
                            cantRepetidos.incrementAndGet();
                        case -1 ->
                            cantFallidos.incrementAndGet();
                    }

                    progreso++;
                    int valor = (int) (((double) progreso / totalRegistros.get()) * 100);
                    carga.setValue(valor);
                }
            }
        };
        hilo.start();
        try {
            hilo.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ImportarPersonasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "Operación Finalizada");

        resultados[0] = cantIngresados.get();
        resultados[1] = cantRepetidos.get();
        resultados[2] = cantFallidos.get();
        resultados[3] = totalRegistros.get();
        return resultados;
    }

    /**
     * Este método se encarga de válidar los encabezados del archivo excel para la insercion de las personas.
     *
     * @param archivoBackup
     * @return
     */
    public static boolean esArchivoExcelValidoParaImportar(File archivoBackup) {
        try (Workbook wb = WorkbookFactory.create(new FileInputStream(archivoBackup))) {
            if (wb.getNumberOfSheets() != 1) {
                return false;
            } else {
                Sheet primeraHoja = wb.getSheetAt(0);
                String[] encabezados = {"Marca temporal", "Ingrese su/sus apellido/s tal como aparecen en su documento",
                    "Ingrese su/sus nombre/s tal como aparecen en su documento",
                    "DNI", "Domicilio", "Localidad", "Número de teléfono o celular", "Segundo número de teléfono o celular",
                    "Día de nacimiento", "Mes de nacimiento", "Año de nacimiento",
                    "¿Tuvo Covid 19?", "¿Ha recibido trasplante de órganos?", "Indique si padece alguna de las siguientes patologías:"};
                boolean sonCoindentes = Arrays.equals(encabezados, Workbok.obtenerEncabezadosHojas(primeraHoja));
                return sonCoindentes;
            }

        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(BackupController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Este método convierte lo que obtiene de un string en true o false.
     *
     * @param texto
     * @return
     */
    private boolean convertirBooleano(String texto) {
        if (texto.equalsIgnoreCase("Sí")) {
            return true;
        } else if (texto.equalsIgnoreCase("No")) {
            return false;
        }
        return false;
    }

    /**
     * Este método valida que el telefo opcional contenga un número, aunque sea por defecto.
     *
     * @param numero
     * @return
     */
    private String validarNumero(String numero) {
        if ("-".equals(numero)) {
            return "0";
        } else {
            return numero;
        }
    }

    /**
     * Este método agrega un digito más para los números menores a 10.
     *
     * @param numero
     * @return
     */
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

    /**
     * Este método se utiliza para obtener el día correspondiente a una fecha ingresada en diversos formatos. Si la fecha es un número seguido de un punto, se devuelve '1' como día. Si la fecha no se puede analizar con ninguno de los patrones especificados, se devuelve '0'.
     *
     * @param fecha La fecha en formato de cadena.
     * @return El día correspondiente como una cadena de texto ('1' si la fecha está en un formato válido pero no contiene información de día, '0' si la fecha no se puede analizar con ninguno de los patrones especificados).
     */
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

            }
        }
        return "0";
    }
}
