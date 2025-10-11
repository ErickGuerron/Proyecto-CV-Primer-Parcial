/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
/**
 *
 * @author maya2
 */
public class JDKFondo extends JDesktopPane {

    private Image imagenFondo;

    public JDKFondo() {
        // Constructor por defecto, no carga ninguna imagen
    }

    // Constructor que recibe la ruta de la imagen
    public JDKFondo(String rutaImagen) {
        try {
            // Carga la imagen usando getResource para que funcione dentro del JAR
            imagenFondo = ImageIO.read(getClass().getResource(rutaImagen));
        } catch (IOException ex) {
            System.err.println("No se pudo cargar la imagen de fondo: " + rutaImagen);
            ex.printStackTrace();
        }
    }
    
    // Método para establecer la imagen de fondo después de la creación
    public void setImagenFondo(String rutaImagen) {
        try {
            imagenFondo = ImageIO.read(getClass().getResource(rutaImagen));
            repaint(); // Vuelve a dibujar el componente con la nueva imagen
        } catch (IOException ex) {
            System.err.println("No se pudo cargar la imagen de fondo: " + rutaImagen);
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Llama al método paintComponent de la clase padre para que dibuje el fondo por defecto
        super.paintComponent(g);

        // Si hay una imagen cargada, la dibuja
        if (imagenFondo != null) {
            // Dibuja la imagen para que ocupe todo el tamaño del JDesktopPane
            // Esto hace que la imagen se estire o encoja según el tamaño de la ventana
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
