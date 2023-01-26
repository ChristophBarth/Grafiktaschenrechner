import Parser.Parser;
import Plotter.PlotPlane;
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
        window.setResizable(false);
        window.setVisible(true);

        window.plotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                String input = window.inputTextField.getText();
                int validity = Parser.getValidity(input);

                if(validity == 0){

                    window.plotPane.removeAll();
                    double[] coefficients = Parser.parseStringToCoefficients(window.inputTextField.getText());
                    PlotPlane res = new Plotter.PlotPlane(coefficients);
                    res.setSize(window.plotPane.getSize());
                    window.plotPane.add(res);
                    window.revalidate();
                    window.repaint();
                }else{

                    window.plotPane.removeAll();
                    JPanel messagePanel = new MessagePanel(validity);
                    messagePanel.setSize(window.plotPane.getSize());
                    window.plotPane.add(messagePanel);
                    window.revalidate();
                    window.repaint();

                }

            }
        });
    }

}