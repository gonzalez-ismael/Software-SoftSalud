package com.softsalud.software.controller;

import com.softsalud.software.controller.resource.PaginarTabla;
import com.softsalud.software.persistence.model.Persona;
import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.model.Vacunacion;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunacionRepository;
import com.softsalud.software.view.JDialogVacunacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Gonzalez Ismael
 */
public class VacunacionController implements ActionListener, TableModelListener {

    //CONSTANTES
    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3, NOEXISTEPERSONA = 4, NOEXISTEVACUNA = 5;
    private final IVacunacionRepository vacunacionRepos;
    private final IPersonaRepository personaRepos;
    private final IVacunaRepository vacunaRepos;
    //VARIABLES
    private JDialogVacunacion ventanaVacunacion;
    private PaginarTabla pag;

    public VacunacionController(IVacunacionRepository vacunacionRepos, IPersonaRepository personaRepos, IVacunaRepository vacunaRepos) {
        this.vacunacionRepos = vacunacionRepos;
        this.personaRepos = personaRepos;
        this.vacunaRepos = vacunaRepos;
    }

    public final void events() {
        ventanaVacunacion.paginaComboBox.addActionListener(this);
        ventanaVacunacion.getTableVacunacion().getModel().addTableModelListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
        Object evt = e.getSource();
        if (evt.equals(ventanaVacunacion.getPaginaComboBox())) {
            pag.eventComboBox(ventanaVacunacion.getPaginaComboBox());
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        pag.actualizarBotones();
    }

    public void listarVacunaciones(JTable tableFrame, JPanel panelBotones) {
        List<Vacunacion> listaDatos = vacunacionRepos.listarVacunaciones();

        pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

        pag.crearListadoFilasPermitidas(panelBotones);

        ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
        events();
        ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
    }

    public List<Vacunacion> listarVacunaciones() {
        return vacunacionRepos.listarVacunacionesCrudo();
    }

    public int agregarRegistro(String dni, String marcaVacuna, String loteVacuna,
            String dosis, String fechaVac, String lugarVacunacion) {
        Persona p = personaRepos.buscarPersona(Long.valueOf(dni));
        if (p == null) {
            return NOEXISTEPERSONA;
        }

        Vacuna v = vacunaRepos.buscarVacunaPorNombre(marcaVacuna);
        if (v == null) {
            return NOEXISTEVACUNA;
        }

        Vacunacion vac = vacunacionRepos.buscarVacunacion(p.getDni(), v.getCodigo(), loteVacuna);
        if (vac != null) {
            return CLAVEREPETIDA;
        }

        LocalDate fecha = LocalDate.parse(fechaVac);
        vac = new Vacunacion();
        vac.setPersona_dni(Long.valueOf(dni));
        vac.setVacuna_codigo(v.getCodigo());
        vac.setLote_vacuna(loteVacuna);
        vac.setNumero_dosis(dosis.equals("Anual") ? 0 : Integer.parseInt(dosis));
        vac.setFecha_vacunacion(fecha);
        vac.setLugar_vacunacion(lugarVacunacion);
        int resultadoOperacion = vacunacionRepos.insertar(vac);
        return (resultadoOperacion != EXITO) ? UNKNOWNFAIL : resultadoOperacion;
    }

    public Vacunacion editarVacunacion(JTable tableFrame) {
        Vacunacion vacunacion = null;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            Long dni = Long.valueOf(tableFrame.getValueAt(row, 0).toString());
            String nombreVacuna = tableFrame.getValueAt(row, 2).toString();
            Vacuna v = vacunaRepos.buscarVacunaPorNombre(nombreVacuna);
            String loteVacuna = tableFrame.getValueAt(row, 3).toString();
            vacunacion = vacunacionRepos.buscarVacunacion(dni, v.getCodigo(), loteVacuna);
            vacunacion.setNombre_vacuna(nombreVacuna);
        }
        return vacunacion;
    }

    public int modificarVacunacion(Long dniBuscado, String marcaVacunaBuscada, String loteVacunaBuscada,
            Long dni, String marcaVacuna, String loteVacuna, int numeroDosis, String fechaVacunacion, String lugarVacunacion) {
        int resultadoOperacion; //= FALLA;
        Vacunacion v = null;
        Vacuna vacuna = vacunaRepos.buscarVacunaPorNombre(marcaVacuna);
        Vacuna vacunaBuscada = vacunaRepos.buscarVacunaPorNombre(marcaVacunaBuscada);
        if (!esMismaVacunacion(dniBuscado, marcaVacunaBuscada, loteVacunaBuscada, dni, marcaVacuna, loteVacuna)) {
            v = vacunacionRepos.buscarVacunacion(dni, vacuna.getCodigo(), loteVacuna);
        }
        if (v == null) {
            v = new Vacunacion();
            v.setPersona_dni(dni);
            v.setVacuna_codigo(vacuna.getCodigo());
            v.setLote_vacuna(loteVacuna);
            v.setNumero_dosis(numeroDosis);
            v.setFecha_vacunacion(LocalDate.parse(fechaVacunacion));
            v.setLugar_vacunacion(lugarVacunacion);
            resultadoOperacion = vacunacionRepos.modificar(v, dniBuscado, vacunaBuscada.getCodigo(), loteVacunaBuscada);
            resultadoOperacion = (resultadoOperacion != EXITO) ? UNKNOWNFAIL : resultadoOperacion;
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
    }

    public int eliminarVacunacion(Vacunacion vacunacion) {
        return vacunacionRepos.eliminar(vacunacion);
    }

    public int eliminarTodosRegistros() {
        return vacunacionRepos.eliminarTodo();
    }

    public void buscarVacunacionDni(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchDni) {
        if (jtfSearchDni != null) {
            List<Vacunacion> listaDatos = vacunacionRepos.buscarVacunacionesPorDni(jtfSearchDni.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    public void buscarVacunacionNomYApe(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchNombre) {
        if (jtfSearchNombre != null) {
            List<Vacunacion> listaDatos = vacunacionRepos.buscarVacunacionesPorNomYApe(jtfSearchNombre.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    public void buscarVacunacionNombreVacuna(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchNombreVacuna) {
        if (jtfSearchNombreVacuna != null) {
            List<Vacunacion> listaDatos = vacunacionRepos.buscarVacunacionesPorNombreVacuna(jtfSearchNombreVacuna.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    public void buscarVacunacionDosis(JTable tableFrame, JPanel panelBotones, String dosis) {
        if (dosis != null) {
            dosis = dosis.equals("Anual") ? "0" : dosis;
            List<Vacunacion> listaDatos = vacunacionRepos.buscarVacunacionesPorDosis(dosis);

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("3"));
        }
    }

    private boolean esMismaVacunacion(Long dniBuscado, String marcaVacunaBuscada, String loteVacunaBuscada,
            Long dni, String marcaVacuna, String loteVacuna) {
        return dniBuscado.equals(dni) && marcaVacunaBuscada.equals(marcaVacuna) && loteVacunaBuscada.equals(loteVacuna);
    }

    public JDialogVacunacion getVentanaVacunacion() {
        return ventanaVacunacion;
    }

    public void setVentanaVacunacion(JDialogVacunacion ventanaVacunacion) {
        this.ventanaVacunacion = ventanaVacunacion;
    }
}
