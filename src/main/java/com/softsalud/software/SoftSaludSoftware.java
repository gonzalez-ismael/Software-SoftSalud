package com.softsalud.software;

import com.softsalud.software.login.JFrameLogin;
import com.softsalud.software.connection.ConnectionDB;
import com.softsalud.software.configuration.ConfiguracionInicial;
import com.softsalud.software.login.LoginListener;
import com.softsalud.software.view.JFrameMain;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gonzalez Ismael
 */
public class SoftSaludSoftware implements LoginListener {

    /**
     * MENÚ PRINCIPAL
     *
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
            ConfiguracionInicial.crearDirectorios();
            ConfiguracionInicial.establecerRutasPredeterminadas();

            SoftSaludSoftware app = new SoftSaludSoftware();
            JFrameLogin login = new JFrameLogin(app);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
        });
    }

    @Override
    public void onLoginSuccess() {
        // Aquí se inicializa y muestra el JFrameMain
        java.awt.EventQueue.invokeLater(() -> {
            try {
                ConnectionDB conexion = obtenerConexion();
                JFrameMain main = new JFrameMain(conexion);
                System.setProperty("java.awt.headless", "false");
                main.setLocationRelativeTo(null);

                // Agregar un WindowListener para manejar el cierre del JFrame
                main.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // Cierra la conexión a la base de datos
                        conexion.closeConnection();
                        System.out.println("\nConexión cerrada correctamente.\n\n");
                    }
                });

                main.setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private ConnectionDB obtenerConexion() {
        ConnectionDB conexion;
        try {
            conexion = new ConnectionDB();
            System.out.println("\n\n");
            System.out.println("Conexion a: " + conexion.getConnection().getCatalog());
            System.out.println("\n\n");
        } catch (SQLException ex) {
            conexion = null;
            System.out.println("ERROR: " + ex.getMessage());
        }
        return conexion;
    }
}
