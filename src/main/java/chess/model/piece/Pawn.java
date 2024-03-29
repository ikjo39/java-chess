package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public abstract class Pawn extends Piece {
    private static final int PAWN_POINT = 1;

    protected Pawn(final Side side) {
        super(side);
    }

    protected abstract boolean isPawnInitialPosition(final ChessPosition source);

    protected abstract boolean canMoveVerticalPaths(final Direction direction, final ChessBoard chessBoard,
                                                    final ChessPosition source);

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public double getPoint() {
        return PAWN_POINT;
    }

    @Override
    protected Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessPosition target,
                                                final ChessBoard chessBoard) {
        final Set<ChessPosition> paths = new HashSet<>();
        Set<Direction> directions = availableDirections();
        for (final Direction direction : directions) {
            ChessPosition current = source;
            if (!isPawnInitialPosition(current) && direction.isDoubleForward()) {
                continue;
            }
            if (canMoveDiagonal(chessBoard, direction, current) || canMoveVertical(direction, chessBoard, current)) {
                paths.add(current.move(direction));
            }
        }
        return paths;
    }

    private boolean canMoveDiagonal(final ChessBoard chessBoard, final Direction direction,
                                    final ChessPosition current) {
        return direction.isDiagonal() && current.canMove(direction) && chessBoard.isEnemy(current.move(direction), side);
    }

    private boolean canMoveVertical(final Direction direction, final ChessBoard chessBoard,
                                    final ChessPosition source) {
        if (!direction.isVertical()) {
            return false;
        }
        return canMoveVerticalPaths(direction, chessBoard, source);
    }
}
