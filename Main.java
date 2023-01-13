import Parser.Parser;
import UI.MessagePanel;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        UI.MainWindow window = new UI.MainWindow();
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        SwingUtilities.updateComponentTreeUI(window.getContentPane());
        window.setVisible(true);

        window.plotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                String input = window.inputTextField.getText();
                int validity = Parser.getValidity(input);

                if(validity == 0){
                    int[] coefficients = Parser.parseStringToCoefficients(window.inputTextField.getText());
                    window.plotPane = Plotter.Plotter.getPlotPlaneFromCoefficients(coefficients);
                    window.repaint();
                    window.pack();
                }else{
                    window.plotPane = new MessagePanel("Fehler " + validity);
                    System.out.println("hallo");
                }

            }
        });
    }

}