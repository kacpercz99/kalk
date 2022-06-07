import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Klawiatura extends JPanel {

    public Klawiatura() {
        String[] nazwy = {
                "MC", "M+", "M-", "\u2190",
                "%", "CE", "C", "/",
                "7", "8", "9", "*",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "\u221A", "0", ".", "="
        };

        setLayout(new GridLayout(6, 4));

        JButton[] klawa = new JButton[nazwy.length];
        for (int i = 0; i < klawa.length; i++) {
            klawa[i] = new JButton(nazwy[i]);
            klawa[i].addActionListener(new ActionListenerKlawiatury(nazwy[i]));
            klawa[i].setFont(Calculator.czcionka);
            klawa[i].setContentAreaFilled(false);
            klawa[i].setFocusPainted(false);
            add(klawa[i]);
        }
    }

    private record ActionListenerKlawiatury(String ktoryPrzycisk) implements ActionListener {
        private static String symbol = "";
        private static boolean errorFlag = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (errorFlag) {
                Calculator.wartoscWyniku = "0";
                errorFlag = false;
            }

            switch (ktoryPrzycisk) {
                case "0" -> {
                    if (!Calculator.wartoscWyniku.equals("0")) Calculator.wartoscWyniku += "0";
                }
                case "." -> {
                    if (!Calculator.wartoscWyniku.contains(".")) Calculator.wartoscWyniku += ".";
                }
                case "C" -> {
                    Calculator.wartoscWyniku = "0";
                    Calculator.poprzednik = 0.0d;
                }
                case "CE" -> Calculator.wartoscWyniku = "0";

                case "\u2190" -> {
                    if (Calculator.wartoscWyniku.length() == 1) {
                        Calculator.wartoscWyniku = "0";
                    } else if (Calculator.wartoscWyniku.length() > 1) {
                        Calculator.wartoscWyniku = Calculator.wartoscWyniku.substring(0, Calculator.wartoscWyniku.length() - 1);
                    }
                }
                case "M+" ->
                        Calculator.wartoscPamieci = String.valueOf((Double.parseDouble(Calculator.wartoscPamieci) + Double.parseDouble(Calculator.wartoscWyniku)));
                case "M-" ->
                        Calculator.wartoscPamieci = String.valueOf((Double.parseDouble(Calculator.wartoscPamieci) - Double.parseDouble(Calculator.wartoscWyniku)));
                case "MC" -> Calculator.wartoscPamieci = "0";
                case "\u221A" -> {
                    if (Double.parseDouble(Calculator.wartoscWyniku) > 0.0d) {
                        Calculator.wartoscWyniku = String.valueOf(Math.sqrt(Double.parseDouble(Calculator.wartoscWyniku)));
                    } else {
                        Calculator.wartoscWyniku = "BŁĄD - NaN";
                        errorFlag = true;
                    }
                }
                case "+", "-", "*", "/" -> {
                    Calculator.poprzednik = Double.parseDouble(Calculator.wartoscWyniku);
                    Calculator.wartoscWyniku = "0";
                    symbol = ktoryPrzycisk;
                }
                case "%" -> {
                    if (!symbol.equals("")) {
                        if (symbol.equals("/") || symbol.equals("*")) {
                            Calculator.wartoscWyniku = String.valueOf((Double.parseDouble(Calculator.wartoscWyniku) / 100));
                        } else {
                            Calculator.wartoscWyniku = String.valueOf((Calculator.poprzednik / 100) * Double.parseDouble(Calculator.wartoscWyniku));
                        }
                    }
                }
                case "=" -> {
                    switch (symbol) {
                        case "+" ->
                                Calculator.wartoscWyniku = String.valueOf(Double.parseDouble(Calculator.wartoscWyniku) + Calculator.poprzednik);
                        case "-" ->
                                Calculator.wartoscWyniku = String.valueOf(Calculator.poprzednik - Double.parseDouble(Calculator.wartoscWyniku));
                        case "*" ->
                                Calculator.wartoscWyniku = String.valueOf(Calculator.poprzednik * Double.parseDouble(Calculator.wartoscWyniku));
                        case "/" -> {
                            if (Calculator.wartoscWyniku.matches("0\\.?0*")) {
                                Calculator.wartoscWyniku = "BŁĄD - X/0";
                                errorFlag = true;
                            } else {
                                Calculator.wartoscWyniku = String.valueOf(Calculator.poprzednik / Double.parseDouble(Calculator.wartoscWyniku));
                            }
                        }
                    }
                    symbol = "";
                }
                default -> {
                    if (Calculator.wartoscWyniku.equals("0")) Calculator.wartoscWyniku = ktoryPrzycisk;
                    else Calculator.wartoscWyniku += ktoryPrzycisk;
                }
            }

            PanelWynikowy.wynik.setText(Calculator.wartoscWyniku);
            PanelWynikowy.pamiec.setText("Pamiec:" + Calculator.wartoscPamieci);

            if (Calculator.wartoscPamieci.equals("0")) PanelWynikowy.pamiec.setVisible(false);
            else if (!PanelWynikowy.pamiec.isVisible()) {
                PanelWynikowy.pamiec.setVisible(true);
            }
        }
    }
}
