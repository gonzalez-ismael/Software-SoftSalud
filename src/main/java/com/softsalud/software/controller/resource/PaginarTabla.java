package com.softsalud.software.controller.resource;

import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.model.Vacunacion;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;

/**
 * Esta clase se encarga de subdividir la tabla de datos en distintos botones de navegacion
 *
 * @author Gonzalez Ismael
 */
public class PaginarTabla {

    //CONSTANTES PÚBLICAS
    public static final int MODO_PERSONA = 99, MODO_VACUNA = 98, MODO_VACUNACION = 97;
    //CONSTANTES PRIVADAS
    private final int[] opcionesFilasPermitidas = {5, 10, 15, 25, 50, 75, 100};
    private final int limiteSup = 7, modo;
    private final DefaultTableModel table;
    private final List listaDatos;
    //VARIABLES
    private JComboBox<Integer> jcbCantidadRegistros;
    private JPanel panelBotones;
    private int cantFilasPermitidas;
    private int paginaActual = 1;

    /**
     * Constructor de la clase
     *
     * @param tabla
     * @param listaDatos
     * @param modo
     */
    public PaginarTabla(JTable tabla, List listaDatos, int modo) {
        this.table = (DefaultTableModel) tabla.getModel();
        this.listaDatos = listaDatos;
        this.modo = modo;
        dividirTabla();
    }

    /**
     * Este método se encarga de dividir la tablas de datos segun su tipo de datos.
     */
    private void dividirTabla() {
        limpiarTabla();
        int inicio = (paginaActual - 1) * this.cantFilasPermitidas, fin;
        if ((inicio + cantFilasPermitidas) > listaDatos.size()) {
            fin = listaDatos.size();
        } else {
            fin = inicio + cantFilasPermitidas;
        }
        switch (modo) {
            case MODO_PERSONA -> {
                List<Persona> nuevaLista = this.listaDatos.subList(inicio, fin);

                Object[] object = new Object[12];
                for (int i = 0; i < nuevaLista.size(); i++) {
                    object[0] = nuevaLista.get(i).getDni();
                    object[1] = nuevaLista.get(i).getApellido();
                    object[2] = nuevaLista.get(i).getNombre();
                    object[3] = nuevaLista.get(i).getFecha_nac();
                    object[4] = nuevaLista.get(i).getEdad();
                    object[5] = nuevaLista.get(i).getNumero_tel();
                    object[6] = nuevaLista.get(i).getNumero_tel_opcional();
                    object[7] = nuevaLista.get(i).getLocalidad();
                    object[8] = nuevaLista.get(i).getDireccion();
                    object[9] = nuevaLista.get(i).isTuvo_covid() ? "SI" : "NO";
                    object[10] = nuevaLista.get(i).isTiene_trasplantes() ? "SI" : "NO";
                    object[11] = nuevaLista.get(i).getFactores_riesgo();
                    table.addRow(object);
                }
            }
            case MODO_VACUNA -> {
                List<Vacuna> nuevaLista = this.listaDatos.subList(inicio, fin);

                Object[] object = new Object[2];
                for (int i = 0; i < nuevaLista.size(); i++) {
                    object[0] = nuevaLista.get(i).getCodigo();
                    object[1] = nuevaLista.get(i).getNombre_vacuna();
                    table.addRow(object);
                }
            }
            case MODO_VACUNACION -> {
                List<Vacunacion> nuevaLista = this.listaDatos.subList(inicio, fin);

                Object[] object = new Object[7];
                for (int i = 0; i < nuevaLista.size(); i++) {
                    object[0] = nuevaLista.get(i).getPersona_dni();
                    object[1] = nuevaLista.get(i).getNombre_completo();
                    object[2] = nuevaLista.get(i).getNombre_vacuna();
                    object[3] = nuevaLista.get(i).getLote_vacuna();
                    object[4] = nuevaLista.get(i).getNumero_dosis() == 0 ? "Anual" : nuevaLista.get(i).getNumero_dosis();
                    object[5] = nuevaLista.get(i).getFecha_vacunacion();
                    object[6] = nuevaLista.get(i).getLugar_vacunacion();
                    table.addRow(object);
                }
            }
        }
    }

