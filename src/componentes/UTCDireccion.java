package componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

public class UTCDireccion extends JTextField {

    private static final int LIMITE = 15;

    public UTCDireccion() {
        super();
        configurarValidacion();
    }

    private void configurarValidacion() {
        ToolTipManager.sharedInstance().setInitialDelay(0);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar(); 
                if (!Character.isLetterOrDigit(c) && c != ' ' && c != '.' && c != ',' && c != '#' && c != '-') {
                    e.consume();
                    setInvalidStyle();
                    setToolTipText("Caracter no permitido en dirección");
                    return;
                } 
                if (getText().length() >= LIMITE) {
                    e.consume();
                    setInvalidStyle();
                    setToolTipText("No puede ingresar más de " + LIMITE + " caracteres");
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String texto = getText().trim();

                if (texto.isEmpty()) {
                    setDefaultStyle();
                    setToolTipText("Ingrese la dirección del estudiante");
                    return;
                }

                if (texto.length() > LIMITE) {
                    setInvalidStyle();
                    setToolTipText("La dirección supera el límite permitido (" + LIMITE + " caracteres)");
                } else {
                    setValidStyle();
                    setToolTipText("Dirección válida");
                }
            }
        });
    } 
    private void setValidStyle() {
        setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
    }

    private void setInvalidStyle() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    private void setDefaultStyle() {
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    }
}
