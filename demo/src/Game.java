import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The main class that initializes and starts the Battleship game.
 * It sets up the game window and handles keyboard input.
 */
public class Game implements KeyListener {
    private GamePanel gamePanel;
    private GameController controller;

    /**
     * Constructs the game by initializing the game panel, controller, and setting up the GUI.
     */
    public Game() {
        // Prompt the user to select an AI difficulty level
        String[] options = {"Easy", "Medium", "Hard"};
        String message = "Easy will make moves entirely randomly,\nMedium will focus on areas where it finds ships,"
                + "\nand Hard will make smarter choices over Medium.";
        int difficultyChoice = JOptionPane.showOptionDialog(null, message, "Choose an AI Difficulty",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        // Set up the game window
        JFrame frame = new JFrame("Battleship Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Initialize the game panel and controller
        gamePanel = new GamePanel();
        controller = new GameController(gamePanel, difficultyChoice);
        gamePanel.setController(controller);

        frame.getContentPane().add(gamePanel);

        frame.addKeyListener(this);
        frame.setJMenuBar(new Menu(gamePanel));
        frame.pack();
        frame.setVisible(true);

        // Start the game by calling restart
        controller.restartGame();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        controller.handleInput(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * The main method to start the game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }
}
