package com.unimelb;
import com.unimelb.renderElements.*;

import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

import static com.unimelb.renderElements.renderTypes.*;

public class whiteboard extends JPanel implements ActionListener {

    private static final int TIME_DELAY = 16;

    private IRenderable tempDrawingItem;
    private IWhiteboardState localState;
    private boolean drawing;
    private boolean typing;
    private Timer timer;
    private ArrayList<String> testArray = new ArrayList<>();
    private ArrayList<String> connectedUserList;
    private String username;

    private colourDropdownPanel colours;
    private drawingTypeDropdownPanel drawTypes;
    private strokeWidthDropdownPanel strokePanel;
    private chatPanel chatPanel;


    public whiteboard(IWhiteboardState whiteboardState) {
        String[] usernamePassword = usernamePasswordInput();
        username = usernamePassword[0];
        try {
            checkPassword(whiteboardState, usernamePassword[1]);
            checkBan(whiteboardState);
            connectedUserList = whiteboardState.getConnectedUsers();
        } catch (Exception exception) {
            RMIDeadShutdown();
        }
        if (username == null || username.equals("")){
            JOptionPane.showMessageDialog(this, "Please try again with valid username");
            System.exit(0);
        } else if (connectedUserList.contains(username)){
            JOptionPane.showMessageDialog(this, "Username taken please try again");
            System.exit(0);
        } else {
            try {
                whiteboardState.addNewUser(username);
            } catch (Exception exception) {
                RMIDeadShutdown();
            }
        }

        drawing = false;
        typing = false;
        this.localState = whiteboardState;
        this.setFocusable(true);


        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        this.colours = new colourDropdownPanel();
        this.strokePanel = new strokeWidthDropdownPanel();
        this.drawTypes = new drawingTypeDropdownPanel();
        this.chatPanel = new chatPanel(whiteboardState, username);

        JPanel topControlPanel = new JPanel();
        topControlPanel.setLayout(new BoxLayout(topControlPanel, BoxLayout.X_AXIS));
        topControlPanel.add(colours);
        topControlPanel.add(strokePanel);
        topControlPanel.add(drawTypes);

        JPanel chatWindow = new JPanel();
        chatWindow.add(chatPanel);

        add(topControlPanel, BorderLayout.NORTH);
        add(chatWindow, BorderLayout.WEST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                requestFocusInWindow();
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    if (!typing) {
                        switch (drawTypes.getSelectedType()) {
                            case "Freehand":
                                tempDrawingItem = new freehandLine(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), STROKE);
                                break;
                            case "Rectangle":
                                tempDrawingItem = new rectangle(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), RECTANGLE);
                                break;
                            case "Ellipse":
                                tempDrawingItem = new ellipse(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), ELLIPSE);
                                break;
                            case "Square":
                                tempDrawingItem = new square(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), SQUARE);
                                break;
                            case "Circle":
                                tempDrawingItem = new circle(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), CIRCLE);
                                break;
                            case "Line":
                                tempDrawingItem = new line(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), LINE);
                                break;
                            case "Text":
                                tempDrawingItem = new textRender(new ArrayList<>(), colours.getColour(), strokePanel.getSelectedWidth(), TEXT);
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
        checkBan(localState);
        RMIheartbeat();
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
            timer.stop();
            RMIDeadShutdown();
        }
        if (Objects.nonNull(tempDrawingItem)) {
            tempDrawingItem.renderSelf(g2d);
        }
        g2d.setStroke(new BasicStroke(3));
    }

    private void renderElements(Graphics2D g2d, ArrayList<IRenderable> elements) {
        try {
            for (int i=0; i < elements.size(); i++) {
                IRenderable el = elements.get(i);
                el.renderSelf(g2d);
            }
        } catch (Exception exception) {
            timer.stop();
            RMIDeadShutdown();
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
            timer.stop();
            RMIDeadShutdown();
        }
    }

    private String[] usernamePasswordInput() {
        JTextField usernameField = new JTextField(20);
        JTextField passwordField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(new JLabel("Blank for no password"));
        panel.add(passwordField);

        int result = JOptionPane.showOptionDialog(null, panel, "Input Username & Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            return new String[]{username, password};
        }

        return null;
    }

    public String getUsername() {
        return username;
    }

    private void RMIheartbeat() {
        try {
            localState.heartbeat();
        } catch (RemoteException exception) {
            timer.stop();
            RMIDeadShutdown();
        }
    }

    private void RMIDeadShutdown() {
        JOptionPane.showMessageDialog(this, "Connection with Server lost");
        System.exit(0);
    }

    private void checkBan(IWhiteboardState state) {
        try {
            if (state.isOnBlackList(username)) {

                if (timer != null) {
                    timer.stop();
                }
                JOptionPane.showMessageDialog(this, "You have been removed from this server");

                System.exit(0);
            }
        } catch (Exception e) {
            timer.stop();
            RMIDeadShutdown();
        }
    }

    private void checkPassword(IWhiteboardState state, String password) {
        try {
            if (!state.checkPassword(password)){
                JOptionPane.showMessageDialog(this, "Incorrect Password");
                System.exit(0);
            }
        } catch (Exception e) {
            timer.stop();
            RMIDeadShutdown();
        }
    }
}
