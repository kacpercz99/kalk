import javax.swing.*;
import java.awt.*;

public class Calculator extends JFrame {
    public static String wartoscWyniku = "0";
    public static String wartoscPamieci = "0";
    public static Double poprzednik = 0.0d;
    public static final Font czcionka = new Font("Arial", Font.BOLD, 20);
    public Calculator() {
        super("Kalkulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        PanelWynikowy panelWynikowy = new PanelWynikowy();
        Klawiatura klawiatura = new Klawiatura();

        add(panelWynikowy, BorderLayout.PAGE_START);
        add(klawiatura, BorderLayout.CENTER);
        setVisible(true);
    }
}
