/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuartouta;

import java.sql.*;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author ErickGuerron
 */
public class CLogin {
    
    public void validarUsuario(JTextField usuario, JPasswordField password){
        try{
            ResultSet rs= null;
            PreparedStatement psd = null;
            
            cuartouta.Conexion cc = new cuartouta.Conexion();
            
            String consulta ="select * from usuarios where usuarios.nom_usu=(?) and usuarios.con_usu=(?);";
            psd = cc.conectar().prepareStatement(consulta);
            
            String contra= String.valueOf(password.getPassword());
            
            psd.setString(1, usuario.getText());
            psd.setString(2, contra);
           
            rs = psd.executeQuery();
            
            if(rs.next()){
                JOptionPane.showMessageDialog(null, "Se ha iniciado session correctamente");
                interfaces.Principal p = new interfaces.Principal();
                p.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(null, "El usuario o contraseña es incorrecto","Credenciales invalidas", JOptionPane.ERROR_MESSAGE );
            }
        
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al iniciar session: "+ e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
