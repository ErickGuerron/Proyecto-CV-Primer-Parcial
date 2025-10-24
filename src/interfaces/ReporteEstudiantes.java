
package interfaces;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import java.sql.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class ReporteEstudiantes extends javax.swing.JInternalFrame{

    
    public ReporteEstudiantes() {
        initComponents();
    }

private void cargarReporte() {
    JasperPrint imprimir = null;
    try {
        cuartouta.Conexion cc = new cuartouta.Conexion();
        Connection cn = cc.conectar();

        Object seleccionado = jcbxSeleccionado.getSelectedItem();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un curso primero", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- INICIO DE CAMBIOS ---

        // 1. Cargar el reporte como un recurso (esto ya lo hacías bien)
        InputStream reporteStream = getClass().getResourceAsStream("/reportes/reportEstudiantesCurso.jasper");
        if (reporteStream == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el archivo del reporte (reportEstudiantesCurso.jasper)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        InputStream imagenHeaderStream = getClass().getResourceAsStream("/img/HeaderReportes.png");
        if (imagenHeaderStream == null) {
            JOptionPane.showMessageDialog(null, "No se encontró la imagen del reporte (HeaderReportes.png)", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Preparar los parámetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cursoSeleccionado", seleccionado.toString());
        parametros.put("IMAGEN_HEADER", imagenHeaderStream); // <-- Aquí pasamos la imagen
        // Ya no necesitas el parámetro "REPORT_DIR", era la causa del problema.

        // --- FIN DE CAMBIOS ---

        // Cargar el objeto del reporte desde el Stream
        JasperReport reporte = (JasperReport) JRLoader.loadObject(reporteStream);

        // Llenar el reporte
        imprimir = JasperFillManager.fillReport(reporte, parametros, cn);
        JasperViewer.viewReport(imprimir, false);

    } catch (Exception ex) {
        // Es buena práctica imprimir el stack trace para depurar
        ex.printStackTrace(); 
        JOptionPane.showMessageDialog(null,
            "Error al generar el reporte: " + ex.getMessage(),
            "Error en el reporte",
            JOptionPane.ERROR_MESSAGE);
    }
}
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcbxSeleccionado = new componentes.UTCComboCursos();
        jbtnSeleccionar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbtnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jbtnSeleccionar.setText("Seleccionar");
        jbtnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSeleccionarActionPerformed(evt);
            }
        });

        jLabel1.setText("Curso:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Seleccione el curso para el reporte");

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jcbxSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnSeleccionar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jbtnCancelar)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbxSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnSeleccionar)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtnCancelar)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSeleccionarActionPerformed
        cargarReporte();
    }//GEN-LAST:event_jbtnSeleccionarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
       this.setVisible(false);
    }//GEN-LAST:event_jbtnCancelarActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReporteEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteEstudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReporteEstudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnSeleccionar;
    private componentes.UTCComboCursos jcbxSeleccionado;
    // End of variables declaration//GEN-END:variables
}
