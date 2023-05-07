package com.unimelb;
import com.unimelb.renderElements.*;

import java.awt.*;
import java.awt.event.*;
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
    private boolean typing;
    private Timer timer;
    private ArrayList<String> testArray = new ArrayList<>();

    private colourDropdownPanel colours;
    private drawingTypeDropdownPanel drawTypes;
    private chatPanel chatPanel;


    public whiteboard(IWhiteboardState whiteboardState) {
        drawing = false;
        typing = false;
        this.localState = whiteboardState;
        this.setFocusable(true);


        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        this.colours = new colourDropdownPanel();
        this.drawTypes = new drawingTypeDropdownPanel();
        this.chatPanel = new chatPanel(whiteboardState);

        JPanel topControlPanel = new JPanel();
        topControlPanel.setLayout(new BoxLayout(topControlPanel, BoxLayout.X_AXIS));
        topControlPanel.add(colours);
        topControlPanel.add(drawTypes);

        JPanel chatWindow = new JPanel();
        chatWindow.add(chatPanel);

        add(topControlPanel, BorderLayout.NORTH);
        add(chatWindow, BorderLayout.WEST);

        // Create a button to reset the list of points
        resetButton = new JButton("Reset Points");
        resetButton.addActionListener(e -> {
            try {
                localState.clearElements();
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            repaint();
        });
        add(resetButton, BorderLayout.SOUTH);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                requestFocusInWindow();
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    if (!typing) {
                        // Start drawing a line
                        switch (drawTypes.getSelectedType()) {
                            case "Freehand":
                                tempDrawingItem = new freehandLine(new ArrayList<>(), colours.getColour(), 3, STROKE);
                                break;
                            case "Rectangle":
                                tempDrawingItem = new rectangle(new ArrayList<>(), colours.getColour(), 3, RECTANGLE);
                                break;
                            case "Ellipse":
                                tempDrawingItem = new ellipse(new ArrayList<>(), colours.getColour(), 3, ELLIPSE);
                                break;
                            case "Square":
                                tempDrawingItem = new square(new ArrayList<>(), colours.getColour(), 3, SQUARE);
                                break;
                            case "Circle":
                                tempDrawingItem = new circle(new ArrayList<>(), colours.getColour(), 3, CIRCLE);
                                break;
                            case "Line":
                                tempDrawingItem = new line(new ArrayList<>(), colours.getColour(), 3, LINE);
                                break;
                            case "Text":
                                tempDrawingItem = new textRender(new ArrayList<>(), colours.getColour(), 3, TEXT);
                                typing = true;
                                break;
                        }
                    } else {
                        tempDrawingItem.updateDrawing(evt.getPoint());
                    }
                    tempDrawingItem.updateDrawing(evt.getPoint());
                    drawing = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    tempDrawingItem.updateDrawing(evt.getPoint());
                    drawing = false;

                    if (!typing) {
                        addRenderElementToRemote(tempDrawingItem);
                        tempDrawingItem = null;
                    }
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

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key recorded");
                if (typing) {
                    int keyCode = e.getKeyCode();
                    if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
                        char c = (char) (keyCode + (e.isShiftDown() ? 0 : 32));
                        tempDrawingItem.addCharacter(c);
                    } else if (keyCode == KeyEvent.VK_SPACE) {
                        tempDrawingItem.addCharacter(' ');
                    } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
                        tempDrawingItem.removeCharacter();
                    } else if (keyCode == KeyEvent.VK_ENTER) {
                        typing = false;
                        addRenderElementToRemote(tempDrawingItem);
                        tempDrawingItem = null;
                    }
                }
            }
        });

        timer = new Timer(TIME_DELAY, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (typing && !drawTypes.getSelectedType().equals("Text")) {
            typing = false;
            tempDrawingItem = null;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

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
                case SQUARE:
                    localState.addElement(new square(renderable));
                    break;
                case CIRCLE:
                    localState.addElement(new circle(renderable));
                    break;
                case LINE:
                    localState.addElement(new line(renderable));
                    break;
                case TEXT:
                    localState.addElement(new textRender((textRender) renderable));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
