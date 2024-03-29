package chess.model.piece;

import static chess.model.position.Direction.DOWN;
import static chess.model.position.Direction.DOWN_LEFT;
import static chess.model.position.Direction.DOWN_RIGHT;
import static chess.model.position.Direction.LEFT;
import static chess.model.position.Direction.RIGHT;
import static chess.model.position.Direction.UP;
import static chess.model.position.Direction.UP_LEFT;
import static chess.model.position.Direction.UP_RIGHT;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece {

    private static final int QUEEN_POINT = 9;

    public Queen(final Side side) {
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
    public double getPoint() {
        return QUEEN_POINT;
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
        return Set.of(
                UP, DOWN, LEFT, RIGHT,
                UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
        );
    }
}
