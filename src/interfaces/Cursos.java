/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfaces;

import cuartouta.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ErickGuerron
 */
public class Cursos extends javax.swing.JInternalFrame {

    Conexion cn = new Conexion();
    java.sql.Connection cc = cn.conectar();

    /**
     * Creates new form Alumnos
     */
    public Cursos() {
        initComponents();
        jtxtNombre.limitLetter(30);
        mostrar();
        cargarCampos();
        botonesInicio();
        textosInicio();
    }

    public String generarSiguienteId() {
        if (cc == null) {
            System.err.println("Error: La conexión a la base de datos (cc) es nula.");
            return null;
        }

        String siguienteId = null;

        String sql = "SELECT MAX(CAST(id_cur AS UNSIGNED)) FROM cursos";

        try (Statement stmt = cc.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            int siguienteNumero = 1; 
            if (rs.next()) {
                int maxId = rs.getInt(1); 
                siguienteNumero = maxId + 1;
            }
            siguienteId = String.valueOf(siguienteNumero);

        } catch (SQLException e) {
            System.err.println("Error al generar el siguiente ID de curso: " + e.getMessage());
        }
        return siguienteId;
    }

    public void guardar() {

        try {
            if (jtxtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Es obligatorio el nombre");
                jtxtNombre.requestFocus();
            } else {
                String SqlInsert = "INSERT INTO cursos (id_cur, nom_cur) VALUES (?, ?)";
                PreparedStatement psd = cc.prepareStatement(SqlInsert);
                psd.setString(1, generarSiguienteId());
                psd.setString(2, jtxtNombre.getText());
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se Guardo Correctamente");
                    mostrar();
                    botonesInicio();
                    textosInicio();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error contactese con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminar() {
        try {
            if ((JOptionPane.showConfirmDialog(null,
                    "Desea eliminar el curso con nombre: '" + jtxtNombre.getText() + "'",
                    "Borrar Curso",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {

                String id=(String) jtblCursos.getValueAt(jtblCursos.getSelectedRow(), 0);
                System.out.println(id);
                String SqlDelete = "delete from cursos where id_cur='" +id+ "'";
                PreparedStatement psd = cc.prepareStatement(SqlDelete);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se elimino correctamente");
                    mostrar();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void editar() {
    try {
        int filaSeleccionada = jtblCursos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un curso para editar.", "Sin Selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoNombre = jtxtNombre.getText().trim();
        if (nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del curso no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
            jtxtNombre.requestFocus();
            return;
        }
        String id = jtblCursos.getValueAt(filaSeleccionada, 0).toString();
        String SqlUpdate = "UPDATE cursos SET nom_cur = ? WHERE id_cur = ?";
        PreparedStatement psd = cc.prepareStatement(SqlUpdate);
        psd.setString(1, nuevoNombre); 
        psd.setString(2, id);          
        
        int n = psd.executeUpdate();
        
        if (n > 0) {
            JOptionPane.showMessageDialog(null, "Se actualizó correctamente");
            mostrar(); 
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el curso para actualizar (ID: " + id + ").", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }

    } catch (SQLException ex){
        JOptionPane.showMessageDialog(null, "Error al actualizar el curso:\n" + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
    }
}

    public void cargarCampos() {
        jtblCursos.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (jtblCursos.getSelectedRow() != -1) {
                selectBots();
                textosNuevo();
                int fila = jtblCursos.getSelectedRow();
                jtxtNombre.setText(jtblCursos.getValueAt(fila, 1).toString());
            }
        });
    }

    public void botonesInicio() {
        jbntNuevo.setEnabled(true);
        jbntGuardar.setEnabled(false);
        jbntEditar.setEnabled(false);
        jbntEliminar.setEnabled(false);
        jbntCancelar.setEnabled(true);
    }

    public void textosInicio() {
        jtxtNombre.setEnabled(false);
        jtxtNombre.setText("");
    }

    public void botonesNuevo() {
        jbntNuevo.setEnabled(false);
        jbntGuardar.setEnabled(true);
        jbntEditar.setEnabled(false);
        jbntEliminar.setEnabled(false);
        jbntCancelar.setEnabled(true);
    }

    public void textosNuevo() {
        jtxtNombre.setEnabled(true);
    }

    public void selectBots() {
        jbntNuevo.setEnabled(true);
        jbntGuardar.setEnabled(false);
        jbntEditar.setEnabled(true);
        jbntEliminar.setEnabled(true);
        jbntCancelar.setEnabled(true);
    }

    public void clear() {
        jtxtNombre.setText("");
    }

    public void mostrar() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"id", "nombre"};
            String registros[] = new String[2];
            modelo.setColumnIdentifiers(titulos);
            String SqlSelect = "select * from cursos";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(SqlSelect);
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                modelo.addRow(registros);
            }
            jtblCursos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtNombre = new componentes.UTCNombre();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jbntNuevo = new javax.swing.JButton();
        jbntGuardar = new javax.swing.JButton();
        jbntEditar = new javax.swing.JButton();
        jbntEliminar = new javax.swing.JButton();
        jbntCancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblCursos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Nombre");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jbntNuevo.setText("Nuevo");
        jbntNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntNuevoActionPerformed(evt);
            }
        });

        jbntGuardar.setText("Guardar");
        jbntGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntGuardarActionPerformed(evt);
            }
        });

        jbntEditar.setText("Editar");
        jbntEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntEditarActionPerformed(evt);
            }
        });

        jbntEliminar.setText("Eliminar");
        jbntEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntEliminarActionPerformed(evt);
            }
        });

        jbntCancelar.setText("Cancelar");
        jbntCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbntCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbntCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jbntGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbntNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbntEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbntEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jbntNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbntGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbntEditar)
                .addGap(12, 12, 12)
                .addComponent(jbntEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbntCancelar)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jtblCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblCursos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbntGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jbntGuardarActionPerformed

    private void jbntEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jbntEliminarActionPerformed

    private void jbntEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbntEditarActionPerformed

    private void jbntNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntNuevoActionPerformed
        botonesNuevo();
        textosNuevo();
        clear();
    }//GEN-LAST:event_jbntNuevoActionPerformed

    private void jbntCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntCancelarActionPerformed
        botonesInicio();
        textosInicio();
        this.setVisible(false);
    }//GEN-LAST:event_jbntCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Alumnos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Alumnos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbntCancelar;
    private javax.swing.JButton jbntEditar;
    private javax.swing.JButton jbntEliminar;
    private javax.swing.JButton jbntGuardar;
    private javax.swing.JButton jbntNuevo;
    private javax.swing.JTable jtblCursos;
    private componentes.UTCNombre jtxtNombre;
    // End of variables declaration//GEN-END:variables
}
