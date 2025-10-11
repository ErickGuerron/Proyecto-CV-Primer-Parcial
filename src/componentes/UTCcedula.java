package componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.ToolTipManager;

public class UTCcedula extends JTextField {

    public UTCcedula() {
        super();
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
                    setToolTipText("Ingrese su número de cédula ecuatoriana");
                    return;
                }

                String resultado = validarNumeroCedula(texto);
                if (resultado != null) {
                    setValidStyle();
                    setToolTipText("Cédula válida");
                } else {
                    setInvalidStyle();
                    setToolTipText("Cédula no válida");
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

    public static String validarNumeroCedula(String cedula) {
        try {
            if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d{10}")) {
                throw new IllegalArgumentException();
            }

            String CEDULA_REGEX = "^(0[1-9]|1[0-9]|2[0-4])[0-6]\\d{7}$";
            if (!Pattern.matches(CEDULA_REGEX, cedula)) {
                throw new IllegalArgumentException();
            }

            int[] coeficientes = {2, 1, 2, 1, 2, 1, 2, 1, 2};
            int suma = 0;

            for (int i = 0; i < 9; i++) {
                int valor = Character.getNumericValue(cedula.charAt(i)) * coeficientes[i];
                if (valor >= 10) valor -= 9;
                suma += valor;
            }

            int verificadorCalculado = (10 - (suma % 10)) % 10;
            int verificadorReal = Character.getNumericValue(cedula.charAt(9));

            if (verificadorCalculado != verificadorReal) {
                throw new IllegalArgumentException();
            }

            return cedula;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}