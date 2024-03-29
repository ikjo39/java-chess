package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.Set;

public abstract class Piece {
    protected final Side side;

    protected Piece(final Side side) {
        this.side = side;
    }

    public abstract boolean isKing();

    public abstract boolean isPawn();

    public abstract double getPoint();

    protected abstract Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessPosition target,
                                                         final ChessBoard chessBoard);

    protected abstract Set<Direction> availableDirections();

    public boolean canMove(final ChessPosition source, final ChessPosition target, final ChessBoard chessBoard) {
        return calculatePaths(source, target, chessBoard).contains(target);
    }

    public boolean isEmpty() {
        return this.side.isEmpty();
    }

    public boolean isSameSide(final Piece other) {
        return this.side == other.side;
    }

    public boolean isSameSide(final Side other) {
        return this.side == other;
    }

    public boolean isEnemy(final Side other) {
        return this.side.isEnemy(other);
    }

    public Piece catchTargetPiece(final Piece targetPiece) {
        if (isEnemy(targetPiece)) {
            return new Empty();
        }
        return targetPiece;
    }

    private boolean isEnemy(final Piece other) {
        return this.side.isEnemy(other.side);
    }

    public Side getSide() {
        return side;
    }
}
