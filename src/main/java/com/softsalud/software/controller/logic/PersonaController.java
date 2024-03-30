package com.softsalud.software.controller.logic;

import com.softsalud.software.persistence.model.Direccion;
import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.repository.interfaz.IDireccionRepository;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.view.JDialogPersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Gonzalez Ismael
 */
public class PersonaController implements ActionListener, TableModelListener {

    private JDialogPersona ventanaPersona;
    private PaginarTabla pag;
    private final IPersonaRepository personaRepos;
    private final IDireccionRepository direccionRepos;
    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3;

    public PersonaController(IPersonaRepository personaRepos, IDireccionRepository direccionRepos) {
        this.personaRepos = personaRepos;
        this.direccionRepos = direccionRepos;
    }

    public final void events() {
        ventanaPersona.paginaComboBox.addActionListener(this);
        ventanaPersona.getTablePerson().getModel().addTableModelListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
        Object evt = e.getSource();
        if (evt.equals(ventanaPersona.getPaginaComboBox())) {
            pag.eventComboBox(ventanaPersona.getPaginaComboBox());
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        pag.actualizarBotones();
    }

    public void listarPersonas(JTable tableFrame, JPanel panelBotones) {
        List<Persona> listaDatos = personaRepos.listarPersonas();

        pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_PERSONA);

        pag.crearListadoFilasPermitidas(panelBotones);

        ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
        events();
        ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
    }

    public int agregarPersona(JRootPane rootPane, String dni, String apellido, String nombre, String fecha_nac,
            String numero_tel, String numero_tel_opcional, String barrio, String calle, String numCasa,
            boolean tuvo_covid, boolean tiene_trasplantes, String factores_riesgo) {
        int resultadoOperacion;
        Persona p = personaRepos.buscarPersona(Long.valueOf(dni));
        if (p == null) {
            LocalDate fecha = LocalDate.parse(fecha_nac);
            int edad = calcularEdad(fecha);
            Direccion direccion = insertarDireccion(barrio, calle, numCasa);
            p = new Persona();
            p.setDni(Long.valueOf(dni));
            p.setApellido(apellido);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setFecha_nac(fecha);
            p.setNumero_tel(Long.valueOf(numero_tel));
            p.setNumero_tel_opcional(numero_tel_opcional.isEmpty() ? 0L : Long.valueOf(numero_tel_opcional));
            p.setDireccion(direccion);
            p.setTuvo_covid(tuvo_covid);
            p.setTuvo_trasplantes(tiene_trasplantes);
            p.setFactores_riesgo(factores_riesgo);
            resultadoOperacion = personaRepos.insertar(p);
            if (resultadoOperacion != EXITO) {
                mostrarMensajesError(rootPane, resultadoOperacion);
            }
        } else {
            resultadoOperacion = CLAVEREPETIDA;
            mostrarMensajesError(rootPane, resultadoOperacion);
        }
        return resultadoOperacion;
    }

    public Persona editarPersona(JRootPane rootPane, JTable tableFrame) {
        Persona persona = null;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            persona = personaRepos.buscarPersona(Long.valueOf(tableFrame.getValueAt(row, 0).toString()));
        } else {
            mostrarMensajesError(rootPane, EMPTY);
        }
        return persona;
    }

    public int modificarPersona(JRootPane rootPane, Long dniBuscado, String nuevoDni, String apellido, String nombre, String fecha_nac,
            String numero_tel, String numero_tel_opcional, String barrio, String calle, String numCasa,
            boolean tuvo_covid, boolean tiene_trasplantes, String factores_riesgo) {
        int resultadoOperacion; //= FALLA;
        Persona p = null;
        if (!Objects.equals(dniBuscado, Long.valueOf(nuevoDni))) {
            p = personaRepos.buscarPersona(Long.valueOf(nuevoDni));
        }
        if (p == null) {
            LocalDate fecha = LocalDate.parse(fecha_nac);
            int edad = calcularEdad(fecha);
            Direccion direccion = insertarDireccion(barrio, calle, numCasa);
            p = new Persona();
            p.setDni(Long.valueOf(nuevoDni));
            p.setApellido(apellido);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setFecha_nac(fecha);
            p.setNumero_tel(Long.valueOf(numero_tel));
            p.setNumero_tel_opcional(numero_tel_opcional.isEmpty() ? 0L : Long.valueOf(numero_tel_opcional));
            p.setDireccion(direccion);
            p.setTuvo_covid(tuvo_covid);
            p.setTuvo_trasplantes(tiene_trasplantes);
            p.setFactores_riesgo(factores_riesgo);
            resultadoOperacion = personaRepos.modificar(p, dniBuscado);
            if (resultadoOperacion != EXITO) {
                mostrarMensajesError(rootPane, resultadoOperacion);
            }
        } else {
            resultadoOperacion = CLAVEREPETIDA;
            mostrarMensajesError(rootPane, resultadoOperacion);
        }
        return resultadoOperacion;
    }

    public boolean eliminarPersonaLogico(JRootPane rootPane, JTable tableFrame) {
        boolean operacionExitosa = false;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            Long dni = Long.valueOf(tableFrame.getValueAt(row, 0).toString());
            if (personaRepos.eliminarLogico(dni) == EXITO) {
                operacionExitosa = true;
            }
        } else {
            mostrarMensajesError(rootPane, EMPTY);
        }
        return operacionExitosa;
    }

    public void buscarPersonaDni(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchDni) {
        if (jtfSearchDni != null) {
            List<Persona> listaDatos = personaRepos.buscarPersonasPorDni(jtfSearchDni.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_PERSONA);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
        }
    }

    public void buscarPersonaNomYApe(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchNombre) {
        if (jtfSearchNombre != null) {
            List<Persona> listaDatos = personaRepos.buscarPersonasPorNomYApe(jtfSearchNombre.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_PERSONA);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
        }
    }

    private void mostrarMensajesError(JRootPane rootPane, int resultadoOperacion) {
        String mensaje;
        String titulo;
        int tipoMensaje;
        switch (resultadoOperacion) {
            case CLAVEREPETIDA -> {
                mensaje = "El DNI ingresado ya está registrado para otra persona.";
                titulo = "Mensaje de Error";
                tipoMensaje = JOptionPane.ERROR_MESSAGE;
            }
            case EMPTY -> {
                mensaje = "Seleccione una celda para editar.";
                titulo = "Atención";
                tipoMensaje = JOptionPane.WARNING_MESSAGE;
            }
            default -> {
                mensaje = "Error desconocido.";
                titulo = "Ups";
                tipoMensaje = JOptionPane.ERROR_MESSAGE;
            }
        }
        JOptionPane.showMessageDialog(rootPane, mensaje, titulo, tipoMensaje);
    }

    private int calcularEdad(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
    }

    private Direccion insertarDireccion(String barrio, String calle, String numCasa) {
        Direccion direccion = direccionRepos.buscarDireccion(barrio, calle, Integer.parseInt(numCasa));
        if (direccion == null) {
            boolean insercionDireccion = direccionRepos.insertar(barrio, calle, Integer.parseInt(numCasa));
            if (insercionDireccion) {
                direccion = direccionRepos.buscarDireccion(barrio, calle, Integer.parseInt(numCasa));
            }
        }
        return direccion;
    }

    public JDialogPersona getVentana() {
        return ventanaPersona;
    }

    public void setVentana(JDialogPersona ventana) {
        this.ventanaPersona = ventana;
    }
}
