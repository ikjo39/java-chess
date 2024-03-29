package chess.model.piece;

import static chess.model.position.Direction.DOWN_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT;
import static chess.model.position.Direction.UP_LEFT;
import static chess.model.position.Direction.UP_RIGHT;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    private static final double BISHOP_POINT = 3;

    public Bishop(final Side side) {
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
        return Point.from(BISHOP_POINT);
    }

    @Override
    protected Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessPosition target,
                                                final ChessBoard chessBoard) {
        final Set<ChessPosition> paths = new HashSet<>();
        Set<Direction> directions = availableDirections();
        for (final Direction direction : directions) {
            ChessPosition current = source;
            while (current.canMove(direction)) {
                current = current.move(direction);
                if (chessBoard.isSameSide(current, side)) {
                    break;
                }
                paths.add(current);
                if (chessBoard.isEnemy(current, side)) {
                    break;
                }
            }
        }
        return paths;
    }

    @Override
    protected Set<Direction> availableDirections() {
        return Set.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    }
}
