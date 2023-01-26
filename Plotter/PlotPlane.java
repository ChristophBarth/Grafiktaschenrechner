package Plotter;

import Parser.Parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlotPlane extends JPanel implements MouseWheelListener {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static int X_TICK = 1;
    private static int Y_TICK = 1;

    private double maxX = 10;
    private double minX = -10;
    private double maxY = 10;
    private double minY = -10;


    private int xDragStart, yDragStart;
    private boolean isDragging;

    private static final double ZOOM_FACTOR = 1.1;

    private double[] Coefs;

    public PlotPlane(double[] coefs) {
        this.Coefs = coefs;

        addMouseWheelListener(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    xDragStart = e.getX();
                    yDragStart = e.getY();
                    isDragging = true;
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                isDragging = false;
                xDragStart = e.getX();
                yDragStart = e.getY();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    int dx = e.getX() - xDragStart;
                    int dy = e.getY() - yDragStart;
                    xDragStart = e.getX();
                    yDragStart = e.getY();
                    minX -= dx * (maxX - minX) / WIDTH;
                    maxX -= dx * (maxX - minX) / WIDTH;
                    minY += dy * (maxY - minY) / HEIGHT;
                    maxY += dy * (maxY - minY) / HEIGHT;
                    repaint();
                }
            }
        });
    }

    @Override
    public void repaint(){
        //super.repaint();
        Graphics g = getGraphics();
        if(g != null)
            paintComponent(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Antialiasing für sauberere Linien
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Zeichnet die Gitterlinien
        g2d.setColor(Color.LIGHT_GRAY);

        Y_TICK = (int)(maxY - minY) / 10;
        X_TICK = (int)(maxX - minX) / 10;
        if(X_TICK < 1)
            X_TICK = 1;
        if(Y_TICK < 1)
            Y_TICK = 1;

        for (int x = 0; x < maxX; x += X_TICK) {
            g2d.drawString(""+x, xToScreen(x), yToScreen(0));
            g2d.drawLine(xToScreen(x), yToScreen(minY), xToScreen(x), yToScreen(maxY));
        }
        for (int y = 0; y < maxY; y += Y_TICK) {
            g2d.drawString(""+y, xToScreen(0), yToScreen(y));
            g2d.drawLine(xToScreen(minX), yToScreen(y), xToScreen(maxX), yToScreen(y));
        }

        for (int x = 0; x > minX; x -= X_TICK) {

            g2d.drawString(""+x, xToScreen(x), yToScreen(0));
            g2d.drawLine(xToScreen(x), yToScreen(minY), xToScreen(x), yToScreen(maxY));
        }
        for (int y = 0; y > minY; y -= Y_TICK) {
            g2d.drawString(""+y, xToScreen(0), yToScreen(y));
            g2d.drawLine(xToScreen(minX), yToScreen(y), xToScreen(maxX), yToScreen(y));
        }

        // Zeichnet die x- und y-Achsen
        g2d.setColor(Color.WHITE);
        g2d.drawLine(xToScreen(minX), yToScreen(0), xToScreen(maxX), yToScreen(0));
        g2d.drawLine(xToScreen(0), yToScreen(minY), xToScreen(0), yToScreen(maxY));

        // Zeichnet die Funktion
        g2d.setColor(Color.RED);

        double incr = (maxX-minX)/400;

        for (double x = minX; x <= maxX; x += incr) {
            double y = Poly(x);
            if (Double.isFinite(y)) {
                int x1 = xToScreen(x);
                int y1 = yToScreen(y);
                int x2 = xToScreen(x + incr);
                int y2 = yToScreen(Poly(x + incr));
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }

    private int xToScreen(double x) {
        return (int) ((x - minX) / (maxX - minX) * WIDTH);
    }

    private int yToScreen(double y) {
        return HEIGHT - (int) ((y - minY) / (maxY - minY) * HEIGHT);
    }
    private double screenToX(double x){
        return minX + (maxX - minX) * x/(double)WIDTH;
    }
    private double screenToY(double y){
        return minY - (maxY - minY) * (y/(double)HEIGHT);
    }

    public double Poly(double x){
        double res = 0;
        for(int i = 0; i< Coefs.length; i++){
            res += Coefs[i] *Math.pow(x, i);
        }
        return res;

    }

    public static void main(String[] args) {

        // Erstellt ein JFrame und fügt den FunctionPlotter hinzu

        double[] polynomial = Parser.parseStringToCoefficients("4+1.1x^2");

        JFrame frame = new JFrame("Polynomial Plotter");
        frame.add(new PlotPlane(polynomial));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int units = e.getUnitsToScroll();
        if(units == 0)
            return;
        double x = (maxX + minX) / 2.0;
        double y = (maxY + minY) / 2.0;
        if (units > 0) {
            minX = x - (x - minX) * ZOOM_FACTOR;
            maxX = x + (maxX - x) * ZOOM_FACTOR;
            minY = y - (y - minY) * ZOOM_FACTOR;
            maxY = y + (maxY - y) * ZOOM_FACTOR;
        } else if (units < 0) {
            minX = x - (x - minX) / ZOOM_FACTOR;
            maxX = x + (maxX - x) / ZOOM_FACTOR;
            minY = y - (y - minY) / ZOOM_FACTOR;
            maxY = y + (maxY - y) / ZOOM_FACTOR;
        }

        repaint();
        updateUI();
    }
}
