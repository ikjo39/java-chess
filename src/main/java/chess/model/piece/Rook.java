package chess.model.piece;

import static chess.model.position.Direction.DOWN;
import static chess.model.position.Direction.LEFT;
import static chess.model.position.Direction.RIGHT;
import static chess.model.position.Direction.UP;

import chess.model.board.Point;
import chess.model.position.Direction;
import java.util.Set;

public class Rook extends JumpingPiece {
    private static final int ROOK_POINT = 5;

    public Rook(Side side) {
        super(side);
    }

    @Override
    public Point getPoint() {
        return Point.from(ROOK_POINT);
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(UP, DOWN, LEFT, RIGHT);
    }
}
