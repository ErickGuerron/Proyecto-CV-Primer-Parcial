/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package componentes;

    import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Un DocumentFilter que solo permite la inserción de letras y espacios.
 * Rechaza números y otros caracteres especiales.
 */
public class JLetterTextField extends JTextField {

    /**
     * Constructor por defecto.
     */
    public JLetterTextField() {
        super();
        initializeFilter();
    }

    /**
     * Constructor que permite definir el número de columnas.
     * @param columns El número de columnas a mostrar.
     */
    public JLetterTextField(int columns) {
        super(columns);
        initializeFilter();
    }

    /**
     * Método privado que aplica el DocumentFilter al componente.
     */
    private void initializeFilter() {
        AbstractDocument doc = (AbstractDocument) this.getDocument();
        doc.setDocumentFilter(new LetterFilter());
    }

    /**
     * Clase interna, privada y estática que implementa la lógica del filtro.
     */
    private static class LetterFilter extends DocumentFilter {
        
        // Expresión regular que permite solo letras (mayúsculas y minúsculas) y espacios.
        private static final String LETTER_REGEX = "[a-zA-Z\\s]*";

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches(LETTER_REGEX)) {
                super.insertString(fb, offset, string, attr);
            } else {
                // Opcional: emitir un sonido de "bip" si el carácter no es válido.
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null && text.matches(LETTER_REGEX)) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                // Opcional: emitir un sonido de "bip" si el carácter no es válido.
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}


