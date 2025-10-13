package componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

public class UTCNombre extends JTextField {

    public UTCNombre() {
        super();
        configurarValidacion();
    }

    private void configurarValidacion() {
        ToolTipManager.sharedInstance().setInitialDelay(0);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String texto = getText().trim();

                if (texto.isEmpty()) {
                    setDefaultStyle();
                    setToolTipText("Este campo es obligatorio.");
                } else {
                    setValidStyle();
                    setToolTipText("Campo válido.");
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