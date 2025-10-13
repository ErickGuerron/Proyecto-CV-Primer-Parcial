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

import interfaces.FormLogin;

/**
 *
 * @author ErickGuerron
 */
public class CLogin {
    
    public void validarUsuario(JTextField usuario, JPasswordField password, FormLogin loginForm){
    try{
        ResultSet rs= null;
        PreparedStatement psd = null;
        
        cuartouta.Conexion cc = new cuartouta.Conexion();
        
        String consulta ="select per_usu from usuarios where nom_usu=? and con_usu=?;";
        psd = cc.conectar().prepareStatement(consulta);
        
        String contra= String.valueOf(password.getPassword());
        
        psd.setString(1, usuario.getText());
        psd.setString(2, contra);
       
        rs = psd.executeQuery();
        
        if(rs.next()){
            String perfilUsuario = rs.getString("per_usu");

            JOptionPane.showMessageDialog(null, "Inicio de sesión correcto. Perfil: " + perfilUsuario);

            interfaces.Principal p = new interfaces.Principal(perfilUsuario); 
            p.setVisible(true);

            loginForm.dispose();
            
        } else {
            JOptionPane.showMessageDialog(null, "El usuario o contraseña es incorrecto","Credenciales inválidas", JOptionPane.ERROR_MESSAGE );
        }
    
    } catch(Exception e){
        JOptionPane.showMessageDialog(null, "Error al iniciar sesión: "+ e,"Error",JOptionPane.ERROR_MESSAGE);
    }
}
}