package componentes;

import cuartouta.Conexion;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.sql.*;
import javax.swing.JOptionPane;

public class UTCComboCursos extends JComboBox<String> {

    private DefaultComboBoxModel<String> modelo;  // inicializa aquí
    private Conexion cc = new Conexion();

    // Constructor público
    public UTCComboCursos() {
        modelo = new DefaultComboBoxModel<>();  // <--- inicialización
        this.setModel(modelo);
        cargarCursos();       
    }
    
    private void cargarCursos() {
        String SQLSelect = "SELECT nom_cur FROM cursos";

        try (Connection cn = cc.conectar();
             Statement psd = cn.createStatement();
             ResultSet rs = psd.executeQuery(SQLSelect)) {

            while (rs.next()) {
                modelo.addElement(rs.getString("nom_cur"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,
                "Error: Contáctese con el administrador",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}