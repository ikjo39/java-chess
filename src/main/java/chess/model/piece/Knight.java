package chess.model.piece;

import static chess.model.position.Direction.DOWN_DOWN_LEFT;
import static chess.model.position.Direction.DOWN_DOWN_RIGHT;
import static chess.model.position.Direction.DOWN_LEFT_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT_RIGHT;
import static chess.model.position.Direction.UP_LEFT_LEFT;
import static chess.model.position.Direction.UP_RIGHT_RIGHT;
import static chess.model.position.Direction.UP_UP_LEFT;
import static chess.model.position.Direction.UP_UP_RIGHT;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece {
    private static final double KNIGHT_POINT = 2.5;

    public Knight(final Side side) {
        super(side);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public Point getPoint() {
        return Point.from(KNIGHT_POINT);
    }

    @Override
    protected Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessPosition target,
                                                final ChessBoard chessBoard) {
        final Set<ChessPosition> paths = new HashSet<>();
        Set<Direction> directions = availableDirections();
        for (final Direction direction : directions) {
            ChessPosition current = source;
            if (current.canMove(direction) && !chessBoard.isSameSide(current.move(direction), side)) {
                paths.add(current.move(direction));
            }
        }
        return paths;
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(
                UP_UP_LEFT, UP_UP_RIGHT, UP_LEFT_LEFT, UP_RIGHT_RIGHT,
                DOWN_DOWN_LEFT, DOWN_DOWN_RIGHT, DOWN_LEFT_LEFT, DOWN_RIGHT_RIGHT
        );
    }
}
