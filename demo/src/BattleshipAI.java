import java.util.ArrayList;
import java.util.List;

/**
 * Template class to be extended to provide actual AI behavior.
 * The selectMove() method is used to determine which move should be applied.
 * It should be overridden by classes to implement real functionality.
 */
public abstract class BattleshipAI {
    protected SelectionGrid playerGrid;
    protected List<Position> validMoves;

    public BattleshipAI(SelectionGrid playerGrid) {
        this.playerGrid = playerGrid;
        createValidMoveList();
    }

    public abstract Position selectMove();

    public void reset() {
        createValidMoveList();
    }

    private void createValidMoveList() {
        validMoves = new ArrayList<>();
        for (int x = 0; x < SelectionGrid.GRID_WIDTH; x++) {
            for (int y = 0; y < SelectionGrid.GRID_HEIGHT; y++) {
                validMoves.add(new Position(x, y));
            }
        }
    }
}
