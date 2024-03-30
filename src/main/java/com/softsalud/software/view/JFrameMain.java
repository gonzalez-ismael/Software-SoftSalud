package com.softsalud.software.view;

import com.softsalud.software.connection.ConnectionDB;
import com.softsalud.software.controller.logic.PersonaController;
import com.softsalud.software.controller.logic.VacunaController;
import com.softsalud.software.persistence.repository.DireccionRepos;
import com.softsalud.software.persistence.repository.PersonaRepos;
import com.softsalud.software.persistence.repository.VacunaRepos;
import com.softsalud.software.persistence.repository.interfaz.IDireccionRepository;
import com.softsalud.software.persistence.repository.interfaz.IPersonaRepository;
import com.softsalud.software.persistence.repository.interfaz.IVacunaRepository;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Gonzalez Ismael
 */
public class JFrameMain extends javax.swing.JFrame {

    private final IVacunaRepository iVacunaRepos;
    private final IDireccionRepository iDireccionRepos;
    private final IPersonaRepository iPersonaRepos;
    private PersonaController personaController;
    private VacunaController vacunaController;
    private final ConnectionDB connection;

    public JFrameMain(ConnectionDB connection) {
        initComponents();
        this.setTitle("Menú Principal - SoftSalud.inc | LP 2023 - ADES - UART - UNPA");

        this.connection = connection;
        iVacunaRepos = new VacunaRepos(this.connection.getConnection());
        iDireccionRepos = new DireccionRepos(this.connection.getConnection());
        iPersonaRepos = new PersonaRepos(this.connection.getConnection());

        // Agregar un WindowListener para escuchar el evento de cierre del JFrame
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                connection.closeConnection(); // Cerrar la conexión cuando se cierre el JFrame
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JMPersonaMenu = new javax.swing.JMenuItem();
        JMVacunaMenu = new javax.swing.JMenuItem();
        JMVacunacionMenu = new javax.swing.JMenuItem();
        JMReporteMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FINAL LP 2023");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        labelImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo soft salud horizontal - chikito.png"))); // NOI18N

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

        JMPersonaMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        JMPersonaMenu.setText("Persona");
        JMPersonaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMPersonaMenuActionPerformed(evt);
            }
        });
        jMenu1.add(JMPersonaMenu);

        JMVacunaMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        JMVacunaMenu.setText("Vacuna");
        JMVacunaMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMVacunaMenuActionPerformed(evt);
            }
        });
        jMenu1.add(JMVacunaMenu);

        JMVacunacionMenu.setText("Vacunación");
        JMVacunacionMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMVacunacionMenuActionPerformed(evt);
            }
        });
        jMenu1.add(JMVacunacionMenu);

        JMReporteMenu.setText("Reportes");
        JMReporteMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JMReporteMenuActionPerformed(evt);
            }
        });
        jMenu1.add(JMReporteMenu);

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

    private void JMVacunaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMVacunaMenuActionPerformed
        vacunaController = new VacunaController(iVacunaRepos);
        JDialogVacuna JDialogVacuna = new JDialogVacuna(this, true, vacunaController);
        JDialogVacuna.setTitle("Menú de las Vacunas");
        JDialogVacuna.setLocationRelativeTo(null);
        JDialogVacuna.setVisible(true);
    }//GEN-LAST:event_JMVacunaMenuActionPerformed

    private void JMPersonaMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMPersonaMenuActionPerformed
        personaController = new PersonaController(iPersonaRepos, iDireccionRepos);
        JDialogPersona personDialog = new JDialogPersona(this, true, personaController);
        personDialog.setTitle("Menú de las Personas");
        personDialog.setLocationRelativeTo(null);
        personDialog.setVisible(true);
    }//GEN-LAST:event_JMPersonaMenuActionPerformed

    private void JMVacunacionMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMVacunacionMenuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JMVacunacionMenuActionPerformed

    private void JMReporteMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JMReporteMenuActionPerformed
//        JDialogReport reportDialog = new JDialogReport(this,true, reportController);
//        reportDialog.setTitle("Menú de Reportes");
//        reportDialog.setLocationRelativeTo(null);
//        reportDialog.setVisible(true);
    }//GEN-LAST:event_JMReporteMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold> 

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                ConnectionDB conexion = new ConnectionDB();
                System.out.println("\n\n");
                System.out.println("Conexion a: " + conexion.getConnection().getCatalog());
                System.out.println("\n\n");

                JFrameMain main = new JFrameMain(conexion);
                System.setProperty("java.awt.headless", "false");
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem JMPersonaMenu;
    private javax.swing.JMenuItem JMReporteMenu;
    private javax.swing.JMenuItem JMVacunaMenu;
    private javax.swing.JMenuItem JMVacunacionMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelImage;
    // End of variables declaration//GEN-END:variables
}
