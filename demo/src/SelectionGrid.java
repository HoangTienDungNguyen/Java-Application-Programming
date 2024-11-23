import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The SelectionGrid class represents the grid for placing and attacking ships.
 * It handles drawing the grid, placing ships, and marking hits/misses.
 */
public class SelectionGrid extends Rectangle {
    public static final int CELL_SIZE = 30;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 10;
    public static final int[] BOAT_SIZES = {5, 4, 3, 3, 2};

    private Marker[][] markers = new Marker[GRID_WIDTH][GRID_HEIGHT];
    private List<Ship> ships;
    private Random rand;
    private boolean showShips;
    private boolean allShipsDestroyed;

    /**
     * Constructs a SelectionGrid at the specified position.
     *
     * @param x The x-coordinate of the grid's position.
     * @param y The y-coordinate of the grid's position.
     */
    public SelectionGrid(int x, int y) {
        super(x, y, CELL_SIZE * GRID_WIDTH, CELL_SIZE * GRID_HEIGHT);
        createMarkerGrid();
        ships = new ArrayList<>();
        rand = new Random();
        showShips = false;
    }

    /**
     * Paints the grid, markers, and ships.
     *
     * @param g The Graphics object for painting.
     */
    public void paint(Graphics g) {
        g.setColor(new Color(210, 180, 140));
        g.fillRect(position.x, position.y, width, height);

        for (Ship ship : ships) {
            if (showShips || GamePanel.debugModeActive || ship.isDestroyed()) {
                ship.paint(g);
            }
        }
        drawMarkers(g);
        drawGrid(g);
    }

    /**
     * Sets whether the ships should be shown.
     *
     * @param showShips True to show the ships, false otherwise.
     */
    public void setShowShips(boolean showShips) {
        this.showShips = showShips;
    }

    /**
     * Resets the grid, markers, and ships.
     */
    public void reset() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y].reset();
            }
        }
        ships.clear();
        showShips = false;
        allShipsDestroyed = false;
    }

    /**
     * Marks the specified position and checks if all ships are destroyed.
     *
     * @param posToMark The position to mark.
     * @return True if a ship was hit, false otherwise.
     */
    public boolean markPosition(Position posToMark) {
        markers[posToMark.x][posToMark.y].mark();

        allShipsDestroyed = true;
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                allShipsDestroyed = false;
                break;
            }
        }
        return markers[posToMark.x][posToMark.y].isShip();
    }

    /**
     * Checks if all ships are destroyed.
     *
     * @return True if all ships are destroyed, false otherwise.
     */
    public boolean areAllShipsDestroyed() {
        return allShipsDestroyed;
    }

    /**
     * Checks if the specified position is marked.
     *
     * @param posToTest The position to check.
     * @return True if the position is marked, false otherwise.
     */
    public boolean isPositionMarked(Position posToTest) {
        return markers[posToTest.x][posToTest.y].isMarked();
    }

    /**
     * Gets the marker at the specified position.
     *
     * @param posToSelect The position to get the marker from.
     * @return The marker at the specified position.
     */
    public Marker getMarkerAtPosition(Position posToSelect) {
        return markers[posToSelect.x][posToSelect.y];
    }

    /**
     * Gets the grid position corresponding to the specified mouse coordinates.
     *
     * @param mouseX The x-coordinate of the mouse.
     * @param mouseY The y-coordinate of the mouse.
     * @return The grid position.
     */
    public Position getPositionInGrid(int mouseX, int mouseY) {
        if (!isPositionInside(new Position(mouseX, mouseY))) return new Position(-1, -1);

        return new Position((mouseX - position.x) / CELL_SIZE, (mouseY - position.y) / CELL_SIZE);
    }

    /**
     * Checks if a ship can be placed at the specified position.
     *
     * @param gridX The x-coordinate of the grid position.
     * @param gridY The y-coordinate of the grid position.
     * @param segments The number of segments of the ship.
     * @param sideways True if the ship is sideways, false otherwise.
     * @return True if the ship can be placed, false otherwise.
     */
    public boolean canPlaceShipAt(int gridX, int gridY, int segments, boolean sideways) {
        if (gridX < 0 || gridY < 0) return false;

        if (sideways) {
            if (gridY > GRID_HEIGHT || gridX + segments > GRID_WIDTH) return false;
            for (int x = 0; x < segments; x++) {
                if (markers[gridX + x][gridY].isShip()) return false;
            }
        } else {
            if (gridY + segments > GRID_HEIGHT || gridX > GRID_WIDTH) return false;
            for (int y = 0; y < segments; y++) {
                if (markers[gridX][gridY + y].isShip()) return false;
            }
        }
        return true;
    }

    /**
     * Draws the grid lines.
     *
     * @param g The Graphics object for drawing.
     */
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        int y2 = position.y;
        int y1 = position.y + height;
        for (int x = 0; x <= GRID_WIDTH; x++)
            g.drawLine(position.x + x * CELL_SIZE, y1, position.x + x * CELL_SIZE, y2);

        int x2 = position.x;
        int x1 = position.x + width;
        for (int y = 0; y <= GRID_HEIGHT; y++)
            g.drawLine(x1, position.y + y * CELL_SIZE, x2, position.y + y * CELL_SIZE);
    }

    /**
     * Draws the markers on the grid.
     *
     * @param g The Graphics object for drawing.
     */
    private void drawMarkers(Graphics g) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y].paint(g);
            }
        }
    }

    /**
     * Creates the grid of markers.
     */
    private void createMarkerGrid() {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                markers[x][y] = new Marker(position.x + x * CELL_SIZE, position.y + y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    /**
     * Populates the grid with ships at random positions.
     */
    public void populateShips() {
        ships.clear();
        for (int i = 0; i < BOAT_SIZES.length; i++) {
            boolean sideways = rand.nextBoolean();
            int gridX, gridY;
            do {
                gridX = rand.nextInt(sideways ? GRID_WIDTH - BOAT_SIZES[i] : GRID_WIDTH);
                gridY = rand.nextInt(sideways ? GRID_HEIGHT : GRID_HEIGHT - BOAT_SIZES[i]);
            } while (!canPlaceShipAt(gridX, gridY, BOAT_SIZES[i], sideways));
            placeShip(gridX, gridY, BOAT_SIZES[i], sideways);
        }
    }

    /**
     * Places a ship at the specified grid position.
     *
     * @param gridX The x-coordinate of the grid position.
     * @param gridY The y-coordinate of the grid position.
     * @param segments The number of segments of the ship.
     * @param sideways True if the ship is sideways, false otherwise.
     */
    public void placeShip(int gridX, int gridY, int segments, boolean sideways) {
        placeShip(new Ship(new Position(gridX, gridY),
                new Position(position.x + gridX * CELL_SIZE, position.y + gridY * CELL_SIZE),
                segments, sideways), gridX, gridY);
    }

    /**
     * Places a ship on the grid.
     *
     * @param ship The ship to place.
     * @param gridX The x-coordinate of the grid position.
     * @param gridY The y-coordinate of the grid position.
     */
    public void placeShip(Ship ship, int gridX, int gridY) {
        ships.add(ship);
        if (ship.isSideways()) {
            for (int x = 0; x < ship.getSegments(); x++) {
                markers[gridX + x][gridY].setAsShip(ships.get(ships.size() - 1));
            }
        } else {
            for (int y = 0; y < ship.getSegments(); y++) {
                markers[gridX][gridY + y].setAsShip(ships.get(ships.size() - 1));
            }
        }
    }
}
