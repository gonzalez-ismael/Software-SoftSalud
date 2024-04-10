package com.softsalud.software.controller.logic;

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
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 *
 * @author Gonzalez Ismael
 */
public class VacunacionController implements ActionListener, TableModelListener {

    private final int EMPTY = -1, EXITO = 1, CLAVEREPETIDA = 2, UNKNOWNFAIL = 3, NOEXISTEPERSONA = 4, NOEXISTEVACUNA = 5;
    private final IVacunacionRepository vacunacionRepos;
    private final IPersonaRepository personaRepos;
    private final IVacunaRepository vacunaRepos;
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

    public void ListarVacunacion(JTable tableFrame, JPanel panelBotones) {
        List<Vacunacion> listaDatos = vacunacionRepos.listarVacunaciones();

        pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNACION);

        pag.crearListadoFilasPermitidas(panelBotones);

        ventanaVacunacion.setPaginaComboBox(pag.getJcbCantidadRegistros());
        events();
        ventanaVacunacion.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
    }

    public int agregarVacunacion(String dni, String marcaVacuna, String loteVacuna,
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

    public Vacunacion editarVacunacion(JRootPane rootPane, JTable tableFrame) {
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
            resultadoOperacion = vacunacionRepos.modificar(v, dni, vacuna.getCodigo(), loteVacuna);
            resultadoOperacion = (resultadoOperacion != EXITO) ? UNKNOWNFAIL : resultadoOperacion;
        } else {
            resultadoOperacion = CLAVEREPETIDA;
        }
        return resultadoOperacion;
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
