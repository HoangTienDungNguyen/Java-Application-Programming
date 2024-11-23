import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;

/**
 * The GameController class handles the input and game logic for the Battleship game.
 * It interacts with the model (game state) and updates the view (game panel) accordingly.
 */
public class GameController implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;
    private SelectionGrid playerGrid;
    private SelectionGrid computerGrid;
    private BattleshipAI aiController;
    private int difficultyLevel;

    /**
     * Constructs a GameController with a reference to the game panel and sets the AI difficulty level.
     *
     * @param gamePanel The game panel (view).
     * @param difficultyLevel The difficulty level for the AI.
     */
    public GameController(GamePanel gamePanel, int difficultyLevel) {
        this.gamePanel = gamePanel;
        this.difficultyLevel = difficultyLevel;
        this.playerGrid = gamePanel.getPlayerGrid();
        this.computerGrid = gamePanel.getComputerGrid();
        initAI();
    }

    /**
     * Returns the current difficulty level of the AI.
     *
     * @return The AI difficulty level.
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Returns the current game state.
     *
     * @return The current game state.
     */
    public GamePanel.GameState getGameState() {
        return gamePanel.getGameState();
    }

    /**
     * Changes the difficulty level of the AI and restarts the game.
     *
     * @param level The new difficulty level.
     */
    public void changeDifficulty(int level) {
        this.difficultyLevel = level;
        initAI();
        restartGame();
    }

    /**
     * Initializes the AI based on the current difficulty level.
     */
    public void initAI() {
        if (difficultyLevel == 0) {
            aiController = new SimpleRandomAI(playerGrid);
        } else {
            aiController = new SmarterAI(playerGrid, difficultyLevel == 2, difficultyLevel == 2);
        }
    }

    /**
     * Restarts the game by resetting grids, AI, and other game states.
     */
    public void restartGame() {
        computerGrid.reset();
        playerGrid.reset();
        playerGrid.setShowShips(true);
        aiController.reset();
        gamePanel.setTempPlacingPosition(new Position(0, 0));
        gamePanel.setPlacingShip(new Ship(new Position(0, 0), new Position(playerGrid.getPosition().x, playerGrid.getPosition().y), SelectionGrid.BOAT_SIZES[0], true));
        gamePanel.setPlacingShipIndex(0);
        updateShipPlacement(gamePanel.getTempPlacingPosition());
        computerGrid.populateShips();
        GamePanel.debugModeActive = false;
        gamePanel.getStatusPanel().reset();
        gamePanel.setGameState(GamePanel.GameState.PlacingShips);
    }

    /**
     * Updates the ship placement position and validates the placement.
     *
     * @param targetPos The target position for ship placement.
     */
    public void updateShipPlacement(Position targetPos) {
        Ship placingShip = gamePanel.getPlacingShip();
        if (placingShip.isSideways()) {
            targetPos.x = Math.min(targetPos.x, SelectionGrid.GRID_WIDTH - SelectionGrid.BOAT_SIZES[gamePanel.getPlacingShipIndex()]);
        } else {
            targetPos.y = Math.min(targetPos.y, SelectionGrid.GRID_HEIGHT - SelectionGrid.BOAT_SIZES[gamePanel.getPlacingShipIndex()]);
        }
        placingShip.setDrawPosition(new Position(targetPos),
                new Position(playerGrid.getPosition().x + targetPos.x * SelectionGrid.CELL_SIZE,
                        playerGrid.getPosition().y + targetPos.y * SelectionGrid.CELL_SIZE));
        gamePanel.setTempPlacingPosition(targetPos);
        if (playerGrid.canPlaceShipAt(gamePanel.getTempPlacingPosition().x, gamePanel.getTempPlacingPosition().y,
                SelectionGrid.BOAT_SIZES[gamePanel.getPlacingShipIndex()], placingShip.isSideways())) {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Valid);
        } else {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Invalid);
        }
    }

    /**
     * Handles keyboard input for the game.
     *
     * @param keyCode The code of the pressed key.
     */
    public void handleInput(int keyCode) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        } else if (keyCode == KeyEvent.VK_D) {
            GamePanel.debugModeActive = !GamePanel.debugModeActive;
        }
        gamePanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position mousePosition = new Position(e.getX(), e.getY());
        if (gamePanel.getGameState() == GamePanel.GameState.PlacingShips && playerGrid.isPositionInside(mousePosition)) {
            tryPlaceShip(mousePosition);
        } else if (gamePanel.getGameState() == GamePanel.GameState.FiringShots && computerGrid.isPositionInside(mousePosition)) {
            tryFireAtComputer(mousePosition);
        }
        gamePanel.repaint();
    }

    /**
     * Attempts to place a ship at the specified mouse position.
     *
     * @param mousePosition The position of the mouse click.
     */
    private void tryPlaceShip(Position mousePosition) {
        Position targetPosition = playerGrid.getPositionInGrid(mousePosition.x, mousePosition.y);
        updateShipPlacement(targetPosition);
        if (playerGrid.canPlaceShipAt(targetPosition.x, targetPosition.y,
                SelectionGrid.BOAT_SIZES[gamePanel.getPlacingShipIndex()], gamePanel.getPlacingShip().isSideways())) {
            placeShip(targetPosition);
        }
    }

    /**
     * Places the ship at the specified target position.
     *
     * @param targetPosition The target position for ship placement.
     */
    private void placeShip(Position targetPosition) {
        Ship placingShip = gamePanel.getPlacingShip();
        placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Placed);
        playerGrid.placeShip(placingShip, gamePanel.getTempPlacingPosition().x, gamePanel.getTempPlacingPosition().y);
        gamePanel.setPlacingShipIndex(gamePanel.getPlacingShipIndex() + 1);
        if (gamePanel.getPlacingShipIndex() < SelectionGrid.BOAT_SIZES.length) {
            gamePanel.setPlacingShip(new Ship(new Position(targetPosition.x, targetPosition.y),
                    new Position(playerGrid.getPosition().x + targetPosition.x * SelectionGrid.CELL_SIZE,
                            playerGrid.getPosition().y + targetPosition.y * SelectionGrid.CELL_SIZE),
                    SelectionGrid.BOAT_SIZES[gamePanel.getPlacingShipIndex()], true));
            updateShipPlacement(gamePanel.getTempPlacingPosition());
        } else {
            gamePanel.setGameState(GamePanel.GameState.FiringShots);
            gamePanel.getStatusPanel().setTopLine(Translations.getTranslation(gamePanel.getCurrentLanguage(), "attackComputer"));
            gamePanel.getStatusPanel().setBottomLine(Translations.getTranslation(gamePanel.getCurrentLanguage(), "destroyAllShipsToWin"));
        }
    }

    /**
     * Attempts to fire at the computer's grid based on the mouse position.
     *
     * @param mousePosition The position of the mouse click.
     */
    private void tryFireAtComputer(Position mousePosition) {
        Position targetPosition = computerGrid.getPositionInGrid(mousePosition.x, mousePosition.y);
        if (!computerGrid.isPositionMarked(targetPosition)) {
            doPlayerTurn(targetPosition);
            if (!computerGrid.areAllShipsDestroyed()) {
                doAITurn();
            }
        }
    }

    /**
     * Executes the player's turn by marking the target position.
     *
     * @param targetPosition The target position to mark.
     */
    private void doPlayerTurn(Position targetPosition) {
        boolean hit = computerGrid.markPosition(targetPosition);
        String hitMiss = hit ? Translations.getTranslation(gamePanel.getCurrentLanguage(), "hit") : Translations.getTranslation(gamePanel.getCurrentLanguage(), "missed");
        String destroyed = "";
        if (hit && computerGrid.getMarkerAtPosition(targetPosition).getAssociatedShip().isDestroyed()) {
            destroyed = Translations.getTranslation(gamePanel.getCurrentLanguage(), "destroyed");
        }
        gamePanel.getStatusPanel().setTopLine(String.format(Translations.getTranslation(gamePanel.getCurrentLanguage(), "playerHitMiss"), hitMiss, targetPosition, destroyed));
        if (computerGrid.areAllShipsDestroyed()) {
            gamePanel.setGameState(GamePanel.GameState.GameOver);
            gamePanel.getStatusPanel().showGameOver(true);
        }
    }

    /**
     * Executes the AI's turn by selecting a move and marking the target position.
     */
    private void doAITurn() {
        Position aiMove = aiController.selectMove();
        boolean hit = playerGrid.markPosition(aiMove);
        String hitMiss = hit ? Translations.getTranslation(gamePanel.getCurrentLanguage(), "hit") : Translations.getTranslation(gamePanel.getCurrentLanguage(), "missed");
        String destroyed = "";
        if (hit && playerGrid.getMarkerAtPosition(aiMove).getAssociatedShip().isDestroyed()) {
            destroyed = Translations.getTranslation(gamePanel.getCurrentLanguage(), "destroyed");
        }
        gamePanel.getStatusPanel().setBottomLine(String.format(Translations.getTranslation(gamePanel.getCurrentLanguage(), "computerHitMiss"), hitMiss, aiMove, destroyed));
        if (playerGrid.areAllShipsDestroyed()) {
            gamePanel.setGameState(GamePanel.GameState.GameOver);
            gamePanel.getStatusPanel().showGameOver(false);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gamePanel.getGameState() != GamePanel.GameState.PlacingShips) return;
        tryMovePlacingShip(new Position(e.getX(), e.getY()));
        gamePanel.repaint();
    }

    /**
     * Attempts to move the ship being placed to the specified mouse position.
     *
     * @param mousePosition The position of the mouse.
     */
    private void tryMovePlacingShip(Position mousePosition) {
        if (playerGrid.isPositionInside(mousePosition)) {
            Position targetPos = playerGrid.getPositionInGrid(mousePosition.x, mousePosition.y);
            updateShipPlacement(targetPos);
        }
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
}
