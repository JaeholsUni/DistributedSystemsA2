package com.unimelb;
import com.unimelb.renderElements.ellipse;
import com.unimelb.renderElements.freehandLine;
import com.unimelb.renderElements.rectangle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

import static com.unimelb.renderElements.renderTypes.*;

public class whiteboard extends JPanel implements ActionListener {

    private static final int TIME_DELAY = 16;

    private IRenderable tempDrawingItem;
    private IWhiteboardState localState;
    private JButton resetButton;
    private boolean drawing;
    private Timer timer;

    private colourDropdownPanel colours;
    private drawingTypeDropdownPanel drawTypes;


    public whiteboard(IWhiteboardState whiteboardState) {
        drawing = false;
        this.localState = whiteboardState;

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        this.colours = new colourDropdownPanel();
        this.drawTypes = new drawingTypeDropdownPanel();

        JPanel topControlPanel = new JPanel();
        topControlPanel.setLayout(new BoxLayout(topControlPanel, BoxLayout.X_AXIS));
        topControlPanel.add(colours);
        topControlPanel.add(drawTypes);

        add(topControlPanel, BorderLayout.NORTH);

        // Create a button to reset the list of points
        resetButton = new JButton("Reset Points");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear the list of points and repaint the panel

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
                    switch (drawTypes.getSelectedType()) {
                        case "Freehand":
                            tempDrawingItem = new freehandLine(new ArrayList<>(), colours.getColour(), 3, STROKE);
                            tempDrawingItem.updateDrawing(evt.getPoint());
                            break;
                        case "Rectangle":
                            tempDrawingItem = new rectangle(new ArrayList<>(), colours.getColour(), 3, RECTANGLE);
                            tempDrawingItem.updateDrawing(evt.getPoint());
                            break;
                        case "Ellipse":
                            tempDrawingItem = new ellipse(new ArrayList<>(), colours.getColour(), 3, ELLIPSE);
                            tempDrawingItem.updateDrawing(evt.getPoint());
                            break;
                    }
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    tempDrawingItem.updateDrawing(evt.getPoint());
                    drawing = false;

                    addRenderElementToRemote(tempDrawingItem);
                    tempDrawingItem = null;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                if (drawing) {
                    tempDrawingItem.updateDrawing(evt.getPoint());
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

        if (Objects.nonNull(tempDrawingItem)) {
            tempDrawingItem.renderSelf(g2d);
        }
    }

    private void renderElements(Graphics2D g2d, ArrayList<IRenderable> elements) {
        try {
            for (int i=0; i < elements.size(); i++) {
                IRenderable el = elements.get(i);
                el.renderSelf(g2d);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private void addRenderElementToRemote(IRenderable renderable) {
        try {
            switch (renderable.getType()) {
                case STROKE:
                    localState.addElement(new freehandLine(renderable));
                    break;
                case RECTANGLE:
                    localState.addElement(new rectangle(renderable));
                    break;
                case ELLIPSE:
                    localState.addElement(new ellipse(renderable));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
