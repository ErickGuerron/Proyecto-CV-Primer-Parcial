package componentes;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

public class UTCNombre extends JTextField {
    private static int limite = 15;

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

                if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                    e.consume();
                    return;
                }
                int len = getText().length();
                int sel = Math.abs(getSelectionEnd() - getSelectionStart());
                int restante = limite - (len - sel);

                if (restante <= 0) {
                    e.consume();
                    Toolkit.getDefaultToolkit().beep();
                    setInvalidStyle();
                    setToolTipText("No puede ingresar más de " + limite + " caracteres");
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

    @Override
    public void replaceSelection(String content) {
        if (content == null || content.isEmpty()) {
            super.replaceSelection(content);
            return;
        }

        String limpio = content.codePoints()
                .filter(cp -> Character.isLetter(cp) || Character.isWhitespace(cp))
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        if (limpio.isEmpty()) return;

        int len = getText().length();
        int sel = Math.abs(getSelectionEnd() - getSelectionStart());
        int restante = limite - (len - sel);

        if (restante <= 0) {
            Toolkit.getDefaultToolkit().beep();
            setInvalidStyle();
            setToolTipText("Se alcanzó el máximo de " + limite + " caracteres.");
            return;
        }

        if (limpio.length() > restante) {
            limpio = limpio.substring(0, restante);
            Toolkit.getDefaultToolkit().beep();
        }

        super.replaceSelection(limpio);
    }
    
    public void limitLetter(int limit){
        limite = limit;
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
