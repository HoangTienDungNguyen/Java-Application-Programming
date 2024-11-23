import java.awt.*;

/**
 * The StatusPanel class defines a simple text panel to show a top and bottom line of text.
 * Some of these are already defined in the class, and it provides additional methods to set the messages to custom values.
 */
public class StatusPanel extends Rectangle {
    private final Font font = new Font("Calibri", Font.BOLD, 20);
    private String placingShipLine1;
    private String placingShipLine2;
    private String gameOverLossLine;
    private String gameOverWinLine;
    private String gameOverBottomLine;
    private String topLine;
    private String bottomLine;

    /**
     * Constructs the StatusPanel with its position and size.
     *
     * @param position The position of the panel.
     * @param width The width of the panel.
     * @param height The height of the panel.
     */
    public StatusPanel(Position position, int width, int height) {
        super(position, width, height);
        updateLanguage("en");
    }

    /**
     * Resets the status panel to show placing ship instructions.
     */
    public void reset() {
        topLine = placingShipLine1;
        bottomLine = placingShipLine2;
    }

    /**
     * Shows the game over message based on whether the player won or lost.
     *
     * @param playerWon True if the player won, false if lost.
     */
    public void showGameOver(boolean playerWon) {
        topLine = (playerWon) ? gameOverWinLine : gameOverLossLine;
        bottomLine = gameOverBottomLine;
    }

    /**
     * Sets the top line of the status panel.
     *
     * @param message The message to set as the top line.
     */
    public void setTopLine(String message) {
        topLine = message;
    }

    /**
     * Sets the bottom line of the status panel.
     *
     * @param message The message to set as the bottom line.
     */
    public void setBottomLine(String message) {
        bottomLine = message;
    }

    /**
     * Paints the status panel.
     *
     * @param g The Graphics object for drawing.
     */
    public void paint(Graphics g) {
        g.setColor(new Color(112, 70, 44));
        g.fillRect(position.x, position.y, width, height);
        g.setColor(Color.BLACK);
        g.setFont(font);
        int strWidth = g.getFontMetrics().stringWidth(topLine);
        g.drawString(topLine, position.x + width / 2 - strWidth / 2, position.y + 20);
        strWidth = g.getFontMetrics().stringWidth(bottomLine);
        g.drawString(bottomLine, position.x + width / 2 - strWidth / 2, position.y + 40);
    }

    /**
     * Updates the language of the status panel based on the specified language.
     *
     * @param language The language to set.
     */
    public void updateLanguage(String language) {
        placingShipLine1 = Translations.getTranslation(language, "placeShips");
        placingShipLine2 = Translations.getTranslation(language, "rotate");
        gameOverLossLine = Translations.getTranslation(language, "gameOverLoss");
        gameOverWinLine = Translations.getTranslation(language, "gameOverWin");
        gameOverBottomLine = Translations.getTranslation(language, "restart");
        reset();
    }
}
