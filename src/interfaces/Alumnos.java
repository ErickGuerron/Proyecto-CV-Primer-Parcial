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
public class Alumnos extends javax.swing.JInternalFrame {

    Conexion cn = new Conexion();
    java.sql.Connection cc = cn.conectar();

    /**
     * Creates new form Alumnos
     */
    public Alumnos() {
        initComponents();
        mostrarEstudiantes();
        cargarCampos();
        botonesInicio();
        textosInicio();
    }

    public void guardar() {

        try {
            if (jtxtCedula.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Es obligatorio la cedula");
                jtxtCedula.requestFocus();
            } else if (jtxtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Es obligatorio el nombre");
                jtxtNombre.requestFocus();
            } else if (jtxtApellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Es obligatorio el apellido");
                jtxtApellido.requestFocus();
            } else {
                String SqlInsert = "insert into estudiantes values(?,?,?,?,?)";
                PreparedStatement psd = cc.prepareStatement(SqlInsert);
                psd.setString(1, jtxtCedula.getText());
                psd.setString(2, jtxtNombre.getText());
                psd.setString(3, jtxtApellido.getText());
                psd.setString(4, jtxtDireccion.getText().trim().isEmpty() ? "S/N" : jtxtDireccion.getText());
                psd.setString(5, jtxtTelefono.getText().trim().isEmpty() ? "0000000000" : jtxtTelefono.getText());
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se Guardo Correctamente");
                    mostrarEstudiantes();
                    botonesInicio();
                    textosInicio();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error contactese con el administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eliminarEstudiante() {
        try {
            if ((JOptionPane.showConfirmDialog(null,
                    "Desea eliminar al estudiante con cedula: '" + jtxtCedula.getText() + "'",
                    "Borrar Estudiante",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {

                String SqlDelete = "delete from estudiantes where id_est='" + jtxtCedula.getText() + "'";
                PreparedStatement psd = cc.prepareStatement(SqlDelete);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se elimino correctamente");
                    mostrarEstudiantes();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void editarEstudiante() {
        try {
            String SqlUpdate = "update estudiantes set nom_est='" + jtxtNombre.getText()
                    + "',ape_est='" + jtxtApellido.getText() + "',"
                    + "dir_est='" + jtxtDireccion.getText() + "',"
                    + "tel_est='" + jtxtTelefono.getText() + "'"
                    + " where id_est='" + jtxtCedula.getText() + "'";
            PreparedStatement psd;
            psd = cc.prepareStatement(SqlUpdate);
            int n = psd.executeUpdate();
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                mostrarEstudiantes();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarCampos() {
        jtblAlumnos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblAlumnos.getSelectedRow() != -1) {
                    selectBotsEst();
                    textosNuevo();
                    int fila = jtblAlumnos.getSelectedRow();
                    jtxtCedula.setText(jtblAlumnos.getValueAt(fila, 0).toString());
                    jtxtNombre.setText(jtblAlumnos.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblAlumnos.getValueAt(fila, 2).toString());
                    jtxtDireccion.setText(jtblAlumnos.getValueAt(fila, 3).toString());
                    jtxtTelefono.setText(jtblAlumnos.getValueAt(fila, 4).toString());
                }
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
        jtxtCedula.setEnabled(false);
        jtxtNombre.setEnabled(false);
        jtxtApellido.setEnabled(false);
        jtxtDireccion.setEnabled(false);
        jtxtTelefono.setEnabled(false);
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDireccion.setText("");
        jtxtTelefono.setText("");
    }

    public void botonesNuevo() {
        jbntNuevo.setEnabled(false);
        jbntGuardar.setEnabled(true);
        jbntEditar.setEnabled(false);
        jbntEliminar.setEnabled(false);
        jbntCancelar.setEnabled(true);
    }

    public void textosNuevo() {
        jtxtCedula.setEnabled(true);
        jtxtNombre.setEnabled(true);
        jtxtApellido.setEnabled(true);
        jtxtDireccion.setEnabled(true);
        jtxtTelefono.setEnabled(true);
    }

    public void selectBotsEst() {
        jbntNuevo.setEnabled(true);
        jbntGuardar.setEnabled(false);
        jbntEditar.setEnabled(true);
        jbntEliminar.setEnabled(true);
        jbntCancelar.setEnabled(true);
    }

    public void clear() {
        jtxtCedula.setText("");
        jtxtNombre.setText("");
        jtxtApellido.setText("");
        jtxtDireccion.setText("");
        jtxtTelefono.setText("");
    }

    public void mostrarEstudiantes() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String titulos[] = {"cedula", "nombre", "apellido", "direccion", "telefono"};
            String registros[] = new String[5];
            modelo.setColumnIdentifiers(titulos);
            String SqlSelect = "select * from estudiantes";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(SqlSelect);
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                modelo.addRow(registros);
            }
            jtblAlumnos.setModel(modelo);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtxtCedula = new componentes.UTCcedula();
        jtxtTelefono = new componentes.UTCTelefono();
        jtxtDireccion = new componentes.UTCDireccion();
        jtxtNombre = new componentes.UTCNombre();
        jtxtApellido = new componentes.UTCNombre();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jbntNuevo = new javax.swing.JButton();
        jbntGuardar = new javax.swing.JButton();
        jbntEditar = new javax.swing.JButton();
        jbntEliminar = new javax.swing.JButton();
        jbntCancelar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblAlumnos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Cedula");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido");

        jLabel4.setText("Direccion");

        jLabel5.setText("Telefono");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(126, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel1))
                    .addComponent(jtxtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jtxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
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

        jtblAlumnos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jtblAlumnos);

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
        eliminarEstudiante();
    }//GEN-LAST:event_jbntEliminarActionPerformed

    private void jbntEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbntEditarActionPerformed
        editarEstudiante();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JTable jtblAlumnos;
    private componentes.UTCNombre jtxtApellido;
    private componentes.UTCcedula jtxtCedula;
    private componentes.UTCDireccion jtxtDireccion;
    private componentes.UTCNombre jtxtNombre;
    private componentes.UTCTelefono jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
