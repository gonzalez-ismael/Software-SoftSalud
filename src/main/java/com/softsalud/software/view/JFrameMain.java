package com.softsalud.software.view;

import com.softsalud.software.controller.PersonLogic;
import com.softsalud.software.controller.ReportLogic;
import com.softsalud.software.controller.VaccineLogic;
import com.softsalud.software.persistence.service.interfaces.IAddressService;
import com.softsalud.software.persistence.service.interfaces.IPersonService;
import com.softsalud.software.persistence.service.interfaces.IVaccineService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Gonzalez Ismael
 */
public class JFrameMain extends javax.swing.JFrame {
    
    private final VaccineLogic vaccineController;
    private final IVaccineService iVaccineServi;
    private final PersonLogic personController;
    private final IPersonService iPersonServi;
    private final ReportLogic reportController;
    

    /**
     * Creates new form JFrameMain
     *
     * @param vaccineService
     * @param iPersonService
     * @param iAddressService
     */
    @Autowired
    public JFrameMain(IVaccineService vaccineService, IPersonService iPersonService, IAddressService iAddressService) {
        initComponents();
        this.setTitle("Menú Principal - SoftSalud.inc | LP 2023 - ADES - UART - UNPA");
        
        this.iVaccineServi = vaccineService;
        this.vaccineController = new VaccineLogic(iVaccineServi);
        
        this.iPersonServi = iPersonService;
        this.personController = new PersonLogic(iPersonServi, iAddressService);

        this.reportController = new ReportLogic(iVaccineServi);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelImage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiPersonMenu = new javax.swing.JMenuItem();
        jmiVaccineMenu = new javax.swing.JMenuItem();
        jmiDoseHistoryMenu = new javax.swing.JMenuItem();
        jmiReportMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FINAL LP 2023");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/report/image/logo soft salud horizontal - chikito.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelImage, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jMenu1.setText("Menú");

        jmiPersonMenu.setText("Persona");
        jmiPersonMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiPersonMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jmiPersonMenu);

        jmiVaccineMenu.setText("Vacuna");
        jmiVaccineMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiVaccineMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jmiVaccineMenu);

        jmiDoseHistoryMenu.setText("Vacunación");
        jmiDoseHistoryMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiDoseHistoryMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jmiDoseHistoryMenu);

        jmiReportMenu.setText("Reportes");
        jmiReportMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiReportMenuActionPerformed(evt);
            }
        });
        jMenu1.add(jmiReportMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Configuración");
        jMenuBar1.add(jMenu2);

        jMenu4.setText("Personalización");
        jMenuBar1.add(jMenu4);

        jMenu3.setText("Acerca de Nosotros");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiVaccineMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiVaccineMenuActionPerformed
        JDialogVaccine vaccineDialog = new JDialogVaccine(this, true, vaccineController);
        vaccineDialog.setTitle("Menú de las Vacunas");
        vaccineDialog.setLocationRelativeTo(null);
        vaccineDialog.setVisible(true);
    }//GEN-LAST:event_jmiVaccineMenuActionPerformed

    private void jmiPersonMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiPersonMenuActionPerformed
        JDialogPerson personDialog = new JDialogPerson(this, true, personController);
        personDialog.setTitle("Menú de las Personas");
        personDialog.setLocationRelativeTo(null);
        personDialog.setVisible(true);
    }//GEN-LAST:event_jmiPersonMenuActionPerformed

    private void jmiDoseHistoryMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiDoseHistoryMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jmiDoseHistoryMenuActionPerformed

    private void jmiReportMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiReportMenuActionPerformed
        JDialogReport reportDialog = new JDialogReport(this,true, reportController);
        reportDialog.setTitle("Menú de Reportes");
        reportDialog.setLocationRelativeTo(null);
        reportDialog.setVisible(true);
    }//GEN-LAST:event_jmiReportMenuActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem jmiDoseHistoryMenu;
    private javax.swing.JMenuItem jmiPersonMenu;
    private javax.swing.JMenuItem jmiReportMenu;
    private javax.swing.JMenuItem jmiVaccineMenu;
    private javax.swing.JLabel labelImage;
    // End of variables declaration//GEN-END:variables
}
