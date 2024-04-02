package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.Set;

public abstract class Pawn extends Piece {
    private static final int PAWN_POINT = 1;

    protected Pawn(final Side side) {
        super(side);
    }

    protected abstract boolean isPawnInitialPosition(final ChessPosition source);

    protected abstract boolean canMoveVerticalPaths(final ChessPosition source, final ChessBoard chessBoard,
                                                    final Direction direction);

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isPawn() {
        return true;
    }

    @Override
    public Point getPoint() {
        return Point.from(PAWN_POINT);
    }

    @Override
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Set<Direction> directions) {
        directions.stream()
                .filter(direction -> isPawnInitialPosition(source) || !direction.isDoubleForward())
                .forEach(direction -> addPossiblePaths(source, chessBoard, paths, direction));
    }

    @Override
    protected void addPossiblePaths(final ChessPosition source, final ChessBoard chessBoard,
                                    final Set<ChessPosition> paths, final Direction direction) {
        if (canMoveDiagonal(chessBoard, direction, source) || canMoveVertical(direction, chessBoard, source)) {
            paths.add(source.move(direction));
        }
    }

    private boolean canMoveDiagonal(final ChessBoard chessBoard, final Direction direction,
                                    final ChessPosition current) {
        return direction.isDiagonal()
                && current.canMove(direction)
                && chessBoard.isEnemy(current.move(direction), side);
    }

    private boolean canMoveVertical(final Direction direction, final ChessBoard chessBoard,
                                    final ChessPosition source) {
        if (!direction.isVertical()) {
            return false;
        }
        return canMoveVerticalPaths(source, chessBoard, direction);
    }
}
