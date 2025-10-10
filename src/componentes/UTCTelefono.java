
package componentes;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;
import javax.swing.border.LineBorder;

public class UTCTelefono extends JTextField {

    public UTCTelefono() {
        configurarValidacion();
    }

    private void configurarValidacion() {
        ToolTipManager.sharedInstance().setInitialDelay(0);

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();

                if (!Character.isDigit(c) || getText().length() >= 10) {
                    e.consume();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String texto = getText().trim();

                
                if (texto.isEmpty()) {
                    setDefaultStyle();
                    setToolTipText("Ingrese su número de teléfono (debe empezar con 09)");
                    return;
                }

                
                if (validarTelefono(texto)) {
                    setValidStyle();
                    setToolTipText("Número de teléfono válido");
                } else {
                    setInvalidStyle();
                    setToolTipText("El número debe empezar con 09 y tener 10 dígitos");
                }
            }
        });
    }

    private boolean validarTelefono(String numero) {
        return numero.matches("^09\\d{8}$");
        
    }

    
    private void setDefaultStyle() {
        setBorder(new LineBorder(Color.GRAY));
    }

    
    private void setValidStyle() {
        setBorder(new LineBorder(Color.GREEN));
    }

    private void setInvalidStyle() {
        setBorder(new LineBorder(Color.RED));
    }
    
    public Long getTextAsNumber() {
        if (getText() == null || getText().isEmpty()) {
            return 0L;
        }
        return Long.valueOf(getText());
    }

    public void setTextAsNumber(Long valor) {
        setText(String.valueOf(valor));
    }
}