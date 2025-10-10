package cuartouta;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conectar = null;

    public Connection conectar() {

        try {
            //para cualquier conexion de base de datos se necesitan los parametros: servidor, usuario, y la contraseña
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://62nDJ5drqeHoxFq.root:ziF0LH47"
                    + "kuPrQGEa@gateway01.us-east-1.prod.aws.tidbcloud.com:4000"
                    + "/cuartouta?sslMode=VERIFY_IDENTITY");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return conectar;
    }

}