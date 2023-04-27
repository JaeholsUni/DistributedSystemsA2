package com.unimelb;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.*;

public class whiteboard extends JPanel implements ActionListener {

    private static final int TIME_DELAY = 16;

    private ArrayList<Point> tempPoints;
    private IWhiteboardState localState;
    private JButton resetButton;
    private boolean drawing;
    private Timer timer;

    private colourDropdownPanel colours;


    public whiteboard(IWhiteboardState whiteboardState) {
        tempPoints = new ArrayList<>();
        drawing = false;
        this.localState = whiteboardState;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        this.colours = new colourDropdownPanel();
        add(colours, BorderLayout.NORTH);

        // Create a button to reset the list of points
        resetButton = new JButton("Reset Points");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the list of points and repaint the panel
                tempPoints.clear();

                try {
                    localState.clearElements();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                repaint();
            }
        });
        add(resetButton, BorderLayout.SOUTH);









        // Drawing logic
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
                    ArrayList<Point> newPoints = new ArrayList<>();
                    newPoints.addAll(tempPoints);
                    newStroke(newPoints, colours.getColour());
                    tempPoints.clear();

                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                if (drawing) {
                    // Draw a line segment between the last point and the current mouse position
                    tempPoints.add(evt.getPoint());

                }
            }
        });

        timer = new Timer(TIME_DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing to smooth out the line
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            renderElements(g2d, localState.getElementArray());
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        renderLine(g2d, Color.BLUE, 3, tempPoints);
    }

    private void renderLine(Graphics2D g2d, Color color, float thickness, ArrayList<Point> pointSet) {
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness));

        // Draw the line using the list of points
        for (int i = 0; i < pointSet.size() - 1; i++) {
            Point p1 = pointSet.get(i);
            Point p2 = pointSet.get(i + 1);
            g2d.draw(new Line2D.Double(p1, p2));
        }
    }

    private void renderElements(Graphics2D g2d, ArrayList<renderElement> elements) {

        try {
            for (int i=0; i < elements.size(); i++) {
                renderElement el = elements.get(i);
                if (el.getType().equals(renderTypes.STROKE)) {
                    renderLine(g2d, el.getColor(), el.getStrokeWidth(), el.getPoints());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }


    private void newStroke(ArrayList<Point> points, Color colour) {
        try {
            localState.addElement(new renderElement(points, colour, 3, renderTypes.STROKE));

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