    /**
     * Este método crea el panel contenedor de los botones, ademas de crear la dispercion y cantidad máxima de estos.
     *
     * @param panelPrincipal
     */
    public void crearListadoFilasPermitidas(JPanel panelPrincipal) {
        this.panelBotones = new JPanel(new GridLayout(1, limiteSup, 3, 3));
        panelPrincipal.removeAll();
        panelPrincipal.add(panelBotones);
        if (opcionesFilasPermitidas != null) {
            Integer valores[] = new Integer[opcionesFilasPermitidas.length];
            for (int i = 0; i < opcionesFilasPermitidas.length; i++) {
                valores[i] = opcionesFilasPermitidas[i];
            }
            this.jcbCantidadRegistros = new JComboBox(valores);
            panelPrincipal.add(Box.createHorizontalStrut(15));
            panelPrincipal.add(new JLabel("Número de Filas: "));
            panelPrincipal.add(this.jcbCantidadRegistros);
        }
    }

    /**
     * Este método mantiene el primer registro visible, al cambiar de pagina con el numero de filas a mostrar.
     *
     * @param pageComboBox
     */
    public void eventComboBox(JComboBox<Integer> pageComboBox) {
        int primeraFilaPaginaAcual = ((paginaActual - 1) * cantFilasPermitidas) + 1;
        cantFilasPermitidas = (Integer) pageComboBox.getSelectedItem();
        paginaActual = ((primeraFilaPaginaAcual - 1) / cantFilasPermitidas) + 1;
        dividirTabla();
    }

    /**
     * Este método actualiza la cantidad de botones segun la cantidad de registros a mostrar.
     */
    public void actualizarBotones() {
        panelBotones.removeAll();
        int totalFilas = listaDatos.size();
        int paginas = (int) Math.ceil((double) totalFilas / cantFilasPermitidas);
        if (paginas > limiteSup) {
            agregarBoton(panelBotones, 1);
            if (paginaActual <= (limiteSup + 1) / 2) {
                agregarRangoBotones(panelBotones, 2, limiteSup - 2);
                panelBotones.add(crearElipses());
                agregarBoton(panelBotones, paginas);
            } else if (paginaActual > (paginas - (limiteSup + 1) / 2)) {
                panelBotones.add(crearElipses());
                agregarRangoBotones(panelBotones, paginas - limiteSup + 3, paginas);
            } else {
                panelBotones.add(crearElipses());
                int inicio = paginaActual - (limiteSup - 4) / 2;
                int fin = inicio + limiteSup - 5;
                agregarRangoBotones(panelBotones, inicio, fin);
                panelBotones.add(crearElipses());
                agregarBoton(panelBotones, paginas);
            }
        } else {
            agregarRangoBotones(panelBotones, 1, paginas);
        }
        panelBotones.getParent().validate();
        panelBotones.getParent().repaint();
    }

    /**
     * Este método crea los botones adicionales, en el caso de que se exceda el límite de botonoes.
     *
     * @return
     */
    private JLabel crearElipses() {
        JLabel label = new JLabel("...");
        label.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto horizontalmente
        return label;
    }

    /**
     * Este metodo crea una serie de botones con sus correspondientes números.
     *
     * @param panelPadre
     * @param inicio
     * @param fin
     */
    private void agregarRangoBotones(JPanel panelPadre, int inicio, int fin) {
        int init = inicio;
        for (inicio = init; inicio <= fin; inicio++) {
            agregarBoton(panelPadre, inicio);
        }
    }

    /**
     * Este método es el responsable de crear botones individuales.
     *
     * @param panelPadre
     * @param numeroPagina
     */
    private void agregarBoton(JPanel panelPadre, int numeroPagina) {
        JToggleButton toggleButton = new JToggleButton(Integer.toString(numeroPagina));
        toggleButton.setMargin(new Insets(1, 3, 1, 3));
        panelPadre.add(toggleButton);
        if (numeroPagina == paginaActual) {
            toggleButton.setSelected(true);
        }
        toggleButton.addActionListener((ActionEvent e) -> {
            paginaActual = Integer.parseInt(e.getActionCommand());
            dividirTabla();
        });
    }

    /**
     * Este método limpia la tabla de datos dejandola vacía.
     */
    private void limpiarTabla() {
        this.table.setRowCount(0);
    }

    public JComboBox<Integer> getJcbCantidadRegistros() {
        return jcbCantidadRegistros;
    }

    public void setJcbCantidadRegistros(JComboBox<Integer> jcbCantidadRegistros) {
        this.jcbCantidadRegistros = jcbCantidadRegistros;
    }

}
