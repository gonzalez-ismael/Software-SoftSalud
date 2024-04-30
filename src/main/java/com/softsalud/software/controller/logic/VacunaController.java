package com.softsalud.software.controller.logic;

import com.softsalud.software.persistence.model.Vacuna;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import com.softsalud.software.view.JDialogVacuna;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class VacunaController implements ActionListener, TableModelListener {

    //CONSTANTES
    private final IVacunaRepository vacunaRepos;
    private final int EMPTY = -1;
    //VARIABLES
    private JDialogVacuna ventanaVacuna;
    private PaginarTabla pag;

    public VacunaController(IVacunaRepository vacunaRepos) {
        this.vacunaRepos = vacunaRepos;
    }

    public final void events() {
        ventanaVacuna.paginaComboBox.addActionListener(this);
        ventanaVacuna.getTableVaccine().getModel().addTableModelListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ventanaVacuna.setPaginaComboBox(pag.getJcbCantidadRegistros());
        Object evt = e.getSource();
        if (evt.equals(ventanaVacuna.getPaginaComboBox())) {
            pag.eventComboBox(ventanaVacuna.getPaginaComboBox());
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        pag.actualizarBotones();
    }

    public void listarVacunas(JTable tableFrame, JPanel panelBotones) {
        List<Vacuna> listaDatos = vacunaRepos.listarVacunas();

        pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNA);

        pag.crearListadoFilasPermitidas(panelBotones);

        ventanaVacuna.setPaginaComboBox(pag.getJcbCantidadRegistros());
        events();
        ventanaVacuna.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
    }

    public List<Vacuna> listarVacunas() {
        return vacunaRepos.listarVacunas();
    }

    public int agregarVacuna(String nameFrame) {
        if (!nameFrame.isEmpty()) {
            return vacunaRepos.insertar(nameFrame);
        } else {
            return -1;
        }
    }

    public String[] editarVacuna(JTable tableFrame) {
        String[] data = new String[2];
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            data[0] = tableFrame.getValueAt(row, 0).toString();
            data[1] = tableFrame.getValueAt(row, 1).toString();
        }
        return data;
    }

    public int modificarVacuna(String idFrame, String nameFrame) {
        if (!nameFrame.isEmpty()) {
            return vacunaRepos.modificar(Integer.parseInt(idFrame), nameFrame);
        } else {
            return -1;
        }
    }

    public boolean eliminarVacuna(JTable tableFrame) {
        boolean vacunaEliminada = false;
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            int id = Integer.parseInt(tableFrame.getValueAt(row, 0).toString());
            vacunaEliminada = vacunaRepos.eliminar(id);
        }
        return vacunaEliminada;
    }

    public void buscarVacunaPorNombre(JTable tableFrame, JPanel panelBotones, JTextField jtfSearchDni) {
        if (jtfSearchDni != null) {
            List<Vacuna> listaDatos = vacunaRepos.listarVacunasPorNombre(jtfSearchDni.getText());

            pag = new PaginarTabla(tableFrame, listaDatos, PaginarTabla.MODO_VACUNA);

            pag.crearListadoFilasPermitidas(panelBotones);

            ventanaVacuna.setPaginaComboBox(pag.getJcbCantidadRegistros());
            events();
            ventanaVacuna.getPaginaComboBox().setSelectedIndex(Integer.parseInt("2"));
        }
    }

    public JDialogVacuna getVentana() {
        return ventanaVacuna;
    }

    public void setVentana(JDialogVacuna ventanaVacuna) {
        this.ventanaVacuna = ventanaVacuna;
    }
}
