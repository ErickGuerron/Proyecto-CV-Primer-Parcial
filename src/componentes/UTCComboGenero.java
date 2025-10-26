package componentes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class UTCComboGenero extends JComboBox<String> {

    private Border bordeOriginal; 

    public UTCComboGenero() {
        super(new String[]{"Seleccione...", "Masculino", "Femenino"});
        setEditable(false);
        bordeOriginal = getBorder();
        addItemListener(e -> actualizarBorde());
        setBorder(bordeOriginal);
    }

    public String obtenerGeneroSeleccionado() {
        return (String) getSelectedItem();
    }

    public boolean esSeleccionValida() {
        String selected = (String) getSelectedItem();
        return selected != null && !selected.equals("Seleccione...");
    }

    public void actualizarBorde() {
        if (esSeleccionValida()) {
            setBorder(new LineBorder(Color.GREEN, 2));
        } else {
            setBorder(new LineBorder(Color.RED, 2));
        }
    }
     public void restaurarBordeOriginal() {
        setBorder(bordeOriginal);
    }
}
