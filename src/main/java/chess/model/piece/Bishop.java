package chess.model.piece;

import static chess.model.position.Direction.DOWN_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT;
import static chess.model.position.Direction.UP_LEFT;
import static chess.model.position.Direction.UP_RIGHT;

import chess.model.board.Point;
import chess.model.position.Direction;
import java.util.Set;

public class Bishop extends JumpingPiece {

    private static final double BISHOP_POINT = 3;

    public Bishop(final Side side) {
        super(side);
    }

    @Override
    public Point getPoint() {
        return Point.from(BISHOP_POINT);
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    }
}
