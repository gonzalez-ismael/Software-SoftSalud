package com.softsalud.software.view;

import com.softsalud.software.controller.logic.VacunaController;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Gonzalez Ismael
 */
public class JDialogVacuna extends javax.swing.JDialog {

    public JComboBox<Integer> paginaComboBox;
    private final VacunaController controller;
    private final int EXITO = 1, CLAVEREPETIDA = 2;

    public JDialogVacuna(java.awt.Frame parent, boolean modal, VacunaController controller) {
        super(parent, modal);
        initComponents();
        this.controller = controller;
        this.controller.setVentana(this);
        controller.listarVacunas(tableVaccine, jPanelBotonesPagina);
        getContentPane().setBackground(Color.darkGray);

        eventoBuscarVacuna();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableVaccine = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabelID = new javax.swing.JLabel();
        jLabelNameVaccine = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jTextFieldNameVaccine = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnReloader = new javax.swing.JButton();
        jLabelSearchVaccine = new javax.swing.JLabel();
        jTextFieldSearchVaccine = new javax.swing.JTextField();
        jlErrorNombreVacuna = new javax.swing.JLabel();
        jPanelBotonesPagina = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(200, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Detalle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(60, 63, 65))); // NOI18N

        tableVaccine.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre de la Vacuna"
            }
        ));
        jScrollPane1.setViewportView(tableVaccine);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(200, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(41, 43, 45)), "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 1, 12), new java.awt.Color(60, 63, 65))); // NOI18N

        jLabelID.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabelID.setForeground(new java.awt.Color(0, 0, 0));
        jLabelID.setText("ID :");

        jLabelNameVaccine.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabelNameVaccine.setForeground(new java.awt.Color(0, 0, 0));
        jLabelNameVaccine.setText("Nombre/Marca Vacuna:");

        jTextFieldID.setEditable(false);

        btnSave.setText("Agregar");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnEdit.setText("Editar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Eliminar");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setText("Guardar Cambios");
        btnUpdate.setEnabled(false);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancelar");
        btnCancel.setEnabled(false);
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnReloader.setText("Recargar Página");
        btnReloader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloaderActionPerformed(evt);
            }
        });

        jLabelSearchVaccine.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabelSearchVaccine.setForeground(new java.awt.Color(0, 0, 0));
        jLabelSearchVaccine.setText("Buscar Vacuna : ");

        jlErrorNombreVacuna.setForeground(new java.awt.Color(175, 0, 50));
        jlErrorNombreVacuna.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReloader)
                    .addComponent(jLabelSearchVaccine)
                    .addComponent(jLabelNameVaccine)
                    .addComponent(jLabelID))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 199, Short.MAX_VALUE)
                        .addComponent(btnUpdate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jlErrorNombreVacuna, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextFieldSearchVaccine, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                                .addComponent(jTextFieldNameVaccine)
                                .addComponent(jTextFieldID)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelID)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelNameVaccine)
                    .addComponent(jTextFieldNameVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnCancel)
                        .addComponent(btnEdit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlErrorNombreVacuna)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jTextFieldSearchVaccine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSearchVaccine))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnDelete)
                    .addComponent(btnSave)
                    .addComponent(btnUpdate)
                    .addComponent(btnReloader))
                .addContainerGap())
        );

        jPanelBotonesPagina.setBackground(new java.awt.Color(200, 255, 255));
        jPanelBotonesPagina.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBotonesPagina, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBotonesPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!jTextFieldNameVaccine.getText().isEmpty()) {
            int resultado = controller.agregarVacuna(jTextFieldNameVaccine.getText());
            switch (resultado) {
                case EXITO -> {
                    controller.listarVacunas(tableVaccine, jPanelBotonesPagina);
                    clearCells();
                }
                case CLAVEREPETIDA -> {
                    String mensaje = "Ingresar un nombre de Vacuna que no este en uso.";
                    String titulo = "Nombre vacuna repetido";
                    int modo = JOptionPane.ERROR_MESSAGE;
                    mostrarMensaje(mensaje, titulo, modo);
                    jlErrorNombreVacuna.setText("Ingresar un nombre válido.");
                    jTextFieldNameVaccine.requestFocus();
                }
            }
        } else {
            String mensaje = "Complete el nombre de la vacuna para poder ingresarla.";
            String titulo = "El nombre de la vacuna esta vacio";
            int modo = JOptionPane.ERROR_MESSAGE;
            mostrarMensaje(mensaje, titulo, modo);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        String[] data = controller.editarVacuna(tableVaccine);
        if (!"EMPTY".equals(data[0])) {
            jTextFieldID.setText(data[0]);
            jTextFieldNameVaccine.setText(data[1]);
            btnCancel.setEnabled(true);
            btnUpdate.setEnabled(true);
            btnEdit.setEnabled(false);
            btnSave.setEnabled(false);
            btnDelete.setEnabled(false);
        } else {
            String mensaje = "Seleccione una celda para editar.";
            String titulo = "Ups";
            int modo = JOptionPane.ERROR_MESSAGE;
            mostrarMensaje(mensaje, titulo, modo);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (controller.eliminarVacuna(tableVaccine)) {
            controller.listarVacunas(tableVaccine, jPanelBotonesPagina);
            clearCells();
        } else {
            String mensaje = "Seleccione una celda para editar.";
            String titulo = "Ups";
            int modo = JOptionPane.ERROR_MESSAGE;
            mostrarMensaje(mensaje, titulo, modo);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (!jTextFieldNameVaccine.getText().isEmpty()) {
            int resultado = controller.modificarVacuna(jTextFieldID.getText(), jTextFieldNameVaccine.getText());
            switch (resultado) {
                case EXITO -> {
                    controller.listarVacunas(tableVaccine, jPanelBotonesPagina);
                    clearCells();
                    clearBtns();
                }
                case CLAVEREPETIDA -> {
                    String mensaje = "Ingresar un nombre de Vacuna que no este en uso.";
                    String titulo = "Nombre vacuna repetido";
                    int modo = JOptionPane.ERROR_MESSAGE;
                    mostrarMensaje(mensaje, titulo, modo);
                    jlErrorNombreVacuna.setText("Ingresar un nombre válido.");
                    jTextFieldNameVaccine.requestFocus();
                }
            }
        } else {
            String mensaje = "Complete el nombre de la vacuna para poder ingresarla.";
            String titulo = "El nombre de la vacuna esta vacio";
            int modo = JOptionPane.ERROR_MESSAGE;
            mostrarMensaje(mensaje, titulo, modo);
        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        clearCells();
        clearBtns();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnReloaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloaderActionPerformed
        controller.listarVacunas(tableVaccine, jPanelBotonesPagina);
        clearCells();
        clearBtns();
    }//GEN-LAST:event_btnReloaderActionPerformed

    private void eventoBuscarVacuna() {
        // Agregamos un DocumentListener al dni
        jTextFieldSearchVaccine.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Llamamos a la función cada vez que se inserta un carácter
                controller.buscarVacunaPorNombre(tableVaccine, jPanelBotonesPagina, jTextFieldSearchVaccine);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Llamamos a la función cada vez que se elimina un carácter
                controller.buscarVacunaPorNombre(tableVaccine, jPanelBotonesPagina, jTextFieldSearchVaccine);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // No es relevante para JTextFields sin formato
            }
        });
    }

    private void clearBtns() {
        btnCancel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnEdit.setEnabled(true);
        btnSave.setEnabled(true);
        btnDelete.setEnabled(true);
    }

    private void clearCells() {
        jTextFieldID.setText("");
        jTextFieldNameVaccine.setText("");
        jlErrorNombreVacuna.setText(" ");
    }

    private void mostrarMensaje(String mensaje, String titulo, int modo) {
        JOptionPane.showMessageDialog(rootPane, mensaje, titulo, modo);
    }

    public JComboBox<Integer> getPaginaComboBox() {
        return paginaComboBox;
    }

    public void setPaginaComboBox(JComboBox<Integer> paginaComboBox) {
        this.paginaComboBox = paginaComboBox;
    }

    public JTable getTableVaccine() {
        return tableVaccine;
    }

    public void setTableVaccine(JTable tableVaccine) {
        this.tableVaccine = tableVaccine;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnReloader;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNameVaccine;
    private javax.swing.JLabel jLabelSearchVaccine;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelBotonesPagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNameVaccine;
    private javax.swing.JTextField jTextFieldSearchVaccine;
    private javax.swing.JLabel jlErrorNombreVacuna;
    private javax.swing.JTable tableVaccine;
    // End of variables declaration//GEN-END:variables
}
