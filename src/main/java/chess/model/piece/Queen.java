package chess.model.piece;

import static chess.model.position.Direction.DOWN;
import static chess.model.position.Direction.DOWN_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT;
import static chess.model.position.Direction.LEFT;
import static chess.model.position.Direction.RIGHT;
import static chess.model.position.Direction.UP;
import static chess.model.position.Direction.UP_LEFT;
import static chess.model.position.Direction.UP_RIGHT;

import chess.model.board.Point;
import chess.model.position.Direction;
import java.util.Set;

public class Queen extends JumpingPiece {

    private static final int QUEEN_POINT = 9;

    public Queen(final Side side) {
        super(side);
    }

    @Override
    public Point getPoint() {
        return Point.from(QUEEN_POINT);
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(
                UP, DOWN, LEFT, RIGHT,
                UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
        );
    }
}
