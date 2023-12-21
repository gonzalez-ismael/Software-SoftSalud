package com.softsalud.software.controller;

import com.softsalud.software.persistence.entity.Vaccine;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gonzalez Ismael
 */
public class VaccineLogic {

    private final IVaccineService iVaccineServi;
    private final int EMPTY = -1;

    public VaccineLogic(IVaccineService iVaccineService) {
        this.iVaccineServi = iVaccineService;
    }

    public void listVaccine(JTable tableFrame) {
        DefaultTableModel table = (DefaultTableModel) tableFrame.getModel();

        table.setRowCount(0);

        List<Vaccine> lista = iVaccineServi.getVaccines();
        Object[] object = new Object[2];

        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getCode();
            object[1] = lista.get(i).getNameVaccine();
            table.addRow(object);
        }
    }

    public void addVaccine(JRootPane rootPane, String nameFrame) {
        if (!nameFrame.isEmpty()) {
            iVaccineServi.saveVacine(nameFrame);
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Ha ocurrido un error, revise e intente de nuevo.",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] editVaccine(JRootPane rootPane, JTable tableFrame) {
        String[] data = new String[2];
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            data[0] = tableFrame.getValueAt(row, 0).toString();
            data[1] = tableFrame.getValueAt(row, 1).toString();
        } else {
            data[0] = "EMPTY";
            JOptionPane.showMessageDialog(rootPane,
                    "Seleccione una celda para editar.",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    public void updateVaccine(JRootPane rootPane, String idFrame, String nameFrame) {
        if (!nameFrame.isEmpty()) {
            iVaccineServi.updateVaccine(Long.valueOf(idFrame), nameFrame);
        } else { //NO deberia entrar nunca aca pero lo dejo porsi
            JOptionPane.showMessageDialog(rootPane,
                    "Ha ocurrido un error, revise e intente de nuevo",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteVaccine(JRootPane rootPane, JTable tableFrame) {
        int row = tableFrame.getSelectedRow();
        if (row != EMPTY) {
            Long id = Long.valueOf(tableFrame.getValueAt(row, 0).toString());
            iVaccineServi.deleteVaccine(id);
        } else {
            JOptionPane.showMessageDialog(rootPane,
                    "Seleccione una celda para editar.",
                    "Ups",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
