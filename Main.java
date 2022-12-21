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
                int[] coefficients = Parser.Parser.parseStringToCoefficients(window.inputTextField.getText());
                window.plotPane = Plotter.Plotter.getPlotPlaneFromCoefficients(coefficients);
                window.repaint();
                window.pack();
            }
        });
    }

}