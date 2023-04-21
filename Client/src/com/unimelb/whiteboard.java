package com.unimelb;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;

public class whiteboard extends JPanel {

    private ArrayList<Point> tempPoints;
    private ArrayList<renderElement> elements;
    private JButton resetButton;
    private boolean drawing;

    public whiteboard() {
        tempPoints = new ArrayList<>();
        drawing = false;

        // Create a button to reset the list of points
        resetButton = new JButton("Reset Points");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the list of points and repaint the panel
                tempPoints.clear();
                repaint();
            }
        });
        add(resetButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    // Start drawing a line
                    tempPoints.add(evt.getPoint());
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    // Stop drawing a line
                    tempPoints.add(evt.getPoint());
                    drawing = false;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                if (drawing) {
                    // Draw a line segment between the last point and the current mouse position
                    tempPoints.add(evt.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing to smooth out the line
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set the color and thickness of the line
        g2d.setColor(Color.BLUE);
        g2d.setStroke(new BasicStroke(3));

        // Draw the line using the list of points
        for (int i = 0; i < tempPoints.size() - 1; i++) {
            Point p1 = tempPoints.get(i);
            Point p2 = tempPoints.get(i + 1);
            g2d.draw(new Line2D.Double(p1, p2));
        }
    }
}
