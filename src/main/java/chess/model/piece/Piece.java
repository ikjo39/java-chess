package chess.model.piece;

import chess.model.board.ChessBoard;
import chess.model.board.Point;
import chess.model.position.ChessPosition;
import chess.model.position.Direction;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece {
    protected final Side side;

    protected Piece(final Side side) {
        this.side = side;
    }

    public abstract boolean isKing();

    public abstract boolean isPawn();

    public abstract Point getPoint();

    protected abstract Set<Direction> availableDirections();

    protected abstract void addPossiblePaths(final ChessPosition source,
                                             final ChessBoard chessBoard,
                                             final Set<ChessPosition> paths,
                                             final Set<Direction> directions);

    protected abstract void addPossiblePaths(final ChessPosition source,
                                             final ChessBoard chessBoard,
                                             final Set<ChessPosition> paths,
                                             final Direction direction);

    public boolean canMove(final ChessPosition source,
                           final ChessPosition target,
                           final ChessBoard chessBoard) {
        return calculatePaths(source, chessBoard).contains(target);
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

    protected Set<ChessPosition> calculatePaths(final ChessPosition source, final ChessBoard chessBoard) {
        final Set<ChessPosition> paths = new HashSet<>();
        final Set<Direction> directions = availableDirections();
        addPossiblePaths(source, chessBoard, paths, directions);
        return paths;
    }

    public Side getEnemy() {
        return this.side.getEnemy();
    }

    public Side getSide() {
        return side;
    }
}
