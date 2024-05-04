package com.softsalud.software.controller;

import com.softsalud.software.controller.resource.PaginarTabla;
import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.view.JDialogPersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Gonzalez Ismael
 */
public class PersonaController implements ActionListener, TableModelListener {

    //CONSTANTES
    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3;
    private final IPersonaRepository personaRepos;
    //VARIABLES
    private JDialogPersona ventanaPersona;
    private PaginarTabla pag;

    public PersonaController(IPersonaRepository personaRepos) {
        this.personaRepos = personaRepos;
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
        ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
    }
    
    public List<Persona> listarPersonas(){
        return personaRepos.listarPersonas();
    }

    public int agregarRegistro(String dni, String apellido, String nombre, String fecha_nac,
            String numero_tel, String numero_tel_opcional, String localidad, String direccion,
            boolean tuvo_covid, boolean tiene_trasplantes, String factores_riesgo) {
        int resultadoOperacion;
        Persona p = personaRepos.buscarPersona(Long.valueOf(dni));
        if (p == null) {
            LocalDate fecha = LocalDate.parse(fecha_nac);
            int edad = calcularEdad(fecha);
            p = new Persona();
            p.setDni(Long.valueOf(dni));
            p.setApellido(apellido);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setFecha_nac(fecha);
            p.setNumero_tel(Long.valueOf(numero_tel));
            p.setNumero_tel_opcional(numero_tel_opcional.isEmpty() ? 0L : Long.valueOf(numero_tel_opcional));
            p.setLocalidad(localidad);
            p.setDireccion(direccion);
            p.setTuvo_covid(tuvo_covid);
            p.setTiene_trasplantes(tiene_trasplantes);
            p.setFactores_riesgo(factores_riesgo);
            resultadoOperacion = personaRepos.insertar(p);
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
    }

    public Persona editarPersona(JTable tableFrame) {
        Persona persona = null;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            persona = personaRepos.buscarPersona(Long.valueOf(tableFrame.getValueAt(row, 0).toString()));
        }
        return persona;
    }

    public int modificarPersona(Long dniBuscado, String nuevoDni, String apellido, String nombre, String fecha_nac,
            String numero_tel, String numero_tel_opcional, String localidad, String direccion,
            boolean tuvo_covid, boolean tiene_trasplantes, String factores_riesgo) {
        int resultadoOperacion; //= FALLA;
        Persona p = null;
        if (!Objects.equals(dniBuscado, Long.valueOf(nuevoDni))) {
            p = personaRepos.buscarPersona(Long.valueOf(nuevoDni));
        }
        if (p == null) {
            LocalDate fecha = LocalDate.parse(fecha_nac);
            int edad = calcularEdad(fecha);
            p = new Persona();
            p.setDni(Long.valueOf(nuevoDni));
            p.setApellido(apellido);
            p.setNombre(nombre);
            p.setEdad(edad);
            p.setFecha_nac(fecha);
            p.setNumero_tel(Long.valueOf(numero_tel));
            p.setNumero_tel_opcional(numero_tel_opcional.isEmpty() ? 0L : Long.valueOf(numero_tel_opcional));
            p.setLocalidad(localidad);
            p.setDireccion(direccion);
            p.setTuvo_covid(tuvo_covid);
            p.setTiene_trasplantes(tiene_trasplantes);
            p.setFactores_riesgo(factores_riesgo);
            resultadoOperacion = personaRepos.modificar(p, dniBuscado);
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
    }

    public boolean eliminarPersonaLogico(JTable tableFrame) {
        boolean operacionExitosa = false;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            Long dni = Long.valueOf(tableFrame.getValueAt(row, 0).toString());
            if (personaRepos.eliminarLogico(dni) == EXITO) {
                operacionExitosa = true;
            }
        }
        return operacionExitosa;
    }
    
    public int eliminarTodosRegistros(){
        return personaRepos.eliminarTodo();
    }

    public void buscarPersonaDni(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchDni) {
        if (jtfSearchDni != null) {
            List<Persona> listaDatos = personaRepos.buscarPersonasPorDni(jtfSearchDni.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_PERSONA);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    public void buscarPersonaNomYApe(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchNombre) {
        if (jtfSearchNombre != null) {
            List<Persona> listaDatos = personaRepos.buscarPersonasPorNomYApe(jtfSearchNombre.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_PERSONA);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaPersona.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaPersona.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    private int calcularEdad(LocalDate birthdate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthdate, currentDate).getYears();
    }

    public JDialogPersona getVentana() {
        return ventanaPersona;
    }

    public void setVentana(JDialogPersona ventana) {
        this.ventanaPersona = ventana;
    }
}
