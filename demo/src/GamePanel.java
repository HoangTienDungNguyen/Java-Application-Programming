import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.im.InputContext;
import java.util.Locale;

/**
 * The GamePanel class represents the visual aspect of the Battleship game.
 * It handles user interactions and displays the game state.
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    public enum GameState { PlacingShips, FiringShots, GameOver }

    private StatusPanel statusPanel;
    private SelectionGrid computer;
    private SelectionGrid player;
    private BattleshipAI aiController;
    private JTextArea chatDisplayArea;
    private JTextField chatInputField;

    private Ship placingShip;
    private Position tempPlacingPosition;
    private int placingShipIndex;
    private GameState gameState;
    public static boolean debugModeActive;
    private Image backgroundImage;
    private String currentLanguage;
    private int difficultyLevel;
    private GameController controller;

    /**
     * Constructs the game panel, setting up the UI elements and initial state.
     */
    public GamePanel() {
        // Load the background image
        backgroundImage = new ImageIcon("assets/background.png").getImage();

        // Adjust these values to position the grids closer to the center of the left half
        int gridX = 200; // X position for grids
        int computerGridY = 100; // Y position for the computer grid
        int playerGridY = computerGridY + SelectionGrid.CELL_SIZE * SelectionGrid.GRID_HEIGHT + 50; // Y position for the player grid

        computer = new SelectionGrid(gridX, computerGridY);
        player = new SelectionGrid(gridX, playerGridY);

        setPreferredSize(new Dimension(1920, 1080));
        setLayout(null);  // Use null layout for custom positioning

        // Position the status panel to the right side of the screen and make it bigger
        int statusPanelX = 1000; // Adjust the X position for the right side
        int statusPanelY = 200; // Adjust the Y position as needed
        int statusPanelWidth = 450; // New width for the status panel
        int statusPanelHeight = 60; // New height for the status panel

        statusPanel = new StatusPanel(new Position(statusPanelX, statusPanelY), statusPanelWidth, statusPanelHeight);

        // Chat area components
        chatDisplayArea = new JTextArea();
        chatDisplayArea.setEditable(false);
        chatDisplayArea.setBackground(new Color(194, 178, 128));
        JScrollPane chatScrollPane = new JScrollPane(chatDisplayArea);
        chatScrollPane.setBounds(statusPanelX, statusPanelY + statusPanelHeight + 10, statusPanelWidth, 300);
        add(chatScrollPane);

        chatInputField = new JTextField();
        chatInputField.setBackground(new Color(229, 218, 191));
        chatInputField.setBounds(statusPanelX, statusPanelY + statusPanelHeight + 320, statusPanelWidth, 30);
        chatInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatInputField.getText();
                if (!message.trim().isEmpty()) {
                    chatDisplayArea.append("You: " + message + "\n");
                    chatInputField.setText("");
                }
            }
        });
        add(chatInputField);

        setupKeyBindings();

        addMouseListener(this);
        addMouseMotionListener(this);

        currentLanguage = "en"; // Default language
    }

    /**
     * Sets the controller for this game panel.
     *
     * @param controller The GameController instance.
     */
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Gets the controller for this game panel.
     *
     * @return The GameController instance.
     */
    public GameController getController() {
        return controller;
    }

    /**
     * Sets up key bindings for rotating ships and restarting the game.
     */
    private void setupKeyBindings() {
        // Rotate ship with Z key
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "rotateShip");
        getActionMap().put("rotateShip", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameState == GameState.PlacingShips && !chatInputField.isFocusOwner()) {
                    placingShip.toggleSideways();
                    controller.updateShipPlacement(tempPlacingPosition);
                    repaint();
                }
            }
        });

        // Restart game with R key
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0), "restartGame");
        getActionMap().put("restartGame", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.restartGame();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        computer.paint(g);
        player.paint(g);
        if (gameState == GameState.PlacingShips) {
            placingShip.paint(g);
        }
        statusPanel.paint(g);
    }

    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        } else if (keyCode == KeyEvent.VK_D) {
            debugModeActive = !debugModeActive;
        }
        repaint();
    }

    public void restart() {
        computer.reset();
        player.reset();
        player.setShowShips(true);
        aiController.reset();
        tempPlacingPosition = new Position(0, 0);
        placingShip = new Ship(new Position(0, 0),
                               new Position(player.getPosition().x, player.getPosition().y),
                               SelectionGrid.BOAT_SIZES[0], true);
        placingShipIndex = 0;
        controller.updateShipPlacement(tempPlacingPosition);
        computer.populateShips();
        debugModeActive = false;
        statusPanel.reset();
        gameState = GameState.PlacingShips;
    }

    public void updateLanguage(String language) {
        this.currentLanguage = language;
        if (statusPanel != null) {
            statusPanel.updateLanguage(language);
        }
        InputContext inputContext = chatInputField.getInputContext();
        switch (language) {
            case "fr":
                inputContext.selectInputMethod(new Locale("fr", "FR"));
                break;
            case "vi":
                inputContext.selectInputMethod(new Locale("vi", "VN"));
                break;
            default:
                inputContext.selectInputMethod(new Locale("en", "US"));
                break;
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        controller.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        controller.mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}

    public void setPlacingShip(Ship placingShip) {
        this.placingShip = placingShip;
    }

    public void setTempPlacingPosition(Position tempPlacingPosition) {
        this.tempPlacingPosition = tempPlacingPosition;
    }

    public void setPlacingShipIndex(int placingShipIndex) {
        this.placingShipIndex = placingShipIndex;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public JTextField getChatInputField() {
        return chatInputField;
    }

    public SelectionGrid getPlayerGrid() {
        return player;
    }

    public SelectionGrid getComputerGrid() {
        return computer;
    }

    public StatusPanel getStatusPanel() {
        return statusPanel;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Ship getPlacingShip() {
        return placingShip;
    }

    public Position getTempPlacingPosition() {
        return tempPlacingPosition;
    }

    public int getPlacingShipIndex() {
        return placingShipIndex;
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }
}
