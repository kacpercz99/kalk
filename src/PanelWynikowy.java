import javax.swing.*;
import java.awt.*;

public class PanelWynikowy extends JPanel {
    public static JLabel wynik;
    public static JLabel pamiec;

    public PanelWynikowy() {
        setLayout(new GridLayout(2,1));
        wynik = new JLabel(Calculator.wartoscWyniku);
        pamiec = new JLabel("Pamiec: " + Calculator.wartoscPamieci);
        wynik.setHorizontalAlignment(JLabel.CENTER);
        pamiec.setHorizontalAlignment(JLabel.CENTER);
        wynik.setFont(Calculator.czcionka);
        pamiec.setFont(Calculator.czcionka);
        pamiec.setVisible(false);
        add(PanelWynikowy.wynik);
        add(PanelWynikowy.pamiec);
    }
}
