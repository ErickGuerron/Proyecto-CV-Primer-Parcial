
package componentes;

import cuartouta.Conexion;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class UTCComboCursos extends JComboBox {
    DefaultComboBoxModel modelo;
    cuartouta.Conexion cc = new cuartouta.Conexion();

    UTCComboCursos(){
        this.setModel(modelo);
        cargarCursos();       
    }
    
    private void cargarCursos(){
    try {
            Connection cn = cc.conectar();
            String SQLSelect = "SELECT nom_cur FROM cursos";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(SQLSelect);
             while (rs.next()) {
                modelo.addElement(rs.getString("nom_cur"));
            }
             rs.close();
            psd.close();
            cn.close();
        } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: Contactese con el administrador", "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
}
