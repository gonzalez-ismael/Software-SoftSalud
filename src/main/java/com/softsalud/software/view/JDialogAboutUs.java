package com.softsalud.software.view;

/**
 *
 * @author Gonzalez Ismael
 */
public class JDialogAboutUs extends javax.swing.JDialog {

    /**
     * Creates new form JDialogAboutUs
     * @param parent
     * @param modal
     */
    public JDialogAboutUs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jTextArea1.setBackground(new java.awt.Color(204, 204, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Roboto", 1, 13)); // NOI18N
        jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea1.setRows(5);
        jTextArea1.setText("          Descripción:\n     Este software fue desarrollado por un estudiante de la carrera de Analista de Sistemas de la \n     Universidad Nacional de la Patagonia Austral para uso exclusivo del personal de salud. \n     Está diseñado para cargar personas, vacunas y registrar sus vacunaciones \n     y dosis de manera eficiente y segura.\n\n          Desarrollador:\n     Mi nombre es GONZALEZ ESPADA, José Ismael y soy un estudiante próximo a titularme como \n     Analista de Sistemas de la UART-UNPA. Me apasiona el desarrollo de software y me esfuerzo por \n     ofrecer soluciones de calidad que se adapten a las necesidades y preferencias de mis usuarios.\n\n          ¿Qué ofrezco?\n     Ofrezco software de calidad, personalizado para satisfacer las necesidades específicas \n     del personal de salud. Mi objetivo es proporcionar herramientas efectivas y fáciles \n     de usar que simplifiquen las tareas diarias y mejoren la eficiencia del trabajo.\n\n          Contacto:\n     Si tienes alguna pregunta o problema con el software, no dudes en comunicarte \n     conmigo a través de ISMAQTY33@GMAIL.COM y estaré encantado de ayudarte.");
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo soft salud horizontal - chikito.png"))); // NOI18N
        jLabel1.setText(" ");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
